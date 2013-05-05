package com.search.index;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.search.DAO.Connect;
import com.search.data.Document;

public class DocumentThread extends Thread {
	private LinkedList<Document> documents;
	private Logger logger=LogManager.getLogger("DocumentThread");
	
	private String SaveDocument_sql() throws Exception, SQLException {
		String sql = "insert into Document(id,rank,url,create_date,store_attributes,index_attributes) values (?,?,?,?,?,?)";
		return sql;
	}
	
	public DocumentThread(LinkedList<Document> documents){
		this.documents=documents;
	}

	public void run() {
		Connection con=null;
		try {
			con = Connect.getConnection();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		Iterator<Document> iterator=documents.iterator();
		while (iterator.hasNext()) {
			try {
				Document document=iterator.next();
				
				PreparedStatement stmt = con.prepareStatement(this.SaveDocument_sql());
				
				logger.info("writing id="+document.getID()+" document");
				
				//序列化
				ByteArrayOutputStream attributes_out=new ByteArrayOutputStream();
				ObjectOutputStream attributes_object=new ObjectOutputStream(attributes_out);
				attributes_object.writeObject(document.getStore_attributes());
				ByteArrayInputStream attributes_in=new ByteArrayInputStream(attributes_out.toByteArray());
				
				//序列化
				ByteArrayOutputStream indexattributes_out=new ByteArrayOutputStream();
				ObjectOutputStream indexattributes_object=new ObjectOutputStream(indexattributes_out);
				indexattributes_object.writeObject(document.getIndex_attributes());
				ByteArrayInputStream indexattributes_in=new ByteArrayInputStream(indexattributes_out.toByteArray());
				
				//写占位符
				stmt.setLong(1, document.getID());
				stmt.setInt(2, document.getRanks());
				stmt.setString(3, document.getUrl());
				stmt.setTimestamp(4, new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
				stmt.setAsciiStream(5,attributes_in);
				stmt.setAsciiStream(6,indexattributes_in);
				stmt.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

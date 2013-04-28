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

import com.search.DAO.Connect;
import com.search.data.Document;

public class DocumentThread extends Thread {
	private LinkedList<Document> documents;
	
	private String SaveDocument_sql() throws Exception, SQLException {
		String sql = "insert into Document(id,rank,create_date,attributes,index_number) values (?,?,?,?,?)";
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
				
				System.out.println("正在写第ID为：" + document.getID()+ "个文件");
				
				//序列化
				ByteArrayOutputStream attributes_out=new ByteArrayOutputStream();
				ObjectOutputStream attributes_object=new ObjectOutputStream(attributes_out);
				attributes_object.writeObject(document.getAllAttributes());
				ByteArrayInputStream attributes_in=new ByteArrayInputStream(attributes_out.toByteArray());
				
				//序列化
				ByteArrayOutputStream indexnumber_out=new ByteArrayOutputStream();
				ObjectOutputStream indexnumber_object=new ObjectOutputStream(indexnumber_out);
				indexnumber_object.writeObject(document.getIndex_number());
				ByteArrayInputStream indexnumber_in=new ByteArrayInputStream(indexnumber_out.toByteArray());
				
				//写占位符
				stmt.setLong(1, document.getID());
				stmt.setInt(2, document.getRanks());
				stmt.setTimestamp(3, new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
				stmt.setAsciiStream(4,attributes_in);
				stmt.setAsciiStream(5, indexnumber_in);
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

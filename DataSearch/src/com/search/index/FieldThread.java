package com.search.index;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.search.DAO.Connect;
import com.search.data.Field;
import com.search.data.IDhandler;

public class FieldThread extends Thread {
	private LinkedList<Field> fields;
	private Logger logger=LogManager.getLogger("FieldThread");
	
	private String SaveField_sql() throws Exception, SQLException {
		String sql = "insert into Field(id,priority,content,attributes) values(?,?,?,?)";
		return sql;
	}
	
	public FieldThread(LinkedList<Field> fields) {
		this.fields = fields;
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
		Iterator<Field> iterator=fields.iterator();
		while (iterator.hasNext()) {
			try {
				String sql = this.SaveField_sql();
				Field field=iterator.next();
				PreparedStatement stmt = con.prepareStatement(sql);

				//序列化
//				ByteArrayInputStream bin = new ByteArrayInputStream(field
//						.getText().getBytes());	
				
				// 序列化
				ByteArrayOutputStream attributes_out=new ByteArrayOutputStream();
				ObjectOutputStream attributes_object=new ObjectOutputStream(attributes_out);
				attributes_object.writeObject(field.getAllAttributes());
				ByteArrayInputStream attributes_in=new ByteArrayInputStream(attributes_out.toByteArray());
				
				
				logger.info("writing id="+field.getID()+" field");
				stmt.setLong(1, field.getID());
				stmt.setInt(2, field.getPriority());				
				stmt.setString(3, field.getText());
				stmt.setAsciiStream(4, attributes_in);
	
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

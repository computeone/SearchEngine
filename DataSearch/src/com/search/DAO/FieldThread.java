package com.search.DAO;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.search.data.Field;
import com.search.data.IDhandler;

public class FieldThread extends Thread {
	private Field field;

	public FieldThread(Field field) {
		this.field = field;
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
		while (true) {
			try {
				String sql = DataBaseOp.SaveField_sql();
				
				PreparedStatement stmt = con.prepareStatement(sql);

				stmt.setLong(1, field.getID());
				stmt.setInt(2, field.getPriority());
				// 序列化
				ByteArrayInputStream bin = new ByteArrayInputStream(field
						.getText().getBytes());
				InputStreamReader reader = new InputStreamReader(bin, "gb2312");
				stmt.setCharacterStream(3, reader);
				System.out.println("正在写Field");
				System.out.println(sql);
				IDhandler idhandler = new IDhandler(field.getID());
				System.out.println(idhandler.getField_id() >> 20);
				System.out.println(field.getText());
				stmt.execute();

				field = DataBaseOp.getField();
				if (field == null) {
					con.close();
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

package com.search.DAO;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;

import com.search.data.Page;

public class DocumentThread extends Thread {
	private String dirpath;
	private String filename;

	public DocumentThread(String dirpath, String filename) {
		this.dirpath = dirpath;
		this.filename = filename;
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
				File file = new File(dirpath, filename);
				System.out.println(file.getName());
				FileReader reader = new FileReader(file);
				
				PreparedStatement stmt = con.prepareStatement(DataBaseOp
						.SavePage_sql());
				DateFormat format = DateFormat.getDateTimeInstance();
				String date = format.format(Calendar.getInstance().getTime());
				long id = Long.parseLong(filename.substring(8));
				Page page = new Page(id);
				System.out.println("正在写第" + id + "个文件");
				System.out.println(page.getID());
				System.out.println(date);
				stmt.setLong(1, page.getID());
				stmt.setTimestamp(2, new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
				stmt.setCharacterStream(3, reader);
				stmt.execute();
				
				filename=DataBaseOp.getDocument();
				if(filename==null){
					con.close();
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

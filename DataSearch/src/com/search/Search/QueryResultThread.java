package com.search.Search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.search.DAO.Connect;
import com.search.data.Field;
import com.search.data.IDhandler;

public class QueryResultThread extends Thread {

	public void run() {
		Connection con = null;
		try {
			con = Connect.getConnection();
		} catch (Exception e1) {
			System.out.println("数据库连接失败");
			e1.printStackTrace();
			System.exit(0);
		}
		while (true) {
			try {
				Statement stmt = con.createStatement();
				SearchResult result;
				Long id = Search.getField_ID();
				if (id == null) {
					con.close();
					break;
				}
				IDhandler idhandler = new IDhandler(id);
				ResultSet resultset = stmt.executeQuery("select priority,"
						+ "content from field where id='"
						+ idhandler.getCurrent_Field_id() + "'");
				Field field = null;
				while (resultset.next()) {
					String text = resultset.getString("content");
					int priority = resultset.getInt("priority");
					field = new Field(text, idhandler.getDocumnent_id(),
							idhandler.getField_id() >> 20);
					field.setPriority(priority);
				}
				Statement stmt_page = con.createStatement();
				ResultSet resultset_page = stmt_page
						.executeQuery("select content from document where "
								+ "id='" + idhandler.getDocumnent_id() + "'");
				String url = null;
				while (resultset_page.next()) {
					String content = resultset_page.getString("content");
					String[] split = content.split("\n");
					int num = (int) ((idhandler.getField_id() >> 20) - 1);
					url = split[num - 1];
				}
				result = new SearchResult(field, url);
				result.setID(id);
				Search.addSearchResult(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

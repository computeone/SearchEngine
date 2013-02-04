package com.search.Search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;

import com.search.DAO.CURD;
import com.search.DAO.Connect;
import com.search.data.Field;
import com.search.data.IDhandler;

public class Search {

	public SearchResult[] search(String str) throws SQLException, Exception {
		CURD curd = new CURD();
		Connection con = Connect.getConnection(
				"jdbc:mysql://localhost:3306/niubaisui", "root", "niubaisui",
				Connect.DATABASE_TYPE_MYSQL);
		Statement stmt = con.createStatement();
		LinkedList<Long> id_list = curd.selectIndex(str).getTokens_id();
		SearchResult[] searchresult = new SearchResult[id_list.size()];
		Iterator<Long> iterator_id = id_list.iterator();
		int i = -1;
		while (iterator_id.hasNext()) {
			SearchResult result;
			Long id = iterator_id.next();
			IDhandler idhandler = new IDhandler(id);
			ResultSet resultset = stmt.executeQuery("select priority,"
					+ "content from field where id='" + idhandler.Field_id()
					+ "'");
			Field field = null;
			while (resultset.next()) {
				String text = resultset.getString("content");
				int priority = resultset.getInt("priority");
				field = new Field(text, idhandler.getPage_id(),
						idhandler.getField_id() >> 20);
				field.setPriority(priority);
			}
			Statement stmt_page = con.createStatement();
			ResultSet resultset_page = stmt_page
					.executeQuery("select content from document where "
							+ "id='" + idhandler.getPage_id() + "'");
			String url = null;
			while (resultset_page.next()) {
				String content = resultset_page.getString("content");
				String[] split = content.split("\n");
				int num = (int) ((idhandler.getField_id() >> 20) - 1);
				// System.out.println(num);
				url = split[num - 1];
			}
			result = new SearchResult(field, url);
			searchresult[++i] = result;
			// System.out.println("pageid:"+(idhandler.getPage_id()>>40));
			// System.out.println("field:"+(idhandler.getField_id()>>20));
			// System.out.println("token:"+idhandler.getToken_id());

		}
		con.close();
		return searchresult;
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		Search search = new Search();
		SearchResult[] result = search.search("运动会");
		long end = System.currentTimeMillis();
		System.out.println("用时为：" + (end - start) + "ms");
		for (SearchResult s : result) {
			System.out.println(s.getURL());
			System.out.println(s.getContent());
			System.out.println(s.getPriority());
		}
	}
}

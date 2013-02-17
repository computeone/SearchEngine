package com.http.ADO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DataBaseCRUD {
	private Connection con;
	public DataBaseCRUD(){
		try{
		con=Connect.getConnection();
		}catch(Exception e){
			System.out.println("数据库打开失败");
		}
	}
	public void insert_visitedURL(String url) throws Exception, SQLException {
		Statement stmt = con.createStatement();
		stmt.executeUpdate("insert into visitedurl(url,priority)values ('"
				+ url + "',0)");
	}

	public void insert_visitedURL(String url, int priority) throws Exception,
			SQLException {
		Statement stmt = con.createStatement();
		stmt.executeUpdate("insert into visitedurl(url,priority)values ('"
				+ url + "','" + priority + "')");
	}

	public void insert_unvisitedURL(String url) throws Exception, SQLException {
		Statement stmt = con.createStatement();
		stmt.execute("insert into unvisitedurl(url,priority)values('" + url
				+ "',0)");
	}

	public HashMap<String, Integer> select_visitedURL(String url)
			throws Exception, SQLException {
		Statement stmt = con.createStatement();
		stmt.executeQuery("select * from visitedurl where url='" + url + "'");
		ResultSet rs = stmt.getResultSet();
		boolean isExist = rs.next();
		if (!isExist) {
			return null;
		}

		int priority = rs.getInt("priority");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put(url, priority);
		return map;
	}

	public HashMap<String, Integer> select_unvisitedURL(String url)
			throws Exception, SQLException {
		Statement stmt = con.createStatement();
		stmt.executeQuery("select * from unvisitedurl where url='" + url + "'");
		ResultSet rs = stmt.getResultSet();
		boolean isExist = rs.next();
		if (!isExist) {
			return null;
		}

		int priority = rs.getInt("priority");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put(url, priority);
		return map;
	}

	public HashMap<String, Integer> getURL() throws Exception, SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from unvisitedurl "
				+ "where priority=(select max(priority) from unvisitedurl)");
		boolean isExist = rs.next();
		if (!isExist) {
			return null;
		} else {
			String url = rs.getString("url");
			int priority = rs.getInt("priority");
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put(url, priority);
			stmt.executeUpdate("delete from unvisitedurl where url='" + url
					+ "'");
			return map;
		}
	}

	public void updatedVisitedURL(String url, int priority) throws Exception,
			SQLException {
		Statement stmt = con.createStatement();
		stmt.executeUpdate("update visitedurl set priority='" + priority + "'"
				+ "  where url='" + url + "'");
	}

	public void updatedUNVisitedURL(String url, int priority) throws Exception,
			SQLException {
		Statement stmt = con.createStatement();
		stmt.executeUpdate("update unvisitedurl set priority='" + priority
				+ "'" + "where priority='" + priority + "'");
	}

	public int selectVisited_Size() throws Exception, SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select count(url) from visitedurl");
		
		while (rs.next()) {
			int size=rs.getInt("count(url)");
			return size;
		}
		return -1;
	}

	public int selectUNVisited_Size() throws Exception, SQLException {
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select count(url) from unvisitedurl");		
		while (rs.next()) {	
			int size=rs.getInt("count(url)");
			return size;
		}
		return -1;
	}
	public static void main(String[] args) throws SQLException, Exception {
		DataBaseCRUD crud=new DataBaseCRUD();
		int size=crud.selectUNVisited_Size();
		System.out.println(size);
		HashMap<String,Integer> hash=crud.getURL();
		String key=(String)hash.keySet().toArray()[0];
		System.out.println(key);
		System.out.println(hash.get(key));
	}

}

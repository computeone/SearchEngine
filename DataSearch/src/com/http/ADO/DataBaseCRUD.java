package com.http.ADO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.http.Search.CrawlUrl;

public class DataBaseCRUD {
	private Connection con;
	public DataBaseCRUD(){
		try{
		con=Connect.getConnection();
		}catch(Exception e){
			System.out.println("数据库打开失败");
		}
	}
	public void insert_visitedURL(CrawlUrl url) throws Exception, SQLException {
		Statement stmt = con.createStatement();
		stmt.executeUpdate("insert into visitedurl(url,priority)values ('"
				+ url.getOriUrl() + "',0)");
	}

	public void insert_unvisitedURL(CrawlUrl url) throws Exception, SQLException {
		Statement stmt = con.createStatement();
		stmt.execute("insert into unvisitedurl(url,priority)values('" + url.getOriUrl()
				+ "',0)");
	}

	public CrawlUrl query_visitedURL(CrawlUrl url)
			throws Exception, SQLException {
		Statement stmt = con.createStatement();
		stmt.executeQuery("select * from visitedurl where url='" + url.getOriUrl() + "'");
		ResultSet rs = stmt.getResultSet();
		boolean isExist = rs.next();
		if (!isExist) {
			return null;
		}

		int priority = rs.getInt("priority");
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl(url.getOriUrl());
		crawlurl.setPriority(priority);
		return crawlurl;
	}

	public CrawlUrl query_unvisitedURL(CrawlUrl url)
			throws Exception, SQLException {
		Statement stmt = con.createStatement();
		stmt.executeQuery("select * from unvisitedurl where url='" + url.getOriUrl() + "'");
		ResultSet rs = stmt.getResultSet();
		boolean isExist = rs.next();
		if (!isExist) {
			return null;
		}

		int priority = rs.getInt("priority");
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl(url.getOriUrl());
		crawlurl.setPriority(priority);
		return crawlurl;
	}

	public CrawlUrl getURL() throws Exception, SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from unvisitedurl "
				+ "where priority=(select max(priority) from unvisitedurl)");
		boolean isExist = rs.next();
		if (!isExist) {
			return null;
		} else {
			String url = rs.getString("url");
			int priority = rs.getInt("priority");
			CrawlUrl crawlurl=new CrawlUrl();
			crawlurl.setOriUrl(url);
			crawlurl.setPriority(priority);
			stmt.executeUpdate("delete from unvisitedurl where url='" + url
					+ "'");
			return crawlurl;
		}
	}

	public void updatedVisitedURL(CrawlUrl url) throws Exception,
			SQLException {
		Statement stmt = con.createStatement();
		stmt.executeUpdate("update visitedurl set priority='" + url.getPriority() + "'"
				+ "  where url='" + url.getOriUrl()+ "'");
	}

	public void updatedUNVisitedURL(CrawlUrl url) throws Exception,
			SQLException {
		Statement stmt = con.createStatement();
		stmt.executeUpdate("update unvisitedurl set priority='" + url.getPriority()
				+ "'" + "where priority='" + url.getPriority() + "'");
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
	
	}

}

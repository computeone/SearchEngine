package com.http.ADO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.http.Search.CrawlUrl;
import com.search.DAO.Connect;

public class DataBaseCRUD {
	private Connection con;
	private String insert_visitedurl_sql="insert into visitedurl(oriUrl,url,urlNo,statusCode," +
			"hitNum,charSet,abstractText,author,priority,description," +
			"title,type,layer) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private String insert_unvisitedurl_sql="insert into unvisitedurl(oriUrl,url,urlNo,statusCode," +
			"hitNum,charSet,abstractText,author,priority,description," +
			"title,type,layer) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
	public DataBaseCRUD(){
		try{
		con=Connect.getConnection();
		}catch(Exception e){
			System.out.println("数据库打开失败");
		}
	}
	/*
	 * 
	 */
	public void insert_visitedURL(CrawlUrl url) throws Exception, SQLException {
		PreparedStatement stmt=con.prepareStatement(insert_visitedurl_sql);
		
		//设置占位符字段
		stmt.setString(1, url.getOriUrl());
		stmt.setString(2,url.getUrl());
		stmt.setInt(3, url.getUrlNo());
		stmt.setInt(4, url.getStatusCode());
		stmt.setInt(5, url.getHitNum());
		stmt.setString(6, url.getCharSet());
		stmt.setString(7, url.getAbstractText());
		stmt.setString(8, url.getAuthor());
		stmt.setInt(9, url.getPriority());
		stmt.setString(10, url.getDescription());
		stmt.setString(11, url.getTitle());
		stmt.setString(12, url.getType());
		stmt.setInt(13, url.getLayer());
		
		stmt.execute();
		stmt.close();
	}

	/*
	 * 
	 */
	public void insert_unvisitedURL(CrawlUrl url) throws Exception, SQLException {
		PreparedStatement stmt=con.prepareStatement(insert_unvisitedurl_sql);
		//设置占位符字段
		stmt.setString(1, url.getOriUrl());
		stmt.setString(2,url.getUrl());
		stmt.setInt(3, url.getUrlNo());
		stmt.setInt(4, url.getStatusCode());
		stmt.setInt(5, url.getHitNum());
		stmt.setString(6, url.getCharSet());
		stmt.setString(7, url.getAbstractText());
		stmt.setString(8, url.getAuthor());
		stmt.setInt(9, url.getPriority());
		stmt.setString(10, url.getDescription());
		stmt.setString(11, url.getTitle());
		stmt.setString(12, url.getType());
		stmt.setInt(13, url.getLayer());
		
		stmt.execute();
		stmt.close();
	}

	/*
	 * 
	 */
	public CrawlUrl query_visitedURL(CrawlUrl url)
			throws Exception, SQLException {
		Statement stmt = con.createStatement();
		stmt.executeQuery("select * from visitedurl where oriUrl='" + url.getOriUrl() + "'");
		ResultSet rs = stmt.getResultSet();
		boolean isExist = rs.next();
		if (!isExist) {
			return null;
		}

		int priority = rs.getInt("priority");
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl(url.getOriUrl());
		crawlurl.setPriority(priority);
		stmt.close();
		return crawlurl;
	}

	/*
	 * 
	 */
	public CrawlUrl query_unvisitedURL(CrawlUrl url)
			throws Exception, SQLException {
		Statement stmt = con.createStatement();
		stmt.executeQuery("select * from unvisitedurl where oriUrl='" + url.getOriUrl() + "'");
		ResultSet rs = stmt.getResultSet();
		boolean isExist = rs.next();
		if (!isExist) {
			return null;
		}

		int priority = rs.getInt("priority");
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl(url.getOriUrl());
		crawlurl.setPriority(priority);
		stmt.close();
		return crawlurl;
	}

	/*
	 * 选择优先级最大的未被访问的url
	 */
	public CrawlUrl getURL() throws Exception, SQLException {
		Statement stmt = con.createStatement();
		stmt.setMaxRows(2);
		ResultSet rs = stmt.executeQuery("select * from unvisitedurl "
				+ "where layer=(select min(layer) from unvisitedurl)");
		boolean isExist = rs.next();
		if (!isExist) {
			return null;
		} else {
			String url = rs.getString("oriUrl");
			int priority = rs.getInt("priority");
			int layer=rs.getInt("layer");
			CrawlUrl crawlurl=new CrawlUrl();
			crawlurl.setOriUrl(url);
			crawlurl.setPriority(priority);
			crawlurl.setLayer(layer);
			
			stmt.close();
			//
			String sql="delete from unvisitedurl where oriUrl=?";
			PreparedStatement update_stmt=con.prepareStatement(sql);
			update_stmt.setString(1, url);
			update_stmt.executeUpdate();
			update_stmt.close();
			
			return crawlurl;
		}
	}

	public void updatedVisitedURL(CrawlUrl url) throws Exception,
			SQLException {
		Statement stmt = con.createStatement();
		stmt.executeUpdate("update visitedurl set priority='" + url.getPriority() + "'"
				+ "  where oriUrl='" + url.getOriUrl()+ "'");
		stmt.close();
	}

	public void updatedUNVisitedURL(CrawlUrl url) throws Exception,
			SQLException {
		Statement stmt = con.createStatement();
		stmt.executeUpdate("update unvisitedurl set priority='" + url.getPriority()
				+ "'" + "where oriUrl='" + url.getOriUrl() + "'");
	}

	public int selectVisited_Size() throws Exception, SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select count(oriUrl) from visitedurl");
		
		while (rs.next()) {
			int size=rs.getInt("count(oriUrl)");
			return size;
		}
		stmt.close();
		return -1;
	}

	public int selectUNVisited_Size() throws Exception, SQLException {
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select count(oriUrl) from unvisitedurl");		
		while (rs.next()) {	
			int size=rs.getInt("count(oriUrl)");
			return size;
		}
		stmt.close();
		return -1;
	}

}

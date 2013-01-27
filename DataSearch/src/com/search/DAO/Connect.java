package com.search.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	public static final String DATABASE_TYPE_MYSQL="MySQL";
	public static final String DATABASE_TYPE_ORACLE="Oracle";
	public static final String DATABASE_TYPE_SQLSERVER="SqlServer";
	public static Connection getConnection(String url,String user,String password,
			String database_type) throws ClassNotFoundException, SQLException{
		switch(database_type){
		case DATABASE_TYPE_MYSQL:
			Class.forName("com.mysql.jdbc.Driver");
			break;
		case DATABASE_TYPE_ORACLE:
			Class.forName("com.oracle.jdbc.Driver");
			break;
		case DATABASE_TYPE_SQLSERVER:
			Class.forName("com.sqlserver.jdbc.Driver");
			break;
		default:
			Class.forName("com.mysql.jdbc.Driver");
		}
		Connection connection=DriverManager.getConnection(url, user, password);
		return connection;
	}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/niubaisui","root","niubaisui");
		boolean result=connection.isClosed();
		System.out.println(result);
		Statement stmt=connection.createStatement();
		ResultSet rs=stmt.executeQuery("select * from Student");
		while(rs.next()){
			System.out.println(rs.getString("Name"));
			System.out.println(rs.getInt("ID"));
		}
		//byte[] name=resultset.getBytes("Name");
		//System.out.println(name);
	}
	
}

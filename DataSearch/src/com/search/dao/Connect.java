package com.search.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	public static final String DATABASE_TYPE_MYSQL = "MySQL";
	public static final String DATABASE_TYPE_ORACLE = "Oracle";
	public static final String DATABASE_TYPE_SQLSERVER = "SqlServer";

	public synchronized static Connection getConnection()
			throws ClassNotFoundException, SQLException {
		switch (Connect.DATABASE_TYPE_MYSQL) {
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
		Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/search","root", "niubaisui");
		return connection;
	}

}

package com.http.ADO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDataBase {
	public void init() throws Exception, SQLException {
		Connection con = Connect.getConnection();
		Statement stmt = con.createStatement();
		stmt.execute("create table visitedurl( url varchar(255) not null"
				+ " primary key,priority int not null);");
		stmt.execute("create table unvisitedurl( url varchar(255) not null"
				+ " primary key,priority int not null);");
		con.close();
	}
	public static void main(String[] args) throws Exception, Exception {
		InitDataBase init=new InitDataBase();
		init.init();
	}
	
}

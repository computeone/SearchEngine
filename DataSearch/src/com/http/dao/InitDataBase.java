package com.http.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import com.search.dao.Connect;

public class InitDataBase {
	public void init() throws Exception, SQLException {
		Connection con = Connect.getConnection();
		String visited_sql="create table visitedurl("
				+"oriUrl varchar(255) not null primary key,"
				+"url    varchar(255) ,"
				+"urlNo       int,"
				+"statusCode  int,"
				+"hitNum      int,"
				+"charSet     varchar(10),"
				+"abstractText varchar(255),"
				+"author       varchar(100),"
				+"priority     int,"
				+"description  varchar(255),"
				+"title        varchar(255),"
				+"type         varchar(100),"
				+"layer        int);";
		String unvisited_sql="create table unvisitedurl("
				+"oriUrl varchar(255) not null primary key,"
				+"url    varchar(255) ,"
				+"urlNo       int,"
				+"statusCode  int,"
				+"hitNum      int,"
				+"charSet     varchar(10),"
				+"abstractText varchar(255),"
				+"author       varchar(100),"
				+"priority     int,"
				+"description  varchar(255),"
				+"title        varchar(255),"
				+"type         varchar(100),"
				+"layer        int);";
		Statement stmt = con.createStatement();
		stmt.execute(visited_sql);
		stmt.execute(unvisited_sql);
		con.close();
	}
	public static void main(String[] args) throws Exception, Exception {
		InitDataBase init=new InitDataBase();
		init.init();
	}
	
}

package com.search.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseInit {
	public void init() throws Exception, SQLException{
		Connection con = Connect.getConnection(
				"jdbc:mysql://localhost:3306/niubaisui", "root", "niubaisui",
				Connect.DATABASE_TYPE_MYSQL);
		Statement stmt=con.createStatement();
		stmt.execute("create table document(ID BIGINT NOT NULL PRIMARY KEY,CREATE_DATE TIMESTAMP,CONTENT TEXT);");
		stmt.execute(" create table field( ID  BIGINT NOT NULL PRIMARY KEY,PRIORITY INT,CONTENT TEXT);");
		
		stmt.execute("CREATE TABLE token_1( FREQUENCY  INT,TERM VARCHAR(1) not null primary key, TOKENS_ID LONGBLOB );");
		stmt.execute("CREATE TABLE token_2(FREQUENCY  INT,TERM VARCHAR(2) not null primary key,TOKENS_ID LONGBLOB );");
		stmt.execute("CREATE TABLE token_3( FREQUENCY INT,TERM  VARCHAR(3) not null primary key, TOKENS_ID LONGBLOB );");
		stmt.execute(" CREATE TABLE token_4( FREQUENCY INT, TERM  VARCHAR(4) not null primary key, TOKENS_ID LONGBLOB  );");
		
		stmt.execute("CREATE TABLE token_5(FREQUENCY INT,TERM VARCHAR(5) not null primary key,TOKENS_ID LONGBLOB );");
		stmt.execute(" CREATE TABLE token_6(FREQUENCY INT, TERM VARCHAR(6) not null primary key, TOKENS_ID LONGBLOB );");
		stmt.execute("CREATE TABLE token_7(FREQUENCY INT, TERM  varchar(7) not null primary key, TOKENS_ID LONGBLOB );");
		stmt.execute("CREATE TABLE token_8(FREQUENCY INT, TERM varchar(8) not null primary key, TOKENS_ID LONGBLOB);");
		stmt.execute("create table token( FREQUENCY INT, TERM varchar(255)  not null primary key, TOKENS_ID LONGBLOB);");
	}

}

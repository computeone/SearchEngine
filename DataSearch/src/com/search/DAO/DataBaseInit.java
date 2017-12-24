package com.search.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseInit {
	public void init() throws Exception, SQLException {
		Connection con = Connect.getConnection();
		Statement stmt = con.createStatement();
		stmt.execute("create table Document(id bigint not null primary key,rank int," +
				"url text,create_date timestamp,store_attributes longblob,index_attributes longblob);");
		stmt.execute(" create table Field(id bigint not null primary key,priority int," +
				"content text,attributes longblob);");

		stmt.execute("CREATE TABLE token_1( FREQUENCY INT,TERM VARCHAR(1) not null primary key, TOKENS_ID LONGBLOB );");
		stmt.execute("CREATE TABLE token_2( FREQUENCY INT,TERM VARCHAR(2) not null primary key,TOKENS_ID LONGBLOB );");
		stmt.execute("CREATE TABLE token_3( FREQUENCY INT,TERM  VARCHAR(3) not null primary key, TOKENS_ID LONGBLOB );");
		stmt.execute("CREATE TABLE token_4( FREQUENCY INT,TERM  VARCHAR(4) not null primary key, TOKENS_ID LONGBLOB  );");

		stmt.execute("CREATE TABLE token_5(FREQUENCY INT, TERM VARCHAR(5) not null primary key,TOKENS_ID LONGBLOB );");
		stmt.execute("CREATE TABLE token_6(FREQUENCY INT, TERM VARCHAR(6) not null primary key, TOKENS_ID LONGBLOB );");
		stmt.execute("CREATE TABLE token_7(FREQUENCY INT, TERM  varchar(7) not null primary key, TOKENS_ID LONGBLOB );");
		stmt.execute("CREATE TABLE token_8(FREQUENCY INT, TERM varchar(8) not null primary key, TOKENS_ID LONGBLOB);");
		stmt.execute("create table token ( FREQUENCY INT, TERM varchar(255)  not null primary key, TOKENS_ID LONGBLOB);");
	}
	public static void main(String[] argv ) throws SQLException, Exception{
		DataBaseInit init=new DataBaseInit();
		init.init();
	}

}

package com.search.DAO;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOThread extends Thread{
	private String dirpath;
	private Connection connection;
	private int start;
	private int count;
	public DAOThread(String dirpath,int count,int start){
		this.dirpath=dirpath;
		this.count=count;
		this.start=start;
	}
	public DAOThread(Connection connection,int count,int start){
		this.connection=connection;
		this.count=count;
		this.start=start;
	}
	public void run(){
		DataBaseOp op=new DataBaseOp();
		try {
			op.DataBaseSave(dirpath, count,start);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

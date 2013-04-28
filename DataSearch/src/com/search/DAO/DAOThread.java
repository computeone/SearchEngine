package com.search.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import com.search.index.BuildIndexDataBase;

public class DAOThread extends Thread{
	private String dirpath;
	private int start;
	private int count;
	public DAOThread(String dirpath,int count,int start){
		this.dirpath=dirpath;
		this.count=count;
		this.start=start;
	}
	public DAOThread(Connection connection,int count,int start){
		this.count=count;
		this.start=start;
	}
	public void run(){
		BuildIndexDataBase op=new BuildIndexDataBase();
		try {
			op.DataBaseSave(dirpath, count,start);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

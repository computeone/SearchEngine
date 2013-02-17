package com.http.Search;

import java.sql.SQLException;
import java.util.HashMap;

import com.http.ADO.DataBaseCRUD;

public class VisitedURL {
	private DataBaseCRUD crud = new DataBaseCRUD();
	//如果存在则更新返回false,否则返回true
	public boolean addURL(String url) throws Exception {
		HashMap<String, Integer> map = isExist(url);
		// 如果存在则更新，否则什么都不做
		if (map != null) {
			updatedURL(url, map.get(url));
			return false;
		}
		else{
			return true;
		}
	}

	private void updatedURL(String url, int priority) throws SQLException,
			Exception {
		crud.updatedVisitedURL(url, priority);
	}

	//即在不存在的情况下直接插入
	public void addURL(String url, int priority) throws SQLException, Exception {
		crud.insert_visitedURL(url, priority);
	}

	private HashMap<String, Integer> isExist(String url) throws Exception {
		HashMap<String, Integer> map = crud.select_visitedURL(url);
		return map;
	}

	public int getSize() throws Exception {
		return crud.selectVisited_Size();
	}

}

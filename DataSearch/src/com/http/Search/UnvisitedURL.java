package com.http.Search;

import java.sql.SQLException;
import java.util.HashMap;

import com.http.ADO.DataBaseCRUD;

public class UnvisitedURL {
	private DataBaseCRUD crud = new DataBaseCRUD();
	//如果存在则更新且返回false，否则返回true
	public boolean addURL(String url) throws SQLException, Exception {
		HashMap<String, Integer> map = isExist(url);
		// 如果存在则更新，否则插入
		if (map != null) {
			crud.updatedUNVisitedURL(url, map.get(url));
			return false;
		} else {
			crud.insert_unvisitedURL(url);
			return true;
		}
	}

	// 得到优先级最高的url之一
	public HashMap<String, Integer> getURL() throws SQLException, Exception {
		return crud.getURL();
	}

	// 判断是否存在
	private HashMap<String, Integer> isExist(String url) throws SQLException,
			Exception {
		return crud.select_unvisitedURL(url);
	}

	public int getSize() throws SQLException, Exception {
		return crud.selectUNVisited_Size();
	}

}

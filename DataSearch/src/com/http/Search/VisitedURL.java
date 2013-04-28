package com.http.Search;

import java.sql.SQLException;

import com.http.ADO.DataBaseCRUD;

public class VisitedURL {
	private UrlBloomFilter bloomfilter;
	private DataBaseCRUD crud = new DataBaseCRUD();
	
	public VisitedURL(){
		bloomfilter=new UrlBloomFilter();
	}
	//如果存在则更新返回true,否则返回false
	public boolean addURL(CrawlUrl url) throws Exception {
		//如果存在则更新，否则什么都不做
		if(bloomfilter.contains(url)){
			//优先级加十
			CrawlUrl crawlurl=crud.query_visitedURL(url);
			int priority=crawlurl.getPriority();
			crawlurl.setPriority(priority+10);
			updatedURL(crawlurl);
			return true;
		}
		
		else{
			return false;
		}
	}

	private void updatedURL(CrawlUrl url) throws SQLException,
			Exception {
		crud.updatedVisitedURL(url);
	}

	//即在不存在的情况下直接插入
	public void add_known_URL(CrawlUrl url) throws SQLException, Exception {
		//添加进布隆过滤器
		bloomfilter.add(url);
		crud.insert_visitedURL(url);
	}

	public int getSize() throws Exception {
		return crud.selectVisited_Size();
	}

}

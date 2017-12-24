package com.http.traversal;

import java.sql.SQLException;

import com.http.dao.DataBaseCRUD;

public class VisitedURL {
	private UrlBloomFilter bloomfilter;
	private DataBaseCRUD crud = new DataBaseCRUD();
	
	public VisitedURL(){
		bloomfilter=new UrlBloomFilter();
	}
	//�����������·���true,���򷵻�false
	public boolean addURL(CrawlUrl url) throws Exception {
		//�����������£�����ʲô������
		if(bloomfilter.contains(url)){
			//���ȼ���ʮ
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

	//���ڲ����ڵ������ֱ�Ӳ���
	public void add_known_URL(CrawlUrl url) throws SQLException, Exception {
		//��ӽ���¡������
		bloomfilter.add(url);
		crud.insert_visitedURL(url);
	}

	public int getSize() throws Exception {
		return crud.selectVisited_Size();
	}

}

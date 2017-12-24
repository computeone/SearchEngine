package com.http.traversal;

import java.sql.SQLException;
import com.http.dao.DataBaseCRUD;

/*
 * 
 */
public class UnVisitedURL {
	private UrlBloomFilter bloomfilter;
	private DataBaseCRUD crud = new DataBaseCRUD();

	public UnVisitedURL(){
		bloomfilter=new UrlBloomFilter();
	}
	//�������������ҷ���true��������뷵��false
	public boolean addURL(CrawlUrl url) throws SQLException, Exception {
		// �����������£��������
		if (bloomfilter.contains(url)) {
			
			CrawlUrl crawlurl=crud.query_unvisitedURL(url);
			int priority=crawlurl.getPriority();
			crawlurl.setPriority(priority+10);
			//ÿ��һ���������ȼ���ʮ
			crud.updatedUNVisitedURL(crawlurl);
			return true;
		} else {
			//��ʼ��ʱ���ȼ�Ϊ0
			bloomfilter.add(url);
			url.setPriority(0);
			crud.insert_unvisitedURL(url);
			return false;
		}
	}

	// �õ����ȼ���ߵ�url֮һ
	public CrawlUrl getURL() throws SQLException, Exception {
		return crud.getURL();
	}

	// �ж��Ƿ����

	public int getSize() throws SQLException, Exception {
		return crud.selectUNVisited_Size();
	}

}

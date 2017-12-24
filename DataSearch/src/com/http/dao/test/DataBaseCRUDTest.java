/**
 * 
 */
package com.http.dao.test;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.http.dao.DataBaseCRUD;
import com.http.traversal.CrawlUrl;

/**
 * @author niubaisui
 *
 */
public class DataBaseCRUDTest {

	/**
	 * @throws java.lang.Exception
	 */
	private static DataBaseCRUD crud;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		crud=new DataBaseCRUD();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.http.ADO.DataBaseCRUD#insert_visitedURL(com.http.Search.CrawlUrl)}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Ignore
	public void testInsert_visitedURL() throws SQLException, Exception {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("www.jd.com");
		crawlurl.setUrl("www.ifeng.com");
		crud.insert_visitedURL(crawlurl);
	}

	/**
	 * Test method for {@link com.http.ADO.DataBaseCRUD#insert_unvisitedURL(com.http.Search.CrawlUrl)}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Ignore
	public void testInsert_unvisitedURL() throws SQLException, Exception {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("www.jd.com");
		crawlurl.setUrl("www.ifeng.com");
		crawlurl.setPriority(100);
		
		CrawlUrl crawlurl1=new CrawlUrl();
		crawlurl1.setOriUrl("www.microsoft.com");
		crawlurl1.setUrl("www.ifeng.com");
		crawlurl1.setPriority(200);
		
		crud.insert_unvisitedURL(crawlurl);
		crud.insert_unvisitedURL(crawlurl1);
	}

	/**
	 * Test method for {@link com.http.ADO.DataBaseCRUD#query_visitedURL(com.http.Search.CrawlUrl)}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Ignore
	public void testQuery_visitedURL() throws SQLException, Exception {
		CrawlUrl url=new CrawlUrl();
		url.setOriUrl("www.jd.com");
		CrawlUrl crawlurl=crud.query_visitedURL(url);
		Assert.assertTrue(crawlurl.getOriUrl()=="www.jd.com");
	}

	/**
	 * Test method for {@link com.http.ADO.DataBaseCRUD#query_unvisitedURL(com.http.Search.CrawlUrl)}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Ignore
	public void testQuery_unvisitedURL() throws SQLException, Exception {
		CrawlUrl url=new CrawlUrl();
		url.setOriUrl("www.jd.com");
		CrawlUrl crawlurl=crud.query_unvisitedURL(url);
		Assert.assertTrue(crawlurl.getOriUrl()=="www.jd.com");
	}

	/**
	 * Test method for {@link com.http.ADO.DataBaseCRUD#getURL()}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Test
	public void testGetURL() throws SQLException, Exception {
		CrawlUrl crawlurl=crud.getURL();
		System.out.println("url:"+crawlurl.getOriUrl());
		System.out.println(crawlurl.getLayer());
		
	}

	/**
	 * Test method for {@link com.http.ADO.DataBaseCRUD#updatedVisitedURL(com.http.Search.CrawlUrl)}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Ignore
	public void testUpdatedVisitedURL() throws SQLException, Exception {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("www.baidu.com");
		crawlurl.setPriority(300);
		crud.updatedVisitedURL(crawlurl);
		CrawlUrl url=crud.query_visitedURL(crawlurl);
		Assert.assertTrue(url.getPriority()==300);
		
	}

	/**
	 * Test method for {@link com.http.ADO.DataBaseCRUD#updatedUNVisitedURL(com.http.Search.CrawlUrl)}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Ignore
	public void testUpdatedUNVisitedURL() throws SQLException, Exception {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("www.jd.com");
		crawlurl.setPriority(400);
		crud.updatedUNVisitedURL(crawlurl);
		CrawlUrl url=crud.query_unvisitedURL(crawlurl);
		System.out.println(url.getPriority());
		Assert.assertTrue(url.getPriority()==400);
	}

	/**
	 * Test method for {@link com.http.ADO.DataBaseCRUD#selectVisited_Size()}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Test
	public void testSelectVisited_Size() throws SQLException, Exception {
		int size=crud.selectVisited_Size();
		System.out.println(size);
	}

	/**
	 * Test method for {@link com.http.ADO.DataBaseCRUD#selectUNVisited_Size()}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Test
	public void testSelectUNVisited_Size() throws SQLException, Exception {
		int size=crud.selectUNVisited_Size();
		System.out.println(size);
	}

}

/**
 * 
 */
package com.http.traversal.test;


import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.http.traversal.CrawlUrl;
import com.http.traversal.UnVisitedURL;

/**
 * @author niubaisui
 *
 */
public class UnVisitedURLTest {

	/**
	 * @throws java.lang.Exception
	 */
	private static UnVisitedURL unvisitedurl;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		unvisitedurl=new UnVisitedURL();
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
	 * Test method for {@link com.http.Search.UnVisitedURL#addURL(com.http.Search.CrawlUrl)}.
	 * @throws Exception 
	 */
	@Test
	public void testAddURL() throws Exception {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("www.baidu.com");
		crawlurl.setUrl("www.ifeng.com");
		unvisitedurl.addURL(crawlurl);
		unvisitedurl.addURL(crawlurl);
	}

	/**
	 * Test method for {@link com.http.Search.UnVisitedURL#getURL()}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Test
	public void testGetURL() throws SQLException, Exception {
		CrawlUrl crawlurl=unvisitedurl.getURL();
		System.out.println(crawlurl.getOriUrl());
	}

	/**
	 * Test method for {@link com.http.Search.UnVisitedURL#getSize()}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Test
	public void testGetSize() throws SQLException, Exception {
		int size=unvisitedurl.getSize();
		System.out.println(size);
	}

}

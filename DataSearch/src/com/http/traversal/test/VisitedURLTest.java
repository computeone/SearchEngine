/**
 * 
 */
package com.http.traversal.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.http.traversal.CrawlUrl;
import com.http.traversal.VisitedURL;

/**
 * @author niubaisui
 *
 */
public class VisitedURLTest {

	/**
	 * @throws java.lang.Exception
	 */
	private static VisitedURL visitedurl;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		visitedurl=new VisitedURL();
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
	 * Test method for {@link com.http.Search.VisitedURL#addURL(com.http.Search.CrawlUrl)}.
	 * @throws Exception 
	 */
	@Ignore
	public void testAddURL() throws Exception {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("www.ibm.com");
		crawlurl.setUrl("www.ifeng.com");
		assertTrue(visitedurl.addURL(crawlurl));
		
	}

	/**
	 * Test method for {@link com.http.Search.VisitedURL#add_known_URL(com.http.Search.CrawlUrl)}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Test
	public void testAdd_known_URL() throws SQLException, Exception {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("www.orcale.com");
		visitedurl.add_known_URL(crawlurl);
	}

	/**
	 * Test method for {@link com.http.Search.VisitedURL#getSize()}.
	 * @throws Exception 
	 */
	@Test
	public void testGetSize() throws Exception {
		int size=visitedurl.getSize();
		assertTrue(size==5);
	}

}

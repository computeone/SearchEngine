/**
 * 
 */
package com.http.Search.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.http.Search.BreadthFirstTraversal;
import com.http.Search.CrawlUrl;

/**
 * @author niubaisui
 *
 */
public class BreadthFirstTraversalTest {

	/**
	 * @throws java.lang.Exception
	 */
//	private static BreadthFirstTraversal breadthfirsttraversal=null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("http://www.suse.edu.cn");
		CrawlUrl[] initurl=new CrawlUrl[1];
		initurl[0]=crawlurl;
//		breadthfirsttraversal=BreadthFirstTraversal.getBreadthFirstTraversal(initurl);
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
	 * Test method for {@link com.http.Search.BreadthFirstTraversal#addURLVisited(com.http.Search.CrawlUrl)}.
	 */
	@Ignore
	public void testAddURLVisited() {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("http://www.baidu.com");
		boolean result=BreadthFirstTraversal.addURLVisited(crawlurl);
		assertTrue(result);
	}

	/**
	 * Test method for {@link com.http.Search.BreadthFirstTraversal#add_known_URLVisited(com.http.Search.CrawlUrl)}.
	 */
	@Test
	public void testAdd_known_URLVisited() {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("http://www.ibm.com");
		System.out.println("test");
		BreadthFirstTraversal.add_known_URLVisited(crawlurl);
	}

	/**
	 * Test method for {@link com.http.Search.BreadthFirstTraversal#addUNURLVisited(com.http.Search.CrawlUrl)}.
	 */
	@Ignore
	public void testAddUNURLVisited() {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("http://www.oracle.com");
		BreadthFirstTraversal.addUNURLVisited(crawlurl);
	}

	/**
	 * Test method for {@link com.http.Search.BreadthFirstTraversal#getUNVisitedURL()}.
	 */
	@Ignore
	public void testGetUNVisitedURL() {
//		CrawlUrl crawlurl=BreadthFirstTraversal.getUNVisitedURL();
		
	}

	/**
	 * Test method for {@link com.http.Search.BreadthFirstTraversal#getVisitedURL_Size()}.
	 */
	@Test
	public void testGetVisitedURL_Size() {
	}

	/**
	 * Test method for {@link com.http.Search.BreadthFirstTraversal#getUNVisitedURL_Size()}.
	 */
	@Test
	public void testGetUNVisitedURL_Size() {
	}

	/**
	 * Test method for {@link com.http.Search.BreadthFirstTraversal#setCrawlThreadPool(com.http.control.CrawlThreadPool)}.
	 */
	@Test
	public void testSetCrawlThreadPool() {
	}

}

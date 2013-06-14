/**
 * 
 */
package com.http.control.test;



import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.http.Search.CrawlUrl;
import com.http.connect.HttpConnectPool;
import com.http.control.CrawlThread;

/**
 * @author niubaisui
 *
 */
public class CrawlThreadTest {

	/**
	 * @throws java.lang.Exception
	 */
	private static CrawlUrl crawlurl;
	private static CrawlThread crawlthread;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HttpConnectPool.GetHttpConnectPool(10);
		crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("http://www.baidu.com");
		crawlthread=new CrawlThread(crawlurl);
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
	 * Test method for {@link com.http.control.CrawlThread#run()}.
	 */
	@Test
	public void testRun(){
		crawlthread.run();
	}

	/**
	 * Test method for {@link com.http.control.CrawlThread#CrawlThread(com.http.Search.CrawlUrl)}.
	 */
	@Test
	public void testCrawlThread() {
		
	}

	/**
	 * Test method for {@link com.http.control.CrawlThread#getCrawlurl()}.
	 */
	@Test
	public void testGetCrawlurl() {
		
	}

	/**
	 * Test method for {@link com.http.control.CrawlThread#setCrawlurl(com.http.Search.CrawlUrl)}.
	 */
	@Test
	public void testSetCrawlurl() {
	}

}

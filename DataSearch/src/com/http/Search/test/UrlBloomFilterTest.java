/**
 * 
 */
package com.http.Search.test;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.http.Search.CrawlUrl;
import com.http.Search.UrlBloomFilter;

/**
 * @author niubaisui
 *
 */
public class UrlBloomFilterTest {

	/**
	 * @throws java.lang.Exception
	 */
	private  UrlBloomFilter bloom;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
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
		bloom=new UrlBloomFilter();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
	}

	/**
	 * Test method for {@link com.http.Search.UrlBloomFilter#UrlBloomFilter()}.
	 */
	@Test
	public void testUrlBloomFilter() {
		
	}

	/**
	 * Test method for {@link com.http.Search.UrlBloomFilter#add(com.http.Search.CrawlUrl)}.
	 */
	@Test
	public void testAddCrawlUrl() {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("www.baidu.com");
		bloom.add(crawlurl);
	}

	/**
	 * Test method for {@link com.http.Search.UrlBloomFilter#add(java.lang.String)}.
	 */
	@Test
	public void testAddString() {
	}

	/**
	 * Test method for {@link com.http.Search.UrlBloomFilter#contains(com.http.Search.CrawlUrl)}.
	 */
	@Test
	public void testContainsCrawlUrl() {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl("www.baidu.com");
		CrawlUrl url=new CrawlUrl();
		url.setOriUrl("www.youku.com");
		bloom.add(crawlurl);
		Assert.assertTrue(bloom.contains(crawlurl));
		Assert.assertFalse(bloom.contains(url));
		bloom.add(url);
		Assert.assertTrue(bloom.contains(url));
	}

	/**
	 * Test method for {@link com.http.Search.UrlBloomFilter#contains(java.lang.String)}.
	 */
	@Test
	public void testContainsString() {
		
	}

}

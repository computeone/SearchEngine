package com.http.connect.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.http.Search.CrawlUrl;
import com.http.connect.HttpConnect;
import com.http.connect.HttpResponseHeader;

public class HttpConnectTest {

	private static HttpConnect httpconnect;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		httpconnect=new HttpConnect();
		
		
	}

	@After
	public void tearDown() throws Exception {
		httpconnect=null;
	}

	@Ignore
	public void testGetCrawlurl() throws Exception {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setLayer(1);
		String[] urls=new String[6];
		urls[0]="http://www.ifeng.com";
		
		
		httpconnect.setCrawlUrl(crawlurl);	
		httpconnect.Connect();
		CrawlUrl crawl=httpconnect.getCrawlurl();
		System.out.println(crawl.getOriUrl());
		
		Assert.assertNotNull(crawlurl.getLastUpdateTime());
		System.out.println(crawlurl.getLastUpdateTime().getTime());
		
	}

	@Test
	public void testGetInputStream() {
		
	}

	@Test
	public void testGetOutputStream() {
		
	}

	@Ignore
	public void testGetHttpResponseHeader() throws Exception{
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setLayer(1);
		String[] urls=new String[6];
		urls[0]="http://www.ifeng.com";
		
		
		httpconnect.setCrawlUrl(crawlurl);	
		httpconnect.Connect();
		
		
		HttpResponseHeader httpresponseheader=httpconnect.getHttpresponseheader();
		System.out.println("-------------------------------");
		System.out.println("encoding:"+crawlurl.getCharSet());
		System.out.println(httpresponseheader.getContent_Type());
		System.out.println(httpresponseheader.getContent_Language());
		System.out.println(httpresponseheader.getContent_Encoding());
		System.out.println(httpresponseheader.getSet_Cookie());
	}
	
	@Test
	public void testGetFields() throws Exception {
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setLayer(1);
		String[] urls=new String[6];
		urls[0]="http://www.ifeng.com";
		urls[1]="http://news.ifeng.com/opinion/special/wangping/xinxinrenlei.shtml\" target=\"_blank\"";
		urls[2]="http://tyx.suse.edu.cn";
		crawlurl.setOriUrl(urls[2]);
		
		
		httpconnect.setCrawlUrl(crawlurl);	
		httpconnect.Connect();
		httpconnect.printFields();
	}

	@Test
	public void testHttpConnect() {
	}

	@Test
	public void testSuccess() {
	}

	@Test
	public void testDiscard() {
	}

	@Test
	public void testRedirect() {
	}

	@Test
	public void testConnect() {
	}

	@Test
	public void testReleaseConnect() {
	}

	@Test
	public void testPrintFields() {
	}

}

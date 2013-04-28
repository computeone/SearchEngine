package com.http.connect.test;

import java.io.IOException;
import java.io.InputStream;
import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.http.Search.CrawlUrl;
import com.http.connect.HttpConnect;
import com.http.connect.HttpResponseHeader;

public class HttpConnectTest {

	private static HttpConnect httpconnect;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		httpconnect=new HttpConnect();
		httpconnect.setUrl("http://www.baidu.com");
		httpconnect.Connect();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testGetCrawlurl() {
		CrawlUrl crawlurl=httpconnect.getCrawlurl();
		System.out.println(crawlurl.getOriUrl());
		Assert.assertTrue(crawlurl.getUrl()=="http://www.baidu.com");
		Assert.assertNotNull(crawlurl.getLastUpdateTime());
		System.out.println(crawlurl.getLastUpdateTime().getTime());
		
	}

	@Test
	public void testGetInputStream() {
		InputStream in=httpconnect.getInputStream();
		Assert.assertNotNull(in);
	}

	@Test
	public void testGetOutputStream() {
		
	}

	@Test
	public void testGetHttpResponseHeader(){
		HttpResponseHeader httpresponseheader=httpconnect.getHttpresponseheader();
		System.out.println("-------------------------------");
		System.out.println(httpresponseheader.getContent_Type());
		System.out.println(httpresponseheader.getContent_Language());
		if(httpresponseheader.getContent_Encoding()==null){
			
		}
		System.out.println(httpresponseheader.getContent_Encoding());
		System.out.println(httpresponseheader.getSet_Cookie());
	}
	
	@Test
	public void testGetFields() throws IOException {
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

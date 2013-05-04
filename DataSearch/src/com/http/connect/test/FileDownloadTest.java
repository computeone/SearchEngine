/**
 * 
 */
package com.http.connect.test;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.http.Search.CrawlUrl;
import com.http.connect.FileDownload;
import com.http.connect.HttpConnect;

/**
 * @author niubaisui
 *
 */
public class FileDownloadTest {

	/**
	 * @throws java.lang.Exception
	 */
	private static FileDownload filedownload;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String[] url=new String[5];
		url[0]="http://www.suse.edu.cn";
		url[1]="http://bc.ifeng.com/main/c?db=ifeng&bid=18587,18272," +
				"3995&cid=2268,46,1&sid=38519&advid=548&camid=4114&show" +
				"=ignore&url=http://www.533.com/hfsxy1/";
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl(url[0]);
		crawlurl.setLayer(1);
		HttpConnect httpconnect=new HttpConnect(crawlurl);
		httpconnect.Connect();
		httpconnect.printFields();
		System.out.println(httpconnect.getCrawlurl().getOriUrl());
		filedownload=new FileDownload(httpconnect.getInputStream());
		filedownload.setCrawlUrl(httpconnect.getCrawlurl());
		filedownload.download();
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
	 * Test method for {@link com.http.connect.FileDownload#getFileName()}.
	 */
	@Ignore
	public void testGetFileName() {
	}

	/**
	 * Test method for {@link com.http.connect.FileDownload#getAbsolutePath()}.
	 */
	@Ignore
	public void testGetAbsolutePath() {
		String filepath=filedownload.getAbsolutePath();
		System.out.println("absolutepath:"+filepath);
	}

	/**
	 * Test method for {@link com.http.connect.FileDownload#getEncoding()}.
	 */
	@Ignore
	public void testGetEncoding() {
		String encoding=filedownload.getEncoding();
		System.out.println(encoding);
		Assert.assertTrue(encoding.equals("utf-8"));
	}

	/**
	 * Test method for {@link com.http.connect.FileDownload#parseURL()}.
	 * @throws Exception 
	 */
	@Ignore
	public void testParseURL() throws Exception {
		String[] dir=filedownload.parseURL();
		for(String s:dir){
			System.out.println(s);
		}
	}

	/**
	 * Test method for {@link com.http.connect.FileDownload#isParser()}.
	 */
	@Ignore
	public void testIsParser() {
		Assert.assertTrue(filedownload.isParser());
	}

	/**
	 * Test method for {@link com.http.connect.FileDownload#download()}.
	 */
	@Test
	public void testDownload() {
	}

	/**
	 * Test method for {@link com.http.connect.FileDownload#printFile()}.
	 * @throws Exception 
	 */
	@Test
	public void testPrintFile() throws Exception {
		filedownload.printFile();
		filedownload.isParser();
		System.out.println(filedownload.isParser());
	}

}

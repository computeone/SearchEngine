/**
 * 
 */
package com.http.connect.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
		HttpConnect httpconnect=new HttpConnect();
		httpconnect.setUrl("http://hxx.suse.edu.cn");
		httpconnect.Connect();
		filedownload=new FileDownload(httpconnect.getInputStream());
		filedownload.setHttpresponseHeader(httpconnect.getHttpresponseheader());
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
	@Test
	public void testGetFileName() {
		String filename=filedownload.getFileName();
		System.out.println("filename:"+filename);
	}

	/**
	 * Test method for {@link com.http.connect.FileDownload#getAbsolutePath()}.
	 */
	@Test
	public void testGetAbsolutePath() {
		String filepath=filedownload.getAbsolutePath();
		System.out.println("absolutepath:"+filepath);
	}

	/**
	 * Test method for {@link com.http.connect.FileDownload#getEncoding()}.
	 */
	@Test
	public void testGetEncoding() {
		String encoding=filedownload.getEncoding();
		System.out.println(encoding);
		Assert.assertTrue(encoding.equals("utf-8"));
	}

	/**
	 * Test method for {@link com.http.connect.FileDownload#parseURL()}.
	 */
	@Test
	public void testParseURL() {
	}

	/**
	 * Test method for {@link com.http.connect.FileDownload#isParser()}.
	 */
	@Test
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
	}

}

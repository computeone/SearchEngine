/**
 * 
 */
package com.http.connect.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.http.connect.SimpleHttpURLParser;

/**
 * @author niubaisui
 * 
 */
public class SimpleHttpURLParserTest {

	/**
	 * @throws java.lang.Exception
	 */
	private static SimpleHttpURLParser parser;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		parser = new SimpleHttpURLParser();
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
	 * Test method for
	 * {@link com.http.connect.SimpleHttpURLParser#parserURL(java.lang.String)}.
	 */
	@Test
	public void testParserURL() {
		String[] urls = new String[6];
		urls[0] = "http://www.baidu.com/";
		urls[1] = "http://www.suse.edu.cn/";
		urls[2] = "http://www.suse.edu.cn/index.html";
		urls[3] = "http://www.suse.edu.cn/login/ppp.html";
		urls[4] = "http://www.ifeng.com:80/long/temp^&(&*&(/index.html";
		urls[5] = "http://www.ifeng.com:80/long\n\t*/index.html";
		String[] str = parser.parserURL(urls[0]);
		System.out.println("---------------------------");
		System.out.println(str.length);
		for (String s : str) {
			if (s != null && s.equals("")) {
				System.out.println("is null char");
			}
			System.out.println(s);
		}
		

	}

	/**
	 * Test method for
	 * {@link com.http.connect.SimpleHttpURLParser#vertifyURL(java.lang.String)}
	 * .
	 */
	@Ignore
	public void testVertifyURL() {
		String[] urls = new String[6];
		urls[0] = "http://www.baidu.com/";
		urls[1] = "http://www.suse.edu.cn/";
		urls[2] = "http://www.suse.edu.cn/index.html";
		urls[3] = "http://www.suse.edu.cn/login/ppp.html";
		urls[4] = "http://www.ifeng.com:80/long/temp^&(&*&(/index.html";
		urls[5] = "http://www.ifeng.com:80/long\n\t/index.html";
		boolean result = parser.vertifyURL(urls[5]);
		Assert.assertTrue(result);
	}

	/**
	 * Test method for
	 * {@link com.http.connect.SimpleHttpURLParser#parserPath(java.lang.String)}
	 * .
	 */
	@Ignore
	public void testParserPath() {
		String[] str = parser.parserPath("e:\\spider/niubaisui/test.html");
		for (String s : str) {
			System.out.println(s);
		}
	}

	/**
	 * Test method for
	 * {@link com.http.connect.SimpleHttpURLParser#deleteIllegalChar(java.lang.String)}
	 * .
	 */
	@Test
	public void testDeleteIllegalChar() {
		String[] urls = new String[6];
		urls[0] = "http://www.baidu.com/";
		urls[1] = "http://www.suse.edu.cn/";
		urls[2] = "http://www.suse.edu.cn/index.html";
		urls[3] = "http://www.suse.edu.cn/login/ppp.html";
		urls[4] = "http://www.ifeng.com:80/long/temp^&(&*&(/index.html";
		urls[5] = "http://www.ifeng.com:80/long.-\n\t*/index.html";
		String s = parser.deleteIllegalChar(urls[5]);
		System.out.println("·Ç·¨×Ö·û");
		System.out.println(s);
	}

}

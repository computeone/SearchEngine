/**
 * 
 */
package com.http.connect.test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
		parser=new SimpleHttpURLParser();
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
	 * Test method for {@link com.http.connect.SimpleHttpURLParser#parserURL(java.lang.String)}.
	 */
	@Test
	public void testParserURL() {
		String[] str=parser.parserURL("http://www.baidu.com:80");
		System.out.println("---------------------------");
		System.out.println(str.length);
		for(String s:str){
			System.out.println(s);
		}
		
	}

	/**
	 * Test method for {@link com.http.connect.SimpleHttpURLParser#vertifyURL(java.lang.String)}.
	 */
	@Test
	public void testVertifyURL() {
		String url="http://www.baidu.com";
		boolean result=parser.vertifyURL(url);
		Assert.assertTrue(result);
	}

	/**
	 * Test method for {@link com.http.connect.SimpleHttpURLParser#parserPath(java.lang.String)}.
	 */
	@Test
	public void testParserPath() {
		String[] str=parser.parserPath("e:\\spider/niubaisui/test.html");
		for(String s:str){
			System.out.println(s);
		}
	}

	/**
	 * Test method for {@link com.http.connect.SimpleHttpURLParser#deleteIllegalChar(java.lang.String)}.
	 */
	@Test
	public void testDeleteIllegalChar() {
	}

}

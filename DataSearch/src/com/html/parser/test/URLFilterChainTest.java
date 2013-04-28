/**
 * 
 */
package com.html.parser.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.html.parser.SuseURLFilter;
import com.html.parser.URLFilterChain;

/**
 * @author niubaisui
 *
 */
public class URLFilterChainTest {

	/**
	 * @throws java.lang.Exception
	 */
	private static URLFilterChain chain;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		chain=new URLFilterChain();
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
	 * Test method for {@link com.html.parser.URLFilterChain#addFilter(com.html.parser.URLFilter)}.
	 */
	@Test
	public void testAddFilter() {
	}

	/**
	 * Test method for {@link com.html.parser.URLFilterChain#filter(java.lang.String)}.
	 */
	@Test
	public void testFilter() {
		String str="http://www.suse.edu.cn/qjmy/hd/index.html";
		SuseURLFilter suse=new SuseURLFilter();
		chain.addFilter(suse);
		assertTrue(chain.filter(str));
	}

}

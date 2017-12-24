/**
 * 
 */
package com.parser.html.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.parser.html.SuseURLFilter;
import com.parser.html.URLFilterChain;

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
		chain = new URLFilterChain();
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
	 * {@link com.html.parser.URLFilterChain#addFilter(com.html.parser.URLFilter)}
	 * .
	 */
	@Test
	public void testAddFilter() {
	}

	/**
	 * Test method for
	 * {@link com.html.parser.URLFilterChain#filter(java.lang.String)}.
	 */
	@Test
	public void testFilter() {
		String str = "http://www.suse.edu.cn";
		SuseURLFilter suse = new SuseURLFilter();
		chain.addFilter(suse);
		assertTrue(chain.filter(str));
	}

}

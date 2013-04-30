/**
 * 
 */
package com.search.data.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.search.data.Document;

/**
 * @author niubaisui
 *
 */
public class DocumentTest {

	/**
	 * @throws java.lang.Exception
	 */
	private Document document;
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
		document=new Document(100l);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		document=null;
	}

	/**
	 * Test method for {@link com.search.data.Document#addIndex_attribute(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddIndex_attribute() {
		document.addIndex_attribute("name","niubaisui");
		document.addIndex_attribute("lejie", "dongfangbubei");
		document.addIndex_attribute("aiwo", "chengqiaoen");
		
		int lejie=document.getIndex_count("lejie");
		int name=document.getIndex_count("name");
		int aiwo=document.getIndex_count("aiwo");
		assertEquals(1,name);
		assertEquals(2, lejie);
		assertEquals(3, aiwo);
	}

	/**
	 * Test method for {@link com.search.data.Document#addStore_attribute(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddStore_attribute() {
	}

	/**
	 * Test method for {@link com.search.data.Document#getIndex_attribute(java.lang.String)}.
	 */
	@Test
	public void testGetIndex_attribute() {
	}

	/**
	 * Test method for {@link com.search.data.Document#getStore_attriubte(java.lang.String)}.
	 */
	@Test
	public void testGetStore_attriubte() {
	}

}

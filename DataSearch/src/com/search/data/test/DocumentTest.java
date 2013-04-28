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
	private  Document document;
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
		document=new Document(1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		document=null;
		
	}

	/**
	 * Test method for {@link com.search.data.Document#getID()}.
	 */
	@Test
	public void testGetID() {
		long id=document.getID();
		System.out.println(id);
		assertEquals((long)1<<40,id);
	}

	/**
	 * Test method for {@link com.search.data.Document#getRanks()}.
	 */
	@Test
	public void testGetRanks() {
		int rank=document.getRanks();
		assertEquals(0, rank);
	}

	/**
	 * Test method for {@link com.search.data.Document#getAllAttributes()}.
	 */
	@Test
	public void testGetAllAttributes() {
		document.addAttribute("keyword","niubaisui");
		document.addAttribute("charset", "utf-8");
		String charset=document.getAttribute("charset");
		assertEquals("utf-8", charset);
	}

	/**
	 * Test method for {@link com.search.data.Document#addAttribute(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddAttribute() {
	}

	/**
	 * Test method for {@link com.search.data.Document#getIndexcount(java.lang.String)}.
	 */
	@Test
	public void testGetIndexcount() {
		document.addAttribute("keyword", "niubaisui");
		document.addAttribute("charset", "utf-8");
		int indexcount=document.getIndexcount("charset");
		assertEquals(2, indexcount);
	}

	/**
	 * Test method for {@link com.search.data.Document#getAttribute(java.lang.String)}.
	 */
	@Test
	public void testGetAttribute() {
		document.addAttribute("description", "searchengine");
		String value=document.getAttribute("description");
		assertEquals("searchengine", value);
	}

}

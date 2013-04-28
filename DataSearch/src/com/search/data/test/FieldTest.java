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

import com.search.data.Field;

/**
 * @author niubaisui
 *
 */
public class FieldTest {

	/**
	 * @throws java.lang.Exception
	 */
	private Field field;
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
		field=new Field("niubaisui",(long)1<<40,100);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		field=null;
	}

	/**
	 * Test method for {@link com.search.data.Field#getText()}.
	 */
	@Test
	public void testGetText() {
		String text=field.getText();
		assertEquals("niubaisui", text);
	}

	/**
	 * Test method for {@link com.search.data.Field#getID()}.
	 */
	@Test
	public void testGetID() {
		long id=field.getID();
		long a=(long)1<<40;
		long b=(long)100<<20;
		long expected=a+b;
		assertEquals(expected,id);
	}

	/**
	 * Test method for {@link com.search.data.Field#addAttribute(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddAttribute() {
		field.addAttribute("keyword", "niubaisui");
		String value=field.getAttribute("keyword");
		assertEquals("niubaisui", value);
	}

	/**
	 * Test method for {@link com.search.data.Field#getAttribute(java.lang.String)}.
	 */
	@Test
	public void testGetAttribute() {
	}

	/**
	 * Test method for {@link com.search.data.Field#getAttributes()}.
	 */
	@Test
	public void testGetAttributes() {
	}

	/**
	 * Test method for {@link com.search.data.Field#getIndexcount(java.lang.String)}.
	 */
	@Test
	public void testGetIndexcount() {
		field.addAttribute("charset", "niubaisui");
		field.addAttribute("class", "a");
		int indexcount=field.getIndexcount("class");
		assertEquals(2, indexcount);
	}

}

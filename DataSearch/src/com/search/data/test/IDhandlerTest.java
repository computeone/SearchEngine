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

import com.search.data.IDhandler;

/**
 * @author niubaisui
 *
 */
public class IDhandlerTest {

	/**
	 * @throws java.lang.Exception
	 */
	private static IDhandler idhandler;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		idhandler=new IDhandler(1);
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
	 * Test method for {@link com.search.data.IDhandler#getCurrent_Document_id()}.
	 */
	@Test
	public void testGetCurrent_Document_id() {
		long id=109951267635200l;//100 100
		idhandler.setID(id);
		assertEquals(100, idhandler.getCurrent_Document_id());
	}

	/**
	 * Test method for {@link com.search.data.IDhandler#getDocumnent_id()}.
	 */
	@Test
	public void testGetDocumnent_id() {
		long id=109951267635200l;//100 100
		idhandler.setID(id);
		assertEquals((long)100<<40, idhandler.getDocumnent_id());
	}

	/**
	 * Test method for {@link com.search.data.IDhandler#getField_id()}.
	 */
	@Test
	public void testGetField_id() {
		long id=109951267635200l;//100 100
		idhandler.setID(id);
		assertEquals(id, idhandler.getField_id());
	}

	/**
	 * Test method for {@link com.search.data.IDhandler#getCurrent_Field_id()}.
	 */
	@Test
	public void testGetCurrent_Field_id() {
		long id=109951267635200l;//100 100
		idhandler.setID(id);
		assertEquals(100, idhandler.getCurrent_Field_id());
	}

	/**
	 * Test method for {@link com.search.data.IDhandler#getCurrent_Token_id()}.
	 */
	@Test
	public void testGetCurrent_Token_id() {
		long id=109951267635300l;//100 100 100
		idhandler.setID(id);
		assertEquals(100, idhandler.getCurrent_Token_id());
	}

}

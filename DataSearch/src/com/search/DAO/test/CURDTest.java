/**
 * 
 */
package com.search.DAO.test;


import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.search.DAO.CURD;

/**
 * @author niubaisui
 *
 */
public class CURDTest {

	/**
	 * @throws java.lang.Exception
	 */
	private CURD curd;
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
		curd=new CURD();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		curd=null;
	}

	/**
	 * Test method for {@link com.search.DAO.CURD#selectIndex(java.lang.String)}.
	 */
	@Test
	public void testSelectIndex() {
	}

	/**
	 * Test method for {@link com.search.DAO.CURD#selectField(java.util.LinkedList)}.
	 */
	@Test
	public void testSelectField() {
	}

	/**
	 * Test method for {@link com.search.DAO.CURD#selectDocument(long)}.
	 */
	@Test
	public void testSelectDocument() {
	}

	/**
	 * Test method for {@link com.search.DAO.CURD#selectDocuments(java.util.LinkedList)}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Test
	public void testSelectDocuments() throws SQLException, Exception {
		
	}

}
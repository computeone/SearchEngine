/**
 * 
 */
package com.search.DAO.test;



import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.search.DAO.CRUD;
import com.search.data.Document;

/**
 * @author niubaisui
 *
 */
public class CURDTest {

	/**
	 * @throws java.lang.Exception
	 */

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
	 * Test method for {@link com.search.DAO.CRUD#selectIndex(java.lang.String)}.
	 */
	@Test
	public void testSelectIndex() {
	}

	/**
	 * Test method for {@link com.search.DAO.CRUD#selectField(java.util.LinkedList)}.
	 */
	@Test
	public void testSelectField() {
	}

	/**
	 * Test method for {@link com.search.DAO.CRUD#selectDocument(long)}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Test
	public void testSelectDocument() throws SQLException, Exception {
		Document document=CRUD.selectDocument(65970697666560l);
		System.out.println(document.getRanks());
		System.out.println(document.getUrl());
		System.out.println(document.getIndex_attribute("title"));
		System.out.println(document.getIndex_attribute("keywords"));
		System.out.println(document.getIndex_attribute("description"));
		
//		ByteArrayInputStream in=new ByteArrayInputStream(document.getIndex_attribute("title").getBytes());
//		InputStreamReader reader=new InputStreamReader(in,"ISO-8859-1");
//		BufferedReader buffer_reader=new BufferedReader(reader);
//		System.out.println(buffer_reader.readLine());
		
	}

	/**
	 * Test method for {@link com.search.DAO.CRUD#selectDocuments(java.util.LinkedList)}.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	@Test
	public void testSelectDocuments() throws SQLException, Exception {
		
	}

}

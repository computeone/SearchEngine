/**
 * 
 */
package com.search.index.test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.search.DAO.CRUD;
import com.search.data.Document;
import com.search.data.Field;
import com.search.data.IDhandler;
import com.search.data.Token;
import com.search.index.Index;
import com.search.index.SimpleBuildIndex;

/**
 * @author niubaisui
 *
 */
public class SimpleBuildIndexTest {

	/**
	 * @throws java.lang.Exception
	 */
	private SimpleBuildIndex simplebuildindex;
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
		Document document1=CRUD.selectDocument(65970697666560l);
		document=new Document(2l);
		document.addIndex_attribute("keywords", "dongfangbubei lejjie juewu");
		document.addIndex_attribute("description", "lejie");
		document.addIndex_attribute("title", null);
		simplebuildindex=new SimpleBuildIndex(document1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		document=null;
		simplebuildindex=null;
	}

	/**
	 * Test method for {@link com.search.index.SimpleBuildIndex#buildIndex()}.
	 * @throws Exception 
	 */
	@Test
	public void testBuildIndex() throws Exception {
		Index index=simplebuildindex.buildIndex();
		assertNotNull(index);
	}

	/**
	 * Test method for {@link com.search.index.SimpleBuildIndex#getDocuemnt()}.
	 */
	@Test
	public void testGetDocuemnt() {
		
	}

	/**
	 * Test method for {@link com.search.index.SimpleBuildIndex#getField()}.
	 * @throws Exception 
	 */
	@Test
	public void testGetField() throws Exception {
		Index index=simplebuildindex.buildIndex();
		LinkedList<Field> fields=index.getFields();
		IDhandler idhandler=new IDhandler(1l);
		for(Field f:fields){
			System.out.println("field--------------------");
			idhandler.setID(f.getID());
			System.out.println("id:"+f.getID());
			System.out.println("document id:"+idhandler.getCurrent_Document_id());
			System.out.println("field id:"+idhandler.getCurrent_Field_id());
			System.out.println("text:"+f.getText());
			System.out.println("field---------------------------");
		}
	}

	/**
	 * Test method for {@link com.search.index.SimpleBuildIndex#getToken()}.
	 * @throws Exception 
	 */
	@Test
	public void testGetTokens() throws Exception {
		Index index=simplebuildindex.buildIndex();
		LinkedList<Token> tokens=index.getTokens();
		IDhandler idhandler=new IDhandler(1l);
		
		for(Token t:tokens){
			System.out.println("------------------------");
			idhandler.setID(t.getID());
			System.out.println("id:"+t.getID());
			System.out.println("document id:"+idhandler.getCurrent_Document_id());
			System.out.println("field id:"+idhandler.getCurrent_Field_id());
			System.out.println("token id:"+idhandler.getCurrent_Token_id());
			System.out.println("text:"+t.getTerm());
		}
		System.out.println("-----------------------------");
	}

	/**
	 * Test method for {@link com.search.index.SimpleBuildIndex#writeDocument_to_file()}.
	 */
	@Test
	public void testWriteDocument_to_file() {
	}

	/**
	 * Test method for {@link com.search.index.SimpleBuildIndex#writeField_to_file()}.
	 */
	@Test
	public void testWriteField_to_file() {
	}

	/**
	 * Test method for {@link com.search.index.SimpleBuildIndex#writeToken_to_file()}.
	 */
	@Test
	public void testWriteToken_to_file() {
	}

}

/**
 * 
 */
package com.search.index.test;


import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.search.data.Document;
import com.search.index.IndexWriter_to_Database;

/**
 * @author niubaisui
 *
 */
public class IndexWriter_to_DatabaseTest {
	/**
	 * @throws java.lang.Exception
	 */
	private IndexWriter_to_Database indexwriter;
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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.search.index.IndexWriter_to_Database#indexWriter()}.
	 */
	@Test
	public void testIndexWriter() throws Exception{
		LinkedList<Document> documents=new LinkedList<Document>();
		Document document1=new Document(10l);
		document1.setUrl("http://www.suse.edu.cn");
		document1.addIndex_attribute("keyword", "¡ÀΩ·  aiwo");
		document1.setUrl("http://www.aiwo.edu.cn");
		Document document2=new Document(20l);
		document2.addIndex_attribute("title", "juewu dongfangbubai");
		document2.addIndex_attribute("keyword", "dongfangbubai");
		document2.setUrl("http://www.lejie.com");
		Document document3=new Document(30l);
		document3.addIndex_attribute("keyword", "lejie dongfangbubai");
		document3.setRanks(100);
		Document document4=new Document(40l);
		document4.addIndex_attribute("title", "lejie juewu");
		document4.addIndex_attribute("keyword", "aiwo dongfangbubai");
		document4.setRanks(130);
		Document document5=new Document(50l);
		document5.setRanks(120);
		document5.addIndex_attribute("keyword", "dongfangbubai guliang dongfangbubai");
		document5.addIndex_attribute("description", "lejie");
		Document document6=new Document(6l);
		document6.addIndex_attribute("keyword", "∞ÆŒ“");
		document6.setRanks(3000);
		
		documents=new LinkedList<Document>();
		documents.add(document1);
		documents.add(document2);
		documents.add(document3);
		documents.add(document4);
		documents.add(document5);
		documents.add(document6);
		
		indexwriter=new IndexWriter_to_Database(documents);
		indexwriter.indexWriter();
	}

	/**
	 * Test method for {@link com.search.index.IndexWriter_to_Database#getDocuments()}.
	 * @throws Exception 
	 */
	@Ignore
	public void testGetDocuments() throws Exception {
		LinkedList<Document> documents=new LinkedList<Document>();
		Document document=new Document(1000);
		documents.add(document);
		IndexWriter_to_Database index1=new IndexWriter_to_Database(documents);
		index1.indexWriter();
		IndexWriter_to_Database index2=new IndexWriter_to_Database(documents);
		index2.indexWriter();
	}

	/**
	 * Test method for {@link com.search.index.IndexWriter_to_Database#getFields()}.
	 */
	@Test
	public void testGetFields() {
	}

	/**
	 * Test method for {@link com.search.index.IndexWriter_to_Database#getTokens()}.
	 */
	@Test
	public void testGetTokens() {
	}

}

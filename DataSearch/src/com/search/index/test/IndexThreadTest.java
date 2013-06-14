/**
 * 
 */
package com.search.index.test;


import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.search.data.Document;
import com.search.data.PageIDOverException;
import com.search.index.IndexThread;
import com.search.index.SimpleMergeIndex;

/**
 * @author niubaisui
 *
 */
public class IndexThreadTest {

	/**
	 * @throws java.lang.Exception
	 */
	private IndexThread index_thread;
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
	 * Test method for {@link com.search.index.IndexThread#run()}.
	 * @throws PageIDOverException 
	 */
	@Test
	public void testRun() throws PageIDOverException,Exception{
		LinkedList<Document> documents=new LinkedList<Document>();
		Document document1=new Document(1l);
		document1.addIndex_attribute("keyword", "¡ÀΩ·");
		Document document2=new Document(2l);
		document2.addIndex_attribute("keyword", "dongfangbubei");
		Document document3=new Document(3l);
		document3.addIndex_attribute("keyword", "lejie");
		Document document4=new Document(4l);
		document4.addIndex_attribute("keyword", "aiwo");
		Document document5=new Document(5l);
		document5.addIndex_attribute("keyword", "dongfangbubei guliang");
		Document document6=new Document(6l);
		document6.addIndex_attribute("keyword", "∞ÆŒ“");
		
		documents=new LinkedList<Document>();
		documents.add(document1);
		documents.add(document2);
		documents.add(document3);
		documents.add(document4);
		documents.add(document5);
		documents.add(document6);
		
		SimpleMergeIndex simplemergeindex=new SimpleMergeIndex(documents);
		simplemergeindex.mergeIndex();
		
		index_thread=new IndexThread(simplemergeindex.getToken_Structure(),null);
		index_thread.run();
		
	}

}

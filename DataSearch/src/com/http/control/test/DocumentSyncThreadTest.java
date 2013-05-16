/**
 * 
 */
package com.http.control.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.http.control.DocumentSyncThread;
import com.search.data.Document;

/**
 * @author niubaisui
 *
 */
public class DocumentSyncThreadTest {

	/**
	 * @throws java.lang.Exception
	 */
	
	private Document document;
	private DocumentSyncThread syncthread;
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
	 * Test method for {@link com.http.control.DocumentSyncThread#run()}.
	 */
	@Test
	public void testRun() throws Exception {
		syncthread=new DocumentSyncThread();
		document=new Document(1l);
		document.setUrl("http://www.suse.edu.cn");
		syncthread.start();
	}

}

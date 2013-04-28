/**
 * 
 */
package com.search.analyzer.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.search.analyzer.SimpleAnalyzer;
import com.search.data.Token;

/**
 * @author niubaisui
 *
 */
public class SimpleAnalyzerTest {

	/**
	 * @throws java.lang.Exception
	 */
	private static SimpleAnalyzer simpleanalyzer;
	private static String str="Internet中有一个专门组织IANA来确认标准的MIME类型";
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		simpleanalyzer=new SimpleAnalyzer(str,true,0);
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
	 * Test method for {@link com.search.analyzer.SimpleAnalyzer#_analyzer()}.
	 * @throws IOException 
	 */
	@Test
	public void test_analyzer() throws IOException {
		LinkedList<String> str=simpleanalyzer._analyzer();
		for(String s:str){
			System.out.println(s);
		}
		System.out.println("------------------");
	}

	/**
	 * Test method for {@link com.search.analyzer.SimpleAnalyzer#analyzer()}.
	 * @throws Exception 
	 */
	@Test
	public void testAnalyzer() throws Exception {
		LinkedList<Token> tokens=simpleanalyzer.analyzer();
		for(Token t:tokens){
			System.out.println(t.getID());
			System.out.println(t.getTerm());
			System.out.println("----------------------");
		}
	}

}

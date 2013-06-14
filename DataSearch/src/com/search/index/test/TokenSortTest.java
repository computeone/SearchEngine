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

import com.search.data.Token;

/**
 * @author niubaisui
 *
 */
public class TokenSortTest {

	/**
	 * @throws java.lang.Exception
	 */
	private LinkedList<Token> tokens=new LinkedList<Token>();
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
		tokens.add(new Token("niubaisui",100,1));
		tokens.add(new Token("excepiton",100,2));
		tokens.add(new Token("dongfangbubei",100,3));
		tokens.add(new Token("lejie",100,4));
		tokens.add(new Token("∞ÆŒ“",100,5));
		tokens.add(new Token("Â–“£",100,6));
		tokens.add(new Token("¡ÀΩ·",100,7));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		tokens=null;
	}

	/**
	 * Test method for {@link com.search.index.TokenSort#Sort(java.util.LinkedList)}.
	 */
	@Test
	public void testSortLinkedListOfToken() {
		
	}

	/**
	 * Test method for {@link com.search.index.TokenSort#MergeSort(java.util.LinkedList, java.util.LinkedList)}.
	 */
	@Test
	public void testMergeSort() {
	}

}

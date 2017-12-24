/**
 * 
 */
package com.search.search.test;


import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.search.search.FieldCompare_Matcher;
import com.common.*;
import com.search.data.Field;
import com.search.data.FieldIDOverException;

/**
 * @author niubaisui
 *
 */
public class ShellSortTest {

	/**
	 * @throws java.lang.Exception
	 */
	
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
	 * Test method for {@link com.search.Search.ShellSort#Sort(java.util.LinkedList, java.util.Comparator)}.
	 * @throws FieldIDOverException 
	 */
	@Test
	public void testSort() throws FieldIDOverException {
		LinkedList<Field>  fields=new LinkedList<Field>();
		
		Field f1=new Field("",1l,1l);
		f1.addAttribute("matcher", String.valueOf(200));
		Field f2=new Field("",2l,1l);
		f2.addAttribute("matcher", String.valueOf(223));
		Field f3=new Field("",100l,1l);
		f3.addAttribute("matcher", String.valueOf(245));
		Field f4=new Field("",267l,1l);
		f4.addAttribute("matcher", String.valueOf(235));
		Field f5=new Field("",154l,1l);
		f5.addAttribute("matcher", String.valueOf(224));
		Field f6=new Field("",245l,1l);
		f6.addAttribute("matcher", String.valueOf(223));
		Field f7=new Field("",156l,1l);
		f7.addAttribute("matcher", String.valueOf(217));
		Field f8=new Field("",453l,1l);
		f8.addAttribute("matcher", String.valueOf(257));
		
		fields.add(f1);
		fields.add(f2);
		fields.add(f3);
		fields.add(f4);
		fields.add(f5);
		fields.add(f6);
		fields.add(f7);
		fields.add(f8);
		
		ShellSort.Sort(fields, new FieldCompare_Matcher());
		
		for(Field f:fields){
			String matcher=f.getAttriubte("matcher");
			System.out.println(Integer.parseInt(matcher));
		}
		
		
	}

}

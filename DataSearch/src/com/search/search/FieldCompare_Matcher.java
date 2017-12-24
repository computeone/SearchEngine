/**
 * 
 */
package com.search.search;

import java.util.Comparator;

import com.search.data.Field;

/**
 * @author niubaisui
 *
 */
public class FieldCompare_Matcher implements Comparator<Field>{

	@Override
	public int compare(Field f1, Field f2) {
		// TODO Auto-generated method stub
		String matcher1=f1.getAttriubte("matcher");
		String matcher2=f2.getAttriubte("matcher");
		if(Integer.parseInt(matcher1)>Integer.parseInt(matcher2)){
			return 1;
		}
		
		if(Integer.parseInt(matcher1)==Integer.parseInt(matcher2)){
			return 0;
		}
		else{
			return -1;
		}
	}
}

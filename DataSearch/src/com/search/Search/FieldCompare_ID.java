package com.search.Search;

import java.util.Comparator;

import com.search.data.Field;

/*
 * 
 */
public class FieldCompare_ID implements Comparator<Field>{

	@Override
	public int compare(Field f1, Field f2) {
		if(f1.getID()>f2.getID()){
			return 1;
		}
		else if(f1.getID()==f2.getID()){
			return 0;
		}
		else{
			return -1;
		}
	}
}

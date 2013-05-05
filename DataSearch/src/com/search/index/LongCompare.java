package com.search.index;

import java.util.Comparator;

public class LongCompare implements Comparator<Long>{

	@Override
	public int compare(Long o1, Long o2) {
		if(o1>o2){
			return 1;
		}
		else if(o1==o2){
			return 0;
		}
		else{
			return -1;
		}
	}

	
}

package com.search.Search;

import java.util.Comparator;

public class SearchResultCompare_ID implements Comparator<SearchResult>{

	@Override
	public int compare(SearchResult s1, SearchResult s2) {
		if(s1.getID()>s2.getID()){
			return 1;
		}
		else if(s1.getID()==s2.getID()){
			return 0;
		}
		else{
			return -1;
		}
	}

}

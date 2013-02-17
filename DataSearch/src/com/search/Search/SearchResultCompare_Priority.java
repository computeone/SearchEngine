package com.search.Search;

import java.util.Comparator;

public class SearchResultCompare_Priority implements Comparator<SearchResult>{

	@Override
	public int compare(SearchResult s1, SearchResult s2) {
		if(s1.getPriority()>s2.getPriority()){
			return -1;
		}
		else if(s1.getPriority()==s2.getPriority()){
			return 0;
		}
		else{
			return 1;
		}
	}

}

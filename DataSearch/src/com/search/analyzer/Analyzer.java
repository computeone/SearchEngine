package com.search.analyzer;

import java.util.LinkedList;
/*
 * 
 */
import com.search.data.Token;

public interface Analyzer {
	public LinkedList<Token> analyzer() throws Exception;
}

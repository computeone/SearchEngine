package com.search.analyzer;

import com.search.data.Token;

public interface Analyzer {
	public Token[] analyzer() throws Exception;
}

package com.search.Search;

import com.search.data.Field;

//搜索得到的结果
public class SearchResult {
	private Field field;
	private String url;

	public SearchResult(Field field, String url) {
		this.field = field;
		this.url = url;
	}

	public int getPriority() {
		return field.getPriority();
	}

	public String getContent() {
		return field.getText();
	}

	public String getURL() {
		return url;
	}
}

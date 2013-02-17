package com.http.connect.filter;

public interface HttpURLFilterChain {
	public void addFilter(HttpURLFilter filter);

	public HttpURLFilter getFilter();

}

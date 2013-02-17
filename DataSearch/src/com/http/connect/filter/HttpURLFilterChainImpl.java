package com.http.connect.filter;

import java.util.Stack;

public class HttpURLFilterChainImpl implements HttpURLFilterChain {

	private Stack<HttpURLFilter> filterstack;

	public HttpURLFilterChainImpl() {
		filterstack = new Stack<HttpURLFilter>();
	}

	@Override
	public void addFilter(HttpURLFilter filter) {
		filterstack.push(filter);
	}

	@Override
	public HttpURLFilter getFilter() {
		return filterstack.pop();
	}
}

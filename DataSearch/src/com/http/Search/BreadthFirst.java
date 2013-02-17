package com.http.Search;

public interface BreadthFirst {
	public void addURLVisited(String url);

	public void addUNURLVisited(String url);

	public String getUNVisitedURL();

	public int getSizeVisited();

	public int getSizeUNVisited();
}

package com.search.search;


/*
 * 搜索得到的结果
 */
public class SearchResult {
	private String url;
	private int rank;
	private String content;
	
	public SearchResult(String url,int rank,String content) {
		this.url=url;
		this.rank=rank;
		this.content=content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

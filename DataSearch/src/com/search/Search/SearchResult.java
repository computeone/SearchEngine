package com.search.Search;

import com.search.data.Field;

//搜索得到的结果
public class SearchResult {
	private Field field;
	private String url;
	private long id;
	private String term;
	
	public SearchResult(Field field, String url) {
		this.field = field;
		this.url = url;
	}

	public void setTerm(String term){
		this.term=term;
	}
	public String getTerm(){
		return this.term;
	}
	public void setID(long id){
		this.id=id;
	}
	public long getID(){
		return id;
	}
	public void setPriority(int priority){
		this.field.setPriority(priority);
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

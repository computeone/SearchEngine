package com.http.Search;



import java.util.LinkedList;

import com.search.data.Document;
/*
 * 
 * 
 */
public class WebPage {
	private LinkedList<Document> document;
	public WebPage() {
		document=new LinkedList<Document>();
	}
	
	public void addDocument(Document document){
		this.document.addLast(document);
	}
	
	public Document nextDocument(){
		if(document.isEmpty()){
			return null;
		}
		else {
			return document.pollFirst();
		}
	}
	public boolean hasNext(){
		if(document.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
}

package com.http.Search;



import java.util.LinkedList;

import com.search.data.Document;
/*
 * 
 * 
 */
public class WebPage {
	private  LinkedList<Document> documents;
	public WebPage() {
		documents=new LinkedList<Document>();
	}
	
	public synchronized void addDocument(Document document){
		this.documents.addLast(document);
	}
	
	public synchronized Document nextDocument(){
		return documents.pollFirst();
	}
	
	public synchronized int getSize(){
		return documents.size();
	}
	public synchronized boolean hasNext(){
		if(documents.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
}

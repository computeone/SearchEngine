package com.http.Search;



import java.util.LinkedList;

import com.search.data.Document;
/*
 * 
 * 
 */
public class WebPage {
	private LinkedList<Document> documents;
	public WebPage() {
		documents=new LinkedList<Document>();
	}
	
	public void addDocument(Document document){
		this.documents.addLast(document);
	}
	
	public Document nextDocument(){
		return documents.pollFirst();
	}
	public boolean hasNext(){
		if(documents.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
}

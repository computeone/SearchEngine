/**
 * 
 */
package com.search.index;

import java.util.LinkedList;

import com.search.data.Document;
import com.search.data.Field;

/**
 * @author niubaisui
 *
 */
public class IndexWriter_to_Database implements IndexWriter{

	private LinkedList<Document> documents=new LinkedList<Document>();
	private LinkedList<Field> fields=new LinkedList<Field>();
	private LinkedList<Token_Structure> tokens=new LinkedList<Token_Structure>();
	

	public IndexWriter_to_Database(LinkedList<Document> documents){
		this.documents=documents;
	}
	//主要的方法
	@Override
	public void indexWriter() throws Exception {
		int size=documents.size();
		int count=size/100;
		//document_thread线程数最大为5
		if(count>5){
			count=5;
		}
		
		//
		if(size%100!=0){
			count++;
		}
		
		LinkedList<Document> document=new LinkedList<Document>();
		int n=0;
		for(int i=0;i<count;i++){
			
			if(i==count-1){
				for(int j=0;j<documents.size()-(count-1)*100;j++){
					document.add(documents.get(n++));
				}
			}
			else{
				for(int j=0;j<100;j++){
					document.add(documents.get(n++));
				}
			}
			SimpleMergeIndex mergeindex=new SimpleMergeIndex(document);
			mergeindex.mergeIndex();
			
			//启动一个document_thread线程
			DocumentThread document_thread=new DocumentThread(document);
			document_thread.run();
			
			//启动一个field_thread线程
			FieldThread field_thread=new FieldThread(mergeindex.getField());
			field_thread.run();
			
			//启动一个index线程
			IndexThread index_thread=new IndexThread(mergeindex.getToken_Structure());
			index_thread.run();
		}
	}
	
	public LinkedList<Document> getDocuments() {
		return documents;
	}
	
	public LinkedList<Field> getFields() {
		return fields;
	}
	
	public LinkedList<Token_Structure> getTokens() {
		return tokens;
	}
	
}

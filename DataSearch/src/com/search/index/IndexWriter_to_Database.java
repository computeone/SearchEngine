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
	

	@Override
	public void indexWriter() throws Exception {
			
	}
	public LinkedList<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(LinkedList<Document> documents) {
		this.documents = documents;
	}
	public LinkedList<Field> getFields() {
		return fields;
	}
	public void setFields(LinkedList<Field> fields) {
		this.fields = fields;
	}
	public LinkedList<Token_Structure> getTokens() {
		return tokens;
	}
	public void setTokens(LinkedList<Token_Structure> tokens) {
		this.tokens = tokens;
	}

}

/**
 * 
 */
package com.search.index;

import java.util.LinkedList;

import com.search.data.Document;
import com.search.data.Field;
import com.search.data.Token;

/**
 * @author niubaisui
 *
 */
public class Index {
	
	private Document document;
	private LinkedList<Field> fields;
	private LinkedList<Token> tokens;
	
	public Index(Document document,LinkedList<Field> fields,LinkedList<Token> tokens){
		this.document=document;
		this.fields=fields;
		this.tokens=tokens;
	}
	public Document getDocument() {
		return document;
	}
	public LinkedList<Field> getFields() {
		return fields;
	}
	public LinkedList<Token> getTokens() {
		return tokens;
	}
	

}

package com.search.index;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

import com.search.analyzer.SimpleAnalyzer;
import com.search.data.Attribute;
import com.search.data.Field;
import com.search.data.IDhandler;
import com.search.data.Document;
import com.search.data.Token;
/*
 * 
 * 一个简单的buildindex
 */
public class SimpleBuildIndex implements BuildIndex{
	private Document document;
	private LinkedList<Field> fields=new LinkedList<Field>();
	private LinkedList<Token> tokens=new LinkedList<Token>();
	private String dirpath;
	private Index index;
	private long id;

	public SimpleBuildIndex(Document document) throws IOException {
		this.document=document;
		this.id=document.getID();
	}


	public void setPath(String dirpath) {
		this.dirpath = dirpath;
	}

	@Override
	public Index buildIndex() throws Exception {
		buildField();
		buildToken();
		index=new Index(document,fields,tokens);
		return index;
	}

	public Document getDocuemnt() {
		return document;
	}

	public LinkedList<Field> getField() {
		return fields;
	}

	public LinkedList<Token> getTokens() {
		return tokens;
	}

	// 将String的数据写到Field中
	private void buildField() throws Exception {
		
		LinkedHashMap<String,Attribute> index_attributes=document.getIndex_attributes();
		if(index_attributes.size()!=0){
			Set<String> keys=index_attributes.keySet();
			for(String s:keys){
				//判断关键字不为空
				if(s!=null){
					Attribute attribute=index_attributes.get(s);
					
					//判断值不为空
					if(attribute.getValue()!=null){
						Field field=new Field(attribute.getValue(),id,attribute.getIndex());
						fields.add(field);
					}
				}			
			}
		}
		
	}

	//构建token
	private void buildToken() throws Exception {
		SimpleAnalyzer analyzer=new SimpleAnalyzer(" ",true,1l);
		for(Field f:fields){
			analyzer.setContent(f.getText());
			analyzer.setID(f.getID());
			LinkedList<Token> t=analyzer.analyzer();
			Iterator<Token> iterator=t.iterator();
			while(iterator.hasNext()){
				tokens.addLast(iterator.next());
			}
		}
	}

	// 将page写到文件中
	public void writeDocument_to_file() throws IOException {
		
		File page_file = new File(dirpath, String.valueOf(id) + ".document");
		FileOutputStream out = new FileOutputStream(page_file);
		out.write(String.valueOf(document.getID()).getBytes());
		out.write('\n');
		out.write(String.valueOf(document.getRanks()).getBytes());
		out.write('\n');
		out.write(String.valueOf(document.getDate().getTime()).getBytes());
		out.close();

	}

	// 将field写入到文件中
	public void writeField_to_file() throws IOException {
		IDhandler idhandler = new IDhandler(1l);
		for(Field f:fields){
			idhandler.setID(f.getID());
			File file=new File(dirpath,String.valueOf(idhandler.getCurrent_Field_id())+".field");
			
			FileOutputStream out=new FileOutputStream(file);
			out.write('\n');
			out.write(String.valueOf(f.getID()).getBytes());
			out.write('\n');
			out.write(String.valueOf(f.getPriority()).getBytes());
			out.write('\n');
			out.write(f.getText().getBytes());
			
			//
			out.close();
		}
	}

	// 将token写入到文件中
	public void writeToken_to_file() throws IOException {
		IDhandler idhandler = new IDhandler(1l);
		for(Token t:tokens){
			idhandler.setID(t.getID());
			File file=new File(dirpath,String.valueOf(idhandler.getCurrent_Token_id())+".token");
			FileOutputStream out=new FileOutputStream(file);
			out.write(String.valueOf(t.getID()).getBytes());
			out.write('\n');
			out.write(String.valueOf(t.getTerm()).getBytes());	
			out.close();
		}
	}

	public void writerPage_to_database() {

	}

	public void writerField_to_database() {

	}

	public void writerToken_to_database() {

	}

}

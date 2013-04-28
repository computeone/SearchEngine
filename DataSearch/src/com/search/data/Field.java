package com.search.data;

import java.util.HashMap;

public class Field {
	private long ID = 1;
	private String text;
	private int priority = 0;
	private final int Max_Row_Count = 1 << 20;// 文档最大有一百万行
	//属性，可以扩展，也就是说可以自定义多种属性
	private HashMap<String,String> attributes=new HashMap<String,String>();
	private int indexcount=1;
	private HashMap<String,Integer> index_number=new HashMap<String,Integer>();

	// id为Document的id号，offset是当前文档中的偏移量
	public Field(String text, long id, long offset) throws FieldIDOverException {
		if (offset < Max_Row_Count) {
			this.text = text;
			this.ID = id+ (offset << 20);
		} else {
			throw new FieldIDOverException();
		}
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public String getText() {
		return text;
	}

	public long getID() {
		return this.ID;
	}
	
	public void addAttribute(String k,String v){
		index_number.put(k, indexcount++);
		attributes.put(k, v);
	}
	
	public String getAttribute(String k){
		return attributes.get(k);
	}
	
	public HashMap<String,String> getAttributes(){
		return attributes;
	}
	
	public int getIndexcount(String k){
		return index_number.get(k);
	}
}

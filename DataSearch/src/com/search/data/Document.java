package com.search.data;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
/*
 * ID为64位的long型整数Document中的24位用来表示可索引的文档的最大数量，20位用来表示文档中的最大行数，
 *20位表示一行中的词条的最大数量(在目前是足够的。。。）
 */


public class Document {
	private long ID = 1;// 文档号
	private Timestamp date; 
	private int ranks;
	private String url;
	private LinkedHashMap<String,Attribute> index_attributes=new LinkedHashMap<String,Attribute>();
	private HashMap<String,String> store_attributes=new HashMap<String,String>();   
	private int indexcount=1;
	private final long Max_Page_ID = 1 << 24;
	/*
	 * 用索引号将索引绑定
	 */
	
	//当前文档的id号，例如1,2,3等，
	public Document(long id) throws PageIDOverException {
		if (id < this.Max_Page_ID) {
			ID = id * ID << 40;
			this.date = new Timestamp(Calendar.getInstance().getTimeInMillis());
		} else {
			throw new PageIDOverException();
		}
	}

	public long getID() {
		return ID;
	}

	public void setRanks(int ranks){
		this.ranks=ranks;
	}
	
	public int getRanks(){
		return ranks;
	}
	
	public void setDate(Timestamp date){
		this.date=date;
	}
	
	public Timestamp getDate() {
		return date;
	}

	public void setUrl(String url){
		this.url=url;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void addIndex_attribute(String key,String value){
		Attribute attribute=new Attribute(key,value);
		attribute.setIndex(indexcount++);
		index_attributes.put(key, attribute);
	}
	
	
	public void addStore_attribute(String key,String value){
		store_attributes.put(key, value);
	}
	
	public void alterStore_attribute(String key,String new_value){
		String value=store_attributes.get(key);
		if(value!=null){
			this.addStore_attribute(key, new_value);
		}
	}
	public String getIndex_attribute(String key){
		return index_attributes.get(key).getValue();
	}
	
	public String getStore_attriubte(String key){
		return store_attributes.get(key);
	}
	
	public LinkedHashMap<String,Attribute> getIndex_attributes() {
		return index_attributes;
	}
	public int getIndex_count(String key){
		return index_attributes.get(key).getIndex();
	}

	public HashMap<String,String> getStore_attributes() {
		return store_attributes;
	}
	
}

package com.search.data;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
//ID为64位的long型整数Page中的24位用来表示可索引的文档的最大数量，20位用来表示文档中的最大行数，
//20位表示一行中的词条的最大数量
public class Page {
	private long ID=1;//文档号
	private Calendar date;
	private final long Max_Page_ID=1<<24;
	public Page(long id) throws PageIDOverException{
		if(id<this.Max_Page_ID){
			ID=id*ID<<40;
			this.date=Calendar.getInstance();
		}
		else{
			throw new PageIDOverException();
		}
	}
	public Calendar getDate(){
		return date;
	}
	public long getID(){
		return ID;
	}
	public static void main(String[] args) throws Exception {
		for(int i=0;i<256;i++){
			Page page=new Page(i);
			System.out.println(page.getID());
		}
	}
}

package com.search.data;
//分离ID号
public class IDhandler {
	private long id;
	public IDhandler(long id){
		this.id=id;
	}
	public void setID(long id){
		this.id=id;
	}
	public long getPage_id(){
		long a=1;
		a=~((a<<40)-1);//首先得到类似的掩码 1111111111 00000000格式
		a=a&id;//与运算得到pageID
		return a;
	}
	public long getField_id(){
		long a=((long)1<<40)-1;//先构造类似的掩码  000000000 1111111111格式
		a=a^(((long)1<<20)-1);//再构造类似的结构   00000000 111111111 00000000格式
		a=a&id;//与运算得到fieldID
		return a;
	}
	public long Field_id(){
		long a=((long)1<<20)-1;
		a=~a;
		a=a&id;
		return a;
	}
	public long getToken_id(){
		long a=((long)1<<20)-1;//先构造类似的掩码 00000000000 111111111
		a=a&id;//与运算得到TokenID
		return a;
	}
	public static void main(String[] args) {
		long id=704786960744459L;
		IDhandler handler=new IDhandler(id);
		System.out.println(handler.getPage_id());
		System.out.println(handler.getField_id());
		System.out.println(handler.getToken_id());
		System.out.println(handler.Field_id());
	}
}

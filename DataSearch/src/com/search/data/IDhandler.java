package com.search.data;

//分离ID号
public class IDhandler {
	private long id;

	public IDhandler(long id) {
		this.id = id;
	}

	public void setID(long id) {
		this.id = id;
	}

	//分离出在当前的文档号
	public long getCurrent_Document_id(){
		long a=id;
		a=a>>40;
		return a;
	}
	//得到documentid左动40位后产生的
	public long getDocumnent_id() {
		long a = 1;
		a = ~((a << 40) - 1);// 首先得到类似的掩码 1111111111 00000000格式
		a = a & id;// 与运算得到pageID
		return a;
	}

	//分离出来行号即field id
	public long getField_id() {
		long a=id;
		a=a>>20;
		a=a<<20;
		return a;
	}
	//得到Field在当前文档中的号
	public long getCurrent_Field_id() {
		long a=id;
		a=a<<24;
		a=a>>44;
		return a;
	}

	//分离出在当前文档中的偏移量
	public long getCurrent_Token_id() {
		long a=id;
		a=a<<44;
		a=a>>44;
		return a;
	}
}

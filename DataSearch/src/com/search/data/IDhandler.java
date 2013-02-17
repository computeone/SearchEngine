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

	public long getPage_id() {
		long a = 1;
		a = ~((a << 40) - 1);// 首先得到类似的掩码 1111111111 00000000格式
		a = a & id;// 与运算得到pageID
		return a;
	}

	//分离出来行号即field id
	public long getField_id() {
		long a = ((long) 1 << 40) - 1;// 先构造类似的掩码 000000000 1111111111格式
		a = a ^ (((long) 1 << 20) - 1);// 再构造类似的结构 00000000 111111111 00000000格式
		a = a & id;// 与运算得到fieldID
		return a;
	}
	//得到文档号+行号
	public long Field_id() {
		long a = ((long) 1 << 20) - 1;
		a = ~a;
		a = a & id;
		return a;
	}

	//分离出行的偏移量
	public long getToken_id() {
		long a = ((long) 1 << 20) - 1;// 先构造类似的掩码 00000000000 111111111
		a = a & id;// 与运算得到TokenID
		return a;
	}
}

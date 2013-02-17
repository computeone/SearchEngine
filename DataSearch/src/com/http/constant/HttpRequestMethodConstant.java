package com.http.constant;

public class HttpRequestMethodConstant {
	public  static final String GET="get";//请求服务器文档
	public static final String HEAD="head";//请求关于文档的信息，但不是这个文档本身
	public static final String POST="post";//从客户向服务器发送一些信息
	public static final String PUT="put";//从服务器向客户发送回送
	public static final String TRACE="trace";//把到达的请求回送
	public static final String CONNECT="connect";//保留
	public static final String DELETE="delete";//删除Web网页
	public static final String OPTIONs="options";//询问关于可用的选项
}

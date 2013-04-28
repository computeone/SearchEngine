package com.http.connect;


/*
 *
 */
public class HttpResponseHeader {
	private String Date;// 给出当前日期
	private String Upgrade;// 指明优先使用的通信协议
	private String Server;// 给出服务器的相关信息
	private String Set_Cookie;// 服务器请求客户保存cookie
	private String Content_Encoding;// 指明编码方案
	private String Content_Language;// 指明语言
	private String Content_Length;// 给出文档的长度
	private String Content_Type;// 指明媒体类型
	private String Location;// 请求客户将请求发送到另一站点
	private String Accept_Ranges;// 服务器将接受请求的字节范围
	private String Last_modefied;// 给出上次改变的日期和时间

	public HttpResponseHeader() {
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getUpgrade() {
		return Upgrade;
	}

	public void setUpgrade(String upgrade) {
		Upgrade = upgrade;
	}

	public String getServer() {
		return Server;
	}

	public void setServer(String server) {
		Server = server;
	}

	public String getSet_Cookie() {
		return Set_Cookie;
	}

	public void setSet_Cookie(String set_Cookie) {
		Set_Cookie = set_Cookie;
	}

	public String getContent_Encoding() {
		return Content_Encoding;
	}

	public void setContent_Encoding(String content_Encoding) {
		Content_Encoding = content_Encoding;
	}

	public String getContent_Language() {
		return Content_Language;
	}

	public void setContent_Language(String content_Language) {
		Content_Language = content_Language;
	}

	public String getContent_Length() {
		return Content_Length;
	}

	public void setContent_Length(String content_Length) {
		Content_Length = content_Length;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getContent_Type() {
		return Content_Type;
	}

	public void setContent_Type(String content_Type) {
		Content_Type = content_Type;
	}

	public String getAccept_Ranges() {
		return Accept_Ranges;
	}

	public void setAccept_Ranges(String accept_Ranges) {
		Accept_Ranges = accept_Ranges;
	}

	public String getLast_modefied() {
		return Last_modefied;
	}

	public void setLast_modefied(String last_modefied) {
		Last_modefied = last_modefied;
	}

}

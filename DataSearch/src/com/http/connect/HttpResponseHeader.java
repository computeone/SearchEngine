package com.http.connect;

import java.util.List;

public class HttpResponseHeader {
	private List<String> Date;// 给出当前日期
	private List<String> Upgrade;// 指明优先使用的通信协议
	private List<String> Server;// 给出服务器的相关信息
	private List<String> Set_Cookie;// 服务器请求客户保存cookie
	private List<String> Content_Encoding;// 指明编码方案
	private List<String> Content_Language;// 指明语言
	private List<String> Content_Length;// 给出文档的长度
	private List<String> Content_Type;// 指明媒体类型
	private List<String> Location;// 请求客户将请求发送到另一站点
	private List<String> Accept_Ranges;// 服务器将接受请求的字节范围
	private List<String> Last_modefied;// 给出上次改变的日期和时间

	public HttpResponseHeader() {
	}

	public List<String> getDate() {
		return Date;
	}

	public void setDate(List<String> date) {
		Date = date;
	}

	public List<String> getUpgrade() {
		return Upgrade;
	}

	public void setUpgrade(List<String> upgrade) {
		Upgrade = upgrade;
	}

	public List<String> getServer() {
		return Server;
	}

	public void setServer(List<String> server) {
		Server = server;
	}

	public List<String> getSet_Cookie() {
		return Set_Cookie;
	}

	public void setSet_Cookie(List<String> set_Cookie) {
		Set_Cookie = set_Cookie;
	}

	public List<String> getContent_Encoding() {
		return Content_Encoding;
	}

	public void setContent_Encoding(List<String> content_Encoding) {
		Content_Encoding = content_Encoding;
	}

	public List<String> getContent_Language() {
		return Content_Language;
	}

	public void setContent_Language(List<String> content_Language) {
		Content_Language = content_Language;
	}

	public List<String> getContent_Length() {
		return Content_Length;
	}

	public void setContent_Length(List<String> content_Length) {
		Content_Length = content_Length;
	}

	public List<String> getLocation() {
		return Location;
	}

	public void setLocation(List<String> location) {
		Location = location;
	}

	public List<String> getContent_Type() {
		return Content_Type;
	}

	public void setContent_Type(List<String> content_Type) {
		Content_Type = content_Type;
	}

	public List<String> getAccept_Ranges() {
		return Accept_Ranges;
	}

	public void setAccept_Ranges(List<String> accept_Ranges) {
		Accept_Ranges = accept_Ranges;
	}

	public List<String> getLast_modefied() {
		return Last_modefied;
	}

	public void setLast_modefied(List<String> last_modefied) {
		Last_modefied = last_modefied;
	}

}

package com.http.connect;

/*
 * 
 */
public class HttpRequestHeader {
	private String User_agent;// 标志客户程序
	private String Accept;// 给出客户能够接受的媒体格式
	private String Accept_charset;// 给出客户能够处理的字符集
	private String Accept_encoding;// 给出客户能够处理的编码方案
	private String Accpet_language;// 给出客户能够接受的语言
	private String Authorization;// 给出客户具有何种准许
	private String Host;// 给出客户的主机和端口号
	private String Date;// 给出当前日期
	private String Upgrade;// 指明优先使用的通信协议
	private String Cookie;// 把cookie回送给服务器
	private String If_Modified_Since;// 只有在指明日期以后更新的文档才发送

	public String getUser_agent() {
		return User_agent;
	}

	public void setUser_agent(String user_agent) {
		User_agent = user_agent;
	}

	public String getAccept() {
		return Accept;
	}

	public void setAccept(String accept) {
		Accept = accept;
	}

	public String getAccept_encoding() {
		return Accept_encoding;
	}

	public void setAccept_encoding(String accept_encoding) {
		Accept_encoding = accept_encoding;
	}

	public String getAccept_charset() {
		return Accept_charset;
	}

	public void setAccept_charset(String accept_charset) {
		Accept_charset = accept_charset;
	}

	public String getAccpet_language() {
		return Accpet_language;
	}

	public void setAccpet_language(String accpet_language) {
		Accpet_language = accpet_language;
	}

	public String getAuthorization() {
		return Authorization;
	}

	public void setAuthorization(String authorization) {
		Authorization = authorization;
	}

	public String getHost() {
		return Host;
	}

	public void setHost(String host) {
		Host = host;
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

	public String getCookie() {
		return Cookie;
	}

	public void setCookie(String cookie) {
		Cookie = cookie;
	}

	public String getIf_Modified_Since() {
		return If_Modified_Since;
	}

	public void setIf_Modified_Since(String if_Modified_Since) {
		If_Modified_Since = if_Modified_Since;
	}
}

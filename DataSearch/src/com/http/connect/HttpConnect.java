package com.http.connect;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.http.connect.filter.HttpURLFilterChain;
import com.http.constant.HttpResponseHeaderConstant;

public class HttpConnect {
	private String protocol;// 所使用的协议
	private String url;// 网络资源的URL
	private InputStream in;
	private Map<String, List<String>> fields;
	private HttpResponseHeader httpresponseheader;
	private HttpURLConnection httpconnect;
	private Logger logger = LogManager.getLogger("HttpConnect");

	public HttpConnect(String url) {
		this.url = url;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InputStream getInputStream() {
		return this.in;
	}

	public Map<String, List<String>> getFields() {
		return this.fields;
	}

	public void registerHttpURLParser(HttpURLParser httpurlparser) {
		logger.debug(this.toString() + ":" + "register HttpURLParser");
	}

	public void registerHttpURLFilterChain(HttpURLFilterChain httpurlfilterchain) {
		logger.debug(this.toString() + ":" + "register HttpURLFilterChain");
	}

	public void success() {
	}

	public void discard() throws Exception {
		throw new Exception();
	}

	public void redirect() throws Exception {
		String redirecturl = this.getHttpresponseheader().getLocation().get(0);
		this.setUrl(redirecturl);
		this.Connect();
	}

	public void Connect() throws Exception {// 连接失败在Spiderthread中处理
		HttpURLParserImpl httpparser = new HttpURLParserImpl();
		boolean result = httpparser.vertifyURL(url);
		logger.info(url);
		if (!result) {
			throw new IOException();
		}
		/*
		 * 使用DNS缓存
		 */
		URL urlconnect = new URL(url);
		httpconnect = (HttpURLConnection) urlconnect.openConnection();
		httpconnect.setConnectTimeout(1000);
		if (httpconnect.getResponseCode() == 301
				|| httpconnect.getResponseCode() == 302) {
			this.redirect();
		}
		in = httpconnect.getInputStream();
		fields = httpconnect.getHeaderFields();
		httpresponseheader = new HttpResponseHeader();
		this.setHttpresponseheader();
		// urlconnect.disconnect();//释放http连接
	}

	public HttpResponseHeader getHttpresponseheader() {
		return httpresponseheader;
	}

	// 保存下载下来的文件的响应头
	private void setHttpresponseheader() {
		Set<Map.Entry<String, List<String>>> entryset = fields.entrySet();
		Iterator<Map.Entry<String, List<String>>> iterator = entryset
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<String>> map = iterator.next();
			this.setEntry(map.getKey(), map.getValue());
		}
	}

	// 设置响应头的项
	public void releaseConnect() {
		httpconnect.disconnect();
	}

	private void setEntry(String entry, List<String> value) {
		if (entry == null) {
			return;
		}
		switch (entry) {
		case HttpResponseHeaderConstant.Date:
			httpresponseheader.setDate(value);
			break;
		case HttpResponseHeaderConstant.Upgrade:
			httpresponseheader.setUpgrade(value);
			break;
		case HttpResponseHeaderConstant.Set_Cookie:
			httpresponseheader.setSet_Cookie(value);
		case HttpResponseHeaderConstant.Server:
			httpresponseheader.setServer(value);
			break;
		case HttpResponseHeaderConstant.Location:
			httpresponseheader.setLocation(value);
			break;
		case HttpResponseHeaderConstant.Last_modefied:
			httpresponseheader.setLast_modefied(value);
			break;
		case HttpResponseHeaderConstant.Content_Type:
			httpresponseheader.setContent_Type(value);
			break;
		case HttpResponseHeaderConstant.Content_Length:
			httpresponseheader.setContent_Length(value);
			break;
		case HttpResponseHeaderConstant.Content_Language:
			httpresponseheader.setContent_Language(value);
			break;
		case HttpResponseHeaderConstant.Content_Encoding:
			httpresponseheader.setContent_Encoding(value);
			break;
		case HttpResponseHeaderConstant.Accept_Ranges:
			httpresponseheader.setAccept_Ranges(value);
			break;
		default:
		}
	}

	public void printFields() throws IOException {
		// 解析http的头数据
		Set<Map.Entry<String, List<String>>> entryset = fields.entrySet();
		Iterator<Map.Entry<String, List<String>>> iterator = entryset
				.iterator();
		System.out.println("http header fields:");
		System.out.println();
		while (iterator.hasNext()) {
			Map.Entry<String, List<String>> map = iterator.next();
			System.out.print(map.getKey() + ":");
			List<String> value = map.getValue();
			Iterator<String> valueiterator = value.iterator();
			while (valueiterator.hasNext()) {
				System.out.print(valueiterator.next() + "  ");
			}
			System.out.println();
		}
	}
}

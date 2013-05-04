package com.http.connect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.http.Search.BreadthFirstTraversal;
import com.http.Search.CrawlUrl;
import com.http.constant.HttpResponseHeaderConstant;

/*
 * HTTP连接主要的类
 */
public class HttpConnect {
	private CrawlUrl crawlurl;//抓取到的URL 
	private InputStream in;//网络输出入流
	private OutputStream out;//网络输出流
	private Map<String, List<String>> fields;
	private HttpRequestHeader  httprequestheader;//http请求头
	private HttpResponseHeader httpresponseheader;//http答复头
	private HttpURLConnection httpconnect;
	private Logger logger = LogManager.getLogger("HttpConnect");
	public CrawlUrl getCrawlurl() {
		return crawlurl;
	}

	public void setCrawlUrl(CrawlUrl crawlurl){
		this.crawlurl=crawlurl;
	}
	public InputStream getInputStream() {
		return this.in;
	}

	public OutputStream getOutputStream(){
		return out;
	}
	public Map<String, List<String>> getFields() {
		return this.fields;
	}

	public HttpRequestHeader getHttprequestheader() {
		return httprequestheader;
	}	

	public HttpResponseHeader getHttpresponseheader() {
		return httpresponseheader;
	}
	//
	private void setHttpResponseHeader(Map<String,List<String>> header){
		Set<String> keyset=header.keySet();
		for(String key:keyset){
			
			
			if(key==null){
				continue;
			}
			
			//设置content-Type
			if(key.equals("Content-Type")){
				List<String> content_type=header.get(key);
				String document_type=content_type.get(0);
				if(document_type!=null){
					String[] str=document_type.split(";");
					if(str.length>1){
						httpresponseheader.setContent_Type(str[0]);
						httpresponseheader.setContent_Encoding(str[1].substring(9, str[1].length()));
					}
					else{
						if(str[0].contains("charset=")){
							httpresponseheader.setContent_Encoding(str[0].substring(9, str[0].length()));
						}
						else{
							httpresponseheader.setContent_Type(str[0]);
						}
					}
				}
			}
			//
			else if(key.equals("Last-Modified")){
				httpresponseheader.setLast_modefied(header.get(key).get(0));
			}
			//
			else if(key.equals("Content-Location")){
				httpresponseheader.setLocation(header.get(key).get(0));
			}
			//
			else if(key.equals("Date")){
				
			}
		}
	}
	
	public HttpConnect(){
		
	}
	public HttpConnect(CrawlUrl crawlurl) {
		this.crawlurl=crawlurl;
	}
	/*
	 * 连接成功时，处理方法
	 */
	public void success() throws Exception {
		if (httpconnect.getResponseCode() == 301
				|| httpconnect.getResponseCode() == 302) {
//			this.redirect();
			logger.info("访问失败URL:"+crawlurl.getOriUrl());
		}
		else{
			in = httpconnect.getInputStream();
			fields = httpconnect.getHeaderFields();
			
			
			//设置httpresponseheader和crawlurl
			httpresponseheader = new HttpResponseHeader();
			this.setHttpResponseHeader(fields);
			this.setCrawlUrl();	
			logger.info("成功访问了URL："+crawlurl.getOriUrl());
		}
		
	}

	/*
	 * 丢弃连接
	 */
	public void discard() throws Exception {
		logger.info("访问失败URL:"+crawlurl.getOriUrl());
		throw new Exception();
	}

	/*
	 * 重定向连接
	 */
	public void redirect() throws Exception {
		logger.info("重定向URL:"+crawlurl.getOriUrl());
		String redirecturl = this.getHttpresponseheader().getLocation();
		crawlurl.setOriUrl(redirecturl);
		Connect();
	}
	/*
	 * 连接的主要方法
	 */
	public void Connect() throws Exception {
		
		//访问过的，加入visitiedurl表中
		logger.info("加入VistiedUrl表中URL:"+crawlurl.getOriUrl());
		BreadthFirstTraversal.add_known_URLVisited(crawlurl);
		
		SimpleHttpURLParser httpparser = new SimpleHttpURLParser();
		boolean result = httpparser.vertifyURL(crawlurl.getOriUrl());
		logger.info("连接到url:"+crawlurl.getOriUrl());
		if (!result) {
			throw new IOException();
		}
		
		URL urlconnect = new URL(crawlurl.getOriUrl());
		httpconnect = (HttpURLConnection) urlconnect.openConnection();
//		httpconnect.setConnectTimeout(100);
		if(httpconnect!=null){
			success();
		}
		else{
			discard();
		}
		
	}
	// 设置响应头的项
	public void releaseConnect() {
		httpconnect.disconnect();
	}

	private void setCrawlUrl(){
		crawlurl.setLastUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		this.setEncoding();
		this.setContentType();
		logger.info("encoding:"+crawlurl.getCharSet());
	}
	
	//设置文档类型
	private void setContentType(){
		crawlurl.setType(httpresponseheader.getContent_Type());
	}
	
	//设置文档编码类型
	private void setEncoding() {
		crawlurl.setCharSet(httpresponseheader.getContent_Encoding());
	}
	public void printFields() throws IOException {
		// 解析http的头数据
		System.out.println("Http Header:");
		Set<String> keys=fields.keySet();
		for(String key:keys){
			List<String> list=fields.get(key);
			System.out.println(key+":");
			for(String s:list){
				System.out.println("   "+s);
			}
		}
	}
}

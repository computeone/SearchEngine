package com.http.connect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.http.Search.CrawlUrl;
import com.search.DAO.Connect;
/*
 * 一般用法见FileDownloadTest
 */
public class FileDownload {
	
	//绝对路径表示为rootdir+host+path
	private File file;
	private CrawlUrl crawlurl;
	private Logger logger = LogManager.getLogger("HtmlDownload");
	private String host;// 主机 如:www.baidu.com/index 则host为www.baidu.com
	private String path;// 目录 路径 如：www.baidu.com/index/index.html 则default_filename为index/index.html
	private String rootdir="d:\\spider";// 根目录
	private String encoding="utf-8";// 编码
	private InputStream in;

	// 构造方法
	public FileDownload( InputStream in) {
		this.in = in;
	}
	
	public FileDownload(InputStream in,CrawlUrl crawlurl){
		this.in=in;
		this.crawlurl=crawlurl;
	}
	
	
	public File getFile(){
		return file;
	}
	
	
	public void setCrawlUrl(CrawlUrl crawlurl){
		this.crawlurl=crawlurl;
	}
	
	public CrawlUrl getCrawlUrl(){
		return crawlurl;
	}
	
	public FileDownload(Connect connect){
		//undo
	}
	
	public String getAbsolutePath(){
		return rootdir+"/"+host+"/"+path;
	}

	public String getEncoding() {
		return encoding;
	}

	/*
	 * 得到文档的编码
	 */
	
	// 解析URL result[2]=host result[4]=path
	public String[] parseURL() {
		SimpleHttpURLParser urlparser = new SimpleHttpURLParser();
		String result[] = urlparser.parserURL(crawlurl.getOriUrl());
		this.host = result[2];
		this.path =result[4];
		String[] dirs=null;
		
		//如果符合下面条件返回null
		if(path==null){
			return dirs;
		}
		if(path.equals("")){
			return dirs;
		}
		else {
			dirs = urlparser.parserPath(path);
		}
		return dirs;
	}
	//判断这个网页是不是需要下载
	public boolean isParser() {

		//通过判断返回的文档的类型来判断
		if(crawlurl.getType()==null){
			return true;
		}
		else if (crawlurl.getType().equals("text/html")) {
			return true;
		}
		else{
			return false;
		}
	}

	/*
	 * 文件下载的主要方法
	 */
	public void download() throws Exception {
		logger.info("encoding:"+crawlurl.getCharSet());
		logger.info("文档类型:"+crawlurl.getType());
		
		String[] dirs = this.parseURL();// 解析url
		SimpleHttpURLParser dirparser = new SimpleHttpURLParser();
		
		
		
		
		File hostdir = new File(rootdir + "/" + host);
		hostdir.mkdirs();
		String currentdir = rootdir + "/" + host;
		String filename=null;
		if(dirs==null){
			filename="index.html";
		}
		else{
			// 检查是不是含有非法字符
			for (int i = 0; i < dirs.length; i++) {
				dirs[i] = dirparser.deleteIllegalChar(dirs[i]);
				//长度超过256则截断
				if (dirs[i].length() > 256) {
					dirs[i] = dirs[i].substring(0, 256);
				}
			}
			// 创建相应的目录
			for (int i = 0; i < dirs.length - 1; i++) {
				File tempdir = new File(currentdir, dirs[i]);
				tempdir.mkdir();
				currentdir = currentdir + "/" + dirs[i];
			}
			filename=dirs[dirs.length-1];
		}
			
		// 写到相应的文件中
		file = new File(currentdir+"/"+filename);
		logger.info("下载文件绝对路径:"+file.getAbsolutePath());
		
		
		//写文件
		FileOutputStream fout = new FileOutputStream(file);
		int ch;
		while ((ch = in.read()) != -1) {
			fout.write(ch);
		}
		in.close();
		fout.close();
	}

	
	// 打印文件
	public void printFile() throws Exception {
		FileInputStream in = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(in, encoding);
		int ch;
		while ((ch = reader.read()) != -1) {
			System.out.print((char) ch);
		}
		in.close();
		reader.close();
		
	}

}

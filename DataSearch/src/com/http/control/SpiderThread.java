package com.http.control;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.html.parser.DocumentParser;
import com.html.parser.HtmlLinkFilterChain;
import com.html.parser.HtmlParser;
import com.http.Search.BreadthFirstTraversal;
import com.http.connect.FileDownload;
import com.http.connect.HttpConnect;
import com.http.connect.HttpConnectPool;
import com.http.connect.NOHttpConnectException;

public class SpiderThread extends Thread {
	private String url;
	private int priority;
	private String rootdir;
	private Logger logger = LogManager.getLogger("SpiderThread");
	private LinkedList<String> todoUrl;// 将要访问的url列表

	public String getRootdir() {
		return rootdir;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SpiderThread(String url, int priority) {
		super();
		this.url = url;
		this.rootdir = SpiderWebCentral.rootdir;
		this.priority = priority;
	}

	public LinkedList<String> getURL() {
		return this.todoUrl;
	}

	public void run() {
		logger.info("Task url:" + url);
		HttpConnect httpconnect = null;
		while (url != null) {
			try {
				logger.entry("httpconnect");
				// 创建连接
				httpconnect = HttpConnectPool.getHttpConnect();
				httpconnect.setUrl(url);
				// 建立连接
				httpconnect.Connect();
				httpconnect.printFields();
				logger.exit("httpconnect");
				// 下载文件
				logger.entry("HtmlDownLoad");
				FileDownload htmldownload = new FileDownload(
						httpconnect.getHttpresponseheader(),
						httpconnect.getInputStream(), url, rootdir);
				htmldownload.download(url);
				logger.exit("HtmlDownLoad");
				//下载完成之后释放连接
				httpconnect.releaseConnect();
				HttpConnectPool.releaseHttpConnect(httpconnect);
				logger.debug("Release Connect");
				// htmldownload.printFile();
				// 将访问过的节点天骄的visited列表
				BreadthFirstTraversal.addURLVisited(url);
				// 解析html文件
				logger.entry("HtmlParser");
				logger.debug(htmldownload.isParser());
				//判断是不是可以解析的
				if (htmldownload.isParser()) {
					DocumentParser htmlparser = new HtmlParser();
					htmlparser.setUrl(url);
					// 将解析出来的URL添加进todo列表
					htmlparser
							.registerLinkFilterChain(new HtmlLinkFilterChain());
					todoUrl = htmlparser.parser(htmldownload.getFile(),
							htmldownload.getEncoding());

					BreadthFirstTraversal.addURLVisited(url, priority);
					logger.exit("HtmlParser");
					// 迭代加入表中
					Iterator<String> iterator = todoUrl.iterator();
					while (iterator.hasNext()) {
						String url = iterator.next();
						boolean isExist=BreadthFirstTraversal.addURLVisited(url);
						//如果不在visited中，则增加到unvisitedurl
						if(isExist){
							BreadthFirstTraversal.addUNURLVisited(url);
						}
						
					}
				}
				// 取出来一个优先级最高的url，添加到visitedurl中
				HashMap<String, Integer> map = BreadthFirstTraversal
						.getUNVisitedURL();
				if (map != null) {
					this.url = (String) map.keySet().toArray()[0];
					this.priority = map.get(url);
				} else {
					this.url = null;
				}

			} catch (Exception e) {
				//如果没有http连接了睡眠等待100ms
				if (e instanceof NOHttpConnectException) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						logger.fatal("fatal error! Thread Sleep default!");
					}
					continue;
				}
				//释放连接，取得新的url
				HttpConnectPool.releaseHttpConnect(httpconnect);
				HashMap<String, Integer> map = BreadthFirstTraversal
						.getUNVisitedURL();
				if (map != null) {
					url = (String) map.keySet().toArray()[0];
					this.priority = map.get(url);
				} else {
					url = null;
				}
				logger.fatal("Connect Default! or Download default!");
			}
		}
	}

}

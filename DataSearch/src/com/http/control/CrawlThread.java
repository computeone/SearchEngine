package com.http.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.html.parser.DocumentParser;
import com.html.parser.HtmlLinkFilterChain;
import com.html.parser.HtmlParser;
import com.html.parser.SuseURLFilter;
import com.html.parser.URLFilterChain;
import com.http.traversal.BreadthFirstTraversal;
import com.http.traversal.CrawlUrl;
import com.http.connect.FileDownload;
import com.http.connect.HttpConnect;
import com.http.connect.HttpConnectPool;
import com.http.connect.NOHttpConnectException;
/*
 * 
 */
public class CrawlThread extends Thread {
	private CrawlUrl crawlurl;
	private Logger logger = LogManager.getLogger("CrawlThread");

	public CrawlThread(CrawlUrl crawurl) {
		super();
		this.crawlurl=crawurl;
	}
	public CrawlUrl getCrawlurl() {
		return crawlurl;
	}

	public void setCrawlurl(CrawlUrl crawlurl) {
		this.crawlurl = crawlurl;
	}

	public void run() {
		
		HttpConnect httpconnect = null;
		while (crawlurl!= null) {
			try {
				logger.info("Task url:" + crawlurl.getOriUrl());
				logger.info("层数:"+crawlurl.getLayer());
				logger.entry("httpconnect");
				// 创建连接
				httpconnect = HttpConnectPool.getHttpConnect();
				httpconnect.setCrawlUrl(crawlurl);
				// 建立连接
				httpconnect.Connect();
				httpconnect.printFields();
				//
				logger.exit("httpconnect");
				// 下载文件
				
				FileDownload htmldownload=null;
				if(httpconnect.isDownload()){
					logger.entry("HtmlDownLoad");
					htmldownload= new FileDownload(
						httpconnect.getInputStream());
					htmldownload.setCrawlUrl(httpconnect.getCrawlurl());
					htmldownload.download();
					System.out.println("---------------------------");
					htmldownload.printFile();
					logger.exit("HtmlDownLoad");
				}
				
				//下载完成之后释放连接
				httpconnect.releaseConnect();
				HttpConnectPool.releaseHttpConnect(httpconnect);
				logger.debug("Release Connect");
				// htmldownload.printFile();
				
				
				// 将访问过的节点添加到visited列表
				
				
				// 解析html文件
				if(htmldownload!=null){
					logger.debug("parser:"+htmldownload.isParser());
					//判断是不是可以解析的
					if (htmldownload.isParser()) {
						
						logger.entry("HtmlParser");
						DocumentParser htmlparser = new HtmlParser(htmldownload.getFile(),htmldownload.getCrawlUrl());
										
						// 将解析出来的URL添加进todo列表
						HtmlParser parser=(HtmlParser)htmlparser;
						parser.registerLinkFilterChain(new HtmlLinkFilterChain());
						
						//注册url过滤器
						URLFilterChain chain=new URLFilterChain();
						chain.addFilter(new SuseURLFilter());
						parser.registerURLFilter(chain);
						parser.parser();
						logger.exit("HtmlParser");
						
						// 迭代加入表中
					}
				}
								
				// 取出来一个优先级最高的url，添加到visitedurl中
				CrawlUrl new_crawlurl= BreadthFirstTraversal
						.getUNVisitedURL();
								
				if (new_crawlurl!=null) {
					crawlurl=new_crawlurl;
				} else {
					crawlurl = null;
				}
				logger.info("新的CrawlUrl:"+crawlurl.getOriUrl());
				logger.info("----------------------------------------------");

			} catch (Exception e) {
				//如果没有http连接了睡眠等待100ms
				if (e instanceof NOHttpConnectException) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						logger.fatal("fatal error! Thread Sleep default!");
						logger.info("------------------------------------------");
					}
					continue;
				}
				//释放连接，取得新的url
				HttpConnectPool.releaseHttpConnect(httpconnect);
				CrawlUrl new_crawlurl = BreadthFirstTraversal
						.getUNVisitedURL();
				if (new_crawlurl!= null) {
					crawlurl=new_crawlurl;
				} else {
					crawlurl = null;
				}
				logger.info("新的CrawlUrl:"+crawlurl.getOriUrl());
				logger.fatal("Connect Default! or Download default!");
				logger.info("-----------------------------------------------");
			}
		}
	}

	
}

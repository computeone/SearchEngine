package com.http.Search;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.http.control.CrawlThread;

public class BreadthFirstTraversal {
	// private String rootdir;// 存放下载下来文件的根目录
	private static VisitedURL visitedUrl;
	private static UnvisitedURL unvisitedUrl;
	private Logger logger = LogManager.getLogger("BreadthFirstTraversal");

	// 初始化

	public static BreadthFirstTraversal getBreadthFirstTraversal(
			CrawlUrl[] initurl) throws SQLException, Exception {
		return new BreadthFirstTraversal(initurl);
	}

	private BreadthFirstTraversal(CrawlUrl[] initurl) throws SQLException,
			Exception {
		if (initurl == null) {
			logger.fatal("No InitSeedUrl procedure exit...");
			System.exit(0);
		} else {
			visitedUrl = new VisitedURL();
			unvisitedUrl = new UnvisitedURL();
			for (CrawlUrl url : initurl) {
				logger.info("seed: " + url);
				//
				unvisitedUrl.addURL(url);
			}
		}
	}

	// 添加url到visitedurl,如果存在返回false,不存在返回true,出错返回null
	public static synchronized boolean addURLVisited(CrawlUrl url) {
		try {
			boolean result=visitedUrl.addURL(url);
			return result;
		} catch (Exception e) {
			return (Boolean) null;
		}
	}

	// 添加到unvisitedurl,主要针对知道不存在时插入
	public static synchronized void add_known_URLVisited(CrawlUrl url) {
		try {
			visitedUrl.addURL(url);
		} catch (Exception e) {
			
		}
	}

	// 添加到unvisitedurl,存在返回false,否则返回true
	public static synchronized boolean addUNURLVisited(CrawlUrl url) {
		try {
			boolean result=unvisitedUrl.addURL(url);
			return result;
		} catch (Exception e) {
			return (Boolean) null;
		}
	}
	//没有没有被访问的url了是返回null
	public static synchronized CrawlUrl getUNVisitedURL() {
		CrawlUrl url = null;
		try {
			url = unvisitedUrl.getURL();
		} catch (Exception e) {
			
		}
		return url;
	}

	public static synchronized int getSizeVisited() {
		int size = -1;
		try {
			size = visitedUrl.getSize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	public static synchronized int getSizeUNVisited() {
		int size = -1;
		try {
			size = unvisitedUrl.getSize();
		} catch (Exception e) {
			
		}
		return size;
	}

	// 主要的方法，它是整个程序的执行引擎，触发多个线程同时抓取网页。
	public void Traversal(ThreadPoolExecutor pool) throws Exception {
		CrawlUrl crawlurl = BreadthFirstTraversal.getUNVisitedURL();
		ThreadPoolExecutor spiderpool = pool;
		if (crawlurl != null) {
			spiderpool.execute(new CrawlThread(crawlurl));
		}
	}
}

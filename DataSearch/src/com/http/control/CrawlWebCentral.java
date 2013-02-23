package com.http.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.crawl.document.Document;
import com.http.Search.BreadthFirstTraversal;
import com.http.Search.CrawlUrl;
import com.http.Search.WebPage;
import com.http.connect.HttpConnectPool;

/*
 * 
 */
public class CrawlWebCentral {
	private static Logger logger = LogManager.getLogger(CrawlWebCentral.class
			.getName());
	private Marker Web_Central = MarkerManager.getMarker("WEB_CENTRAL");
	public HttpConnectPool httpconnectpool;
	private ThreadPoolExecutor spiderpool;
	public static String rootdir;
	private static long filenumber = 0;
	public static WebPage webpages=new WebPage();

	public synchronized static void addWebPage(Document document) {
		CrawlWebCentral.webpages.addDocument(document);
	}

	public synchronized static void writeWebPage() {
		try {
			String path = "e:\\search";
			while (!webpages.hasNext()) {
				File file = new File(path, "datafile" + filenumber);
				FileOutputStream fileout = new FileOutputStream(file);
				Document document = webpages.nextDocument();
				fileout.write("url:".getBytes());
				fileout.write(document.getUrl().getBytes());
				fileout.write("\n".getBytes());
				fileout.write("keyword".getBytes());
				fileout.write(document.getKeyword().getBytes());
				fileout.write("\n".getBytes());
				fileout.write("title".getBytes());
				fileout.write(document.getTitle().getBytes());
				fileout.write("\n".getBytes());
				fileout.close();
				filenumber++;
			}
		} catch (IOException e) {
			System.out.println("writefile default!");
		}
	}

	protected CrawlWebCentral() {
		logger.info(Web_Central, "Spider Start running...");
	}

	public void Init(CrawlUrl[] initurl) throws SQLException, Exception {
		if (initurl == null || rootdir == null) {
			logger.error("initurl or rootdir null");
			System.exit(0);
		}
		httpconnectpool = HttpConnectPool.GetHttpConnectPool(20);
		BreadthFirstTraversal traversal = BreadthFirstTraversal
				.getBreadthFirstTraversal(initurl);
		ThreadPoolExecutor spiderpool = CrawlThreadPoolExecutor
				.getThreadPool();
		this.spiderpool = spiderpool;
		try {
			traversal.Traversal(spiderpool);
		} catch (Exception e) {
			logger.fatal(Web_Central, "BreadFirstTraversal occur exception");
			logger.error(e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		CrawlWebCentral webcontrol = new CrawlWebCentral();
		CrawlUrl[] initurl = new CrawlUrl[1];
		String url = "http://www.ifeng.com";
		CrawlUrl crawlurl=new CrawlUrl();
		crawlurl.setOriUrl(url);
		crawlurl.setPriority(0);
		initurl[0]=crawlurl;
		String rootdir = "e:\\spider";
		
		CrawlWebCentral.rootdir = rootdir;
		webcontrol.Init(initurl);
		File file = new File("e:\\datafile");
		file.mkdir();
		while (true) {
			// 构造线程的策略是当没有访问列表中的url大于100*线程池中线程是创建线程，但是线程数不能超过15个
			logger.debug(webcontrol.httpconnectpool.getPOOLSIZE());
			System.out.println("未访问的："+BreadthFirstTraversal.getSizeUNVisited());
			if (BreadthFirstTraversal.getSizeUNVisited() > 10 * webcontrol.spiderpool
					.getActiveCount()
					&& webcontrol.spiderpool.getActiveCount() < 25) {

				CrawlUrl new_crawlurl = BreadthFirstTraversal
						.getUNVisitedURL();

				webcontrol.spiderpool.execute(new CrawlThread(new_crawlurl));
				logger.info("Active Thread :"
						+ webcontrol.spiderpool.getActiveCount());
			}
			CrawlWebCentral.writeWebPage();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

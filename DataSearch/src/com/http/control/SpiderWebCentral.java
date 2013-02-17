package com.http.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.http.Search.BreadthFirstTraversal;
import com.http.Search.WebPage;
import com.http.connect.HttpConnectPool;

/*
 * 
 */
public class SpiderWebCentral {
	private static Logger logger = LogManager.getLogger(SpiderWebCentral.class
			.getName());
	private Marker Web_Central = MarkerManager.getMarker("WEB_CENTRAL");
	public HttpConnectPool httpconnectpool;
	private ThreadPoolExecutor spiderpool;
	public static String rootdir;
	private static long filenumber = 0;
	public static LinkedList<WebPage> keywords = new LinkedList<WebPage>();

	public synchronized static void addWebPage(WebPage webpage) {
		SpiderWebCentral.keywords.add(webpage);
	}

	public synchronized static void writeWebPage() {
		try {
			String path = "e:\\search";
			while (!keywords.isEmpty()) {
				File file = new File(path, "datafile" + filenumber);
				FileOutputStream fileout = new FileOutputStream(file);
				WebPage webpage = keywords.pollFirst();
				Set<String> keyset = webpage.keySet();
				Iterator<String> keyiterator = keyset.iterator();
				while (keyiterator.hasNext()) {
					String key = keyiterator.next();
					if (key != null) {
						fileout.write("key:".getBytes());
						fileout.write(key.getBytes());
						fileout.write('\n');
						fileout.write("value:".getBytes());
						fileout.write(webpage.getVaule(key).getBytes());
						fileout.write('\n');
					}
				}
				fileout.close();
				filenumber++;
			}
		} catch (IOException e) {
			System.out.println("writefile default");
		}
	}

	protected SpiderWebCentral() {
		logger.info(Web_Central, "Spider Start running...");
	}

	public void Init(String[] initurl) throws SQLException, Exception {
		if (initurl == null || rootdir == null) {
			logger.error("initurl or rootdir null");
			System.exit(0);
		}
		httpconnectpool = HttpConnectPool.GetHttpConnectPool(20);
		BreadthFirstTraversal traversal = BreadthFirstTraversal
				.getBreadthFirstTraversal(initurl);
		ThreadPoolExecutor spiderpool = SpiderThreadPoolExecutor
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
		SpiderWebCentral webcontrol = new SpiderWebCentral();
		String[] initurl = new String[1];
		initurl[0] = "http://www.suse.edu.cn";
		String rootdir = "e:\\spider";
		SpiderWebCentral.rootdir = rootdir;
		webcontrol.Init(initurl);
		BreadthFirstTraversal traversal = BreadthFirstTraversal
				.getBreadthFirstTraversal(initurl);
		File file = new File("e:\\datafile");
		file.mkdir();
		traversal.Traversal(webcontrol.spiderpool);
		while (true) {
			// 构造线程的策略是当没有访问列表中的url大于100*线程池中线程是创建线程，但是线程数不能超过15个
			logger.debug(webcontrol.httpconnectpool.getPOOLSIZE());
			System.out.println("未访问的："+BreadthFirstTraversal.getSizeUNVisited());
			if (BreadthFirstTraversal.getSizeUNVisited() > 10 * webcontrol.spiderpool
					.getActiveCount()
					&& webcontrol.spiderpool.getActiveCount() < 25) {

				HashMap<String, Integer> map = BreadthFirstTraversal
						.getUNVisitedURL();
				String tempurl = (String) map.keySet().toArray()[0];

				webcontrol.spiderpool.execute(new SpiderThread(tempurl, map
						.get(tempurl)));
				logger.info("Active Thread :"
						+ webcontrol.spiderpool.getActiveCount());
			}
			SpiderWebCentral.writeWebPage();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

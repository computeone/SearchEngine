/**
 * 
 */
package com.http.control;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author niubaisui
 *
 */
public class CrawlThreadPool {
	
	private static CrawlThreadPool crawlthreadpool=null;
	private ThreadPoolExecutor threadpoolexecutor;
	private CrawlThreadPool(){
		threadpoolexecutor=new ThreadPoolExecutor(50, 100, 1, TimeUnit.HOURS,
				new SynchronousQueue<Runnable>());
	}
	
	public static synchronized CrawlThreadPool getCrawlthreadpool(){
		if(crawlthreadpool==null){
			return new CrawlThreadPool();
		}
		else{
			return crawlthreadpool;
		}
	}
	
	public void execute(CrawlThread crawlthread){
		threadpoolexecutor.execute(crawlthread);
	}
	
	public int getActiveCount(){
		return threadpoolexecutor.getActiveCount();
	}
}

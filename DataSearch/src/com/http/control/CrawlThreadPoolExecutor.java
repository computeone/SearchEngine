package com.http.control;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CrawlThreadPoolExecutor {
	private static ThreadPoolExecutor spiderthreadpool;

	public static ThreadPoolExecutor getThreadPool() {
		spiderthreadpool = new ThreadPoolExecutor(50, 100, 1, TimeUnit.HOURS,
				new SynchronousQueue<Runnable>());
		return spiderthreadpool;
	}

}

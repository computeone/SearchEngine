package com.http.connect;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpConnectPool {
	private static int POOLSIZE;
	//可用的httpconnecct池
	private static LinkedList<HttpConnect> AvilibleConnectList;
	//活动的即不可用的httpconnect池
	private static LinkedList<HttpConnect> ActiveConnectList;
	private static Logger logger = LogManager.getLogger("HttpConnectPool");

	public synchronized  int getPOOLSIZE() {
		return POOLSIZE;
	}

	public synchronized static void setPOOLSIZE(boolean flag) {
		if (flag) {
			POOLSIZE = POOLSIZE + 1;
		} else {
			POOLSIZE = POOLSIZE - 1;
		}
	}

	private HttpConnectPool(int poolsize) {
		AvilibleConnectList = new LinkedList<HttpConnect>();
		ActiveConnectList = new LinkedList<HttpConnect>();
		for (int i = 0; i < poolsize; i++) {
			AvilibleConnectList.add(new HttpConnect(""));// 加入一个空url的httpconnect
		}
	}

	public synchronized static HttpConnect getHttpConnect()
			throws NOHttpConnectException {

		if (AvilibleConnectList == null && AvilibleConnectList.size() == 0) {
			logger.info("pool no httpconnect！");
			throw new NOHttpConnectException();
		} else {
			HttpConnect httpconnect = AvilibleConnectList.pollFirst();
			if (httpconnect == null) {
				throw new NOHttpConnectException();
			}
			ActiveConnectList.add(httpconnect);
			HttpConnectPool.setPOOLSIZE(false);
			logger.info("new conncet:");
			logger.info(httpconnect);
			return httpconnect;
		}
	}

	public synchronized static void releaseHttpConnect(HttpConnect connect) {
		if (!ActiveConnectList.contains(connect)) {
			logger.fatal("fatal error release default");
		}
		AvilibleConnectList.add(connect);
		ActiveConnectList.pollFirst();
		HttpConnectPool.setPOOLSIZE(true);
	}

	public static HttpConnectPool GetHttpConnectPool(int poolsize) {
		if (poolsize < 0) {
			logger.fatal("poolsize should be greater than 0!");
			System.exit(0);
		}
		POOLSIZE = poolsize;
		return new HttpConnectPool(poolsize);
	}

	public static void main(String[] args) throws Exception {
		HttpConnectPool httppool=HttpConnectPool.GetHttpConnectPool(100);
		LinkedList<HttpConnect> list=new LinkedList<HttpConnect>();
		File file=new File("e:\\http");
		FileOutputStream writer=new FileOutputStream(file);
		Random random=new Random(100);
		boolean flag=true;
		while(true){
			if(random.nextInt()>50&&list.size()<100){
				list.add(HttpConnectPool.getHttpConnect());
				flag=true;
			}
			else if(list.size()>0){
				HttpConnectPool.releaseHttpConnect(list.pollFirst());
				flag=false;
			}
			writer.write(String.valueOf(httppool.getPOOLSIZE()).getBytes());
			if(flag){
				writer.write(" sub".getBytes());
			}
			writer.write('\n');
			System.out.println(httppool.getPOOLSIZE());
		}
	}
}

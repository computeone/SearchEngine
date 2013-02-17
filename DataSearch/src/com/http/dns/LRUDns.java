package com.http.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Stack;

public class LRUDns {
	private static HashMap<String, String> DNS;
	private static Stack<String> lru;
	private int size;

	public LRUDns(int size) {
		this.size = size;
		DNS = new HashMap<String, String>(size);
		lru = new Stack<String>();
	}

	public synchronized static LRUDns getLRUDns(int size) {
		return new LRUDns(size);
	}

	public synchronized String getInetAddress(String dns)
			throws UnknownHostException {
		return this.querydnsLRU(dns);
	}

	private String queryDns(String dns) throws UnknownHostException {
		InetAddress inet = InetAddress.getByName(dns);
		return inet.getHostAddress();
	}

	private String querydnsLRU(String dns) throws UnknownHostException {
		if (lru.size() < size) {// 如果栈没有满
			if (DNS.containsKey(dns)) {
				/*
				 * 如果DNS缓存中存在指定的项 将这个指定的项移到栈顶
				 */
				int index = lru.indexOf(dns);
				String tempdns = lru.elementAt(index);
				lru.remove(index);
				lru.push(tempdns);
				return DNS.get(dns);
			} else {
				String query_inet = this.queryDns(dns);
				lru.push(dns);
				DNS.put(dns, query_inet);
				return query_inet;
			}
		} else {
			/*
			 * 栈满了，且DNS缓存中存在指定的项，将栈底的元素移除，将这个指定的项压入栈顶。 将DNS缓存中的指定项返回
			 */
			if (DNS.containsKey(dns)) {
				lru.remove(0);
				lru.push(dns);
				return DNS.get(dns);
			} else {
				/*
				 * DNS中没有指定的项，查询指定的项，如果DNS缓存满了，则移除最近没有访问的项,
				 * 将查询得到的项放入缓存中，lru栈中删除栈底的元素，将指定的dns压入栈中
				 */
				String query_inet = queryDns(dns);
				String no_recently_dns = lru.elementAt(0);
				DNS.remove(no_recently_dns);

				DNS.put(dns, query_inet);
				lru.remove(0);
				lru.push(dns);
				return query_inet;
			}
		}
	}
}

/**
 * 
 */
package com.html.parser;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author niubaisui
 *
 */
public class URLFilterChain {
	private LinkedList<URLFilter> chain=new LinkedList<URLFilter>();
	
	public void addFilter(URLFilter filter){
		chain.addLast(filter);
	}
	
	//Ö´ĞĞ¹ıÂËÁ´
	public boolean filter(String url){
		Iterator<URLFilter> iterator=chain.iterator();
		while(iterator.hasNext()){
			boolean result=iterator.next().filter(url);
			if(!result){
				return false;
			}
		}
		return true;
	}

}

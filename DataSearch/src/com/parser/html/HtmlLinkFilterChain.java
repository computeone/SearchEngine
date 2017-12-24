package com.parser.html;

import java.util.Iterator;
import java.util.LinkedList;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;

/*
 * ����Html�ļ��Ĺ�������
 */
public class HtmlLinkFilterChain{

	private static LinkedList<NodeFilter> filterstack;
	private static Iterator<NodeFilter> filteriterator;

	public HtmlLinkFilterChain() {
		filterstack = new LinkedList<NodeFilter>();
		
		//������һЩҪ�õ��Ĺ�����
		TagNameFilter title=new TagNameFilter("title");
		TagNameFilter meta=new TagNameFilter("meta");
		TagNameFilter a=new TagNameFilter("a");
		TagNameFilter frame=new TagNameFilter("frame");
		TagNameFilter iframe=new TagNameFilter("iframe");
		
		HasAttributeFilter href=new HasAttributeFilter("href");
		HasAttributeFilter src=new HasAttributeFilter("src");
		
		AndFilter  a_limt=new AndFilter(a,href);
		AndFilter  frame_limt=new AndFilter(frame,src);
		AndFilter  iframe_limt=new AndFilter(iframe,src);
		
		this.addFilter(title);
		this.addFilter(meta);
		this.addFilter(a_limt);
		this.addFilter(frame_limt);
		this.addFilter(iframe_limt);
		filteriterator = filterstack.iterator();
	}

	private synchronized void addFilter(NodeFilter node) {
		filterstack.add(node);
	}

	public synchronized boolean hasNextNodeFilter() {
		return filteriterator.hasNext();
	}

	public synchronized NodeFilter NextNodeFilter() {
		return filteriterator.next();
	}

	public synchronized int getSize() {
		return filterstack.size();
	}

	public static void main(String[] args) {
		HtmlLinkFilterChain chain = new HtmlLinkFilterChain();
		System.out.println(chain.getSize());
	}

}

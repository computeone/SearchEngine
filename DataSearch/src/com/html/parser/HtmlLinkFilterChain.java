package com.html.parser;

import java.util.Iterator;
import java.util.LinkedList;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.FrameTag;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;

public class HtmlLinkFilterChain{

	private static LinkedList<NodeFilter> filterstack;
	private static Iterator<NodeFilter> filteriterator;

	public HtmlLinkFilterChain() {
		filterstack = new LinkedList<NodeFilter>();
		NodeClassFilter head = new NodeClassFilter(HeadTag.class);
		NodeClassFilter link = new NodeClassFilter(LinkTag.class);
		NodeClassFilter img = new NodeClassFilter(ImageTag.class);
		NodeClassFilter frame = new NodeClassFilter(FrameTag.class);
		TagNameFilter iframe = new TagNameFilter("iframe");
		AndFilter andfilter1 = new AndFilter(new HasParentFilter(head),
				new HasParentFilter(head));
		AndFilter andfilter2 = new AndFilter(link, link);
		AndFilter andfilter3 = new AndFilter(img, img);
		AndFilter andfilter4 = new AndFilter(frame, frame);
		AndFilter andfilter5 = new AndFilter(iframe, iframe);
		this.addFilter(andfilter1);
		this.addFilter(andfilter2);
		this.addFilter(andfilter3);
		this.addFilter(andfilter4);
		this.addFilter(andfilter5);
		// this.addFilter(andfilter3);
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

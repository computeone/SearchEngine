package com.http.Search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class WebPage {
	private HashMap<String, String> node;

	public WebPage() {
		node = new HashMap<String, String>();
	}

	public WebPage getNode() {
		return new WebPage();
	}

	public String getKey() {
		Set<String> key = node.keySet();
		if (!key.isEmpty()) {
			Iterator<String> iterator = key.iterator();
			return iterator.next();
		}
		return null;
	}

	public void put(String key, String value) {
		node.put(key, value);
	}

	public Set<String> keySet() {
		return node.keySet();
	}

	public Set<Entry<String, String>> entrySet() {
		return node.entrySet();
	}

	public String getVaule(String key) {
		return node.get(key);
	}
}

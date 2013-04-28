package com.search.index;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/*
 * 
 */
public class Token_Structure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2078131305584060166L;
	private LinkedList<Long> token_id = new LinkedList<Long>();
	private String term;
	private int frequency = 1;

	public Token_Structure(String term) {
		this.term = term;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getFrequency() {
		return frequency;
	}

	public String getTerm() {
		return term;
	}

	public void add(long id) {
		token_id.addLast(id);
	}

	public long pollFirst() {
		return token_id.pollFirst();
	}

	public long pollLast() {
		return token_id.pollLast();
	}

	public boolean isEmpty() {
		return token_id.isEmpty();
	}

	public int getSize() {
		return token_id.size();
	}

	public Iterator<Long> Iterator() {
		return token_id.iterator();
	}

	public void setTokens_id(LinkedList<Long> tokens_id) {
		this.token_id = tokens_id;
	}

	public LinkedList<Long> getTokens_id() {
		return token_id;
	}
}

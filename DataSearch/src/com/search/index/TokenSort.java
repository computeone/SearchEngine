package com.search.index;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import com.search.data.Token;
import com.search.data.TokenIDOverException;

public class TokenSort {
	public static Token[] Sort(Token[] token) {
		TokenCompare compare = new TokenCompare();
		Arrays.sort(token, compare);
		return token;
	}

	public static LinkedList<Token> MergeSort(LinkedList<Token> t1,
			LinkedList<Token> t2) {
		LinkedList<Token> token = new LinkedList<Token>();
		TokenCompare compare = new TokenCompare();
		while (!t1.isEmpty() && !t2.isEmpty()) {
			if (!t1.isEmpty()
					&& compare.compare(t1.peekFirst(), t2.peekFirst()) <= 0) {
				token.addLast(t1.pollFirst());
			} else {
				if (!t2.isEmpty()) {
					token.add(t2.pollFirst());
				}
			}
			while (t1.isEmpty() && !t2.isEmpty()) {
				token.add(t2.pollFirst());
			}
			while (t2.isEmpty() && !t1.isEmpty()) {
				token.add(t1.pollFirst());
			}
		}
		return token;
	}

	public static Token[] mergeSort(Token[] t1, Token[] t2) {
		Token[] token = new Token[t1.length + t2.length];
		TokenCompare compare = new TokenCompare();
		int i, j, n;
		i = 0;
		j = 0;
		n = -1;
		while (i + j < token.length) {
			if (i < t1.length && compare.compare(t1[i], t2[j]) <= 0) {
				token[++n] = t1[i++];
			} else {
				if (j < t2.length) {
					token[++n] = t2[j++];
				}
			}
			while (i >= t1.length && j < t2.length) {
				token[++n] = t2[j++];
			}
			while (j >= t2.length && i < t1.length) {
				token[++n] = t1[i++];
			}
		}
		return token;
	}
}

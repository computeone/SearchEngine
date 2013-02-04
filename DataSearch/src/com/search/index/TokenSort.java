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

	public static void main(String[] args) throws TokenIDOverException {
		Token[] tk1 = new Token[3];
		Token[] tk2 = new Token[5];
		tk1[0] = new Token("我在", 1000, 1);
		tk1[1] = new Token("哪儿", 1000, 2);
		tk1[2] = new Token("你在", 1000, 3);
		tk2[0] = new Token("哪儿", 2000, 1);
		tk2[1] = new Token("在这", 2000, 2);
		tk2[2] = new Token("more", 2000, 3);
		tk2[3] = new Token("在那", 2000, 4);
		tk2[4] = new Token("天边", 2000, 5);
		TokenSort.Sort(tk1);
		TokenSort.Sort(tk2);
		for (int i = 0; i < tk1.length; i++) {
			System.out.println(1 + "--" + tk1[i].getTerm());
		}
		for (int i = 0; i < tk2.length; i++) {
			System.out.println(2 + "--" + tk2[i].getTerm());
		}
		LinkedList<Token> t1 = new LinkedList<Token>();
		LinkedList<Token> t2 = new LinkedList<Token>();
		for (int i = 0; i < tk1.length; i++) {
			t1.addLast(tk1[i]);
		}
		for (int i = 0; i < tk2.length; i++) {
			t2.addLast(tk2[i]);
		}
		LinkedList<Token> token = TokenSort.MergeSort(t1, t2);
		System.out.println(token.size());
		Iterator<Token> iterator = token.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().getTerm());
		}
	}
}

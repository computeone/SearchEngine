package com.search.index;

import java.util.Comparator;

import com.search.data.Token;
/*
 * token比较类，用来比较两个token的字典序
 * 返回的值为1时，t1大于t2，
 * 返回0时，t1等于t2
 * 返回-1时，t1小于t2
 */
public class TokenCompare implements Comparator<Token> {
	public int compare(Token t1, Token t2) {
		//转化为字符数组
		char[] c1 = ((com.search.data.Token) t1).getTerm().toCharArray();
		char[] c2 = ((com.search.data.Token) t2).getTerm().toCharArray();
		int i, j;
		i = 0;
		j = 0;
		ChineseCompare compare = new ChineseCompare();
		
		//循环比较直到比较出结果
		while (true) {
			if (compare.compare(c1[i], c2[j]) < 0) {
				return -1;
			}
			if (compare.compare(c1[i], c2[j]) > 0) {
				return 1;
			} else {
				if (i == c1.length - 1 && j == c2.length - 1) {
					return 0;
				}
				if (i == c1.length - 1) {
					return -1;
				}
				if (j == c2.length - 1) {
					return 1;
				}
				i++;
				j++;
			}
		}
	}
}

package com.search.index;

import java.util.ArrayList;
import java.util.LinkedList;

import com.search.data.Token;
//将Token分为1字、2字、3字、4字以上的Token
public class Classify_Token {
	public static ArrayList<LinkedList<Token>> classify_Token(Token[] token){
		ArrayList<LinkedList<Token>> t=new ArrayList<LinkedList<Token>>();
		LinkedList<Token> t1=new LinkedList<Token>();
		LinkedList<Token> t2=new LinkedList<Token>();
		LinkedList<Token> t3=new LinkedList<Token>();
		LinkedList<Token> t4=new LinkedList<Token>();
		for(int i=0;i<token.length;i++){
			switch(token[i].getTerm().length()){
			case 1: 
				t1.add(token[i]);
			case 2:
				t2.add(token[i]);
			case 3:
				t3.add(token[i]);
			default:
				t4.add(token[i]);
			}
		}
		t.add(t1);
		t.add(t2);
		t.add(t3);
		t.add(t4);
		return t;
	}

}

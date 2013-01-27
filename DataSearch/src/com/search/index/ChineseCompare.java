package com.search.index;

import java.util.Comparator;
//中文字符比较器
public class ChineseCompare implements Comparator<Character>{
	@Override
	public int compare(Character c1, Character c2) {
		// TODO Auto-generated method stub
		char[] ch=new char[2];
		ch[0]=c1;
		ch[1]=c2;
		byte[] b=String.valueOf(ch).getBytes();
		//是中文字符
		if(b[0]<0&&b[2]<0){
			if(b[0]<b[2]){//第一个汉字的第一个字节小于第二个汉字的第一个字节
				return -1;
			}
			if(b[0]==b[2]){/*第一个汉字的第一个字节等于第二个汉字的第一个字节，
			                                                则继续比较第二个字节*/
				if(b[1]<b[3]){
					return -1;
				}
				if(b[1]==b[3]){
					return 0;
				}
				else{
					return 1;
				}
			}
			//第一个汉字的第一个字节大于第二个汉字的第一个字节
			else{
				return 1;
			}
		}
		//不是中文字符
		else{
			//两者都不是中文字符
			if (b[0] >=0 && b[1] >=0) {
				if (b[0] < b[1]) {
					return -1;
				}
				if (b[0] == b[1]) {
					return 0;
				} else {
					return 1;
				}
			}
			//其中之一是中文字符
			else{
				if(b[0]>=0){
					return -1;
				}
				else{
					return 1;
				}
			}
		}
	}
}

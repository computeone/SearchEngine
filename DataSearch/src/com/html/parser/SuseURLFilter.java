package com.html.parser;

import java.util.regex.Pattern;

public class SuseURLFilter implements URLFilter{
	
	@Override
	public boolean filter(String url) {
		boolean result=Pattern.matches("https?://.*\\.suse\\.edu\\.cn/.*", url);
		if(result){
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		SuseURLFilter suse=new SuseURLFilter();
		String url="https://lidb.suse.edu.cn/index.html";
		boolean result=suse.filter(url);
		System.out.println(result);
	}
}

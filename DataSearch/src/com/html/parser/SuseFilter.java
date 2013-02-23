package com.html.parser;

import java.util.regex.Pattern;

public class SuseFilter {
	public boolean isSuse(String url){
		boolean result=Pattern.matches("https?://.*suse\\.edu\\.cn/.*", url);
		return true;
	}
	public static void main(String[] args) {
		SuseFilter suse=new SuseFilter();
		String url="http://lib.suse.edu.cn/index";
		boolean result=suse.isSuse(url);
		System.out.println(result);
	}
}

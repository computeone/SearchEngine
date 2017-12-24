package com.parser.html;

import java.util.regex.Pattern;

public class SuseURLFilter implements URLFilter{
	
	@Override
	public boolean filter(String url) {
		boolean result=Pattern.matches("https?://.*\\.suse\\.edu\\.cn/?.*", url);
		if(result){
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		SuseURLFilter suse=new SuseURLFilter();
		String url="http://www.suse.edu.cn";
		boolean result=suse.filter(url);
		System.out.println(result);
	}
}

package com.http.connect;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleHttpURLParser implements HttpURLParser {
	private String url;
	private static String regex = "\\Ahttps?://(([\\w+\\.]+)(:\\d{1,5})?/{0,1}(.*)?)\\Z";
	private static String regexpath = "/";
	private static String illegalcharacter = "[\\<>:%*/|&?\\s]";
	private Pattern pattern;

	public SimpleHttpURLParser() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRegex() {
		return regex;
	}

	/*
	 * 第0个为URL，第一个为全路径，第二个为域名，第三个为端口，第四个为文件目录路径
	 * 例如，http://www.baidu.com:80/index.html
	 * 则结果如下:
	 * http://www.baidu.com:80/index.html、www.baidu.com:80/index.html
	 * www.baidu.com、80、index.html
	 */
	public String[] parserURL(String url){
		pattern = Pattern.compile(regex,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(url);
		
		if(matcher.matches()){			
			String[] result = new String[matcher.groupCount() + 1];
			for (int i = 0; i <= matcher.groupCount(); i++) {
				result[i] = matcher.group(i);// 第0个为URL，第一个为全路径，第二个为域名，第三个为端口，第四个为文件目录路径
			}
			return result;
		}		
		else{
			return null;
		}
	}

	public boolean vertifyURL(String url) throws IllegalStateException {
		pattern = Pattern.compile(regex,Pattern.DOTALL);
		if (url != null) {
			boolean result = pattern.matcher(url).matches();
			return result;
		} else
			return false;
	}

	public String[] parserPath(String path) {
		pattern = Pattern.compile(regexpath,Pattern.DOTALL);
		String[] result = pattern.split(path);
		return result;
	}

	public String deleteIllegalChar(String str) {
		pattern = Pattern.compile(illegalcharacter,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		str = matcher.replaceAll("_").trim();
		return str;
	}
}

package com.html.parser;

import java.io.File;
import java.util.LinkedList;

public interface DocumentParser {
	public void setUrl(String url);

	public String getUrl();

	public LinkedList<String> parser(File file, String enconding);

	public void registerLinkFilterChain(HtmlLinkFilterChain linkfilterchain);

}

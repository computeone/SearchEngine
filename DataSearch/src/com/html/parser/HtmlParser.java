package com.html.parser;

import java.io.File;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.http.Search.WebPage;
import com.http.connect.HttpResponseHeader;
import com.http.connect.HttpURLParserImpl;
import com.http.control.SpiderWebCentral;

public class HtmlParser implements DocumentParser {
	private File file;
	private String url;
	private HttpResponseHeader httpresponseheader;
	private HtmlLinkFilterChain linkfilterchain;
	private Logger logger = LogManager.getLogger("HtmlParser");

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public HttpResponseHeader getHttpresponseheader() {
		return httpresponseheader;
	}

	public void setHttpresponseheader(HttpResponseHeader httpresponseheader) {
		this.httpresponseheader = httpresponseheader;
	}

	public void registerLinkFilterChain(HtmlLinkFilterChain linkfilterchain) {
		this.linkfilterchain = linkfilterchain;
	}

	public String getHost() {
		HttpURLParserImpl parserhost = new HttpURLParserImpl();
		String[] path = parserhost.parserURL(url);
		String host = path[path.length - 3];
		return host;
	}

	/*
	 * 主要把标题、关键字、图片链接、框架链接 的链接及文本解析出来
	 */
	public LinkedList<String> parser(File file, String encoding) {
		//links存储的是没有被访问的url
		LinkedList<String> links = new LinkedList<String>();
		WebPage webpage = new WebPage();
		try {
			Parser parser = new Parser(file.getAbsolutePath());
			parser.setEncoding(encoding);
			int size = linkfilterchain.getSize();
			NodeFilter[] filterchain = new NodeFilter[size];
			int num = 0;
			while (linkfilterchain.hasNextNodeFilter()) {
				filterchain[num] = linkfilterchain.NextNodeFilter();
				num++;
			}
			OrFilter chainfilter = new OrFilter(filterchain);
			NodeList nodelist = parser.extractAllNodesThatMatch(chainfilter);
			logger.info("Parser Obtain url:");
			// 正则表达式来识别超链接
			Pattern pattern_keyword = Pattern.compile("<meta.*>");
			Pattern keyword_filter = Pattern
					.compile(".*(?:(?:name=.*).*){0,1}.*content=[\\x22']([^\\x22^']*)[\\x22'].*(?:(?:name"
							+ "=.*).*){0,1}.*/>");

			Pattern pattern_title = Pattern.compile("<title.*");
			Pattern title_filter = Pattern.compile("<title>(.*)</title>");

			Pattern pattern_link = Pattern.compile(".*<a.*/a>.*");
			Pattern link_filter = Pattern
					.compile(".*href=[\\x22']([^\\x22^']*)[\\x22'].*>(.*)<.*");

			Pattern pattern_img = Pattern.compile(".*<img.*/>.*");
			Pattern img_filter = Pattern
					.compile(".*<img.*(?:alt=[\\x22']([^\\x22^']*)[\\x22']){0,1}.*src=[\\x22']([^\\x22^']*)[\\x22'].*(?:alt=[\\x22']"
							+ "([^\\x22^']*)[\\x22']){0,1}.*");

			Pattern pattern_frame = Pattern.compile(".*<i?frame.*>.*");
			Pattern frame_filter = Pattern
					.compile(".*<i?frame.*src=[\\x22']([^\\x22^']*)[\\x22'].*>");
			Matcher matcher_keyword;
			Matcher matcher_title;
			Matcher matcher_link;
			Matcher matcher_frame;
			Matcher matcher_img;
			System.out.println(url);
			for (int i = 0; i < nodelist.size(); i++) {
				Node node = nodelist.elementAt(i);
				matcher_keyword = pattern_keyword.matcher(node.toHtml());
				boolean keyword_result = matcher_keyword.matches();

				matcher_title = pattern_title.matcher(node.toHtml());
				boolean title_result = matcher_title.matches();

				matcher_link = pattern_link.matcher(node.toHtml());
				boolean link_result = matcher_link.matches();

				matcher_img = pattern_img.matcher(node.toHtml());
				boolean img_result = matcher_img.matches();

				matcher_frame = pattern_frame.matcher(node.toHtml());
				boolean frame_result = matcher_frame.matches();
				
				SuseFilter filter=new SuseFilter();
				
				
				// 关键字检查
				if (keyword_result) {
					try {
						// System.out.println(node.toHtml());
						matcher_keyword = keyword_filter.matcher(node.toHtml());
						matcher_keyword.find();
						String keyword = matcher_keyword.group(1);
						System.out.println(keyword);
						// 加入得到的信息，
						webpage.put(this.url, keyword);
					} catch (Exception e) {
						// logger.debug("analyize error");
						continue;
					}
					// 网页的标题
				} else if (title_result) {
					try {
						// 解析出标题
						matcher_title = title_filter.matcher(node.toHtml());
						matcher_title.find();
						String title = matcher_title.group(1);
						System.out.println(title);
						if(title!=null){
							webpage.put(this.url, title);
						}
						
					} catch (Exception e) {
						continue;
					}

					// 一般链接
				} else if (link_result) {
					try {
						//解析出一般的链接
						matcher_link = link_filter.matcher(node.toHtml());
						matcher_link.find();
						String link = matcher_link.group(1);
						String keyword = matcher_link.group(2);
						boolean result = Pattern.matches("http.*", link);
						if (!result) {
							link = "http://" + this.getHost() + "/" + link;
						}
						
						HttpURLParserImpl html_parser = new HttpURLParserImpl();
						boolean isLink = html_parser.vertifyURL(link);
						if (isLink) {
							if(filter.isSuse(link)){
								System.out.println(link);
								links.add(link);
							}
							
						}
						if(keyword!=null){
							if(filter.isSuse(link)){
								webpage.put(link, keyword);
							}
							
						}
						
					} catch (Exception e) {
						// logger.debug("analyize error");
						continue;
					}
					// 图片链接
				} else if (img_result) {
					try {
						// 解析出图片链接,之后加入webpage如果alt不为空
						matcher_img = img_filter.matcher(node.toHtml());
						matcher_img.find();
						String img = matcher_img.group(2);
						String alt = matcher_img.group(1);
						if (matcher_img.group(1) == null) {
							alt = matcher_img.group(3);
						}
						boolean result = Pattern.matches("http.*", img);
						if (!result) {
							img = "http://" + this.getHost() + "/" + img;
						}
		
						HttpURLParserImpl html_parser = new HttpURLParserImpl();
						boolean isLink = html_parser.vertifyURL(img);
						if (isLink) {
							if(filter.isSuse(img)){
								links.add(img);
							}
							
						}
						//如果alt不为空则加入webpage
						if(alt!=null){
							if(filter.isSuse(img)){
								webpage.put(img, alt);
							}
						}
						
					} catch (Exception e) {
						continue;
					}

					// 框架链接
				} else if (frame_result) {
					try {
						// 解析出来之后加入Link中,不加入webpage中
						matcher_frame = frame_filter.matcher(node.toHtml());
						matcher_frame.find();
						String frame = matcher_frame.group(1);
						boolean result = Pattern.matches("http.*", frame);
						if (!result) {
							frame = "http://" + this.getHost() + "/" + frame;
						}
						
						HttpURLParserImpl html_parser = new HttpURLParserImpl();
						boolean isLink = html_parser.vertifyURL(frame);
						if (isLink) {
							if(filter.isSuse(frame)){
								System.out.println(frame);
								links.add(frame);
							}
						}
					} catch (Exception e) {
						continue;
					}
				}
				// System.out.println(node.toHtml());
			}
			SpiderWebCentral.addWebPage(webpage);
			// 解析出来正确的url
		} catch (ParserException e) {
			logger.error(e.getMessage());
		}
		return links;
	}

}

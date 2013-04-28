package com.html.parser;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
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

import com.http.Search.BreadthFirstTraversal;
import com.http.Search.CrawlUrl;
import com.http.connect.SimpleHttpURLParser;
import com.http.control.CrawlWebCentralThread;
import com.search.DAO.Connect;
import com.search.data.Document;

/*
 * 
 * 
 */
public class HtmlParser implements DocumentParser {
	private String encode="utf-8";
	private CrawlUrl crawlurl;
	private File file;
	private URLFilterChain chain;
	private Document document;
	private HtmlLinkFilterChain linkfilterchain;
	private Logger logger = LogManager.getLogger("HtmlParser");
	
	private LinkedList<String> links;


	public HtmlParser(File file,CrawlUrl crawlurl) throws IOException{
		this.file=file;
		this.crawlurl=crawlurl;
	}
	
	public HtmlParser(Connect connect){
		
	}
	
	public HtmlParser(String content){
		
	}
	
	public CrawlUrl getCrawlUrl(){
		return crawlurl;
	}
	
	private void setEncode(String encode){
		this.encode=encode;
	}
	
	public LinkedList<String> getLinks(){
		return links;
	}
	
	public Document getDocument(){
		return document;
	}
	
	public void registerURLFilter(URLFilterChain chain){
		this.chain=chain;
	}
	public void registerLinkFilterChain(HtmlLinkFilterChain linkfilterchain) {
		this.linkfilterchain = linkfilterchain;
	}

	public boolean matcher_meta(String str) {

		String regex1=".*name=\"(.*)\".*content=\"(.*)\".*";
		String regex2=".*content=\"(.*)\".*name=\"(.*)\".*";
		String regex3=".*http-equiv=\"(.*)\".*content=\"(.*)\".*";
		String regex4=".*content=\"(.*)\".*http-equiv=\"(.*)\".*";
		
		
		// 正则表达式来识别超链接
		Pattern pattern_keyword = Pattern.compile("<meta.*/?>");
		Matcher matcher1=Pattern.compile(regex1).matcher(str);
		Matcher matcher2=Pattern.compile(regex2).matcher(str);
		Matcher matcher3=Pattern.compile(regex3).matcher(str);
		Matcher matcher4=Pattern.compile(regex4).matcher(str);
		
		//匹配器
		Matcher matcher_keyword=pattern_keyword.matcher(str);
		boolean result=matcher_keyword.matches();
		if(result){
			if(matcher1.matches()){
				logger.info("name:"+matcher1.group(1)+"  content:"+matcher1.group(2));
				document.addAttribute(matcher1.group(1), matcher1.group(2));
			}
			else if(matcher2.matches()){
				logger.info("name:"+matcher2.group(2)+"  content:"+matcher2.group(1));
				document.addAttribute(matcher2.group(2),matcher2.group(1));
			}
			else if(matcher3.matches()){
				logger.info("http-equiv:"+matcher3.group(1)+"  content:"+matcher3.group(2));
				document.addAttribute(matcher3.group(1), matcher3.group(2));
			}
			else if(matcher4.matches()){
				logger.info("http-equiv:"+matcher1.group(2)+"  content:"+matcher1.group(1));
				document.addAttribute(matcher4.group(2),matcher4.group(1));
			}
			return true;
		}
		return false;
	}
	
	//解析超链接
	public boolean matcher_link(String str){
		String regex1="<a.*/?>";
		String regex2=".*href=\"([^\"]+)\".*";
//		String regex3="https?://.*";
		Matcher matcher1=Pattern.compile(regex1).matcher(str);
		
		if(matcher1.matches()){
			Matcher matcher2=Pattern.compile(regex2).matcher(matcher1.group());
			if(matcher2.matches()){
				if(chain.filter(matcher2.group(1))){
					SimpleHttpURLParser urlparser=new SimpleHttpURLParser();
					if(urlparser.vertifyURL(matcher2.group(1))){
						logger.info("link:"+matcher2.group(1));
						links.addLast(matcher2.group(1));
					}						
				}				
			}
			return true;
		}
		return false;
		
	}
	
	public boolean matcher_iframe(String str){
		String regex="<iframe.*src=\"([^\"]+)\".*";
		String regex2="https?://.*";
		Matcher matcher=Pattern.compile(regex).matcher(str);
		if(matcher.matches()){
			//过滤链进行过滤
			if(chain.filter(matcher.group(1))){
				Matcher m=Pattern.compile(regex2).matcher(matcher.group(1));
				if(m.matches()){
					logger.info("iframe:"+m.group(1));
					links.addLast(m.group(1));
				}
				else{
					logger.info("iframe:"+m.group(1));
					links.addLast(matcher.group(1));
				}
				return true;
			}
			
		}
		return false;
	}
	
	public boolean  matcher_frame(String str){
		String regex="<frame.*src=\"([^\"]+)\".*";
		String regex2="https?://.*";
		Matcher matcher=Pattern.compile(regex).matcher(str);
		if(matcher.matches()){
			//过滤链进行过滤
			if(chain.filter(str)){
				Matcher m=Pattern.compile(regex2).matcher(matcher.group(1));
				if(m.matches()){				
					SimpleHttpURLParser urlparser=new SimpleHttpURLParser();
					if(urlparser.vertifyURL(m.group(1))){
						logger.info("frame:"+m.group(1));
						links.addLast(m.group(1));
					}
				}
				else{
					logger.info("frame:"+m.group(1));
					links.addLast(matcher.group(1));
				}
				return true;
			}
			
		}
		return false;
	}
	
	public boolean matcher_title(String str){
		String regex="<title.*>(.*)</title>";
		Matcher matcher=Pattern.compile(regex).matcher(str);
		if(matcher.matches()){
			logger.info("title:"+matcher.group(1));
			document.addAttribute("title", matcher.group(1));
			return true;
		}
		return false;
	}
	
	/*
	 * 把解析得到的document存入数据库中
	 */
	/*
	 * 主要把标题、关键字、图片链接、框架链接 的链接及文本解析出来
	 */
	public void parser() throws Exception{	
		links=new LinkedList<String>();
		document=new Document(CrawlWebCentralThread.document_id++);
		
		try {
			
			Parser parser = new Parser(file.getAbsolutePath());
			if(crawlurl.getCharSet()!=null){
				this.setEncode(crawlurl.getCharSet());
			}
			parser.setEncoding(encode);
			int size = linkfilterchain.getSize();
			NodeFilter[] filterchain = new NodeFilter[size];
			
			
			//取出要用到的过滤器
			int num = 0;
			while (linkfilterchain.hasNextNodeFilter()) {
				filterchain[num] = linkfilterchain.NextNodeFilter();
				num++;
			}
			
			
			//组成或过滤器
			OrFilter chainfilter = new OrFilter(filterchain);
			NodeList nodelist = parser.extractAllNodesThatMatch(chainfilter);
			logger.info("Parser url:");
			
			
			//过滤处理
			for (int i = 0; i < nodelist.size(); i++) {
				Node node = nodelist.elementAt(i);
				// 关键字检查
				if(matcher_title(node.toHtml()));
				else if(matcher_meta(node.toHtml()));
				else if(matcher_link(node.toHtml()));
				else if(matcher_frame(node.toHtml()));
				else if(matcher_iframe(node.toHtml()));
			}
			
			/*
			 * 将document加入列表
			 * 将crawlurl加入到visitedurl
			 */
			CrawlWebCentralThread.webpages.addDocument(document);
			BreadthFirstTraversal.add_known_URLVisited(crawlurl);
			// 解析出来正确的url
			
		} catch (ParserException e) {
			logger.error(e.getMessage());
		}
		
		//将解析得到的URL加入到unvisitedurl
		Iterator<String> iterator=links.iterator();
		while(iterator.hasNext()){
			CrawlUrl crawl=new CrawlUrl();
			crawl.setOriUrl(iterator.next());
			//查看是否在visitedurl中，如果不存在则插入unvisitdurl
			boolean isExist=BreadthFirstTraversal.addURLVisited(crawl);
			logger.info(crawl.getOriUrl()+":"+isExist);
			if(!isExist){
				boolean isInsert=BreadthFirstTraversal.addUNURLVisited(crawl);
				logger.info(crawl.getOriUrl()+":"+!isInsert);
			}
			
		}
	}


}

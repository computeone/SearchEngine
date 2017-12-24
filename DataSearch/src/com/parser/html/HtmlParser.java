package com.parser.html;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
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

import com.http.traversal.BreadthFirstTraversal;
import com.http.traversal.CrawlUrl;
import com.http.connect.SimpleHttpURLParser;
import com.http.control.CrawlWebCentralThread;
import com.search.dao.Connect;
import com.search.data.Document;

/*
 * 
 * 
 */
public class HtmlParser implements DocumentParser {
	private String encoding="utf-8";
	private CrawlUrl crawlurl;
	private File file;
	private URLFilterChain chain;
	private Document document;
	private HtmlLinkFilterChain linkfilterchain;
	private Logger logger = LogManager.getLogger("HtmlParser");
	
	private LinkedList<CrawlUrl> links;


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
	
	private void setEncode(String encoding){
		this.encoding=encoding;
	}
	
	public LinkedList<CrawlUrl> getLinks(){
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

		String regex1=".*name=\"(.*)\".*content=\"([^\"]*)\".*";
		String regex2=".*content=\"([^\"]*)\".*name=\"(.*)\".*";
		String regex3=".*http-equiv=\"(.*)\".*content=\"([^\"]*)\".*";
		String regex4=".*content=\"([^\"]*)\".*http-equiv=\"(.*)\".*";
		
		
		// 正则表达式来识别超链接
		Pattern pattern_keyword = Pattern.compile("<meta.*/?>",Pattern.DOTALL);
		Matcher matcher1=Pattern.compile(regex1,Pattern.DOTALL).matcher(str);
		Matcher matcher2=Pattern.compile(regex2,Pattern.DOTALL).matcher(str);
		Matcher matcher3=Pattern.compile(regex3,Pattern.DOTALL).matcher(str);
		Matcher matcher4=Pattern.compile(regex4,Pattern.DOTALL).matcher(str);
		
		//匹配器
		Matcher matcher_keyword=pattern_keyword.matcher(str);
		boolean result=matcher_keyword.matches();
		if(result){
			if(matcher1.matches()){
				String s=matcher1.group(1).toLowerCase();
				
				if(s.equals("keywords")||s.equals("description")){
					logger.info("name:"+matcher1.group(1)+"  content:"+matcher1.group(2));
					document.addIndex_attribute(matcher1.group(1), matcher1.group(2));
				}
				
			}
			else if(matcher2.matches()){
				String s=matcher2.group(1).toLowerCase();
				
				if(s.equals("keywords")||s.equals("description")){
					logger.info("name:"+matcher2.group(2)+"  content:"+matcher2.group(1));
					document.addIndex_attribute(matcher2.group(2),matcher2.group(1));
				}
				
			}
			else if(matcher3.matches()){
				logger.info("http-equiv:"+matcher3.group(1)+"  content:"+matcher3.group(2));
				document.addStore_attribute(matcher3.group(1), matcher3.group(2));
			}
			else if(matcher4.matches()){
				logger.info("http-equiv:"+matcher1.group(2)+"  content:"+matcher1.group(1));
				document.addStore_attribute(matcher4.group(2),matcher4.group(1));
			}
			return true;
		}
		return false;
	}
	
	//解析超链接
	public boolean matcher_link(String str){
		String regex1="<a.*/?>";
		String regex2=".*href=\"([^\"]+)\".*";
		Matcher matcher1=Pattern.compile(regex1,Pattern.DOTALL).matcher(str);
		
		if(matcher1.matches()){
			Matcher matcher2=Pattern.compile(regex2,Pattern.DOTALL).matcher(matcher1.group());
			if(matcher2.matches()){
				
				//对相对路劲进行补全
				String link=matcher2.group(1);
				if(!link.startsWith("http")){
					if(crawlurl.getOriUrl().endsWith("/")){
						link=crawlurl.getOriUrl()+link;
					}
					else{
						link=crawlurl.getOriUrl()+"/"+link;
					}
				}
				
				//进行过滤
				if(chain!=null){
					if(chain.filter(link)){
						SimpleHttpURLParser urlparser=new SimpleHttpURLParser();
						if(urlparser.vertifyURL(link)){						
							//
							if(link.endsWith("/")){
								link=link.substring(0, link.length()-1);
							}
							logger.info("link:"+link);
							//添加crawlurl
							CrawlUrl linkurl=new CrawlUrl();
							linkurl.setOriUrl(link);
							linkurl.setLayer(crawlurl.getLayer()+1);
							links.addLast(linkurl);
						}						
					}
				}
				else{
					SimpleHttpURLParser urlparser=new SimpleHttpURLParser();
					if(urlparser.vertifyURL(link)){						
						//
						if(link.endsWith("/")){
							link=link.substring(0, link.length()-1);
						}
						logger.info("link:"+link);
						//添加crawlurl
						CrawlUrl linkurl=new CrawlUrl();
						linkurl.setOriUrl(link);
						linkurl.setLayer(crawlurl.getLayer()+1);
						links.addLast(linkurl);
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
		Matcher matcher=Pattern.compile(regex,Pattern.DOTALL).matcher(str);
		if(matcher.matches()){
						
			//对相对路径补全
		   String iframe_link=matcher.group(1);
		   if(!iframe_link.startsWith("http")){
			   if(!crawlurl.getOriUrl().endsWith("/")){
				   iframe_link=crawlurl.getOriUrl()+"/"+iframe_link;
			   }
			   else{
				   iframe_link=crawlurl.getOriUrl()+iframe_link;
			   }
		   }
		   
			//过滤链进行过滤
		   if(chain!=null){
			   if(chain.filter(iframe_link)){
					Matcher m=Pattern.compile(regex2,Pattern.DOTALL).matcher(iframe_link);
					if(m.matches()){
						logger.info("iframe:"+iframe_link);
						
						//
						CrawlUrl iframe_url=new CrawlUrl();
						iframe_url.setOriUrl(iframe_link);
						iframe_url.setLayer(crawlurl.getLayer()+1);
						links.addLast(iframe_url);
					}
					return true;
				}
		   }
		   
		   else{
			   Matcher m=Pattern.compile(regex2,Pattern.DOTALL).matcher(iframe_link);
				if(m.matches()){
					logger.info("iframe:"+iframe_link);
					
					//
					CrawlUrl iframe_url=new CrawlUrl();
					iframe_url.setOriUrl(iframe_link);
					iframe_url.setLayer(crawlurl.getLayer()+1);
					links.addLast(iframe_url);
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
			
			 String frame_link=matcher.group(1);
			   if(!frame_link.startsWith("http")){
				   if(!crawlurl.getOriUrl().endsWith("/")){
					   frame_link=crawlurl.getOriUrl()+"/"+frame_link;
				   }
				   else{
					   frame_link=crawlurl.getOriUrl()+frame_link;
				   }
			   }
			//过滤链进行过滤
			if(chain!=null){
				if(chain.filter(frame_link)){
					Matcher m=Pattern.compile(regex2,Pattern.DOTALL).matcher(frame_link);
					if(m.matches()){				
						SimpleHttpURLParser urlparser=new SimpleHttpURLParser();
						if(urlparser.vertifyURL(frame_link)){
							logger.info("frame:"+frame_link);
							
							//
							CrawlUrl frame_url=new CrawlUrl();
							frame_url.setOriUrl(frame_link);
							frame_url.setLayer(crawlurl.getLayer()+1);
							
							links.addLast(frame_url);
						}
					}
					
					return true;
				}
			}
			else{
				Matcher m=Pattern.compile(regex2,Pattern.DOTALL).matcher(frame_link);
				if(m.matches()){				
					SimpleHttpURLParser urlparser=new SimpleHttpURLParser();
					if(urlparser.vertifyURL(frame_link)){
						logger.info("frame:"+frame_link);
						
						//
						CrawlUrl frame_url=new CrawlUrl();
						frame_url.setOriUrl(frame_link);
						frame_url.setLayer(crawlurl.getLayer()+1);
						
						links.addLast(frame_url);
					}
				}
				
				return true;
			}			
		}
		return false;
	}
	
	public boolean matcher_title(String str){
		String regex="<title.*>([^\"]*)</title>";
		Matcher matcher=Pattern.compile(regex,Pattern.DOTALL).matcher(str);
		if(matcher.matches()){
			String title=matcher.group(1).replaceAll("\\s", "");
			System.out.println();
			logger.info("title:"+title);			
			document.addIndex_attribute("title", title);
			return true;
		}
		return false;
	}
	
	public void writeDocument_to_database() throws Exception{
		String sql = "insert into Document(id,rank,create_date,store_attributes,index_attributes) values (?,?,?,?,?)";
		Connection con=null;
		try {
			con = Connect.getConnection();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		PreparedStatement stmt = con.prepareStatement(sql);
				
		logger.info("writing id="+document.getID()+" document");
				
		//序列化
		ByteArrayOutputStream attributes_out=new ByteArrayOutputStream();
		ObjectOutputStream attributes_object=new ObjectOutputStream(attributes_out);
		attributes_object.writeObject(document.getStore_attributes());
		ByteArrayInputStream attributes_in=new ByteArrayInputStream(attributes_out.toByteArray());
				
		//序列化
		ByteArrayOutputStream indexattributes_out=new ByteArrayOutputStream();
		ObjectOutputStream indexattributes_object=new ObjectOutputStream(indexattributes_out);
		indexattributes_object.writeObject(document.getIndex_attributes());
		ByteArrayInputStream indexattributes_in=new ByteArrayInputStream(indexattributes_out.toByteArray());
				
		//写占位符
		stmt.setLong(1, document.getID());
		stmt.setInt(2, document.getRanks());
		stmt.setTimestamp(3, new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
		stmt.setAsciiStream(4,attributes_in);
		stmt.setAsciiStream(5,indexattributes_in);
		stmt.execute();
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void parserTitle(File file) throws IOException{
		FileInputStream in=new FileInputStream(file);
		InputStreamReader reader=new InputStreamReader(in,"utf-8");
		String str="";
		int count=0;
		int ch=0;
		while((ch=reader.read())!=-1){
			char c=(char)ch;
			if(c=='\n'){
				count++;
				if(count==50){
					in.close();
					reader.close();
					break;
				}
			}			
			str=str+c;
		}
		in.close();
		reader.close();
		//
		Matcher matcher=Pattern.compile(".*<title.*>([^\"]*)</title>.*", Pattern.DOTALL).matcher(str);
		if(matcher.matches()){
			String title=matcher.group(1).replaceAll("\\s", "");
			logger.info("解析的title:"+title);
			document.addIndex_attribute("title", title);
			
		}
	}
	
	private void parserKeywords(File file) throws IOException{
		FileInputStream in=new FileInputStream(file);
		InputStreamReader reader=new InputStreamReader(in,"utf-8");
		String str="";
		int count=0;
		int ch=0;
		while((ch=reader.read())!=-1){
			char c=(char)ch;
			if(c=='\n'){
				count++;
				if(count==50){
					in.close();
					reader.close();
					break;
				}
			}			
			str=str+c;
		}
		in.close();
		reader.close();
		
		//
		Matcher matcher=Pattern.compile(".*name=\"keywords\"\\s+content=\"([^\"]*)\".*",Pattern.DOTALL).matcher(str);
		if(matcher.matches()){
			logger.info("解析的keywords:"+matcher.group(1));		
			document.addIndex_attribute("keywords", matcher.group(1));
		}
		else{
			Matcher matcher1=Pattern.compile(".*content=\"([^\"]*)\"\\s+name=\"keywords\".*",Pattern.DOTALL).matcher(str);
			if(matcher1.matches()){
				logger.info("解析的keywords:"+matcher1.group(1));
				document.addIndex_attribute("keywords", matcher1.group(1));
			}
		}
	}
	
	private void parserDescription(File file) throws IOException{
		FileInputStream in=new FileInputStream(file);
		InputStreamReader reader=new InputStreamReader(in,"utf-8");
		String str="";
		int count=0;
		int ch=0;
		while((ch=reader.read())!=-1){
			char c=(char)ch;
			if(c=='\n'){
				count++;
				if(count==50){
					in.close();
					reader.close();
					break;
				}
			}			
			str=str+c;
		}
		in.close();
		reader.close();
		//
		Matcher matcher=Pattern.compile(".*name=\"description\"\\s+content=\"([^\"]*)\".*",Pattern.DOTALL).matcher(str);
		if(matcher.matches()){
			logger.info("解析的Description:"+matcher.group(1));
			document.addIndex_attribute("description", matcher.group(1));
		}else{
			Matcher matcher1=Pattern.compile("", Pattern.DOTALL).matcher(str);
			if(matcher1.matches()){
				logger.info("解析的Description:"+matcher1.group(1));
				document.addIndex_attribute("description", matcher1.group(1));
			}
		}
	}
	private void parserEncoding(File file) throws Exception{
		FileInputStream in=new FileInputStream(file);
		InputStreamReader reader=new InputStreamReader(in);
		String str="";
		int count=0;
		int ch=0;
		while((ch=reader.read())!=-1){
			char c=(char)ch;
			if(c=='\n'){
				count++;
				if(count==50){
					in.close();
					reader.close();
					break;
				}
			}			
			str=str+c;
		}
		in.close();
		reader.close();
		//
		Matcher matcher=Pattern.compile(".*charset=\"?([\\w-]+)\".*", Pattern.DOTALL).matcher(str);
		if(matcher.matches()){
			logger.info("解析的encoding:"+matcher.group(1));
			crawlurl.setCharSet(matcher.group(1));
		}
	}
	/*
	 * 把解析得到的document存入数据库中
	 */
	/*
	 * 主要把标题、关键字、图片链接、框架链接 的链接及文本解析出来
	 */
	public void parser() throws Exception{	
		links=new LinkedList<CrawlUrl>();
		document=new Document(CrawlWebCentralThread.document_id++);
		document.setUrl(crawlurl.getOriUrl());
		document.setDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		document.addIndex_attribute("title", null);
		document.addIndex_attribute("keywords", null);
		document.addIndex_attribute("description", null);
		document.addStore_attribute("layer", String.valueOf(crawlurl.getLayer()));
		
		
		try {
			
			Parser parser = new Parser(file.getAbsolutePath());
			parserEncoding(file);
			if(crawlurl.getCharSet()!=null){
				this.setEncode(crawlurl.getCharSet());
			}
			logger.info("编码:"+encoding);
			parser.setEncoding(encoding);
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

			logger.info("encoding:"+crawlurl.getCharSet());
			
				logger.info("开始过滤处理提取");
			//过滤处理
			for (int i = 0; i < nodelist.size(); i++) {
			
				Node node = nodelist.elementAt(i);
				String s=node.toHtml();
				// 关键字
				if(matcher_title(s));
				else if(matcher_meta(s));
				else if(matcher_link(s));
				else if(matcher_frame(s));
				else if(matcher_iframe(s));
			}
			
			//如果charset是gb2312则解析编码会出现乱码，于是纠正
			if(encoding.equals("gb2312")||encoding.equals("gbk")){
				parserTitle(file);
				parserKeywords(file);
				parserDescription(file);
			}
			
			//
			CrawlWebCentralThread.addWebPage(document);
			logger.info("写入文档成功");
			logger.info("解析文档成功");
			/*
			 * 将document加入列表
			 */			
			// 解析出来正确的url
		} catch (ParserException e) {
			CrawlWebCentralThread.addWebPage(document);
			logger.info("写入文档成功");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		//将解析得到的URL加入到unvisitedurl
		Iterator<CrawlUrl> iterator=links.iterator();
		while(iterator.hasNext()){
			CrawlUrl crawl=iterator.next();
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

package com.search.Search;


import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.search.DAO.CURD;
import com.search.analyzer.SimpleAnalyzer;
import com.search.data.Document;
import com.search.data.Field;
/*
 * 
 * 
 */
public class Search {
	
	private Logger logger=LogManager.getLogger("Search");
		
	public LinkedList<Field> selectFields(LinkedList<Long> id) throws InterruptedException{
		
		LinkedList<Field> fields=new LinkedList<Field>();
		//同步锁存器
		CountDownLatch latch=new CountDownLatch(1);
		QueryFieldThread thread=new QueryFieldThread();
		thread.setFields(id);
		thread.run();
		//
		latch.await();		
		fields=thread.getSearchResult();	
		return fields;
	}
	
	//查询document通过Field
	public LinkedList<Document>  selectDocument(LinkedList<Field> fields) throws SQLException, Exception{
			
		
		LinkedList<Document> documents=new LinkedList<Document>();
			
		documents=CURD.selectDocuments(fields);
		
		logger.info("搜到document个数:"+documents.size());
		
		
		return documents;
		}
	//根据词条搜索得到LinkedList<Document>
	public LinkedList<Document> search(String term) throws SQLException, Exception  {
		LinkedList<Long> fields_id=new LinkedList<Long>();
		LinkedList<Field> fields=new LinkedList<Field>();
		fields_id = CURD.selectIndex(term).getTokens_id();	
		logger.info("搜到Token个数："+fields_id.size());
		
		//同步锁存器
		CountDownLatch latch=new CountDownLatch(1);
		QueryFieldThread thread=new QueryFieldThread(latch);
		thread.setFields(fields_id);
		thread.run();
		
		//
		latch.await();		
		fields=thread.getSearchResult();	
		logger.info("搜到Feild个数:"+fields.size());
		
		LinkedList<Document> documents=this.selectDocument(fields);
		
		logger.info("搜到Document个数:"+documents.size());
		return documents;
	}
	
	
	public LinkedList<Field> searchField(String term) throws Exception{
		
		LinkedList<Long> fields_id=new LinkedList<Long>();
		LinkedList<Field> fields=new LinkedList<Field>();
		fields_id = CURD.selectIndex(term).getTokens_id();	
		logger.info("搜到Field个数："+fields_id.size());
		
			
		//同步锁存器
		CountDownLatch latch=new CountDownLatch(1);
		QueryFieldThread thread=new QueryFieldThread(latch);
		thread.setFields(fields_id);
		thread.run();
		
		//
		latch.await();		
		fields=thread.getSearchResult();
		return fields;
	}
	public LinkedList<Field> Sort_ID(LinkedList<Field> result){
		return result;
	}
	
	//按照优先级排序
	public LinkedList<Field> Sort_Priority(LinkedList<Field> result){
		
		return result;
	}
		
	//语句搜索
	public LinkedList<Document> mergeSearch(String str) throws SQLException, Exception{
		//分词
		SimpleAnalyzer analyzer=new SimpleAnalyzer(str,true);
		LinkedList<String> terms=analyzer._analyzer();
		for(String s1:terms){
			logger.info(s1);
		}
		
		//得到LinkedList<SearchResult>
		ArrayList<LinkedList<Field>> search_result=
				new ArrayList<LinkedList<Field>>(terms.size());

		//对每一个词条进行搜索
		for(int i=0;i<terms.size();i++){
			search_result.add(i, this.searchField(terms.get(i)));
		}
		
		//对词之间的匹配度，排序
		logger.info("开始匹配:");
		for(int i=1;i<terms.size();i++){
			matcher(search_result.get(i-1),search_result.get(i),terms.get(i).length());
		}
		LinkedList<Field> fields=new LinkedList<Field>();
		//
		for(int i=0;i<search_result.size();i++){
			
			LinkedList<Field> search_fields=search_result.get(i);			
			//全部加入
			while(!search_fields.isEmpty()){
				fields.addLast(search_fields.removeFirst());
			}			
			
		}		
		
		//对相同匹配度的document按照rank排序
		logger.info("依照Rank进行排序:");
		LinkedList<Document> documents=new LinkedList<Document>();
		documents=this.selectDocument(fields);
		
		ShellSort.Sort(documents, new DocumentCompare_Rank());
		return documents;
	}
	//根据词之间的匹配程度进行设置优先级
	public void matcher(LinkedList<Field> result1,LinkedList<Field> result2,int term_size) throws IOException, Exception{
		

		//为空的话，返回
		if(result1.isEmpty()||result2.isEmpty()){
			return;
		}
		
		//转化为数组
		Field[] fields=new Field[result2.size()];
	    for(int i=0;i<result2.size();i++){
	    	fields[i]=result2.get(i);
	    }
	    
	    //匹配
		for(int i=0;i<result1.size();i++){
			
			Field f=new Field("",result1.get(i).getID(),term_size);
			int r=Arrays.binarySearch(fields,f,new FieldCompare_ID());
			
			if(r>=0){
				
				//如果存在移除result1中的Field,result2中的Field priority+10
				if(r<result2.size()){
					result1.remove(i);
					int matcher=Integer.parseInt(result2.get(r).getAttriubte("matcher"))+10;
					logger.info("result2中第:"+r+"个增加了匹配度");
					result2.get(r).alterAttribute("matcher", String.valueOf(matcher));
				}
			}
			
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		Search search = new Search();
		LinkedList<Document> result = search.search("dongfangbubai");
		long end = System.currentTimeMillis();
		System.out.println("用时为：" + (end - start) + "ms  搜索到:"+result.size()+"个结果");
		for(Document document:result){
			System.out.println(document.getIndex_attribute("keyword"));
		}
		System.out.println("-------------------------------------------------------");
		LinkedList<Document> documents=search.mergeSearch("lejie dongfangbubai");
		for(Document document:documents){
			System.out.println("url:"+document.getUrl());
			System.out.println("keyword:"+document.getIndex_attribute("keyword"));
			System.out.println("ranks:"+document.getRanks());
			System.out.println("matcher:"+Integer.parseInt(document.getStore_attriubte("matcher")));
			System.out.println("------------------------------");
		}
	}
}

package com.search.Search;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.search.DAO.CURD;
import com.search.analyzer.SimpleAnalyzer;
import com.search.data.Document;
import com.search.data.Field;
import com.search.data.IDhandler;
/*
 * 
 * 
 */
public class Search {
	private  LinkedList<Long> id_list=new LinkedList<Long>();
	private  LinkedList<Field> fields=new LinkedList<Field>();
	private  LinkedList<SearchResult> searchresult=new LinkedList<SearchResult>();
	
	private Logger logger=LogManager.getLogger("Search");
	
	
	
	public LinkedList<Field> selectFields(LinkedList<Long> id) throws InterruptedException{
		//同步锁存器
		CountDownLatch latch=new CountDownLatch(1);
		QueryResultThread thread=new QueryResultThread();
		thread.setFields(id_list);
		thread.run();
		//
		latch.await();		
		fields=thread.getSearchResult();	
		return fields;
	}
	
	//查询document通过Field
		public LinkedList<Document>  selectDocument(LinkedList<Field> fields) throws SQLException, Exception{
			CURD curd=new CURD();
			LinkedList<Document> documents=new LinkedList<Document>();
			IDhandler idhandler=new IDhandler(1);
			LinkedList<Long> ids=new LinkedList<Long>();
			Iterator<Field> iterator=fields.iterator();
			while(iterator.hasNext()){
				long id=iterator.next().getID();
				idhandler.setID(id);
				ids.addLast(idhandler.getDocumnent_id());
			}		
			documents=curd.selectDocuments(ids);
			return documents;
		}
	//根据词条搜索得到LinkedList<Document>
	public LinkedList<Document> search(String term) throws SQLException, Exception  {
		CURD curd = new CURD();
		id_list = curd.selectIndex(term).getTokens_id();	
		logger.info("搜到Field个数："+id_list.size());
		if(id_list==null)return null;	
			
		//同步锁存器
		CountDownLatch latch=new CountDownLatch(1);
		QueryResultThread thread=new QueryResultThread(latch);
		thread.setFields(id_list);
		thread.run();
		//
		latch.await();		
		fields=thread.getSearchResult();	
		
		LinkedList<Document> documents=this.selectDocument(fields);
		
		logger.info("搜到Document个数:"+documents.size());
		return documents;
	}
	
	
	public LinkedList<Field> Sort_ID(LinkedList<Field> result){
		return result;
	}
	
	//按照优先级排序
	public LinkedList<Field> Sort_Priority(LinkedList<Field> result){
		
		return result;
	}
	
	
	//语句搜素
	public LinkedList<SearchResult> mergeSearch(String str) throws SQLException, Exception{
		//分词
//		SimpleAnalyzer analyzer=new SimpleAnalyzer(str,true);
//		LinkedList<String> s=analyzer._analyzer();
//		for(String s1:s){
//			System.out.println(s1);
//		}
//		
//		//得到LinkedList<SearchResult>
//		ArrayList<LinkedList<SearchResult>> search_result=
//				new ArrayList<LinkedList<SearchResult>>(s.size());
//
//		//对每一个词条进行搜索
//		for(int i=0;i<s.size();i++){
//			search_result.add(i, this.search(s.get(i)));
//		}
//		
//		//对词之间的匹配度，设置优先级
//		for(int i=1;i<s.size();i++){
//			addPrority(search_result.get(i-1),search_result.get(i));
//		}
//		LinkedList<SearchResult> result=new LinkedList<SearchResult>();
//		for(int i=0;i<search_result.size();i++){
//			Iterator<SearchResult> iterator=search_result.get(i).iterator();
//			while(iterator.hasNext()){
//				result.addLast(iterator.next());
//			}
//		}
//		result=Sort_Priority(result);
//		return result;
		return null;
	}
	//根据词之间的匹配程度进行设置优先级
	public void addPrority(LinkedList<Field> result1,LinkedList<Field> result2) throws IOException{
		//为空的话，返回
		if(result1.isEmpty()||result2.isEmpty()){
			return;
		}
		
		//
		long[] id2=new long[result2.size()];
		for(int i=0;i<result2.size();i++){
			id2[i]=result2.get(i).getID();
		}
		
//		//
//		SearchResult[] id1=new SearchResult[result1.size()];
//		for(int i=0;i<result1.size();i++){
//			id1[i]=result1.get(i);
//		}
//		for(int i=0;i<id1.length;i++){
//			
//			int r=Arrays.binarySearch(id2, id1[i].getID()+id1[i].getTerm().length());
//			
//			if(r>=0){
//				int r1_priority=id1[i].getPriority();				
//				
//				int r2_priority=result2.get(r).getPriority();
//				
//				if(r1_priority>r2_priority){
//					result2.get(r).setPriority(r1_priority+5);
//				}
//				else{
//					result2.get(r).setPriority(r2_priority+5);
//				}
//			}
//		}
		
	}
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		Search search = new Search();
		LinkedList<Document> result = search.search("dongfangbubei");
		long end = System.currentTimeMillis();
		System.out.println("用时为：" + (end - start) + "ms  搜索到:"+result.size()+"个结果");
		for(Document document:result){
			System.out.println(document.getIndex_attribute("keyword"));
		}
	}
}

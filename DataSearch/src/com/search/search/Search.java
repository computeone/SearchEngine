package com.search.search;


import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.search.dao.CRUD;
import com.search.analyzer.SimpleAnalyzer;
import com.search.data.Document;
import com.search.data.Field;
import com.search.data.FieldIDOverException;
import com.search.data.IDhandler;
import com.search.index.Token_Structure;
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
	public LinkedList<Document>  selectDocument(LinkedList<Field> fields) throws Exception, SQLException {
			
		
		LinkedList<Document> documents=new LinkedList<Document>();
			
		documents=CRUD.selectDocuments(fields);	
		
		return documents;
	
	}
	
	//查询document通过Field
	public ArrayList<Document>  queryDocument(LinkedList<Field> fields) throws  Exception{
		ArrayList<Document> documents=new ArrayList<Document>();
		documents=CRUD.queryDocuments(fields);
		return documents;
	}
	
	//根据词条搜索得到LinkedList<Document>
	public LinkedList<Field> searchField(String term) throws InterruptedException, Exception, SQLException, IOException {
		
		LinkedList<Long> fields_id=new LinkedList<Long>();
		LinkedList<Field> fields=new LinkedList<Field>();

		Token_Structure tokens_id=CRUD.selectIndex(term);
		if(tokens_id==null){
			fields_id=new LinkedList<Long>();
		}
		else{
			fields_id=tokens_id.getTokens_id();
		}
		
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
		
	public LinkedList<Document> search(String term) throws Exception {
		LinkedList<Long> fields_id=new LinkedList<Long>();
		LinkedList<Field> fields=new LinkedList<Field>();
		Token_Structure tokens_id=CRUD.selectIndex(term);
		
		if(tokens_id==null){
			fields_id=new LinkedList<Long>();
		}
		else{
			fields_id = tokens_id.getTokens_id();
		}
			
		logger.info("搜到Token个数："+fields_id.size());
		
		//同步锁存器
		CountDownLatch latch=new CountDownLatch(1);
		QueryFieldThread thread=new QueryFieldThread(latch);
		thread.setFields(fields_id);
		thread.run();
		
		//
		latch.await();		
		fields=thread.getSearchResult();	
		
		logger.info("消重之前:"+term);
		IDhandler idhandler1=new IDhandler(1l);
		for(Field f:fields){
			idhandler1.setID(f.getID());
			logger.info("ID:"+idhandler1.getCurrent_Document_id()+" offset::"+idhandler1.getCurrent_Field_id()+" text:"+f.getText());
		}
		logger.info("开始消重:");
		//取出重复的field
//		ShellSort.Sort(fields, new FieldCompare_ID());
		LinkedList<Integer> remove=new LinkedList<Integer>();
		if (!fields.isEmpty()) {
			Field field = fields.getFirst();
			for (int i = 1; i < fields.size(); i++) {
				if (field.getID() == fields.get(i).getID()) {
					remove.addLast(i);
					field = fields.get(i);
				} else {
					field = fields.get(i);
				}
			}
		}
		//
		int count=0;
		for(int n:remove){
			fields.remove(n-count);
			count++;
		}
	
		logger.info("消重之后:"+term);
		IDhandler idhandler=new IDhandler(1l);
		for(Field f:fields){
			idhandler.setID(f.getID());
			logger.info("ID:"+idhandler.getCurrent_Document_id()+" offset::"+idhandler.getCurrent_Field_id()+" text:"+f.getText());
		}
		logger.info("消重结束:"+term);
		logger.info("搜到Feild个数:"+fields.size());
		
		LinkedList<Document> documents=this.selectDocument(fields);
		ShellSort.Sort(documents, new DocumentCompare_Rank());
		logger.info("搜到Document个数:"+documents.size());
		return documents;
	}
	 
	//语句搜索
	public LinkedList<Document> mergeSearch(String str) throws Exception {
		//分词
		SimpleAnalyzer analyzer=new SimpleAnalyzer(str,true);
		LinkedList<String> terms=analyzer._analyzer();
		for(String s1:terms){
			logger.info(s1);
		}
		
		//得到LinkedList<SearchResult>
		ArrayList<LinkedList<Field>> search_result=
				new ArrayList<LinkedList<Field>>(terms.size());

		long start=System.currentTimeMillis();
		//对每一个词条进行搜索
		for(int i=0;i<terms.size();i++){
			LinkedList<Field> tmp_fields=this.searchField(terms.get(i));
			if(tmp_fields!=null){
				search_result.add(i, tmp_fields);
			}
			else{
				search_result.add(new LinkedList<Field>());
			}
			
		}
		long end=System.currentTimeMillis();
		System.out.println("field查找时间"+(end-start));
		
		//消除重复的Field
		logger.info("开始消除重复的Field");
		long start2=System.currentTimeMillis();
		for(int i=0;i<search_result.size();i++){
			LinkedList<Field> result=search_result.get(i);
//			ShellSort.Sort(result, new FieldCompare_ID());
			if(!result.isEmpty()){
				Field field=result.getFirst();
				for(int j=1;j<result.size();j++){
					if(field.getID()==result.get(j).getID()){
						result.remove(j);
					}
					else{
						field=result.get(j);
					}
				}
			}
			
			long end2=System.currentTimeMillis();
			System.out.println("消重时间:"+(end2-start2));
			System.out.println("-----------------------------------");
		}
		//对词之间的匹配度，排序
		logger.info("开始匹配:");
		long start1=System.currentTimeMillis();
		for(int i=1;i<terms.size();i++){
			matcher(search_result.get(i-1),search_result.get(i));
		}
		long end1=System.currentTimeMillis();
		System.out.println("匹配时间:"+(end1-start1));
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
		long start3=System.currentTimeMillis();
		documents=this.selectDocument(fields);
		long end3=System.currentTimeMillis();
		System.out.println("查找Document时间:"+(end3-start3));
		long start4=System.currentTimeMillis();
		ShellSort.Sort(documents, new DocumentCompare_Rank());
		long end4=System.currentTimeMillis();
		System.out.println("排序时间:"+(end4-start4));
		return documents;
	}
	
	public ArrayList<Document> StentenceSearch(String str) throws Exception{
		SimpleAnalyzer analyzer=new SimpleAnalyzer(str,true);
		LinkedList<String> terms=analyzer._analyzer();
		for(String s1:terms){
			logger.info(s1);
		}
		
		//得到LinkedList<SearchResult>
		ArrayList<LinkedList<Field>> search_result=
				new ArrayList<LinkedList<Field>>(terms.size());

		long start=System.currentTimeMillis();
		//对每一个词条进行搜索
		for(int i=0;i<terms.size();i++){
			LinkedList<Field> tmp_fields=this.searchField(terms.get(i));
			if(tmp_fields!=null){
				search_result.add(i, tmp_fields);
			}
			else{
				search_result.add(new LinkedList<Field>());
			}
			
		}
		long end=System.currentTimeMillis();
		System.out.println("field查找时间"+(end-start));
		
		//消除重复的Field
		logger.info("开始消除重复的Field");
		long start2=System.currentTimeMillis();
		for(int i=0;i<search_result.size();i++){
			LinkedList<Field> result=search_result.get(i);
			if(!result.isEmpty()){
				Field field=result.getFirst();
				for(int j=1;j<result.size();j++){
					if(field.getID()==result.get(j).getID()){
						result.remove(j);
					}
					else{
						field=result.get(j);
					}
				}
			}
			
			long end2=System.currentTimeMillis();
			System.out.println("消重时间:"+(end2-start2));
			System.out.println("-----------------------------------");
		}
		//对词之间的匹配度，排序
		logger.info("开始匹配:");
		long start1=System.currentTimeMillis();
		for(int i=1;i<terms.size();i++){
			matcher(search_result.get(i-1),search_result.get(i));
		}
		long end1=System.currentTimeMillis();
		System.out.println("匹配时间:"+(end1-start1));
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
		ArrayList<Document> documents=new ArrayList<Document>();
		long start3=System.currentTimeMillis();
		documents=this.queryDocument(fields);
		long end3=System.currentTimeMillis();
		System.out.println("查找Document时间:"+(end3-start3));
		return documents;
	}
	//根据词之间的匹配程度进行设置优先级
	public void matcher(LinkedList<Field> result1,LinkedList<Field> result2) throws IOException, FieldIDOverException{
		

		//为空的话，返回
		if(result1.isEmpty()||result2.isEmpty()){
			return;
		}
		
		//转化为数组
		Field[] fields=new Field[result2.size()];
	    for(int i=0;i<result2.size();i++){
	    	fields[i]=result2.get(i);
	    }
	    
	    int m=0;
	    //匹配
		for(int i=0;i<result1.size();i++){
			
			Field f=new Field("",result1.get(i).getID(),0);
			int r=Arrays.binarySearch(fields,m,result2.size(),f,new FieldCompare_ID());
			
			if(r>=0){
				//
				m=r;
				//如果存在移除result1中的Field,result2中的Field priority+10
				if(r<result2.size()){
					String matcher1=result1.get(i).getAttriubte("matcher");
					result1.remove(i);
					int matcher=Integer.parseInt(matcher1)+10;
//					logger.info("result2中第:"+r+"个增加了匹配度");
					result2.get(r).alterAttribute("matcher", String.valueOf(matcher));
				}
			}
		}
		
	}
	
	public String getKey(Document document){
		int count=Integer.parseInt(document.getStore_attriubte("index"));
		String key=document.getIndex_key(count);
		return key;
	}
	public static void main(String[] args) throws Exception{
		
		Search search = new Search();
		long start = System.currentTimeMillis();
//		LinkedList<Document> documents = search.search("飞机");
		ArrayList<Document> documents = search.StentenceSearch("2013年运动会");
		long end = System.currentTimeMillis();
		System.out.println("用时为：" + (end - start) + "ms  搜索到:"+documents.size()+"个结果");
		System.out.println("----------------------------------------------------------------------");
		if(documents.isEmpty()){
			System.out.println("没有匹配的结果");
		}
		int size=0;
		while(!documents.isEmpty()&&size<100){
			BuildHeap.buildHeap(documents, new DocumentCompare_Rank());
			size++;
			Document document=documents.remove(0);
			System.out.println("url:"+document.getUrl());
			if(document.getIndex_attribute("title")!=null){
				System.out.println(document.getIndex_attribute("title"));
				System.out.println(new String(document.getIndex_attribute("title").getBytes(),"gb2312"));
			}
			if(document.getIndex_attribute("description")!=null){
				System.out.println(document.getIndex_attribute("description"));
			}
			if(document.getIndex_attribute("keywords")!=null){
				
				System.out.println(document.getIndex_attribute("keywords"));
			}
			System.out.println("matcher:"+Integer.parseInt(document.getStore_attriubte("matcher")));
			System.out.println("ranks:"+document.getRanks());			
			System.out.println("------------------------------");
		}
	}
}

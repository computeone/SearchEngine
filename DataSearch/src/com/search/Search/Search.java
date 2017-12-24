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
		//ͬ��������
		CountDownLatch latch=new CountDownLatch(1);
		QueryFieldThread thread=new QueryFieldThread();
		thread.setFields(id);
		thread.run();
		//
		latch.await();		
		fields=thread.getSearchResult();	
		return fields;
	}
	
	//��ѯdocumentͨ��Field
	public LinkedList<Document>  selectDocument(LinkedList<Field> fields) throws Exception, SQLException {
			
		
		LinkedList<Document> documents=new LinkedList<Document>();
			
		documents=CRUD.selectDocuments(fields);	
		
		return documents;
	
	}
	
	//��ѯdocumentͨ��Field
	public ArrayList<Document>  queryDocument(LinkedList<Field> fields) throws  Exception{
		ArrayList<Document> documents=new ArrayList<Document>();
		documents=CRUD.queryDocuments(fields);
		return documents;
	}
	
	//���ݴ��������õ�LinkedList<Document>
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
		
		logger.info("�ѵ�Field������"+fields_id.size());
				
			
		//ͬ��������
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
	
	//�������ȼ�����
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
			
		logger.info("�ѵ�Token������"+fields_id.size());
		
		//ͬ��������
		CountDownLatch latch=new CountDownLatch(1);
		QueryFieldThread thread=new QueryFieldThread(latch);
		thread.setFields(fields_id);
		thread.run();
		
		//
		latch.await();		
		fields=thread.getSearchResult();	
		
		logger.info("����֮ǰ:"+term);
		IDhandler idhandler1=new IDhandler(1l);
		for(Field f:fields){
			idhandler1.setID(f.getID());
			logger.info("ID:"+idhandler1.getCurrent_Document_id()+" offset::"+idhandler1.getCurrent_Field_id()+" text:"+f.getText());
		}
		logger.info("��ʼ����:");
		//ȡ���ظ���field
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
	
		logger.info("����֮��:"+term);
		IDhandler idhandler=new IDhandler(1l);
		for(Field f:fields){
			idhandler.setID(f.getID());
			logger.info("ID:"+idhandler.getCurrent_Document_id()+" offset::"+idhandler.getCurrent_Field_id()+" text:"+f.getText());
		}
		logger.info("���ؽ���:"+term);
		logger.info("�ѵ�Feild����:"+fields.size());
		
		LinkedList<Document> documents=this.selectDocument(fields);
		ShellSort.Sort(documents, new DocumentCompare_Rank());
		logger.info("�ѵ�Document����:"+documents.size());
		return documents;
	}
	 
	//�������
	public LinkedList<Document> mergeSearch(String str) throws Exception {
		//�ִ�
		SimpleAnalyzer analyzer=new SimpleAnalyzer(str,true);
		LinkedList<String> terms=analyzer._analyzer();
		for(String s1:terms){
			logger.info(s1);
		}
		
		//�õ�LinkedList<SearchResult>
		ArrayList<LinkedList<Field>> search_result=
				new ArrayList<LinkedList<Field>>(terms.size());

		long start=System.currentTimeMillis();
		//��ÿһ��������������
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
		System.out.println("field����ʱ��"+(end-start));
		
		//�����ظ���Field
		logger.info("��ʼ�����ظ���Field");
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
			System.out.println("����ʱ��:"+(end2-start2));
			System.out.println("-----------------------------------");
		}
		//�Դ�֮���ƥ��ȣ�����
		logger.info("��ʼƥ��:");
		long start1=System.currentTimeMillis();
		for(int i=1;i<terms.size();i++){
			matcher(search_result.get(i-1),search_result.get(i));
		}
		long end1=System.currentTimeMillis();
		System.out.println("ƥ��ʱ��:"+(end1-start1));
		LinkedList<Field> fields=new LinkedList<Field>();
		//
		for(int i=0;i<search_result.size();i++){
			
			LinkedList<Field> search_fields=search_result.get(i);			
			//ȫ������
			while(!search_fields.isEmpty()){
				fields.addLast(search_fields.removeFirst());
			}						
		}		
		
		//����ͬƥ��ȵ�document����rank����
		logger.info("����Rank��������:");
		LinkedList<Document> documents=new LinkedList<Document>();
		long start3=System.currentTimeMillis();
		documents=this.selectDocument(fields);
		long end3=System.currentTimeMillis();
		System.out.println("����Documentʱ��:"+(end3-start3));
		long start4=System.currentTimeMillis();
		ShellSort.Sort(documents, new DocumentCompare_Rank());
		long end4=System.currentTimeMillis();
		System.out.println("����ʱ��:"+(end4-start4));
		return documents;
	}
	
	public ArrayList<Document> StentenceSearch(String str) throws Exception{
		SimpleAnalyzer analyzer=new SimpleAnalyzer(str,true);
		LinkedList<String> terms=analyzer._analyzer();
		for(String s1:terms){
			logger.info(s1);
		}
		
		//�õ�LinkedList<SearchResult>
		ArrayList<LinkedList<Field>> search_result=
				new ArrayList<LinkedList<Field>>(terms.size());

		long start=System.currentTimeMillis();
		//��ÿһ��������������
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
		System.out.println("field����ʱ��"+(end-start));
		
		//�����ظ���Field
		logger.info("��ʼ�����ظ���Field");
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
			System.out.println("����ʱ��:"+(end2-start2));
			System.out.println("-----------------------------------");
		}
		//�Դ�֮���ƥ��ȣ�����
		logger.info("��ʼƥ��:");
		long start1=System.currentTimeMillis();
		for(int i=1;i<terms.size();i++){
			matcher(search_result.get(i-1),search_result.get(i));
		}
		long end1=System.currentTimeMillis();
		System.out.println("ƥ��ʱ��:"+(end1-start1));
		LinkedList<Field> fields=new LinkedList<Field>();
		//
		for(int i=0;i<search_result.size();i++){
			
			LinkedList<Field> search_fields=search_result.get(i);			
			//ȫ������
			while(!search_fields.isEmpty()){
				fields.addLast(search_fields.removeFirst());
			}						
		}		
		
		//����ͬƥ��ȵ�document����rank����
		logger.info("����Rank��������:");
		ArrayList<Document> documents=new ArrayList<Document>();
		long start3=System.currentTimeMillis();
		documents=this.queryDocument(fields);
		long end3=System.currentTimeMillis();
		System.out.println("����Documentʱ��:"+(end3-start3));
		return documents;
	}
	//���ݴ�֮���ƥ��̶Ƚ����������ȼ�
	public void matcher(LinkedList<Field> result1,LinkedList<Field> result2) throws IOException, FieldIDOverException{
		

		//Ϊ�յĻ�������
		if(result1.isEmpty()||result2.isEmpty()){
			return;
		}
		
		//ת��Ϊ����
		Field[] fields=new Field[result2.size()];
	    for(int i=0;i<result2.size();i++){
	    	fields[i]=result2.get(i);
	    }
	    
	    int m=0;
	    //ƥ��
		for(int i=0;i<result1.size();i++){
			
			Field f=new Field("",result1.get(i).getID(),0);
			int r=Arrays.binarySearch(fields,m,result2.size(),f,new FieldCompare_ID());
			
			if(r>=0){
				//
				m=r;
				//��������Ƴ�result1�е�Field,result2�е�Field priority+10
				if(r<result2.size()){
					String matcher1=result1.get(i).getAttriubte("matcher");
					result1.remove(i);
					int matcher=Integer.parseInt(matcher1)+10;
//					logger.info("result2�е�:"+r+"��������ƥ���");
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
//		LinkedList<Document> documents = search.search("�ɻ�");
		ArrayList<Document> documents = search.StentenceSearch("2013���˶���");
		long end = System.currentTimeMillis();
		System.out.println("��ʱΪ��" + (end - start) + "ms  ������:"+documents.size()+"�����");
		System.out.println("----------------------------------------------------------------------");
		if(documents.isEmpty()){
			System.out.println("û��ƥ��Ľ��");
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

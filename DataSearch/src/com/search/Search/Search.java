package com.search.Search;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import com.search.DAO.CURD;
import com.search.analyzer.SimpleAnalyzer;
import com.search.data.IDhandler;
/*
 * 
 * 
 */
public class Search {
	private  LinkedList<Long> id_list=new LinkedList<Long>();
	private  static LinkedList<SearchResult> searchresult=new LinkedList<SearchResult>();
	
	public synchronized static void addSearchResult(SearchResult result){
		searchresult.addLast(result);
	}
	
	//按照id排序
	public LinkedList<SearchResult> Sort_ID(LinkedList<SearchResult> result){
		SearchResult[] search=new SearchResult[result.size()];
		int n=-1;
		while(!result.isEmpty()){
			search[++n]=result.pollFirst();
		}
		Arrays.sort(search,new SearchResultCompare_ID());
		for(int i=0;i<search.length;i++){
			result.addLast(search[i]);
		}
		return result;
	}
	
	//按照优先级排序
	public LinkedList<SearchResult> Sort_Priority(LinkedList<SearchResult> result){
		SearchResult[] search=new SearchResult[result.size()];
		int n=-1;
		while(!result.isEmpty()){
			search[++n]=result.pollFirst();
		}
		Arrays.sort(search,new SearchResultCompare_Priority());
		for(int i=0;i<search.length;i++){
			result.addLast(search[i]);
		}
		return result;
	}
	
	//
	public LinkedList<SearchResult> search(String term)  {
		CURD curd = new CURD();
		try{
		id_list = curd.selectIndex(term).getTokens_id();
		}catch(Exception e){
			System.out.println();
		}
		if(id_list==null)return null;
		
		QueryResultThread thread=new QueryResultThread();
		thread.setFields(id_list);
		thread.start();
		LinkedList<SearchResult> search_result=new LinkedList<SearchResult>();
		while(!Search.searchresult.isEmpty()){
			SearchResult s=Search.searchresult.pollFirst();
			s.setTerm(term);
			search_result.addLast(s);
		}
		
		return search_result;
	}
	//根据命中语句设置优先级
	public LinkedList<SearchResult> mergeSearch(String str) throws SQLException, Exception{
		//
		SimpleAnalyzer analyzer=new SimpleAnalyzer(str,true);
		LinkedList<String> s=analyzer._analyzer();
		for(String s1:s){
			System.out.println(s1);
		}
		//
		ArrayList<LinkedList<SearchResult>> search_result=
				new ArrayList<LinkedList<SearchResult>>(s.size());

		for(int i=0;i<s.size();i++){
			search_result.add(i, this.search(s.get(i)));
		}
		for(int i=1;i<s.size();i++){
			addPrority(search_result.get(i-1),search_result.get(i));
		}
		LinkedList<SearchResult> result=new LinkedList<SearchResult>();
		for(int i=0;i<search_result.size();i++){
			Iterator<SearchResult> iterator=search_result.get(i).iterator();
			while(iterator.hasNext()){
				result.addLast(iterator.next());
			}
		}
		result=Sort_Priority(result);
		return result;
	}
	//根据词之间的匹配程度进行设置优先级
	public void addPrority(LinkedList<SearchResult> result1,LinkedList<SearchResult> result2) throws IOException{
		if(result1.isEmpty()||result2.isEmpty()){
			return;
		}
		result2=Sort_ID(result2);
		
		long[] id2=new long[result2.size()];
		for(int i=0;i<result2.size();i++){
			id2[i]=result2.get(i).getID();
		}
		//
		SearchResult[] id1=new SearchResult[result1.size()];
		for(int i=0;i<result1.size();i++){
			id1[i]=result1.get(i);
		}
		for(int i=0;i<id1.length;i++){
			
			int r=Arrays.binarySearch(id2, id1[i].getID()+id1[i].getTerm().length());
			
			if(r>=0){
				int r1_priority=id1[i].getPriority();				
				
				int r2_priority=result2.get(r).getPriority();
				
				if(r1_priority>r2_priority){
					result2.get(r).setPriority(r1_priority+5);
				}
				else{
					result2.get(r).setPriority(r2_priority+5);
				}
			}
		}
		
	}
	public FileOutputStream writefile() {
		FileOutputStream out=null;
		try{
		File file=new File("e:\\resut1");
		out=new FileOutputStream(file);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return out;
	}
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		Search search = new Search();
		LinkedList<SearchResult> result = search.mergeSearch("校园3D巡游");
		long end = System.currentTimeMillis();
		System.out.println("用时为：" + (end - start) + "ms  搜索到:"+result.size()+"个结果");
		File file=new File("e:\\result");
		FileOutputStream out=new FileOutputStream(file);
		IDhandler idhandler=new IDhandler(1);
		int size=10;
		if(result.size()<10){
			size=result.size();
		}
		for(int i=0;i<size;i++){
			SearchResult s=result.get(i);
			System.out.println("-----------------------------");
			System.out.println(s.getURL());
			System.out.println(s.getContent());
			System.out.println(s.getPriority());
			out.write(s.getURL().getBytes());
			out.write('\n');
			out.write(s.getContent().getBytes());
			out.write('\n');
			out.write(String.valueOf(s.getPriority()).getBytes());
			out.write('\n');
			out.write(s.getTerm().getBytes());
			out.write('\n');
			idhandler.setID(s.getID());
			out.write(String.valueOf(s.getID()).getBytes());
			out.write('\n');
			out.write(String.valueOf(idhandler.getCurrent_Field_id()).getBytes());
			out.write('\n');
		}
	}
}

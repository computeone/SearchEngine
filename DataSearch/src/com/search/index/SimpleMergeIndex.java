package com.search.index;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import com.search.Search.ShellSort;
import com.search.data.Document;
import com.search.data.Field;
import com.search.data.IDhandler;
import com.search.data.Token;
/*
 * 将大量的索引合并有利于将索引写入数据库
 */
public class SimpleMergeIndex {
	private LinkedList<Document> documents=new LinkedList<Document>();
	private LinkedList<Field> fields = new LinkedList<Field>();
	private LinkedList<Token_Structure> tokens_structure ;
	
	public SimpleMergeIndex(LinkedList<Document> documents){
		this.documents=documents;
	}
	public LinkedList<Token_Structure> getToken_Structure() {
		return tokens_structure;
	}

	public LinkedList<Document> getDocuments(){
		return documents;
	}
	
	public LinkedList<Field> getField() {
		return fields;
	}

	//主要的核心方法
	public void mergeIndex() throws Exception{
		Sort();
	}
	
	// 从文件中得到tokens然后合并排序
	public void Sort() throws Exception {
		LinkedList<LinkedList<Token>> t = new LinkedList<LinkedList<Token>>();
//		// 从文件中得到tokens
		
		for (Document document:documents) {
			BuildIndex buildindex=new SimpleBuildIndex(document);
			Index index=buildindex.buildIndex();
			
				
			//将Field加入fields
			for(Field f:index.getFields()){
				fields.addLast(f);
			}
				
			LinkedList<Token> temp=new LinkedList<Token>();
			//将token加入tokens
			IDhandler idhandler=new IDhandler(1);
			for(Token token:index.getTokens()){
//				idhandler.setID(token.getID());
//				System.out.println("document id:"+idhandler.getCurrent_Document_id()+" field id:"
//						+idhandler.getCurrent_Field_id()+" offset:"+idhandler.getCurrent_Token_id()
//						+" term:"+token.getTerm());
				temp.addLast(token);
			}
			
			ShellSort.Sort(temp	,new TokenCompare());
			
			t.addLast(temp);		
		}
		System.out.println("t:"+t.size());
		// 进行合并排序
		while(t.size()!=1){
			LinkedList<LinkedList<Token>> list = new LinkedList<LinkedList<Token>>();
			list = this.Merge(t);
			System.out.println("list size:"+list.size());
//			System.out.println("****************************************************");
//			for(LinkedList<Token> t1:list){
//				
//				IDhandler idhandler =new IDhandler(1);
//				System.out.println("--------------------------------------------");
//				for(Token t2:t1){
//					idhandler.setID(t2.getID());
//					System.out.println("document id:"+idhandler.getCurrent_Document_id()+" field id:"
//							+idhandler.getCurrent_Field_id()+" offset:"+idhandler.getCurrent_Token_id()
//							+" term:"+t2.getTerm());
//				}
//				System.out.println("----------------------------------------------");
//			}
//			System.out.println("*********************************************");
			t.clear();
			while (!list.isEmpty()) {
				t.addLast(list.pollFirst());
			}
			
		}

		// 转化为一个文件并生成索引
		LinkedList<Token> index_list = new LinkedList<Token>();
		Iterator<LinkedList<Token>> iterator_list = t.iterator();
		while (iterator_list.hasNext()) {
			Iterator<Token> iterator = iterator_list.next().iterator();
			while (iterator.hasNext()) {
				index_list.add(iterator.next());
			}
		}
		LinkedList<Token_Structure> indexs = getIndexs(index_list);
		tokens_structure = indexs;
	}

	// 将一个大小为n的有序linkedlist<LinkedList<Token>> 合并为一个大小为2/n的LinkedList<LinkedList<Token>>
	private LinkedList<LinkedList<Token>> Merge(
			LinkedList<LinkedList<Token>> tokens){
		LinkedList<LinkedList<Token>> t = new LinkedList<LinkedList<Token>>();
		
		//如果能被2整除
		if(tokens.size()%2==0){
			while (!tokens.isEmpty()) {
				t.addLast(TokenSort.MergeSort(tokens.pollFirst(), tokens.pollLast()));
			}
		}
		
		else{
			int size=tokens.size();
			for(int i=0;i<size/2;i++){
				t.addLast(TokenSort.MergeSort(tokens.pollFirst(), tokens.pollLast()));
			}
			t.addLast(tokens.pollLast());
			
		}
	
		return t;
	}

	// 得到索引结构
	private LinkedList<Token_Structure> getIndexs(LinkedList<Token> linkedList) {
		LinkedList<Token_Structure> index_list = new LinkedList<Token_Structure>();
		while (!linkedList.isEmpty()) {
			Token token = linkedList.pollFirst();
			Token_Structure index = new Token_Structure(token.getTerm());
			index.add(token.getID());
			index_list.addLast(index);
			int frequency = 1;// 设置词频
			
			
			//
			while (!linkedList.isEmpty()
					&& linkedList.peekFirst().getTerm().equals(token.getTerm())) {
				index.add(linkedList.pollFirst().getID());
				frequency++;
			}
			this.Sort_ID(index.getTokens_id());
			index.setFrequency(frequency);
		}
		return index_list;
	}
	
	private void Sort_ID(LinkedList<Long> tokens_id){
		Long id=tokens_id.getFirst();
		int n=0;
		for(int i=0;i<tokens_id.size()-1;i++){
			
			for(int j=i+1;j<tokens_id.size();j++){
				if(id>tokens_id.get(j)){
					id=tokens_id.get(j);
					n=j;
				}
			}
			
			Long tempid=tokens_id.get(n);
			tokens_id.set(n, tokens_id.get(i));
			tokens_id.set(i, tempid);
			
			id=tokens_id.get(i+1);
			n=i+1;
		}
	}
	

	// 将索引写到特定的文件中
	public void write_index_to_file(String dirpath) throws IOException {
//		FileOutputStream out = new FileOutputStream(file);
//		Iterator<Token_Structure> iterator = this.tokens_structure.iterator();
//		while (iterator.hasNext()) {
//			Token_Structure index = iterator.next();
//			out.write("Term:".getBytes());
//			out.write(index.getTerm().getBytes());
//			out.write("\n".getBytes());
//			while (!index.isEmpty()) {
//				out.write(String.valueOf(index.pollFirst()).getBytes());
//				out.write("\n".getBytes());
//			}
//		}
//		out.close();
	}

	// 打印索引
	public void printIndex() throws Exception {
		Iterator<Token_Structure> iterator = tokens_structure.iterator();
		while (iterator.hasNext()) {
			Token_Structure index = iterator.next();
			System.out.print(index.getTerm() + ":");
			Iterator<Long> index_iterator = index.Iterator();
			while (index_iterator.hasNext()) {
				System.out.print(index_iterator.next() + "   ");
			}
			System.out.println();
		}
	}

}

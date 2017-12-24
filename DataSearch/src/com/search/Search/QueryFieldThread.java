package com.search.search;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import com.search.dao.Connect;
import com.search.data.Field;
import com.search.data.IDhandler;

public class QueryFieldThread implements Runnable {

	private LinkedList<Long> tokens_id;
	private CountDownLatch latch;
	private LinkedList<Field> fields=new LinkedList<Field>();
	public void setFields(LinkedList<Long> tokens_id){
		this.tokens_id=tokens_id;
	}
	
	public QueryFieldThread(){	
		
	}
	
	public QueryFieldThread(LinkedList<Long> tokens_id){
		this.tokens_id=tokens_id;
	}
	public QueryFieldThread(CountDownLatch latch){
		this.latch=latch;
	}
	
	public LinkedList<Field> getSearchResult(){
		return fields;
	}
	public LinkedList<Long> getFields(){
		return tokens_id;
	}
	
	@Override
	public void run() {
		Connection con = null;
		try {
			con = Connect.getConnection();
		} catch (Exception e1) {
			System.out.println("���ݿ�����ʧ��");
			e1.printStackTrace();
			System.exit(0);
		}
		
		Iterator<Long> iterator=tokens_id.iterator();
		while (iterator.hasNext()) {
			try {
				Statement stmt = con.createStatement();
				Long id =iterator.next();
				
				IDhandler idhandler = new IDhandler(id);
				ResultSet resultset = stmt.executeQuery("select *"
						+ "from field where id='"
						+ idhandler.getField_id() + "'");
				Field field = null;
				while (resultset.next()) {
					String text = resultset.getString("content");
					int priority = resultset.getInt("priority");
					field = new Field(text, idhandler.getDocumnent_id(),
							idhandler.getCurrent_Field_id());
					
					//�����л�
					ByteArrayInputStream in=new ByteArrayInputStream(resultset.getBytes("attributes"));
					ObjectInputStream object=new ObjectInputStream(in);
					@SuppressWarnings("unchecked")
					HashMap<String,String>  attributes=(HashMap<String, String>) object.readObject();
					
					//
					Set<String> keys=attributes.keySet();
					for(String key:keys){
						field.addAttribute(key, attributes.get(key));
					}				
					field.setPriority(priority);
					
					//���ƥ�������
					field.addAttribute("matcher", String.valueOf(0));
				}
				fields.addLast(field);
		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(latch!=null){
			latch.countDown();
		}
	}
}

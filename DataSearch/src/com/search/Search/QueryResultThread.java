package com.search.Search;

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

import com.search.DAO.Connect;
import com.search.data.Field;
import com.search.data.IDhandler;

public class QueryResultThread implements Runnable {

	private LinkedList<Long> ids;
	private CountDownLatch latch;
	private LinkedList<Field> fields=new LinkedList<Field>();
	public void setFields(LinkedList<Long> ids){
		this.ids=ids;
	}
	
	public QueryResultThread(){	
		
	}
	
	public QueryResultThread(LinkedList<Long> ids){
		this.ids=ids;
	}
	public QueryResultThread(CountDownLatch latch){
		this.latch=latch;
	}
	
	public LinkedList<Field> getSearchResult(){
		return fields;
	}
	public LinkedList<Long> getFields(){
		return ids;
	}
	
	@Override
	public void run() {
		Connection con = null;
		try {
			con = Connect.getConnection();
		} catch (Exception e1) {
			System.out.println("数据库连接失败");
			e1.printStackTrace();
			System.exit(0);
		}
		
		Iterator<Long> iterator=ids.iterator();
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
					
					//反序列化
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

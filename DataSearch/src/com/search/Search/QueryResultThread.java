package com.search.Search;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

public class QueryResultThread extends Thread {

	private LinkedList<Long> ids;
	private CountDownLatch latch;
	public void setFields(LinkedList<Long> ids){
		this.ids=ids;
	}
	
	public QueryResultThread(){	
	}
	
	public QueryResultThread(CountDownLatch latch){
		this.latch=latch;
	}
	public LinkedList<Long> getFields(){
		return ids;
	}
	
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
				SearchResult result;
				Long id =iterator.next();
				
				IDhandler idhandler = new IDhandler(id);
				ResultSet resultset = stmt.executeQuery("select priority,"
						+ "content from field where id='"
						+ idhandler.getField_id() + "'");
				Field field = null;
				while (resultset.next()) {
					String text = resultset.getString("content");
					int priority = resultset.getInt("priority");
					field = new Field(text, idhandler.getDocumnent_id(),
							idhandler.getField_id() >> 20);
					
					//反序列化
					ByteArrayInputStream in=new ByteArrayInputStream(resultset.getBytes("attributes"));
					ObjectInputStream object=new ObjectInputStream(in);
					@SuppressWarnings("unchecked")
					HashMap<String,String>  attributes=(HashMap<String, String>) object.readObject();
					
					Set<String> keys=attributes.keySet();
					for(String key:keys){
						field.addAttribute(key, attributes.get(key));
					}
					
					field.setPriority(priority);
					
				}
				Statement stmt_document = con.createStatement();
				ResultSet resultset_document = stmt_document
						.executeQuery("select content from document where "
								+ "id='" + idhandler.getDocumnent_id() + "'");
				String url = null;
				while (resultset_document.next()) {
					String content = resultset_document.getString("content");
					String[] split = content.split("\n");
					int num = (int) ((idhandler.getField_id() >> 20) - 1);
					url = split[num - 1];
				}
				result = new SearchResult(field, url);
				result.setID(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(latch!=null){
			latch.countDown();
		}
	}
}

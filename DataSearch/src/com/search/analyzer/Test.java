package com.search.analyzer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;

import com.search.DAO.Connect;
import com.search.index.Index_Structure;
public class Test {
	public static void main(String[] args) throws IOException, Exception {
		Connection con=Connect.getConnection("jdbc:mysql://localhost:3306/niubaisui",
				"root","niubaisui",Connect.DATABASE_TYPE_MYSQL);
		Statement stmt=con.createStatement();
		ResultSet resultset=stmt.executeQuery("select * from student where name='china'");
		while(resultset.next()){
			String result=resultset.getString("name");
			System.out.println(result);
		}
		int a=1000;
		int b=20/a;
		System.out.println(b);
		Index_Structure index=new Index_Structure("niubaisui");
		index.setFrequency(100);
		index.add(1000);
		index.add(2000);
		index.add(3000);
		byte[] buff;
		ByteArrayOutputStream bout=new ByteArrayOutputStream();
		ObjectOutputStream oop=new ObjectOutputStream(bout);
		oop.writeObject(index.getTokens_id());
		
		buff=bout.toByteArray();
		System.out.println(new String(buff));
		ByteArrayInputStream bin=new ByteArrayInputStream(buff);
		ObjectInputStream oin=new ObjectInputStream(bin);
		LinkedList<Long> list=(LinkedList<Long>) oin.readObject();
		Iterator<Long> iterator=list.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}

}

package com.search.DAO;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import com.search.data.Document;
import com.search.data.IDhandler;
import com.search.index.Token_Structure;

public class CURD {
	// 对特定的词进行关键字查询
	public Token_Structure selectIndex(String term) throws Exception,
			SQLException {
		Connection con = Connect.getConnection();
		String sql;
		Token_Structure index = new Token_Structure(term);
		byte[] b = term.getBytes();
		switch (b.length) {
		case 1:
			sql = "select * from token_1 where term=?";
			break;
		case 2:
			sql = "select * from token_2 where term=?";
			break;
		case 3:
			sql = "select * from token_3 where term=?";
			break;
		case 4:
			sql = "select * from token_4 where term=?";
			break;
		case 5:
			sql = "select * from token_5 where term=?";
			break;
		case 6:
			sql = "select * from token_6 where term=?";
			break;
		case 7:
			sql = "select * from token_7 where term=?";
			break;
		case 8:
			sql = "select * from token_8 where term=?";
			break;
		default:
			sql = "select * from token where term=?";
		}
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, index.getTerm());
		ResultSet resultset = stmt.executeQuery();
		if (!resultset.next()) {
			con.close();
			return null;
		} else {
			// 得到的tokens_id进行反序列化
			ByteArrayInputStream bin = new ByteArrayInputStream(
					resultset.getBytes("tokens_id"));
			ObjectInputStream oin = new ObjectInputStream(bin);
			@SuppressWarnings("unchecked")
			LinkedList<Long> list = (LinkedList<Long>) oin.readObject();
			int frequency = resultset.getInt("frequency");
			index.setFrequency(frequency);
			index.setTokens_id(list);
		}
		con.close();
		return index;
	}

	// 查询得到一系列Field的content
	public String[] selectField(LinkedList<Long> id) throws Exception,
			SQLException {
		Connection con = Connect.getConnection();
		Statement stmt = con.createStatement();
		Iterator<Long> iterator = id.iterator();
		String[] str = new String[id.size()];
		int i = -1;
		while (iterator.hasNext()) {
			IDhandler idhandler = new IDhandler(iterator.next());
			String sql = "select priority,content from Field where id='"
					+ idhandler.getField_id()+ "'";
			ResultSet resultset = stmt.executeQuery(sql);
			while (resultset.next()) {
				str[++i] = resultset.getString("content");
			}
		}
		con.close();
		return str;
	}

	// 查询得到Document
	public String selectDocument(long id) throws Exception, SQLException {
		Connection con = Connect.getConnection();
		Statement stmt = con.createStatement();
		String sql = "select content from Document where id='" + id + "'";
		ResultSet resultset = stmt.executeQuery(sql);
		String content = resultset.getString("content");
		con.close();
		return content;
	}
	
	//查询得到一系列Document
	public LinkedList<Document> selectDocuments(LinkedList<Long> ids) throws Exception,SQLException{
		Connection con=Connect.getConnection();
		LinkedList<Document> documents=new LinkedList<Document>();
		Statement stmt=con.createStatement();
		Iterator<Long> iterator=ids.iterator();
		
		while(iterator.hasNext()){
			long id=iterator.next();
			String sql="select * from Document where id'"+id+"'";
			stmt.executeQuery(sql);
			
			ResultSet result=stmt.getResultSet();
			//反序列化
			while(result.next()){
				Document document=new Document(result.getLong("id"));
				//反序列化attributes
				ByteArrayInputStream attributes_in=new ByteArrayInputStream(result.getBytes("attributes"));
				ObjectInputStream attributes_object=new ObjectInputStream(attributes_in);
				@SuppressWarnings("unchecked")
				HashMap<String,String> attributes=(HashMap<String,String>)attributes_object.readObject();
				
				//反序列化index_number
				ByteArrayInputStream indexnumber_in=new ByteArrayInputStream(result.getBytes("index_number"));
				ObjectInputStream indexnumber_object=new ObjectInputStream(indexnumber_in);
				@SuppressWarnings("unchecked")
				HashMap<String,Integer> index_number=(HashMap<String,Integer>)indexnumber_object.readObject();
				

				document.setRanks(result.getInt("rank"));
				document.setDate(result.getTimestamp("create_date"));
				document.setAttributes(attributes);
				document.setIndex_number(index_number);
				documents.add(document);
			}
			
			
		}
		return null;
	}

}


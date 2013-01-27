package com.search.DAO;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;

import com.search.data.IDhandler;
import com.search.index.Index_Structure;

public class CURD {
	//对特定的词进行关键字查询
	public Index_Structure selectIndex(String term) throws Exception,
			SQLException {
		Connection con = Connect.getConnection(
				"jdbc:mysql://localhost:3306/niubaisui", "root", "niubaisui",
				Connect.DATABASE_TYPE_MYSQL);
		String sql;
		Index_Structure index = new Index_Structure(term);
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
			//得到的tokens_id进行反序列化
				ByteArrayInputStream bin = new ByteArrayInputStream(
						resultset.getBytes("tokens_id"));
				ObjectInputStream oin = new ObjectInputStream(bin);
				LinkedList<Long> list = (LinkedList<Long>)oin.readObject();
				int frequency = resultset.getInt("frequency");
				index.setFrequency(frequency);
				index.setTokens_id(list);
		}
		con.close();
		return index;
	}
//查询得到Field
	public String[] selectField(LinkedList<Long> id) throws Exception,
			SQLException {
		Connection con = Connect.getConnection(
				"jdbc:mysql://localhost:3306/niubaisui", "root", "niubaisui",
				Connect.DATABASE_TYPE_MYSQL);
		Statement stmt = con.createStatement();
		Iterator<Long> iterator = id.iterator();
		String[] str = new String[id.size()];
		int i = -1;
		while (iterator.hasNext()) {
			IDhandler idhandler = new IDhandler(iterator.next());
			String sql = "select priority,content from field where id='"
					+ idhandler.Field_id() + "'";
			ResultSet resultset = stmt.executeQuery(sql);
			while (resultset.next()) {
				str[++i] = resultset.getString("content");
			}
		}
		con.close();
		return str;
	}
	//查询得到page
	public String selectPage(long id) throws Exception, SQLException{
		Connection con=Connect.getConnection("jdbc:mysql://localhost:3306/niubaisui",
				"root","niubaisui",Connect.DATABASE_TYPE_MYSQL);
		Statement stmt=con.createStatement();
		String sql="select content  from document where id='"+id+"'";
		ResultSet resultset=stmt.executeQuery(sql);
		String content=resultset.getString("content");
		con.close();
		return content;
	}
	
	public static void main(String[] args) throws SQLException, Exception {
		CURD curd=new CURD();
		Index_Structure index=curd.selectIndex("charset");
		if(index==null){
			System.out.println("null");
		}
		else{
			System.out.println("not null");
			System.out.println(index.getTerm());
			System.out.println(index.getFrequency());
			LinkedList<Long> list=index.getTokens_id();
			Iterator<Long> iterator=list.iterator();
			while(iterator.hasNext()){
				System.out.println(iterator.next());
			}
		}
	}
}

package com.search.index;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.search.dao.CRUD;
import com.search.dao.Connect;
import com.common.*;

public class IndexThread extends Thread {
	private LinkedList<Token_Structure> indexs;
	private Logger logger=LogManager.getLogger("IndexThread");
	private CountDownLatch latch;
	public IndexThread(LinkedList<Token_Structure> indexs,CountDownLatch latch) {
		this.indexs = indexs;
		this.latch=latch;
	}

	private String SaveToken_sql(int size) throws Exception, SQLException {
		String sql;
		switch (size) {
		case 1:
			sql = "insert into token_1(FREQUENCY,TERM,TOKENS_ID)values(?,?,?)";
			break;
		case 2:
			sql = "insert into token_2(FREQUENCY,TERM,TOKENS_ID)values(?,?,?)";
			break;
		case 3:
			sql = "insert into token_3(FREQUENCY,TERM,TOKENS_ID)values(?,?,?)";
			break;
		case 4:
			sql = "insert into token_4(FREQUENCY,TERM,TOKENS_ID)values(?,?,?)";
			break;
		case 5:
			sql = "insert into token_5(FREQUENCY,TERM,TOKENS_ID)values(?,?,?)";
			break;
		case 6:
			sql = "insert into token_6(FREQUENCY,TERM,TOKENS_ID)values(?,?,?)";
			break;
		case 7:
			sql = "insert into token_7(FREQUENCY,TERM,TOKENS_ID)values(?,?,?)";
			break;
		case 8:
			sql = "insert into token_8(FREQUENCY,TERM,TOKENS_ID)values(?,?,?)";
			break;
		default:
			sql = "insert into token(FREQUENCY,TERM,TOKENS_ID)values(?,?,?)";
		}
		return sql;
	}
		
	private  String Save_update(int size) {
		String sql;
		switch (size) {
		case 1:
			sql = "update token_1 set frequency=?,tokens_id=? where term=?";
			break;
		case 2:
			sql = "update token_2 set frequency=?,tokens_id=? where term=?";
			break;
		case 3:
			sql = "update token_3 set frequency=?,tokens_id=? where term=?";
			break;
		case 4:
			sql = "update token_4 set frequency=?,tokens_id=? where term=?";
			break;
		case 5:
			sql = "update token_5 set frequency=?,tokens_id=? where term=?";
			break;
		case 6:
			sql = "update token_6 set frequency=?,tokens_id=? where term=?";
			break;
		case 7:
			sql = "update token_7 set frequency=?,tokens_id=? where term=?";
			break;
		case 8:
			sql = "update token_8 set frequency=? ,tokens_id=? where term=?";
			break;
		default:
			sql = "update token set frequency=? ,tokens_id=? where term=?";
		}
		return sql;
	}
	
	public void run() {
		Connection con=null;
		try {
			con = Connect.getConnection();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		Iterator<Token_Structure> iterator=indexs.iterator();
		while (iterator.hasNext()) {
			try {
				
				// 设置词条的unicode代码
				Token_Structure index=iterator.next();
				byte[] unicode = index.getTerm().getBytes();
				String sql = this.SaveToken_sql(unicode.length);
				// 长度超过最大值,截断
				String term=null;
				if (index.getTerm().length() > 250) {
					term=index.getTerm().substring(0, 254);
				}
				else{
					term=index.getTerm();
				}
				
				Token_Structure updated_index = CRUD.selectIndex(term);
				// 如果存在则执行更新
				if (updated_index != null) {
					LinkedList<Long> updated_list = updated_index
							.getTokens_id();
					Iterator<Long> iterator_list = index.Iterator();
					int frequency = updated_index.getFrequency();
					while (iterator_list.hasNext()) {
						Long id = iterator_list.next();
						updated_list.addLast(id);
						frequency++;
					}
					// 执行排序
					ShellSort.Sort(updated_list,new LongCompare());
					// 进行序列化
					updated_index.setFrequency(frequency);
					ByteArrayOutputStream selected_bout = new ByteArrayOutputStream();
					ObjectOutputStream selected_out = new ObjectOutputStream(
							selected_bout);
					selected_out.writeObject(updated_list);

					PreparedStatement stmt = con.prepareStatement(this.Save_update(unicode.length));
					// 写占位符的变量
					
					ByteArrayInputStream updated_bin = new ByteArrayInputStream(
							selected_bout.toByteArray());
				
					logger.info("update term="+index.getTerm()+" index");
					stmt.setInt(1, updated_index.getFrequency());
					stmt.setAsciiStream(2, updated_bin);
					stmt.setString(3, term);
					
					stmt.execute();
					stmt.close();
				}
				// 执行插入操作
				else {
					// 进行排序tokens_id
					LinkedList<Long> tokens_id = index.getTokens_id();
					ShellSort.Sort(tokens_id, new LongCompare());
					// 序列化tokes_id
					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					ObjectOutputStream out = new ObjectOutputStream(bout);
					out.writeObject(tokens_id);
					PreparedStatement stmt = con.prepareStatement(sql);
					// 写占位符的变量
					
					ByteArrayInputStream bin = new ByteArrayInputStream(
							bout.toByteArray());
						
					logger.info("writing term="+index.getTerm()+" index");
					stmt.setInt(1, index.getFrequency());
					stmt.setString(2, term);				
					stmt.setAsciiStream(3, bin);
					stmt.execute();
					
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		latch.countDown();
	}
}

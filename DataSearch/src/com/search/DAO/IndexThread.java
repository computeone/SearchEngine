package com.search.DAO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import com.search.index.Index_Structure;

public class IndexThread extends Thread {
	private Index_Structure index;

	public IndexThread(Index_Structure index) {
		this.index = index;
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
		while (true) {
			try {
				
				// 设置词条的unicode代码
				byte[] unicode = index.getTerm().getBytes();
				String sql = DataBaseOp.SaveToken_sql(unicode.length);
				CURD curd = new CURD();
				Index_Structure updated_index = curd.selectIndex(index
						.getTerm());
				// 执行更新
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
					updated_list = DataBaseOp.Sort(updated_list);
					// 进行序列化
					updated_index.setFrequency(frequency);
					ByteArrayOutputStream selected_bout = new ByteArrayOutputStream();
					ObjectOutputStream selected_out = new ObjectOutputStream(
							selected_bout);
					selected_out.writeObject(updated_list);

					PreparedStatement stmt = con.prepareStatement(DataBaseOp
							.Save_update(unicode.length));
					// 写占位符的变量
					String term = null;
					// 长度超过最大值,截断
					if (index.getTerm().length() > 250) {
						term = updated_index.getTerm().substring(0, 254);
					} else {
						term = updated_index.getTerm();
					}
					stmt.setInt(1, updated_index.getFrequency());
					ByteArrayInputStream updated_bin = new ByteArrayInputStream(
							selected_bout.toByteArray());

					stmt.setAsciiStream(2, updated_bin);
					stmt.setString(3, term);
					System.out.println("正在更新Token");
					System.out.println(DataBaseOp.Save_update(term.length()));
					stmt.execute();

					index=DataBaseOp.getIndex_Structure();
					if(index==null){
						con.close();
						break;
					}
				}
				// 进行插入操作
				else {
					// 进行排序tokens_id
					LinkedList<Long> tokens_id = index.getTokens_id();
					tokens_id = DataBaseOp.Sort(tokens_id);
					// 序列化tokes_id
					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					ObjectOutputStream out = new ObjectOutputStream(bout);
					out.writeObject(tokens_id);
					PreparedStatement stmt = con.prepareStatement(sql);
					// 写占位符的变量
					String term = null;
					// 长度超过最大值,截断
					if (index.getTerm().length() > 250) {
						term = index.getTerm().substring(0, 254);
					} else {
						term = index.getTerm();
					}
					stmt.setInt(1, index.getFrequency());
					stmt.setString(2, term);
					ByteArrayInputStream bin = new ByteArrayInputStream(
							bout.toByteArray());

					stmt.setAsciiStream(3, bin);
					System.out.println("正在写Token");
					System.out.println(sql);
					stmt.execute();

					index=DataBaseOp.getIndex_Structure();
					if(index==null){
						con.close();
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

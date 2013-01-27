package com.search.DAO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.search.data.Field;
import com.search.data.IDhandler;
import com.search.data.Page;
import com.search.data.Token;
import com.search.index.IndexDataBase;
import com.search.index.IndexWriter;
import com.search.index.Index_Structure;

/*
 * 
 */
public class DataBaseOp {
	private static int id = 1;
	public String SavePage_sql() throws Exception, SQLException {
		String sql = "insert into document(ID,CREATE_DATE,CONTENT)values (?,?,?)";
		return sql;
	}

	public void write() throws Exception {

	}

	public String SaveField_sql() throws Exception, SQLException {
		String sql = "insert into field(ID,PRIORITY,CONTENT)values" + "(?,?,?)";
		return sql;
	}

	public String SaveToken_sql(int size) throws Exception, SQLException {
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

	private String Save_update(int size){
		String sql;
		switch(size){
		case 1:sql="update token_1 set frequency=?,tokens_id=? where term=?";
		break;
		case 2:sql="update token_2 set frequency=?,tokens_id=? where term=?";
		break;
		case 3:sql="update token_3 set frequency=?,tokens_id=? where term=?";
		break;
		case 4:sql="update token_4 set frequency=?,tokens_id=? where term=?";
		break;
		case 5:sql="update token_5 set frequency=?,tokens_id=? where term=?";
		break;
		case 6:sql="update token_6 set frequency=?,tokens_id=? where term=?";
		break;
		case 7:sql="update token_7 set frequency=?,tokens_id=? where term=?";
		break;
		case 8:sql="update token_8 set frequency=? ,tokens_id=? where term=?";
		break;
		default:
			sql="update token set frequency=? ,tokens_id=? where term=?";
		}
		return sql;
	}
	private String PrepareProcessor(String str) {
		String s = str.replaceAll("'", "##");
		return s;
	}
	//合并文件的策略,每1024个文件进行合并排序一次,剩下的分多种情况处理
	private LinkedList<Integer> getPart(int sum) {
		LinkedList<Integer> count = new LinkedList<Integer>();
		int n = sum / 1024;
		count.addLast(n);
		int mod = sum % 1024;
		while (true) {
			if (mod >= 512) {
				count.addLast(512);
				mod = mod - 512;
			}
			if (mod < 512 && mod >= 256) {
				count.addLast(256);
				mod = mod - 256;
			}
			if (mod < 256 && mod >= 128) {
				count.addLast(128);
				mod = mod - 128;
			}
			if (mod < 128 && mod >= 64) {
				count.addLast(64);
				mod = mod - 64;
			}
			if (mod < 64) {
				count.addLast(mod);
				break;
			}
		}
		for (int i : count) {
			System.out.println(i);
		}
		return count;
	}
//启动数据库进行一批次的写入
	public void DataBaseSave(String dirpath, boolean isQuery, int count)
			throws Exception, SQLException {
		//连接数据库
		Connection con = Connect.getConnection(
				"jdbc:mysql://localhost:3306/niubaisui", "root", "niubaisui",
				Connect.DATABASE_TYPE_MYSQL);
		//排序
		IndexDataBase database = new IndexDataBase(dirpath);
		database.setCount(count);
		long sortid=id;
		database.Sort(sortid);
		int filenum = database.getFile_Number();
		System.out.println(filenum);
		// 写page
		for (int i = 1; i <= filenum; i++) {
			File file = new File(dirpath, "datafile" + id);
			System.out.println(file.getName());
			FileReader reader = new FileReader(file);
			PreparedStatement stmt = con.prepareStatement(SavePage_sql());
			DateFormat format = DateFormat.getDateTimeInstance();
			String date = format.format(Calendar.getInstance().getTime());
			Page page = new Page(id);			
			id++;
			System.out.println("正在写第" + id + "个文件");
			System.out.println(page.getID());
			System.out.println(date);
			stmt.setLong(1, page.getID());
			stmt.setTimestamp(2, new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			stmt.setCharacterStream(3, reader);
			stmt.execute();
		}

		// 写Field
		LinkedList<Field> list = database.getField();
		Iterator<Field> iterator_field = list.iterator();
		while (iterator_field.hasNext()) {
			String sql = SaveField_sql();
			PreparedStatement stmt = con.prepareStatement(sql);
			Field field = iterator_field.next();
			stmt.setLong(1, field.getID());
			stmt.setInt(2, field.getPriority());
			//序列化
			ByteArrayInputStream bin = new ByteArrayInputStream(field.getText()
					.getBytes());
			InputStreamReader reader = new InputStreamReader(bin, "gb2312");
			stmt.setCharacterStream(3, reader);
			System.out.println("正在写Field");
			System.out.println(sql);
			IDhandler idhandler = new IDhandler(field.getID());
			System.out.println(idhandler.getField_id() >> 20);
			System.out.println(field.getText());
			stmt.execute();
		}
		// 写索引
		LinkedList<Index_Structure> indexs = database.getIndexs();
		Iterator<Index_Structure> iterator_indexs = indexs.iterator();
		// 迭代索引
		
		while (iterator_indexs.hasNext()) {
			Index_Structure index = iterator_indexs.next();
			// 序列化tokes_id
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(index.getTokens_id());
			// 设置词条的unicode代码
			byte[] unicode = index.getTerm().getBytes();
			String sql = this.SaveToken_sql(unicode.length);
			CURD curd = new CURD();
			Index_Structure updated_index = curd.selectIndex(index.getTerm());
			// 执行更新
			if (isQuery && updated_index != null) {
				LinkedList<Long> updated_list = updated_index.getTokens_id();
				Iterator<Long> iterator_list = index.Iterator();
				int frequency = updated_index.getFrequency();
				while (iterator_list.hasNext()) {
					Long id=iterator_list.next();
					updated_list.addLast(id);
					frequency++;
				}
				//进行序列化
				updated_index.setFrequency(frequency);
				ByteArrayOutputStream selected_bout = new ByteArrayOutputStream();
				ObjectOutputStream selected_out = new ObjectOutputStream(
						selected_bout);
				selected_out.writeObject(updated_list);
				
				PreparedStatement stmt = con
						.prepareStatement(Save_update(unicode.length));
				//写占位符的变量
				String term=null;
				//长度超过最大值,截断
				if(index.getTerm().length()>250){
					term=updated_index.getTerm().substring(0,254);
				}
				else{
					term=updated_index.getTerm();
				}
				stmt.setInt(1, updated_index.getFrequency());
				ByteArrayInputStream updated_bin = new ByteArrayInputStream(
						selected_bout.toByteArray());
				
				stmt.setAsciiStream(2, updated_bin);
				stmt.setString(3, term);
				System.out.println("正在更新Token");
				System.out.println(Save_update(term.length()));
				stmt.execute();
			}
			//进行插入操作
			else {
				PreparedStatement stmt = con.prepareStatement(sql);
				//写占位符的变量
				String term=null;
				//长度超过最大值,截断
				if(index.getTerm().length()>250){
					term=index.getTerm().substring(0,254);
				}
				else{
					term=index.getTerm();
				}
				stmt.setInt(1, index.getFrequency());
				stmt.setString(2,term);
				ByteArrayInputStream bin = new ByteArrayInputStream(
						bout.toByteArray());
				
				stmt.setAsciiStream(3, bin);
				System.out.println("正在写Token");
				System.out.println(sql);
				stmt.execute();
			}
		}
		con.close();
	}
//将索引写入数据库中
	public void Save(String dirpath) throws Exception, SQLException {
		boolean isFirst = true;//count中存放的是写的遍数,不是该写那个,count中的第一个特殊,用这个进行标记
		boolean isQuery = true;//第一次写时,直接写,不进行查询
		File f = new File(dirpath);
		int filesum = f.list().length;
		LinkedList<Integer> count = getPart(filesum);//合并排序的策略
		while (!count.isEmpty()) {
			int n = count.pollFirst();
			//写特殊的这个,写完之后设置为false
			if (isFirst) {
				for (; n>0; n--) {
					this.DataBaseSave(dirpath, isQuery,10);
					isQuery = true;
				}
				isFirst = false;
			} else {
				if(n==512){
					this.DataBaseSave(dirpath, isQuery,9);
				}
				else if(n==256){
					this.DataBaseSave(dirpath, isQuery,8);
				}
				else if(n==128){
					this.DataBaseSave(dirpath, isQuery,7);
				}
				else if(n==64){
					this.DataBaseSave(dirpath, isQuery,6);
				}
				else{
					for(;n>0;n--){
						this.DataBaseSave(dirpath, isQuery,0);
					}
				}
			}
		}
	}

	// 保存index
	public static void main(String[] args) throws SQLException, Exception {
		DataBaseOp database = new DataBaseOp();
		try {
			database.Save("e:\\datafile");
		} catch (MySQLSyntaxErrorException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
		}
	}
}

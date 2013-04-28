package com.search.index;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.search.DAO.DAOThread;
import com.search.data.Field;

/*
 * 
 */
public class BuildIndexDataBase {
	
	private static LinkedList<String> documents=new LinkedList<String>();
	private static LinkedList<Field> fields=new LinkedList<Field>();
	private static LinkedList<Token_Structure> indexs=new LinkedList<Token_Structure>();
	

	public synchronized static String SaveDocument_sql() throws Exception, SQLException {
		String sql = "insert into document(ID,CREATE_DATE,CONTENT)values (?,?,?)";
		return sql;
	}

	public synchronized static String SaveField_sql() throws Exception, SQLException {
		String sql = "insert into field(ID,PRIORITY,CONTENT)values" + "(?,?,?)";
		return sql;
	}

	public synchronized static String SaveToken_sql(int size) throws Exception, SQLException {
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

	public synchronized static String Save_update(int size) {
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
	
	public synchronized static Field getField(){
		if(!fields.isEmpty()){
			return fields.pollFirst();
		}
		else{
			return null;
		}
		
	}
	public synchronized static Token_Structure getIndex_Structure(){
		if(!indexs.isEmpty()){
			return indexs.pollFirst();
		}
		else{
			return null;
		}
	}
	
	public synchronized static String getString(){
		return null;
	}
	
	private synchronized boolean fields_isEmpty(){
		return fields.isEmpty();
	}
	private synchronized boolean indexs_isEmpty(){
		return indexs.isEmpty();
	}
	public void write() throws Exception {

	}

	
	
	//对tokens_id进行排序
	public synchronized static LinkedList<Long> Sort(LinkedList<Long> list){
		LinkedList<Long> linkedlist=new LinkedList<Long>();
		long[] array=new long[list.size()];
		Iterator<Long> iterator_list=list.iterator();
		for(int i=0;i<list.size();i++){
			array[i]=iterator_list.next();
		}
		Arrays.sort(array);
		for(long l:array){
			linkedlist.addLast(l);
		}
		return linkedlist;
	}
	// 合并document的策略,每1024 document进行合并排序一次,剩下的分多种情况处理
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

	// 启动数据库进行一批次的写入
	public void DataBaseSave(String dirpath, int count,int start)
			throws Exception, SQLException {
//		// 排序
//		SimpleMergeIndex database = new SimpleMergeIndex(dirpath);
////		database.setCount(count);
//		long sortid = start;
////		database.Sort(sortid);
////		int filenum = database.getDocument_Number();
//		System.out.println(filenum);
//		int thread_num=25;
//		if(filenum<64){
//			thread_num=1;
//		}
//		// 写page
//		for (int i = 1; i <= filenum; i++) {
//			files.addLast("datafile"+start);
//			start++;
//		}
//		for(int i=0;i<thread_num;i++){
//			DocumentThread thread=new DocumentThread(dirpath,DataBaseOp.getDocument());
//			thread.start();
//		}
//
//		// 写Field
//		fields = database.getField();
//		for(int i=0;i<thread_num;i++){
//			FieldThread thread=new FieldThread(DataBaseOp.getField());
//			thread.start();
//		}
//		
//		// 写索引
////		 indexs= database.getIndexs();
//		 for(int i=0;i<thread_num;i++){
//			 IndexThread thread=new IndexThread(DataBaseOp.getIndex_Structure());
//			 thread.start();
//		 }
	}

	// 单线程将索引写入数据库中
	public void Save(String dirpath) throws Exception, SQLException {
//		boolean isFirst = true;// count中存放的是写的遍数,不是该写那个,count中的第一个特殊,用这个进行标记
//		File f = new File(dirpath);
//		int num=0;
//		int id=1;
//		int filesum = f.list().length;
//		LinkedList<Integer> count = getPart(filesum);// 合并排序的策略
//		while (!count.isEmpty()) {
//			int n = count.pollFirst();
//			// 写特殊的这个,写完之后设置为false
//			if (isFirst) {
//				for (; n > 0; n--) {
//					this.DataBaseSave(dirpath, 10,num*1024+1);
//					while(true){
//						if(this.files_isEmpty()&&this.fields_isEmpty()
//							&&this.indexs_isEmpty()){
//							break;
//						}
//					}
//					num++;
//				}
//				id=num*1024+1;
//				isFirst = false;
//			} else {
//				if (n == 512) {
//					this.DataBaseSave(dirpath, 9,id);
//					while(true){
//						if(this.files_isEmpty()&&this.fields_isEmpty()
//							&&this.indexs_isEmpty()){
//							break;
//						}	
//					}
//					id=id+512;
//				} else if (n == 256) {
//					this.DataBaseSave(dirpath,  8,id);
//					while(true){
//						if(this.files_isEmpty()&&this.fields_isEmpty()
//							&&this.indexs_isEmpty()){
//							break;
//						}				
//					}
//					id=id+256;
//				} else if (n == 128) {
//					this.DataBaseSave(dirpath,  7,id);
//					while(true){
//						if(this.files_isEmpty()&&this.fields_isEmpty()
//							&&this.indexs_isEmpty()){
//							break;
//						}				
//					}
//					id=id+128;
//				} else if (n == 64) {
//					this.DataBaseSave(dirpath,  6,id);
//					while(true){
//						if(this.files_isEmpty()&&this.fields_isEmpty()
//							&&this.indexs_isEmpty()){
//							break;
//						}				
//					}
//					id=id+64;
//				} else {
//					for (; n > 0; n--) {
//						this.DataBaseSave(dirpath, 0,id);
//						while(true){
//							if(this.files_isEmpty()&&this.fields_isEmpty()
//								&&this.indexs_isEmpty()){
//								break;
//							}				
//						}
//						id++;
//					}
//				}
//			}
//		}
	}

	//多线程将数据写入数据库中
	public void _Save(String dirpath) throws Exception, Exception{
		boolean isFirst = true;// count中存放的是写的遍数,不是该写那个,count中的第一个特殊,用这个进行标记
		File f = new File(dirpath);
		int num=0;
		int id=1;
		int filesum = f.list().length;
		LinkedList<Integer> count = getPart(filesum);// 合并排序的策略
		while (!count.isEmpty()) {
			int n = count.pollFirst();
			
			// 写特殊的这个,写完之后设置为false
			if (isFirst) {
			    	
				for(int i=0;i<n;i++){
					new DAOThread(dirpath,10,num*1024+1).start();
					System.out.println("thread running");
					num++;
				}	
				id=num*1024+1;
				isFirst = false;
			} else {
				if (n == 512) {
					new DAOThread(dirpath,9,id).start();
					id=id+512;
				} else if (n == 256) {
					new DAOThread(dirpath,8,id).start();
					id=id+256;
				} else if (n == 128) {
					new DAOThread(dirpath,7,id).start();
					id=id+128;
				} else if (n == 64) {
					new DAOThread(dirpath,6,id).start();
					id=id+64;
				} else {
					for (; n > 0; n--) {
						this.DataBaseSave(dirpath,0,id);
						id++;
					}
				}
			}
		}
	}
	// 保存index
	public static void main(String[] args) throws SQLException, Exception {
		BuildIndexDataBase database = new BuildIndexDataBase();
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

/**
 * 
 */
package com.http.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.search.dao.Connect;

/**
 * @author niubaisui
 *
 */
public class DocumentSyncThread extends Thread{

	private Logger logger=LogManager.getLogger("DocumentSyncThread");
	private String sql="update document set rank=? where id=?";
	private String url_sql="select url from document where id=?";
	private String priority_sql="select priority from visitedurl where oriUrl=?";
	private String min_document="select min(id) from document";
	private String max_document="select max(id) from document";
	private long min_id=0;
	private long max_id=0;
	private Connection con=null;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			con=Connect.getConnection();
		}catch(Exception e){
			logger.fatal("数据库连接失败");
			e.printStackTrace();
		}
		
		try {
			logger.info("同步线程先睡眠100s");
			Thread.sleep(100000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){			
			
			try{
				//得到最小的id
				Statement min_stmt=con.createStatement();
				ResultSet min_resultset=min_stmt.executeQuery(min_document);
				while(min_resultset.next()){
					min_id=min_resultset.getLong("min(id)");
					min_id=min_id>>40;
					logger.info("min_id:"+min_id);
				}
				
				//得到最大的id
				Statement max_stmt=con.createStatement();
				ResultSet max_resultset=max_stmt.executeQuery(max_document);
				while(max_resultset.next()){
					max_id=max_resultset.getLong("max(id)");
					max_id=max_id>>40;
					logger.info("max_id:"+max_id);
				}
				
				//更新rank
				PreparedStatement url_stmt=con.prepareStatement(url_sql);
				PreparedStatement priority_stmt=con.prepareStatement(priority_sql);
				PreparedStatement stmt=con.prepareStatement(sql);
				
				
				for(long i=min_id;i<=max_id;i++){
					
					//从document中查询到id对应的url
					
					long id=i<<40;
					logger.info("正在同步id="+id+"的文档");
					
					
					url_stmt.setLong(1, id);
					ResultSet url_resultset=url_stmt.executeQuery();
					String url=null;
					while(url_resultset.next()){
						url=url_resultset.getString("url");
					}					
					//从visitdurl中查询rank				
					priority_stmt.setString(1, url);
					ResultSet priority_resultset=priority_stmt.executeQuery();
					int rank=-1;
					while(priority_resultset.next()){
						rank=priority_resultset.getInt("priority");
					}
					
					if(rank==-1){
						continue;
					}
					
					//更行documnet rank					
					stmt.setInt(1, rank);
					stmt.setLong(2, id);
					stmt.executeUpdate();
					logger.info("id="+id+"的Document同步成功.");
				}
				
				//睡眠500s
				logger.info("线程将睡眠100s");
				Thread.sleep(100000);
				
			}catch(Exception e){
				logger.fatal("线程异常");
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		DocumentSyncThread thread=new DocumentSyncThread();
		thread.start();
	}

}

/**
 * 
 */
package com.search.index;



import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.search.data.Document;
import com.search.data.Field;
import com.search.data.IDhandler;

/**
 * @author niubaisui
 *
 */
public class IndexWriter_to_Database implements IndexWriter{

	private LinkedList<Document> documents=new LinkedList<Document>();
	private LinkedList<Field> fields=new LinkedList<Field>();
	private LinkedList<Token_Structure> tokens=new LinkedList<Token_Structure>();
	private Logger logger=LogManager.getLogger("IndexWriter_to_Database");
	private boolean isSaveDocument=true;

	public void setIsSaveDocument(boolean saveDocument){
		this.isSaveDocument=saveDocument;
	}
	
	public boolean getIsSaveDocument(){
		return this.isSaveDocument;
	}
	public IndexWriter_to_Database(LinkedList<Document> documents){
		this.documents=documents;
	}
	//主要的方法
	@Override
	public void indexWriter() throws Exception {
		int size = documents.size();
		logger.info("总数:" + size);

		SimpleMergeIndex mergeindex = new SimpleMergeIndex(documents);
		mergeindex.mergeIndex();

		int num = 2;
		if (isSaveDocument) {
			num = 3;
		}
		CountDownLatch latch = new CountDownLatch(num);
		// 启动一个document_thread线程
		if (isSaveDocument) {
			DocumentThread document_thread = new DocumentThread(documents,
					latch);
			document_thread.start();
		}

//		IDhandler idhandler =new IDhandler(1);
//		for(Field f:mergeindex.getField()){
//			idhandler.setID(f.getID());
//			System.out.println("document id:"+idhandler.getCurrent_Document_id()+" field id:"+
//			idhandler.getCurrent_Field_id()+" text:"+f.getText());
//		}
//		
//		for(Token_Structure t:mergeindex.getToken_Structure()){
//			System.out.println("term:"+t.getTerm());
//			System.out.println("frequency:"+t.getFrequency());
//			for(long id:t.getTokens_id()){
//				idhandler.setID(id);
//				System.out.println("document id:"+idhandler.getCurrent_Document_id()+" field id:"
//						+idhandler.getCurrent_Field_id()+" offset:"+idhandler.getCurrent_Token_id());
//			}
//		}
		// 启动一个field_thread线程
		FieldThread field_thread = new FieldThread(mergeindex.getField(), latch);
		field_thread.start();
		
		// 启动一个index线程
		IndexThread index_thread = new IndexThread(
				mergeindex.getToken_Structure(), latch);
		
		index_thread.start();
		
		
		latch.await();
		logger.info("写完了一次");
	}
	
		
	public LinkedList<Document> getDocuments() {
		return documents;
	}
	
	public LinkedList<Field> getFields() {
		return fields;
	}
	
	public LinkedList<Token_Structure> getTokens() {
		return tokens;
	}
	
}

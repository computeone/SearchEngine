package com.http.traversal;

import java.sql.Timestamp;
import java.util.Date;
/*
 * 
 */
public class CrawlUrl {
	/*
	 * 
	 */
	private String oriUrl;//ԭʼURL��ֵ����������������
	private String url;//URLֵ������������IP��Ϊ�˷�ֹ�ظ������ĳ���
	private int urlNo;//URL NUM
	private int statusCode;//��ȡURL���صĽ����
	private int hitNum;//��URL�������������õĴ���
	private String charSet;//��URL��Ӧ���µı���
	private String abstractText;//����ժҪ
	private String author;//����
	private int priority;//���µ�Ȩ��
	private String description;//���µ�����
	private int fileSize;//���µĴ�С
	private Timestamp lastUpdateTime;//����޸�ʱ��
	private Date timeToLive;//����ʱ��
	private String title;//���±���
	private String type;//��������
//	private String[] urlReferences;//���õ�����
	private int layer;//��ȡ�ò�Σ������ӿ�ʼ������Ϊ��0�㣬��һ��
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getUrlNo() {
		return urlNo;
	}
	public void setUrlNo(int urlNo) {
		this.urlNo = urlNo;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public int getHitNum() {
		return hitNum;
	}
	public void setHitNum(int hitNum) {
		this.hitNum = hitNum;
	}
	public String getCharSet() {
		return charSet;
	}
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
	public String getAbstractText() {
		return abstractText;
	}
	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Date getTimeToLive() {
		return timeToLive;
	}
	public void setTimeToLive(Date timeToLive) {
		this.timeToLive = timeToLive;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
//	public String[] getUrlReferences() {
//		return urlReferences;
//	}
//	public void setUrlReferences(String[] urlReferences) {
//		this.urlReferences = urlReferences;
//	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public String getOriUrl() {
		return oriUrl;
	}
	public void setOriUrl(String oriUrl) {
		this.oriUrl = oriUrl;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
}

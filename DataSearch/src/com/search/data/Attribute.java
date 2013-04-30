/**
 * 
 */
package com.search.data;

import java.io.Serializable;

/**
 * @author niubaisui
 *
 */
public class Attribute implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	private String value;
	private Integer index;
	
	public Attribute(String key,String value){
		this.key=key;
		this.value=value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
	

}

package com.http.Search;

import java.util.BitSet;

/*
 * ²¼Â¡¹ıÂËÆ÷
 */
public class UrlBloomFilter {
	private static int DEFAULT_SIZE=2<<24;
	private static final int[] seeds=new int[]{7,11,13,31,37,61};
	private BitSet bits=new BitSet(DEFAULT_SIZE);
	
	private SimpleHash[] func=new SimpleHash[seeds.length];
	public  UrlBloomFilter(){
		for(int i=0;i<seeds.length;i++){
			func[i]=new SimpleHash(DEFAULT_SIZE,seeds[i]);
		}
	}
	public void add(CrawlUrl value){
		if(value!=null){
			add(value.getOriUrl());
		}
	}
	
	public void add(String value){
		for(SimpleHash f:func){
			bits.set(f.hash(value),true);
		}
	}
	
	public boolean contains(CrawlUrl value){
		return contains(value.getOriUrl());
	}
	public boolean contains(String value){
		if(value==null){
			return false;
		}
		boolean ret=true;
		for(SimpleHash f:func){
			ret=ret&&bits.get(f.hash(value));
		}
		return ret;
	}
	public static class SimpleHash{
		private int cap;
		private int seed;
		public SimpleHash(int cap,int seed){
			this.cap=cap;
			this.seed=seed;
		}
		
		public int hash(String value){
			int result=0;
			int len=value.length();
			for(int i=0;i<len;i++){
				result=seed*result+value.charAt(i);
			}
			return (cap-1)&result;
		}
	}
}

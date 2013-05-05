/**
 * 
 */
package com.search.Search;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * @author niubaisui
 *
 */
public class ShellSort {

	
	public static <T> void Sort(LinkedList<T> link,Comparator<? super T> t){
		int right=link.size()-1;
		int left=0;
		int h,j;
		for(h=1;h<(right-1)/9;h=3*h+1);
		for(;h>0;h=h/3){
			for(int i=left;i<=right;i=i+h){
				T  v=link.get(i);
				for(j=i;j>0;j=j-h){
					if(t.compare(v,link.get(j-h))==-1){
						link.set(j, link.get(j-h));
					}
					else break;
				}
				link.set(j,v);
			}
		}		
	}
}

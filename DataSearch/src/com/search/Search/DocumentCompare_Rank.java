package com.search.Search;

import java.util.Comparator;

import com.search.data.Document;

/*
 * 匹配度优先，得分次之的简单排序
 */
public class DocumentCompare_Rank implements Comparator<Document>{

	

	@Override
	public int compare(Document document1, Document document2) {
		
		
		String matcher1=document1.getStore_attriubte("matcher");
		String matcher2=document2.getStore_attriubte("matcher");
		
		if(Integer.parseInt(matcher1)==Integer.parseInt(matcher2)){
			if(document1.getRanks()>document2.getRanks()){
				return 1;
			}
			else if(document1.getRanks()==document2.getRanks()){
				return 0;
			}
			else{
				return -1;
			}
		}
		else if(Integer.parseInt(matcher1)>Integer.parseInt(matcher2)){
			return 1;
		}
		else{
			return -1;
		}
		
	}

}

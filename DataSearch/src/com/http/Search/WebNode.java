package com.http.Search;

import java.util.HashMap;

/* 1.HashMap 中键值映射的表示为：结点key是数字，vaule是网址；
 * 2.邻接边key用"adj1"、"adj2"等表示，vaule是边的键值；
 * 3.关键字key用"keyword1"、"keyword2"等表示，vaule关键字是字符串形式的值；
 * 4.其它保留
 */
public class WebNode {
	public HashMap<String, String> Node;

	public WebNode(HashMap<String, String> node) {
		this.Node = node;
	}

}

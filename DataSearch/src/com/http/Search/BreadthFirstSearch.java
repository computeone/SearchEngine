package com.http.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BreadthFirstSearch {
	private int[] pred;
	private int[] dist;
	private int[] color;
	private WebGraph graph;

	public BreadthFirstSearch(WebGraph graph) {
		this.graph = graph;
	}

	/*
	 * 得到节点头在Arraylist即数组中的位置
	 */
	private int getNumber(WebNode node) {
		int number = 0;
		Iterator<String> iterator = node.Node.keySet().iterator();
		Pattern pattern = Pattern.compile("\\d.*");// 正则表达式解析出节点中的位置
		while (iterator.hasNext()) {
			Matcher matcher = pattern.matcher(iterator.next());
			boolean result = matcher.matches();
			if (result) {
				/*
				 * 如果存在的话对解析出来的字符串变换成int
				 */
				String key = matcher.group();
				char[] data = new char[key.length()];
				data = key.toCharArray();
				for (int i = data.length; i < 0; i--) {
					number = data[i] * (int) Math.pow(10, (double) i - 1);
				}
				return number;
			} else {
				return -1;
			}
		}
		return -1;

	}

	/*
	 * 初始化遍历开始时的数据
	 */
	private void init() {
		int size = graph.Graph.size();
		pred = new int[size];
		dist = new int[size];
		color = new int[size];
		/*
		 * 颜色0表示是白色，即没有访问过的; 颜色1表示的灰色，即访问过了但是没有全部访问; 颜色2表示是黑色的，即完全访问过的。
		 */
		for (int i = 0; i < size; i++) {
			pred[i] = -1;
			dist[i] = Integer.MAX_VALUE;
			color[i] = 0;
		}
	}

	/*
	 * 广度优先遍历
	 */
	public void Search() {
		this.init();
		ArrayList<HashMap<WebNode, LinkedList<WebNode>>> arraylist;
		HashMap<WebNode, LinkedList<WebNode>> element;
		WebNode node;
		LinkedList<WebNode> nodelist;
		LinkedList<WebNode> queue = new LinkedList<WebNode>();
		arraylist = graph.Graph;
		element = arraylist.get(0);// 返回邻接表中元素的头元素
		node = element.keySet().iterator().next();// 返回图当中的数组中的节点
		color[0] = 1;
		dist[0] = 1;
		queue.add(node);
		while (!queue.isEmpty()) {
			node = queue.getFirst();// 得到队列中头节点
			int local = this.getNumber(node);// 得到节点在数组中的位置
			element = arraylist.get(local);// 得到节点头
			nodelist = element.get(node);// 得到节点的邻接的链表

			Iterator<WebNode> list_iterator = nodelist.iterator();// 得到链表中的linkedlist迭代器
			while (list_iterator.hasNext()) {
				WebNode childnode = list_iterator.next();
				int num = this.getNumber(childnode);// 得到node在数组中的位置
				if (color[num] == 0) {
					color[num] = 1;// 染成灰色
					pred[num] = local;// 保存其前驱节点
					dist[num] = dist[local] + 1;// 距离增加
					queue.add(childnode);// 加入节点到队列
				}
			}
			queue.removeFirst();// 从队列中移除
			color[local] = 2;// 节点染成黑色
		}
	}

	public static void main(String[] args) {

	}
}

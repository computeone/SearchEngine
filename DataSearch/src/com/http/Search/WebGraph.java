package com.http.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebGraph {

	private WebNode headnode;
	public ArrayList<HashMap<WebNode, LinkedList<WebNode>>> Graph;

	public WebNode getHeadnode() {
		return headnode;
	}

	private int isMatcher(String nodekey) {
		boolean result;
		int node_number;
		Pattern pattern = Pattern.compile("adj(\\d)");
		Matcher matcher = pattern.matcher(nodekey);
		matcher.find();
		result = matcher.matches();
		if (result == true) {
			node_number = matcher.end(1);
			return node_number;
		} else {
			return -1;
		}
	}

	private int[] getNode_edge(WebNode node) {
		int[] edge = new int[node.Node.size()];
		int i = 0;
		int edge_value;
		Iterator<String> keyiterator = node.Node.keySet().iterator();
		while (keyiterator.hasNext()) {
			if ((edge_value = this.isMatcher(keyiterator.next())) != -1) {
				edge[i] = edge_value;
			}
		}
		return edge;
	}

	public void deleteNode() {

	}

	public void addNode(WebNode node) {
		LinkedList<WebNode> nodelist = new LinkedList<WebNode>();
		HashMap<WebNode, LinkedList<WebNode>> nodemap = new HashMap<WebNode, LinkedList<WebNode>>();
		nodemap.put(node, nodelist);// 把webnode和linkedlist加入结点数据结构中
		Graph.add(nodemap);// 初始化时把节点加入邻接表中
		int[] edge = this.getNode_edge(node);// 得到邻接的边表
		/*
		 * 将边表中的节点加入本节点的链表中
		 */
		for (int i = 0; i < edge.length; i++) {
			WebNode linkednode = Graph.get(edge[i]).keySet().iterator().next();// 被将要加入链表中的节点
			/*
			 * 剔除节点中不需要的数据及关键字
			 */
			Iterator<String> keyiterator = linkednode.Node.keySet().iterator();// 得到键值迭代器
			Pattern pattern = Pattern.compile("(key|keyword)\\d");
			while (keyiterator.hasNext()) {
				String key = keyiterator.next();
				Matcher matcher = pattern.matcher(key);
				matcher.find();
				if (!matcher.matches()) {
					linkednode.Node.remove(key);
				}
			}
			nodelist.add(linkednode);// 将节点加入本节点的链表中
		}
	}

	public WebGraph(WebNode headnode) {
		this.headnode = headnode;
		this.addNode(headnode);
	}

}

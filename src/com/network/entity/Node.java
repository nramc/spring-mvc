package com.network.entity;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private String id;
	private String Name;
	private boolean visitedFlg;
	int distFromSrc;
	private boolean srcFlg;
	Node prev;
	Edge prevEdge;
	List<Edge> listEdge = new ArrayList<Edge>();
	
	public Node(String id) {
		this.id = id;
		this.Name = this.id;
		this.visitedFlg = false;
		this.distFromSrc = Integer.MAX_VALUE;
		this.srcFlg = false;
		this.prev = null;
	}
	
	public Node() {}

	public Boolean isVisited() {
		return visitedFlg;
	}
	public void setVisitedFlg(boolean flg) {
		this.visitedFlg = flg;
	}
	public Boolean isSrcNode() {
		return srcFlg;
	}
	public void setSrcFlg(boolean flg) {
		this.srcFlg = flg;
	}
	public int getDistFromSrc() {
		return distFromSrc;
	}
	public void setDistFromSrc(int dist) {
		this.distFromSrc = dist;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Node getPrev() {
		return prev;
	}
	public void setPrev(Node prev) {
		this.prev = prev;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Edge getPrevEdge() {
		return prevEdge;
	}
	public void setPrevEdge(Edge prevEdge) {
		this.prevEdge = prevEdge;
	}

	@Override
	public String toString() {
		return "\tID : "+this.id + "\t Source Flg:"+this.isSrcNode()+"\tDistance:"+this.distFromSrc+"\tPrev Node:"+(prev == null ? "null" : prev.getId() );
	}
	
}

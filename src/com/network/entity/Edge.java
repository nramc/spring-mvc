package com.network.entity;

public class Edge {
	
	String from;
	String to;
	int dist;
	
	public Edge(String from, String to, int dist) {
		this.from = from;
		this.to = to;
		this.dist = dist;
	}

	public Edge() {}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getDist() {
		return dist;
	}
	public void setDist(int dist) {
		this.dist = dist;
	}
	
	
	@Override
	public String toString() {
		return "From:"+from+"\tTo:"+to+"\tDistance:"+dist;
	}

}

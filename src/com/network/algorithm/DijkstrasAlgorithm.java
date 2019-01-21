package com.network.algorithm;

import java.util.ArrayList;
import java.util.List;
import com.network.entity.Edge;
import com.network.entity.Node;

public class DijkstrasAlgorithm {

	private static Node minDistNode(List<Node> listNode) {
		int min = Integer.MAX_VALUE;
		Node minDistNode = null;
		for (Node node : listNode) {
			if (!node.isVisited() && node.getDistFromSrc() <= min) {
				min = node.getDistFromSrc();
				minDistNode = node;
			}
		}
		return minDistNode;
	}

	private static Edge getEdgeBtnNodes(String from, String to, List<Edge> listEdge) {
		for (Edge edge : listEdge) {
			if (
					(edge.getFrom().equalsIgnoreCase(from) && edge.getTo().equalsIgnoreCase(to))
					|| ( edge.getFrom().equalsIgnoreCase(to) && edge.getTo().equalsIgnoreCase(from) )
				) {
				return (edge.getDist() != 0 ) ? edge : null;
			}
		}
		return null;
	}

	private static void initialization(List<Node> listNode) {
		for (Node node : listNode) {
			node.setDistFromSrc(Integer.MAX_VALUE);
			node.setVisitedFlg(false);
			if (node.isSrcNode()) {
				node.setDistFromSrc(0);
			}
		}
	}

	private static void displayResult(List<Node> listNode) {
		System.out.println( "\tSource\tPrev Node\tDistance" );
		System.out.println( "----------------------------------------------------" );
		for (Node node : listNode) {
			System.out.println( node );
		}
	}

	public static void perform(List<Node> listNode, List<Edge> listEdge) {

		System.out.println("Initially set all nodes unvisited and have inifinite distance.. ");
		initialization(listNode);
		
		for (int idx = 0; idx < listNode.size(); idx++) {

			Node minNode = minDistNode(listNode);

			minNode.setVisitedFlg(true);

			for (Node adjNode : listNode) {
				if (!adjNode.isVisited()) {
					Edge edge = getEdgeBtnNodes(minNode.getId(), adjNode.getId(), listEdge);
					if( edge != null
							&& minNode.getDistFromSrc() != Integer.MAX_VALUE
							&& minNode.getDistFromSrc() + edge.getDist() < adjNode.getDistFromSrc()) {
						adjNode.setDistFromSrc(minNode.getDistFromSrc() + edge.getDist());
						adjNode.setPrev(minNode);
						adjNode.setPrevEdge(edge);
					}
				}
			}

		}
		
		displayResult(listNode);
	}


	
	
	public static void main(String[] args) {
		
		// Some manual data generation for testing
		int graph[][] = new int[][]{
			{0, 1, 1, 0 },
      {1, 0, 3, 4 },
      {1, 3, 0, 1 },
      {0, 4, 1, 0 }
     };
     
     graph = new int[][]{
			 {0, 4, 0, 0, 0, 0, 0, 8, 0},
       {4, 0, 8, 0, 0, 0, 0, 11, 0},
       {0, 8, 0, 7, 0, 4, 0, 0, 2},
       {0, 0, 7, 0, 9, 14, 0, 0, 0},
       {0, 0, 0, 9, 0, 10, 0, 0, 0},
       {0, 0, 4, 14, 10, 0, 2, 0, 0},
       {0, 0, 0, 0, 0, 2, 0, 1, 6},
       {8, 11, 0, 0, 0, 0, 1, 0, 7},
       {0, 0, 2, 0, 0, 0, 6, 7, 0}
      };
		
		List<Node> listNode = new ArrayList<Node>();
		List<Edge> listEdge = new ArrayList<Edge>();
		
		for (int idxNode = 0; idxNode < graph.length; idxNode++ ) {
			Node node = new Node("N"+(idxNode+1));
			if( idxNode == 0 ) {
				node.setSrcFlg(true);
			}
			listNode.add(node);
			
			for(int idxEdge = 0; idxEdge < graph.length; idxEdge++) {
				Edge edge = new Edge("N"+(idxNode+1), "N"+(idxEdge+1), graph[idxNode][idxEdge]);
				listEdge.add(edge);
				System.out.println( edge );
			}
		}
		
		DijkstrasAlgorithm.perform(listNode, listEdge);
		
		
		
	}
}

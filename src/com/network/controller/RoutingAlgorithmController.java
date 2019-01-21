package com.network.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.extend.EnginePrivate;
import org.directwebremoting.extend.MarshallException;
import org.directwebremoting.extend.ScriptConduit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.network.algorithm.BellmanFordAlgorithm;
import com.network.algorithm.DijkstrasAlgorithm;
import com.network.entity.Edge;
import com.network.entity.Node;

@Controller
public class RoutingAlgorithmController {
	
	@RequestMapping("index.htm")
	public String handleRequest(ModelMap model) {
		System.out.println("HomeController > handleRequest()");
		
		try {
			EnginePrivate.remoteHandleCallback(new SimpleClass(5), "0", "1", new String("DWR Data"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "index";
	}
	
	public void remoteHandleCallback() {
		System.out.println("internal remoteHandleCallback");
	}
	
	@RequestMapping("home.htm")
	public String redirectToHome(ModelMap model) {
		System.out.println("HomeController > redirectToHome()");
		model.addAttribute("userName", "Dheepshi...!");
		
		return "HomePage";
	}
	
	@RequestMapping("calcEffectivePath.json")
	public @ResponseBody JsonElement calculateEffectivePath(@RequestBody JsonObject jsonObj) {
		System.out.println("HomeController > calculateEffectivePath()");
		System.out.println( jsonObj.toString() );

		String sourceNode = jsonObj.get("sourceNode").getAsString();
		System.out.println( "sourceNode : " + sourceNode);
		
		String algorithm = jsonObj.get("algorithm").getAsString();
		System.out.println( "algorithm : " + algorithm);
		
		JsonArray jsonNodes = jsonObj.getAsJsonArray("nodes");
		List<Node> sourceNodes = getNodes(jsonNodes, sourceNode);
		
		JsonArray jsonEdges = jsonObj.getAsJsonArray("edges");
		List<Edge> sourceEdges = getEdges(jsonEdges);
		
		if( "Dijkstra".equalsIgnoreCase(algorithm) ) {
			DijkstrasAlgorithm.perform(sourceNodes, sourceEdges);
		}
		else if ( "BellmanFord".equalsIgnoreCase(algorithm) ) {
			BellmanFordAlgorithm.perform(sourceNodes, sourceEdges);
		}
		
		
		
		
		JsonObject json = getJson(sourceNodes, sourceNode);
		json.addProperty("algorithm", algorithm);
		return json;
	}
	
	private List<Node> getNodes(JsonArray jsonArray, String sourceNode) {
		List<Node> listNodes = new ArrayList<Node>();
		for (JsonElement jsonElement : jsonArray) {
			Node node = new Node();
			node.setId( jsonElement.getAsJsonObject().get("id").getAsString() );
			node.setName( jsonElement.getAsJsonObject().get("label").getAsString());
			if( !StringUtils.isEmpty(sourceNode) && node.getId().equalsIgnoreCase(sourceNode) ) {
				node.setSrcFlg(true);
			}
			listNodes.add(node);
		}
		
		return listNodes;
	}
	private List<Edge> getEdges(JsonArray jsonArray) {
		List<Edge> listEdges = new ArrayList<Edge>();
		for (JsonElement jsonElement : jsonArray) {
			Edge edge = new Edge();
			edge.setFrom( jsonElement.getAsJsonObject().get("from").getAsString() );
			edge.setTo( jsonElement.getAsJsonObject().get("to").getAsString() );
			edge.setDist( jsonElement.getAsJsonObject().get("label").getAsInt() );
			
			listEdges.add(edge);
		}
		
		return listEdges;
	}
	
	private JsonObject getJson(List<Node> listNodes, String sourceNode) {
		List<Edge> listEdges = new ArrayList<Edge>();
		for (Node node : listNodes) {
			if( node.getPrevEdge() != null) listEdges.add(node.getPrevEdge());
		}
		
		JsonObject json = new JsonObject();
		
		JsonArray jsonNodes = new JsonArray();
		for (Node node : listNodes) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", node.getId());
			jsonObject.addProperty("label", node.getName());
			jsonObject.addProperty("distFromSrc", node.getDistFromSrc());
			jsonObject.addProperty("isSrcFlg", node.isSrcNode());
			
			
			LinkedList<String> pathFromSrc = new LinkedList<String>();
			Node prevNode = node;
			if( prevNode.getPrev() != null ) {
				while( prevNode.getPrev() != null ) {
					pathFromSrc.addFirst(prevNode.getName());
					prevNode = prevNode.getPrev();
				}
			}
			pathFromSrc.addFirst(sourceNode);
			jsonObject.addProperty("pathFromSrc", StringUtils.collectionToDelimitedString(pathFromSrc, " -> "));
			
			jsonNodes.add(jsonObject);
		}
		json.add("nodes", jsonNodes);
		
		JsonArray jsonEdges = new JsonArray();
		for (Edge edge : listEdges) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("from", edge.getFrom());
			jsonObject.addProperty("to", edge.getTo());
			jsonObject.addProperty("label", Integer.toString(edge.getDist()));
			
			jsonEdges.add(jsonObject);
		}
		json.add("edges", jsonEdges);
		json.addProperty("srcNode", sourceNode);
		
		return json;
	}
	

}

class SimpleClass extends ScriptConduit {

	public SimpleClass(int rank) {
		super(rank);
	}

	@Override
	public boolean addScript(ScriptBuffer arg0) throws IOException, MarshallException {
		System.out.println("Script added ");
		return true;
	}
	
}
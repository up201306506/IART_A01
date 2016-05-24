package gui;

import java.util.ArrayList;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import logic.Node;

public class GraphApp {
	
	private Graph graph;
	private String pathName;
	
	private ArrayList<Node> nodeList;
	
	public GraphApp(ArrayList<Node> nodeList){
		// sets configs for graphstream lib
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		graph = new SingleGraph("");
		pathName = GraphApp.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		graph.addAttribute("ui.stylesheet", "url('file:///" + pathName + "../styles/style.css')");
		
		// set data
		this.nodeList = nodeList;
	}
	
	public void createGraph(){
		// add nodes
		for(Node node : nodeList){
			graph.addNode(node.getName()).addAttribute("ui.label", node.getName());
			graph.getNode(node.getName()).addAttribute("layout.frozen");
			graph.getNode(node.getName()).addAttribute("xy", node.getXCord(), node.getYCord());
		}
		
		// add edges
		// TODO
	}
	
	public void display(){
		graph.display();
	}
}
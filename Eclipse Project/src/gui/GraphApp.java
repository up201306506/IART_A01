package gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;

import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import logic.Edge;
import logic.Node;
import logic.NodeStop;

public class GraphApp {
	
	private Graph graph;
	private String pathName;
	private ViewPanel view;
	
	private ArrayList<Node> nodeList;
	private LinkedList<Node> poi;
	private Node sourceNode,destinationNode;
	private GraphController clicks;
	
	public GraphApp(ArrayList<Node> nodeList){
		// sets configs for graphstream lib
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		graph = new SingleGraph("map");
		poi = new LinkedList<>();
		
		Viewer viewer = new Viewer(graph,Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		view = viewer.addDefaultView(false);
		view.setPreferredSize(new Dimension(700,500));
		clicks = new GraphController(graph, viewer, this);
		
		
		pathName = GraphApp.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		graph.addAttribute("ui.stylesheet", "url('file:///" + pathName + "../styles/style.css')");
		
		// set data
		this.nodeList = nodeList;
		clicks.start();
	}
	
	public void createGraph(){
		// add nodes
		for(Node node : nodeList){
			String nodeLabel = "";
			if(node.canRefuel){
				nodeLabel += "(G)";
			}
			if(node.canRest){
				nodeLabel += "(H)";
			}
			graph.addNode(node.getName()).addAttribute("ui.label", nodeLabel + node.getName());
			graph.getNode(node.getName()).addAttribute("logic-node", node);
			graph.getNode(node.getName()).setAttribute("state", 0);
			graph.getNode(node.getName()).addAttribute("layout.frozen");
			graph.getNode(node.getName()).addAttribute("xy", node.getXCord(), node.getYCord());
		}
		
		// add edges
		for(Node node : nodeList){
			for(Edge edge : node.getNeighborNodes()){
				try{
					graph.addEdge(node.getName() + edge.getNeighborNode().getName(),
						node.getName(), edge.getNeighborNode().getName());
				}catch(EdgeRejectedException e){
				}
			}
		}
	}
	
	public void display(){
		graph.display();
	}
	
	public ViewPanel getView(){
		return view;
	}
	
	public boolean isInPOIList(Node n){
		return poi.contains(n);
	}
	
	public void addPOI(Node n){
		poi.add(n);
	}
	
	public void removeLast(){
		poi.removeLast();
	}
	
	public void addSource(Node n){
		sourceNode = n;
	}
	
	public void addDestination(Node n){
		destinationNode = n;
	}
	
	public void clearPOI(){
		poi.clear();
	}
	
	public boolean isValid(){
		return clicks.isValid();
	}
	
	public int getPOISize(){
		return poi.size();
	}
	
	public LinkedList<Node> getPOI(){
		return poi;
	}
	
	public void addSourceAndDestination(){
		poi.addFirst(sourceNode);
		poi.addLast(destinationNode);
	}
	
	public void showPath(LinkedList<NodeStop> path){
		for (int i = 0; i < path.size(); i++) {
			String nodeName = path.get(i).getNode().getName();
			graph.getNode(nodeName).setAttribute("ui.style", "shadow-mode: gradient-radial;");
			graph.getNode(nodeName).setAttribute("ui.style", "shadow-color: purple;");
			graph.getNode(nodeName).setAttribute("ui.style", "shadow-width: 3;");
			graph.getNode(nodeName).setAttribute("ui.style", "shadow-offset: 0;");
			if(i+1 < path.size()){
				String nextNode = path.get(i+1).getNode().getName();
				graph.getNode(nodeName).getEdgeFrom(nextNode).setAttribute("ui.style", "shadow-mode: gradient-radial;");
				graph.getNode(nodeName).getEdgeFrom(nextNode).setAttribute("ui.style", "shadow-color: purple;");
				graph.getNode(nodeName).getEdgeFrom(nextNode).setAttribute("ui.style", "shadow-width: 3;");
				graph.getNode(nodeName).getEdgeFrom(nextNode).setAttribute("ui.style", "shadow-offset: 0;");
			}
		}
	}
}
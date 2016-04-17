package logic;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

public class GraphApplication {
	private Graph graph;
	private String pathName;
	
	public GraphApplication(){
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		graph = new MultiGraph("Tutorial 1");
		pathName = GraphApplication.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		graph.addAttribute("ui.stylesheet", "url('file:///" + pathName + "../styles/test2.css')");
	}
	
	public void start(){
		/* Create nodes and edges */
		graph.addNode("A").addAttribute("ui.label", "A");
		graph.addNode("B").addAttribute("ui.label", "B");
		graph.addEdge("AB", "A", "B");
		
		/* Example attributes: path, gScore and fScore, etc... */
		graph.getNode("B").addAttribute("type", "hotel"); /* other options will be determined */
		graph.getNode("B").addAttribute("cameFrom", "A");
		graph.getNode("B").addAttribute("gScore", 12345);
		graph.getNode("B").addAttribute("fScore", 123);
	
		graph.addEdge("AC", "A", "C");
		graph.addEdge("CB", "C", "B");
		graph.addEdge("AD", "A", "D");
		graph.addEdge("AE", "A", "E");
		graph.addEdge("FG", "F", "G");
		graph.addEdge("FE", "F", "E");
		graph.addEdge("GA", "G", "A");
		graph.addEdge("GC", "G", "C");
		graph.addEdge("BD", "B", "D");
		graph.addEdge("BG", "B", "G");
		graph.addEdge("HC", "H", "C");
		graph.addEdge("HD", "H", "D");
		graph.addEdge("HF", "H", "F");
		graph.addEdge("CF", "C", "F");
		graph.display();
	}
	
}

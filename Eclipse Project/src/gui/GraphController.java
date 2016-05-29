package gui;

import logic.Node;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

public class GraphController extends Thread implements ViewerListener {
	protected boolean loop = true;
	private Viewer viewer;
	private Graph graph;
	private ViewerPipe pipe;
	private GraphApp app;
	private boolean source,destination;
	private int numPath;
	
	public GraphController(Graph g, Viewer v, GraphApp a) {
		viewer = v;
		graph = g;
		app = a;
		source = false;
		destination = false;
		numPath = 0;
		
		pipe = viewer.newViewerPipe();
		pipe.addViewerListener(this);
		pipe.addSink(graph);
	}

	@Override
	public void run () {
		super.run();
		while(loop) {
			pipe.pump();
		}
	}
	public void viewClosed(String id) {
		loop = false;
	}

	public void buttonPushed(String id) {		
		Node n = (Node)graph.getNode(id).getAttribute("logic-node");
		int state = graph.getNode(id).getAttribute("state");
		switch (state) {
		//BLANK
		case 0:
			if(source){
				if(destination){
					numPath++;
					app.addPOI(n);
					graph.getNode(id).setAttribute("ui.label", numPath);
					graph.getNode(id).setAttribute("ui.style", "fill-color: cyan,blue;");
					graph.getNode(id).setAttribute("ui.style", "size: 30px;");
					graph.getNode(id).setAttribute("state",1);
				}else{
					destination = true;
					app.addDestination(n);
					graph.getNode(id).setAttribute("ui.style", "fill-color: red,pink;");
					graph.getNode(id).setAttribute("ui.style", "size: 40px;");
					graph.getNode(id).setAttribute("state",3);
				}
			}else{
				source = true;
				app.addSource(n);
				graph.getNode(id).setAttribute("ui.style", "fill-color: green,cyan;");
				graph.getNode(id).setAttribute("ui.style", "size: 40px;");
				graph.getNode(id).setAttribute("state",2);
			}
			break;
		//POI
		case 1:
			int label = graph.getNode(id).getAttribute("ui.label");
			if(label == numPath){
				numPath--;
				//app.removeLast();
				String nodeLabel = "";
				if(n.canRefuel){
					nodeLabel += "(G)";
				}
				if(n.canRest){
					nodeLabel += "(H)";
				}
				graph.getNode(id).setAttribute("ui.label",nodeLabel + id);
				graph.getNode(id).setAttribute("ui.style", "fill-color: yellow,orange;");
				graph.getNode(id).setAttribute("ui.style", "size: 20px;");
				graph.getNode(id).setAttribute("state",0);
			}
			break;
		//SOURCE
		case 2:
			if(destination){
				source = false;
				numPath++;
				//app.addPOI(n);
				graph.getNode(id).setAttribute("ui.label", numPath);
				graph.getNode(id).setAttribute("ui.style", "fill-color: cyan,blue;");
				graph.getNode(id).setAttribute("ui.style", "size: 30px;");
				graph.getNode(id).setAttribute("state",1);
			}else{
				source = false;
				destination = true;
				app.addDestination(n);
				graph.getNode(id).setAttribute("ui.style", "fill-color: red,pink;");
				graph.getNode(id).setAttribute("ui.style", "size: 40px;");
				graph.getNode(id).setAttribute("state",3);
			}
			break;
		//DESTINATION
		case 3:
			destination = false;
			//app.addPOI(n);
			numPath++;
			graph.getNode(id).setAttribute("ui.label", numPath);
			graph.getNode(id).setAttribute("ui.style", "fill-color: cyan,blue;");
			graph.getNode(id).setAttribute("ui.style", "size: 30px;");
			graph.getNode(id).setAttribute("state",1);
			break;
		}
	}

	public void buttonReleased(String id) {
		
	}
	
	public boolean isValid(){
		return source && destination;
	}	
}
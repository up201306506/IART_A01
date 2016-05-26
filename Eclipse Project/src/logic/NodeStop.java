package logic;

public class NodeStop {
	
	private Node node;
	
	public boolean stoppedToRefuel;
	public boolean stoppedToRest;
	
	private AlgorithmSettings settings;
	
	public NodeStop(Node node, boolean stoppedToRefuel, boolean stoppedToRest, AlgorithmSettings settings){
		this.node = node;
		
		this.stoppedToRefuel = stoppedToRefuel;
		this.stoppedToRest = stoppedToRest;
		
		this.settings = settings;
	}
	
	public Node getNode(){
		return node;
	}
	
	public AlgorithmSettings getLastSettings(){
		return settings;
	}
}
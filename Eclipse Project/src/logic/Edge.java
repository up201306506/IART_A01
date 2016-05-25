package logic;

public class Edge {
	
	private Node neighborNode;
	
	private int duration; // minutes
	private float cost;

	public Edge(Node neighborNode, int duration, float cost){
		this.neighborNode = neighborNode;
		
		this.duration = duration;
		this.cost = cost;
	}
	
	// getters & setters	
	public Node getNeighborNode(){
		return neighborNode;
	}
	
	public int getDuration(){
		return duration;
	}
	
	public float getCost(){
		return cost;
	}
	
	// static methods, aux methods
	public static void associateNodes(Node nodeA, Node nodeB, int duration, float cost){
		if(nodeA.equals(nodeB)) return;
		
		nodeA.addEdge(nodeB, duration, cost);
		nodeB.addEdge(nodeA, duration, cost);
	}
	
	// overwrites
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;

		if(!(obj instanceof Edge)) return false;

		Edge that = (Edge) obj;
		if(this.neighborNode.equals(that.neighborNode)) return true;

		return false;
	}
}
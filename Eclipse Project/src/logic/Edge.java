package logic;

public class Edge {
	
	private Node neighborNode;
	
	private float cost;

	public Edge(Node neighborNode, float cost){
		this.neighborNode = neighborNode;
		
		this.cost = cost;
	}
	
	// getters & setters	
	public Node getNeighborNode(){
		return neighborNode;
	}
	
	public float getCost(){
		return cost;
	}
	
	// static methods, aux methods
	public static void associateNodes(Node nodeA, Node nodeB, float cost){
		if(nodeA.equals(nodeB)) return;
		
		nodeA.addEdge(nodeB, cost);
		nodeB.addEdge(nodeA, cost);
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
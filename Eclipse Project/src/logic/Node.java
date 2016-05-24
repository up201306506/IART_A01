package logic;

import java.util.ArrayList;

public class Node {

	private String nodeName;

	private float xCord;
	private float yCord;

	private boolean canRefuel;
	private boolean canRest;

	private boolean isPointOfInterest;

	private ArrayList<Edge> neighborNodes;

	public Node(String nodeName, float xCord, float yCord, boolean canRefuel, boolean canRest, boolean isPointOfInterest){
		this.nodeName = nodeName;

		this.xCord = xCord;
		this.yCord = yCord;

		this.canRefuel = canRefuel;
		this.canRest = canRest;

		this.isPointOfInterest = isPointOfInterest;

		neighborNodes = new ArrayList<>();
	}

	// getters & setters
	public String getName(){
		return nodeName;
	}

	public float getXCord(){
		return xCord;
	}

	public float getYCord(){
		return yCord;
	}

	public boolean canRefuel(){
		return canRefuel;
	}

	public boolean canRest(){
		return canRest;
	}

	public boolean isPointOfInterest(){
		return isPointOfInterest;
	}

	public ArrayList<Edge> getNeighborNodes(){
		return neighborNodes;
	}

	// functions
	public void addEdge(Node neighborNode, int duration, float cost){
		Edge thisEdge = new Edge(neighborNode, duration, cost);

		if(!neighborNodes.contains(thisEdge))
			neighborNodes.add(thisEdge);
	}

	// overwrites

	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof Node)) return false;

		Node that = (Node) obj;

		if(this.nodeName.equals(that.nodeName)
				&& this.xCord == that.xCord
				&& this.yCord == that.yCord
				&& this.canRefuel == that.canRefuel
				&& this.canRest == that.canRest
				&& this.isPointOfInterest == that.isPointOfInterest)
			return true;

		return false;
	}
}
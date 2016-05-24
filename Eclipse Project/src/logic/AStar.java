package logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStar {

	public static enum RestrictionType {
		NO_RESTRICTION, COST, DURATION, DURATION_COST
	}

	public static LinkedList<Node> runAlgorithm(Node start, Node end, RestrictionType restrictionType){

		PriorityQueue<NodeScore> frontier = new PriorityQueue<>(); // set of nodes to be evaluated
		frontier.add(new NodeScore(start, 0)); // the starting node is the first to be evaluated

		HashMap<Node, Node> cameFrom = new HashMap<>(); // mapping for each node evaluated and its predecessor node
		cameFrom.put(start, start); // predecessor of the start node, is itself

		HashMap<Node, Integer> costSoFar = new HashMap<>(); // mapping of the score of each node evaluated
		costSoFar.put(start, 0); // score of the starting node is always 0

		while(!frontier.isEmpty()){ // while there are nodes to be evaluated
			NodeScore current = frontier.remove(); // evaluates the node with the lowest score at the moment

			// if the node being evaluated is the goal node
			if(current.getNode().equals(end)) return retrievePath(start, end, cameFrom);
			
			// evaluates the neighbor nodes
			for(Edge edgeToNeighbor : current.getNode().getNeighborNodes()){
				Node neighborNode = edgeToNeighbor.getNeighborNode();

				// calculates the cost of the traveling cost from the start node to this node
				int newCost = costSoFar.get(current.getNode()) + calculateCostBetween(current.getNode(), neighborNode, restrictionType);

				// if the node has not been already evaluated
				if(!costSoFar.containsKey(neighborNode)
						// if the cost of traveling to this node is less than the stored
						|| (newCost < costSoFar.get(neighborNode))){ 

					// updates the cost of traveling to this node
					if(costSoFar.containsKey(neighborNode))
						costSoFar.remove(neighborNode);
					costSoFar.put(neighborNode, newCost);

					// estimates the cost to the goal node
					int priority = newCost + calculateHeuristicBetween(neighborNode, end, restrictionType);
					// add it to the list of nodes to be evaluated
					frontier.add(new NodeScore(neighborNode, priority)); // possible path

					// updates the predecessor node to this node
					if(cameFrom.containsKey(neighborNode))
						cameFrom.remove(neighborNode);
					cameFrom.put(neighborNode, current.getNode());
				}
			}
		}
		
		return null; // if no path was found
	}

	// aux to retrieve path
	private static LinkedList<Node> retrievePath(Node start, Node end, HashMap<Node, Node> cameFrom){

		// temporary use of stack to reverse hashmap
		Stack<Node> stack = new Stack<>();
		stack.push(end); // the end node is the bottom of the stack
		
		Node originalNode;
		do{
			originalNode = stack.peek(); // grabs the top of the stack
			stack.push(cameFrom.get(originalNode)); // retrieves the node it came from
		}while(!originalNode.equals(start));
		
		// since there are two starting node in the stack
		// because of the stop condition
		// one his removed, pop
		stack.pop();

		// list of nodes sequence
		LinkedList<Node> result = new LinkedList<>();
		
		// sequences the node order
		while(!stack.isEmpty())
			result.add(stack.pop());

		return result;
	}

	// calculate gscore
	private static int calculateCostBetween(Node src, Node dest, RestrictionType restrictionType){
		switch (restrictionType) {
		case NO_RESTRICTION:
			return 1;
		case COST:
			return calculateCostAttrBetween(src, dest);
		case DURATION:
			return calculateDurationAttrBetween(src, dest);
		case DURATION_COST:
			return calculateDurationAttrBetween(src, dest)
					+ calculateCostAttrBetween(src, dest);
		default:
			return -1;
		}
	}
	
	// gscore calculations for each restriction
	private static int calculateDurationAttrBetween(Node src, Node dest){
		for(Edge edge : src.getNeighborNodes()){
			if(edge.getNeighborNode().equals(dest)) return edge.getDuration();
		}

		return -1;
	}

	private static int calculateCostAttrBetween(Node src, Node dest){
		for(Edge edge : src.getNeighborNodes()){
			if(edge.getNeighborNode().equals(dest)) return Math.round(edge.getCost());
		}

		return -1;
	}

	// calculate hscore
	private static int calculateHeuristicBetween(Node origin, Node end, RestrictionType restrictionType){
		switch (restrictionType) {
		case NO_RESTRICTION:
			return 0;
		case COST:
			return calculateCostHeuristicBetween(origin, end);
		case DURATION:
			return -1;
		case DURATION_COST:
			return -1;
		default:
			return -1;
		}
	}

	//hscore calculations for each restriction
	private static int calculateCostHeuristicBetween(Node origin, Node end){
		// the next step to any other node
		// will cost the min value of the cost in its edges
		
		// if an edge has no neighbors will have no cost to travel
		if(origin.getNeighborNodes().size() == 0) return 0;
		
		// auto sorting of the cots
		PriorityQueue<Float> sortedList = new PriorityQueue<>();
		
		// iterate through edges
		for(Edge edgeToNeighbor : origin.getNeighborNodes())
			sortedList.add(new Float(edgeToNeighbor.getCost()));
		
		return sortedList.peek().intValue();
	}
}
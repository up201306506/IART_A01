package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStar {

	public static enum RestrictionType {
		NO_RESTRICTION,
		DISTANCE, COST, REFUEL, REST
	}	

	public static LinkedList<Node> runAlgorithm(AlgorithmSettings settings, int initialGas, int initialTravelTime,
			Node start, Node end, ArrayList<RestrictionType> restrictionType){

		PriorityQueue<NodeScore> frontier = new PriorityQueue<>(); // set of nodes to be evaluated
		frontier.add(new NodeScore(start, 0)); // the starting node is the first to be evaluated

		HashMap<Node, Node> cameFrom = new HashMap<>(); // mapping for each node evaluated and its predecessor node
		cameFrom.put(start, start); // predecessor of the start node, is itself

		// mapping of the gas and travel time value
		// for each evaluated node
		HashMap<Node, GasTravelTime> pathInformation = new HashMap<>(); 
		pathInformation.put(start, new GasTravelTime(initialGas, initialTravelTime));

		HashMap<Node, ScoreDistance> costSoFar = new HashMap<>(); // mapping of the score of each node evaluated
		costSoFar.put(start, new ScoreDistance(0, 0)); // score of the starting node is always 0

		while(!frontier.isEmpty()){ // while there are nodes to be evaluated
			NodeScore current = frontier.remove(); // evaluates the node with the lowest score at the moment

			// if the node being evaluated is the goal node
			if(current.getNode().equals(end)) return retrievePath(start, end, cameFrom);

			// BLOCK OF CODE NOT TESTED
			// NEEDS HEURISTICS AND TEST CASE GRAPH

			// set the new weight settings
			settings.setNewWeights(pathInformation.get(current.getNode()).currentGas,
					pathInformation.get(current.getNode()).travelTime,
					restrictionType);

			// if refuel weight is high and this node can refuel, will refuel
			if(current.getNode().canRefuel && (settings.varRefuelWeight > 0.8)){
				int oldTimeTravelled = pathInformation.get(current.getNode()).travelTime;

				pathInformation.remove(current.getNode());

				pathInformation.put(current.getNode(), new GasTravelTime(settings.maxGasDeposit, oldTimeTravelled));
			}

			// if rest weight is high and this node allow resting, will rest
			if(current.getNode().canRest && (settings.varRestWeight > 0.8)){
				int oldGas = pathInformation.get(current.getNode()).currentGas;

				pathInformation.remove(current.getNode());

				pathInformation.put(current.getNode(), new GasTravelTime(oldGas, settings.maxTravelTime));
			}

			// BLOCK OF CODE NOT TESTED -- ENDED

			// evaluates the neighbor nodes
			for(Edge edgeToNeighbor : current.getNode().getNeighborNodes()){
				Node neighborNode = edgeToNeighbor.getNeighborNode();

				// calculates the cost of the traveling cost from the start node to this node
				int newCost = costSoFar.get(current.getNode()).score
						+ CostUtils.Cost(settings, current.getNode(), neighborNode, restrictionType);

				// if the node has not been already evaluated
				if(!costSoFar.containsKey(neighborNode)
						// if the cost of traveling to this node is less than the stored
						|| (newCost < costSoFar.get(neighborNode).score)){ 

					// updates the cost of traveling to this node
					if(costSoFar.containsKey(neighborNode))
						costSoFar.remove(neighborNode);
					costSoFar.put(neighborNode, new ScoreDistance(newCost,
							costSoFar.get(current).distance
							+ HeuristicsUtils.EuclideanDistance(current.getNode(), neighborNode)));

					// estimates the cost to the goal node
					int priority = newCost + HeuristicsUtils.heuristic(settings, neighborNode, end,
							restrictionType, costSoFar.get(neighborNode).distance);
					// add it to the list of nodes to be evaluated
					frontier.add(new NodeScore(neighborNode, priority)); // possible path

					// updates the predecessor node to this node
					if(cameFrom.containsKey(neighborNode))
						cameFrom.remove(neighborNode);
					cameFrom.put(neighborNode, current.getNode());

					// update the gas and travel time of the evaluated node
					if(pathInformation.containsKey(neighborNode))
						pathInformation.remove(neighborNode);
					pathInformation.put(neighborNode, new GasTravelTime(
							// calculates the new gas value
							pathInformation.get(current.getNode()).currentGas
							- CostUtils.FuelValue(current.getNode(), neighborNode, settings),
							// calculates the new travel time value
							pathInformation.get(current.getNode()).travelTime
							- CostUtils.RestValue(current.getNode(), neighborNode, settings)));
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
}
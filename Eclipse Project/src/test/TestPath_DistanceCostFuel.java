package test;

import java.util.ArrayList;
import java.util.LinkedList;

import logic.AStar;
import logic.AlgorithmSettings;
import logic.Edge;
import logic.Node;
import logic.NodeStop;
import logic.AStar.RestrictionType;

public class TestPath_DistanceCostFuel {
	
	public static void main(String[] args) {
		ArrayList<Node> nodeList = new ArrayList<>();

		// src node
		Node a = new Node("A", 0, 0, false, false);
		nodeList.add(a);

		// path
		Node b = new Node("B", 3, 0, false, false);
		Node c = new Node("C", 0, 2, false, false);
		Node d = new Node("D", 3, 3, false, false);

		nodeList.add(b);
		nodeList.add(c);
		nodeList.add(d);

		Edge.associateNodes(a, b, 5);
		Edge.associateNodes(a, c, 1);
		Edge.associateNodes(c, b, 0);
		Edge.associateNodes(b, d, 1);
		Edge.associateNodes(c, d, 7);
		
		ArrayList<RestrictionType> restrictionList = new ArrayList<>();
		//restrictionList.add(RestrictionType.REFUEL);
		restrictionList.add(RestrictionType.COST);
		restrictionList.add(RestrictionType.DISTANCE);
		
		// distance weight is max
		//@param initialGasVale, initialTimeTravelValue, averageSpeed, averageGasConsume,
		// maxGasDeposit, maxTravelTime, distanceWeight, costWeight
		AlgorithmSettings settings = new AlgorithmSettings(22, 70, 10, 10, 100, 100, 0.5f, 0.5f);
		
		LinkedList<NodeStop> result = AStar.runAlgorithm(settings,
				settings.nextGasValue, settings.nextTravelTime, a, d, restrictionList);
		for(NodeStop stop : result)
			System.out.println("-> " + stop.getNode().getName() + "\t-- " + stop.stoppedToRefuel);
		
		// should print src - r - dest
	}
}
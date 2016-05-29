package test;

import java.util.ArrayList;
import java.util.LinkedList;

import logic.AStar;
import logic.AlgorithmSettings;
import logic.Edge;
import logic.Node;
import logic.NodeStop;
import logic.AStar.RestrictionType;

public class TestPath_CostRefuelRestriction {
	
	public static void main(String[] args) {
		ArrayList<Node> nodeList = new ArrayList<>();
		
		// src node
		Node src = new Node("Src", 0, 5, false, false);
		nodeList.add(src);
		
		// path		
		Node l = new Node("L", -0.5f, 3, false, false);
		Node r = new Node("R", 0.5f, 3, true, false);
		nodeList.add(l);
		nodeList.add(r);
		
		Node dest = new Node("Dest", 0, 0, false, false);
		nodeList.add(dest);
		
		Edge.associateNodes(src, l, 1);
		Edge.associateNodes(src, r, 2);
		
		Edge.associateNodes(l, dest, 1);
		Edge.associateNodes(r, dest, 1);
		
		ArrayList<RestrictionType> restrictionList = new ArrayList<>();
		restrictionList.add(RestrictionType.REFUEL);
		restrictionList.add(RestrictionType.COST);
		
		// distance weight is max
		//@param initialGasVale, initialTimeTravelValue, averageDurationByDistance, averageGasConsume,
		// maxGasDeposit, maxTravelTime, distanceWeight, costWeight
		AlgorithmSettings settings = new AlgorithmSettings(70, 70, 10, 10, 100, 100, 0, 1);
		
		LinkedList<NodeStop> result = AStar.runAlgorithm(settings,
				settings.nextGasValue, settings.nextTravelTime, src, dest, restrictionList);
		for(NodeStop stop : result)
			System.out.println("-> " + stop.getNode().getName() + "\t-- " + stop.stoppedToRefuel);
		
		// with initialGasValue = 22, should print src - r - dest
		// with initialGasValue = 70, should print src - l - dest
	}
}
package test;

import java.util.ArrayList;
import java.util.LinkedList;

import logic.AStar;
import logic.AlgorithmSettings;
import logic.Edge;
import logic.Node;
import logic.NodeStop;
import logic.AStar.RestrictionType;

public class TestPath_RefuelRestRestriction {
	
	public static void main(String[] args) {
		ArrayList<Node> nodeList = new ArrayList<>();
		
		// src node
		Node src = new Node("Src", 0, 2, false, false);
		nodeList.add(src);
		
		// path
		Node r = new Node("R", 1, 1.5f, true, false);
		Node c = new Node("C", 0, 1, false, false);
		Node l = new Node("L", -1, 0.5f, false, true);
		
		nodeList.add(r);
		nodeList.add(c);
		nodeList.add(l);
		
		Node dest = new Node("Dest", 0, 0, false, false);
		nodeList.add(dest);
		
		Edge.associateNodes(src, r, 1);
		Edge.associateNodes(src, c, 1);
		Edge.associateNodes(r, c, 1);
		
		Edge.associateNodes(c, l, 1);
		Edge.associateNodes(c, dest, 1);
		Edge.associateNodes(l, dest, 1);
		
		ArrayList<RestrictionType> restrictionList = new ArrayList<>();
		//restrictionList.add(RestrictionType.REST);
		restrictionList.add(RestrictionType.REFUEL);
		restrictionList.add(RestrictionType.DISTANCE);
		
		// distance weight is max
		//@param initialGasVale, initialTimeTravelValue, averageSpeed, averageGasConsume,
		// maxGasDeposit, maxTravelTime, distanceWeight, costWeight
		AlgorithmSettings settings = new AlgorithmSettings(12, 14, 10, 10, 100, 100, 0, 0);
		
		LinkedList<NodeStop> result = AStar.runAlgorithm(settings,
				settings.nextGasValue, settings.nextTravelTime, src, dest, restrictionList);
		for(NodeStop stop : result)
			System.out.println("-> " + stop.getNode().getName()
					+ "\t-- " + stop.stoppedToRefuel + " | " + stop.stoppedToRest);
		
		// should print src - r - dest
	}
}
package test;

import java.util.ArrayList;
import java.util.LinkedList;

import logic.AStar;
import logic.AlgorithmSettings;
import logic.Edge;
import logic.Node;
import logic.NodeStop;
import logic.AStar.RestrictionType;

public class TestPath_AllRestrictions {
	
	public static void main(String[] args) {
		ArrayList<Node> nodeList = new ArrayList<>();
		
		// src node
		Node src = new Node("Src", 0, 0, false, false);
		nodeList.add(src);
		
		// path		
		Node ls = new Node("LS", -1, 1, false, true);
		Node lf = new Node("LF", -1, 2, true, false);
		Node rf = new Node("RF",1, 1, true, false);
		Node rs = new Node("RS", 1, 2, false, true);
		
		Node c = new Node("C", 0, 2, false, false);
		
		Node cf = new Node("CFar", -2, 3, false, false);
		Node ce = new Node("CExpensive", 1, 3, false, false);
		
		nodeList.add(ls);
		nodeList.add(lf);
		nodeList.add(rf);
		nodeList.add(rs);
		nodeList.add(c);
		nodeList.add(cf);
		nodeList.add(ce);
		
		Node dest = new Node("Dest", 0, 0, false, false);
		nodeList.add(dest);
		
		Edge.associateNodes(src, ls, 1);
		Edge.associateNodes(src, rf, 1);
		
		Edge.associateNodes(ls, lf, 1);
		Edge.associateNodes(rf, rs, 1);
		
		Edge.associateNodes(lf, c, 1);
		Edge.associateNodes(rs, c, 1);
		
		Edge.associateNodes(c, cf, 1);
		Edge.associateNodes(c, ce, 2);
		
		Edge.associateNodes(cf, dest, 1);
		Edge.associateNodes(ce, dest, 1);
		
		ArrayList<RestrictionType> restrictionList = new ArrayList<>();
		restrictionList.add(RestrictionType.REFUEL);
		//restrictionList.add(RestrictionType.REST);
		//restrictionList.add(RestrictionType.DISTANCE);
		restrictionList.add(RestrictionType.COST);
		
		// distance weight is max
		//@param initialGasVale, initialTimeTravelValue, averageDurationByDistance, averageGasConsume,
		// maxGasDeposit, maxTravelTime, distanceWeight, costWeight
		AlgorithmSettings settings = new AlgorithmSettings(22, 22, 10, 10, 100, 100, 0.4f, 0.6f);
		
		LinkedList<NodeStop> result = AStar.runAlgorithm(settings,
				settings.nextGasValue, settings.nextTravelTime, src, dest, restrictionList);
		for(NodeStop stop : result)
			System.out.println("-> " + stop.getNode().getName());
		
		// result should always prioritize fuel
		// src - rf - rs - c
		// if cost weight more than distance weight
		// c - cfar - dest
		// if not
		// c - cexpensive - dest
	}
}
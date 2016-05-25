package test;

import java.util.ArrayList;
import java.util.LinkedList;

import logic.AStar;
import logic.Edge;
import logic.Node;
import logic.AStar.RestrictionType;

public class TestPath_DistanceRestriction {
	
	public static void main(String[] args) {
		ArrayList<Node> nodeList = new ArrayList<>();
		
		// src node
		Node a = new Node("A", 0, 0, false, false, false);
		nodeList.add(a);
		
		// path
		Node b = new Node("B", 3, 0, false, false, false);
		Node c = new Node("C", 0, 2, false, false, false);
		Node d = new Node("D", 3, 3, false, false, false);
		
		nodeList.add(b);
		nodeList.add(c);
		nodeList.add(d);
		
		Edge.associateNodes(a, b, 1, 1);
		Edge.associateNodes(a, c, 1, 1);
		Edge.associateNodes(b, d, 1, 1);
		Edge.associateNodes(c, d, 1, 1);
		
		ArrayList<RestrictionType> restrictionList = new ArrayList<>();
		restrictionList.add(RestrictionType.DISTANCE);
		
		// distance weight is max
		AStar algorithm = new AStar(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
		
		LinkedList<Node> result = algorithm.runAlgorithm(a, d, restrictionList);
		for(Node node : result)
			System.out.println("-> " + node.getName());
	}
}
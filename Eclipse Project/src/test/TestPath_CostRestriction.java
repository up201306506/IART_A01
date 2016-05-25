package test;

import java.util.ArrayList;
import java.util.LinkedList;

import logic.AStar;
import logic.Edge;
import logic.Node;
import logic.AStar.RestrictionType;
import logic.AlgorithmSettings;

public class TestPath_CostRestriction {

	public static void main(String[] args) {
		ArrayList<Node> nodeList = new ArrayList<>();
		
		// src node
		Node a = new Node("A", 0, 0, false, false, false);
		nodeList.add(a);
		
		// path
		Node b = new Node("B", 1, 1, false, false, false);
		Node c = new Node("C", 2, 1, false, false, false);
		
		nodeList.add(b);
		nodeList.add(c);
		
		Edge.associateNodes(a, b, 3);
		Edge.associateNodes(a, c, 1);
		Edge.associateNodes(b, c, 1);
		
		ArrayList<RestrictionType> restrictionList = new ArrayList<>();
		restrictionList.add(RestrictionType.COST);
		
		// cost weight max
		AlgorithmSettings settings = new AlgorithmSettings(2.4f, 7, 30, 15, 0, 1, 0, 0);
		
		LinkedList<Node> result = AStar.runAlgorithm(settings, 0, 0, a, b, restrictionList);
		for(Node node : result)
			System.out.println("-> " + node.getName());
		
		// should print A - C - B
	}
}
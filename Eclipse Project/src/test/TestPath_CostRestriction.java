package test;

import java.util.ArrayList;
import java.util.LinkedList;

import logic.AStar;
import logic.Edge;
import logic.Node;
import logic.NodeStop;
import logic.AStar.RestrictionType;
import logic.AlgorithmSettings;

public class TestPath_CostRestriction {

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

		Edge.associateNodes(a, b, 3);
		Edge.associateNodes(a, c, 1);
		Edge.associateNodes(c, b, 1);
		Edge.associateNodes(b, d, 1);
		Edge.associateNodes(c, d, 3);

		ArrayList<RestrictionType> restrictionList = new ArrayList<>();
		restrictionList.add(RestrictionType.COST);

		// cost weight max
		AlgorithmSettings settings = new AlgorithmSettings(0, 0, 2.4f, 7, 30, 15, 0, 1);

		LinkedList<NodeStop> result = AStar.runAlgorithm(settings, 0, 0, a, d, restrictionList);
		for(NodeStop stop : result)
			System.out.println("-> " + stop.getNode().getName());

		// should print A - C - B - D
	}
}
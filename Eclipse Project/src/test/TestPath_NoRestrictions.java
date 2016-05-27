package test;

import java.util.ArrayList;
import java.util.LinkedList;

import logic.AStar;
import logic.AlgorithmSettings;
import logic.Edge;
import logic.Node;
import logic.NodeStop;
import logic.AStar.RestrictionType;

public class TestPath_NoRestrictions {

	public static void main(String[] args) {
		ArrayList<Node> nodeList = new ArrayList<>();
		
		// src node
		Node a = new Node("A", 0, 0, false, false);
		nodeList.add(a);
		
		// path
		Node b = new Node("B", 1, 1, false, false);
		Node c = new Node("C", 2, 1, false, false);
		
		nodeList.add(b);
		nodeList.add(c);
		
		Edge.associateNodes(a, b, 1);
		Edge.associateNodes(a, c, 1);
		Edge.associateNodes(b, c, 1);
		
		ArrayList<RestrictionType> restrictionList = new ArrayList<>();
		restrictionList.add(RestrictionType.NO_RESTRICTION);
		
		// since it doest not have any restriction
		// any weight is not important
		AlgorithmSettings settings = new AlgorithmSettings(0, 0, 2.4f, 7, 30, 15, 0, 0);
		
		LinkedList<NodeStop> result = AStar.runAlgorithm(settings, 0, 0, a, b, restrictionList);
		for(NodeStop stop : result)
			System.out.println("-> " + stop.getNode().getName());
	}
}
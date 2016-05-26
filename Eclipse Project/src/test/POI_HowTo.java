package test;

import java.util.ArrayList;
import java.util.LinkedList;

import logic.AStar;
import logic.AlgorithmSettings;
import logic.Edge;
import logic.Node;
import logic.NodeStop;
import logic.AStar.RestrictionType;

public class POI_HowTo {

	public static void main(String[] args) {
ArrayList<Node> nodeList = new ArrayList<>();
		
		// src node
		Node start = new Node("Start", 0, 0, false, false);
		nodeList.add(start);
		
		// path
		Node b = new Node("B", 0, 1, false, false);
		Node c = new Node("C", 1, 1, false, false);
		Node d = new Node("D", 1, 2, false, false);
		
		nodeList.add(b);
		nodeList.add(c);
		nodeList.add(d);
		
		Node poi1 = new Node("POI-1", 1, 0, false, false);
		Node poi2 = new Node("POI-2", 2, 2, false, false);
		
		nodeList.add(poi1);
		nodeList.add(poi2);
		
		// dest node
		Node dest = new Node("Dest", 0, 2, false, false);
		nodeList.add(dest);
		
		Edge.associateNodes(start, b, 1);
		Edge.associateNodes(start, poi1, 1);
		Edge.associateNodes(b, dest, 1);
		Edge.associateNodes(poi1, c, 1);
		Edge.associateNodes(c, poi2, 1);
		Edge.associateNodes(c, d, 1);
		Edge.associateNodes(d, dest, 1);

		ArrayList<RestrictionType> restrictionList = new ArrayList<>();
		restrictionList.add(RestrictionType.NO_RESTRICTION);

		// since it does not have any restriction
		// any weight is not important
		AlgorithmSettings settings = new AlgorithmSettings(0, 0, 2.4f, 7, 30, 15, 0, 0, 0, 0);
		
		// HOW TO IMPLEMENT POI
		LinkedList<Node> poi = new LinkedList<>();
		poi.add(start); // starting node should always be the first poi
		poi.add(poi1);
		poi.add(poi2);
		poi.add(dest); // dest node should always be the last poi
		
		for(int i = 0; i < (poi.size() - 1); i++){
			LinkedList<NodeStop> result = AStar.runAlgorithm(settings, settings.nextGasValue, settings.nextTravelTime,
					poi.get(i), poi.get(i + 1), restrictionList);
			
			for(NodeStop stop : result)
				System.out.println("-> " + stop.getNode().getName());
		}
		
		// shortest path is: Start - B - Dest
		// with poi1: Start - POI1 - Start - B - Dest
		// with poi1 & poi2 : Start - POI1 - C - POI2 - C - D - Dest
	}
}
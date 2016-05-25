package logic;

import java.util.ArrayList;

import logic.AStar.RestrictionType;

public class CostUtils {

	// --------------------------------------
	// --- Cost Functions
	// --------------------------------------

	public static int Cost(Node predecessorNode, Node neighborNode, ArrayList<RestrictionType> restricionsList){

		// gscore
		double g = 0;
		
		for(RestrictionType restrictionType : restricionsList){
			// if no restriction the cost of traveling is always 1
			if(restrictionType == RestrictionType.NO_RESTRICTION)
				return 1;
			
			if (restrictionType == RestrictionType.DISTANCE)
				g += DistanceValue(predecessorNode, neighborNode);
			
			if (restrictionType == RestrictionType.COST)
				g += CostValue(predecessorNode, neighborNode);

			if(restrictionType == RestrictionType.DURATION)
				g += DurationValue(predecessorNode, neighborNode);
		}

		return (int) Math.round(g);
	}

	// --------------------------------------
	// --- Auxiliars
	// --------------------------------------
	
	private static double DistanceValue(Node predecessorNode, Node neighborNode){
		return HeuristicsUtils.EuclideanDistance(predecessorNode, neighborNode);
	}
	
	private static double CostValue(Node predecessorNode, Node neighborNode){
		for(Edge edge : predecessorNode.getNeighborNodes())
			if(edge.getNeighborNode().equals(neighborNode))
				return Math.round(edge.getCost());

		return -1;
	}

	private static double DurationValue(Node predecessorNode, Node neighborNode){
		for(Edge edge : predecessorNode.getNeighborNodes())
			if(edge.getNeighborNode().equals(neighborNode))
				return edge.getDuration();

		return -1;
	}
}
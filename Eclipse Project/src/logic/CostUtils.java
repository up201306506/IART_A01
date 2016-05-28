package logic;

import java.util.ArrayList;

import logic.AStar.RestrictionType;

public class CostUtils {

	// --------------------------------------
	// --- Cost Functions
	// --------------------------------------
	public static int Cost(AlgorithmSettings settings, Node predecessorNode, Node neighborNode,
			ArrayList<RestrictionType> restrictionList){

		// gscore
		double g = 0;

		for(RestrictionType restrictionType : restrictionList){
			// if no restriction the cost of traveling is always 1
			if(restrictionType == RestrictionType.NO_RESTRICTION)
				return 1;

			if (restrictionType == RestrictionType.DISTANCE)
				g += DistanceValue(predecessorNode, neighborNode);

			if (restrictionType == RestrictionType.COST)
				g += CostValue(predecessorNode, neighborNode);

			if(restrictionType == RestrictionType.REFUEL)
				g += FuelValue(predecessorNode, neighborNode, settings);

			if(restrictionType == RestrictionType.REST)
				g += RestValue(predecessorNode, neighborNode, settings);
		}

		return (int) Math.round(g * 20);
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

	public static int FuelValue(Node predecessorNode, Node neighborNode, AlgorithmSettings settings){
		double distance = HeuristicsUtils.EuclideanDistance(predecessorNode, neighborNode);
		return (int) Math.round(distance * settings.averageGasConsume);
	}

	public static int RestValue(Node predecessorNode, Node neighborNode, AlgorithmSettings settings){
		double distance = HeuristicsUtils.EuclideanDistance(predecessorNode, neighborNode);
		return (int) Math.round(distance / settings.averageSpeed);
	}
}
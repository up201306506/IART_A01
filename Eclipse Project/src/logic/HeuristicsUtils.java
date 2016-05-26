package logic;

import java.util.ArrayList;

import logic.AStar.RestrictionType;

public class HeuristicsUtils {

	// --------------------------------------
	// --- Heuristic Functions
	// --------------------------------------
	public static int heuristic(AlgorithmSettings settings, Node currentN, Node targetN,
			ArrayList<RestrictionType> restrictionList, double distance){

		// hscore
		double h = 0;

		for(RestrictionType restrictionType : restrictionList){
			// if no restriction the heuristic of traveling is always 0
			if(restrictionType == RestrictionType.NO_RESTRICTION)
				return 0;

			if (restrictionType == RestrictionType.DISTANCE)
				if(settings.varDistanceWeight <= 0) return 0;
				else h += settings.varDistanceWeight * DistanceValue(currentN, targetN);

			if (restrictionType == RestrictionType.COST)
				if(settings.varCostWeight <= 0) return 0;
				else h += settings.varCostWeight * CostValue(currentN);
			
			if(restrictionType == RestrictionType.REFUEL)
				if(settings.varRefuelWeight <= 0) return 0;
				else h += settings.varRefuelWeight * FuelValue(currentN);
			
			if(restrictionType == RestrictionType.REST)
				if(settings.varRestWeight <= 0) return 0;
				else h += settings.varRestWeight * RestValue(currentN);
		}

		return (int) Math.round(h);
	}

	// --------------------------------------
	// --- Auxiliars
	// --------------------------------------

	// Functions for checking the value of each parameter of the search.
	// Keeping EuclideanDistance public because it might have uses outside this class
	
	private static double DistanceValue(Node currentN, Node targetN){
		//Equals its Euclidean Distance
		return EuclideanDistance(currentN, targetN);
	}

	private static double CostValue(Node currentN){		
		Float minimumCost = null;
		for(Edge neighbourEdge : currentN.getNeighborNodes())
			if(minimumCost == null || minimumCost > neighbourEdge.getCost() )
				minimumCost = neighbourEdge.getCost();
		
		return minimumCost.doubleValue();	
	}
	
	private static double FuelValue(Node currentN){
		Node closestRefuel = findClosestRefuel(currentN);
		return EuclideanDistance(currentN,closestRefuel);
	}
	
	private static double RestValue(Node currentN){
		Node closestRefuel = findClosestRest(currentN);
		return EuclideanDistance(currentN,closestRefuel);
	}

	// public aux functions
	public static double EuclideanDistance(Node A, Node B){
		double dx = Math.abs(B.getXCord() - A.getXCord());
		double dy = Math.abs(B.getYCord() - A.getYCord());
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public static Node findClosestRefuel(Node currentN){
			Node result = null;
			Double bestDist = null;
			
			for (Node tempN : Node.refuelNodeList) {
				double dist = EuclideanDistance(currentN, tempN);
				if(result == null || dist < bestDist){
					result = tempN;
					bestDist = dist;
				}
			}
			
			return result;
	}
	
	public static Node findClosestRest(Node currentN){
		Node result = null;
		Double bestDist = null;
		
		for (Node tempN : Node.restNodeList) {
			double dist = EuclideanDistance(currentN, tempN);
			if(result == null || dist < bestDist){
				result = tempN;
				bestDist = dist;
			}
		}
		
		return result;
	}
}
package logic;

import java.util.ArrayList;

import logic.AStar.RestrictionType;

public class HeuristicsUtils {

	// --------------------------------------
	// --- Heuristic Functions
	// --------------------------------------
	public static int heuristic(AlgorithmSettings settings, Node currentN, Node targetN,
			ArrayList<RestrictionType> restrictionList, double distance, int currentGas, int travelTime){

		// hscore
		double h = 0;

		for(RestrictionType restrictionType : restrictionList){
			// if no restriction the heuristic of traveling is always 0
			if(restrictionType == RestrictionType.NO_RESTRICTION)
				return 0;

			if (restrictionType == RestrictionType.DISTANCE)
				if(settings.varDistanceWeight <= 0) h += 0;
				else h += settings.varDistanceWeight * DistanceValue(currentN, targetN);

			if (restrictionType == RestrictionType.COST)
				if(settings.varCostWeight <= 0) h += 0;
				else h += settings.varCostWeight * CostValue(currentN);

			if(restrictionType == RestrictionType.REFUEL){
				if(settings.varRefuelWeight <= 0) h += 0;

				double distanceLeft = EuclideanDistance(currentN, targetN);
				double distanceToEmptyFuel = currentGas / settings.averageGasConsume;
				double distanceToRefuel = FuelValue(currentN);

				double hFuel;
				if(currentN.equals(targetN)) hFuel = 0;
				else if(Node.refuelNodeList.contains(currentN)) hFuel = 0;
				else if(distanceLeft < distanceToEmptyFuel) hFuel = distanceLeft * settings.averageGasConsume;
				else if(distanceToRefuel < distanceToEmptyFuel) hFuel = distanceToRefuel * settings.averageGasConsume;
				else hFuel = Integer.MAX_VALUE;

				h += settings.varRefuelWeight * hFuel;
			}

			if(restrictionType == RestrictionType.REST){
				if(settings.varRestWeight <= 0) h += 0;

				double distanceLeft = EuclideanDistance(currentN, targetN);
				double distanceNeedToRest = travelTime / settings.averageDurationByDistance;
				double distanceToRest = RestValue(currentN);
				
				double hRest;
				if(currentN.equals(targetN)) hRest = 0;
				else if(Node.restNodeList.contains(currentN)) hRest = 0;
				else if(distanceLeft < distanceNeedToRest) hRest = (float) distanceLeft * settings.averageDurationByDistance;
				else if(distanceToRest < distanceNeedToRest) hRest = (float) distanceToRest * settings.averageDurationByDistance;
				else hRest = Integer.MAX_VALUE;

				h += settings.varRestWeight * hRest;
			}
		}

		return (int) Math.round(h * 20);
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
		return EuclideanDistance(currentN, closestRefuel);
	}

	private static double RestValue(Node currentN){
		Node closestRest = findClosestRest(currentN);
		return EuclideanDistance(currentN, closestRest);
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
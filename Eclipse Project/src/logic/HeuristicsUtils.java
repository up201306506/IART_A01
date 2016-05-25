package logic;

import java.util.ArrayList;

import logic.AStar.RestrictionType;

public class HeuristicsUtils {

	// --------------------------------------
	// --- Set Up
	// --------------------------------------

	// Important values to pay attention to before using Heuristics

	/** 
	 * Values for best cost per distance traveled and duration per distance unit traveled.
	 * This must be set when the graph is being built, because the A* algorithm must expect the best scenario in its predictions.
	 */
	public static Double bestCostRatio, bestDurationRatio;


	// --------------------------------------
	// --- Heuristic Functions
	// --------------------------------------

	// Summon Heuristic() as needed. 
	// Number or arguments grows incrementally with number of operations evaluated.

	public static int heuristic(AStar algorithm, Node currentN, Node targetN, ArrayList<RestrictionType> restrictionList){

		// hscore
		double h = 0;

		for(RestrictionType restrictionType : restrictionList){
			// if no restriction the heuristic of traveling is always 0
			if(restrictionType == RestrictionType.NO_RESTRICTION)
				return 0;
			
			if (restrictionType == RestrictionType.DISTANCE)
				h += algorithm.distanceWeight * DistanceValue(currentN, targetN);
			
			if (restrictionType == RestrictionType.COST)
				if(bestCostRatio != null)
					h += algorithm.costWeight * CostValue(currentN, targetN);

			if(restrictionType == RestrictionType.DURATION)
				if(bestDurationRatio != null)
					h += algorithm.durationWeight * DurationValue(currentN, targetN);
		}

		return (int) Math.round(h);
	}

	/*	
	public static double Heuristic(Node currentN, Node targetN, RestrictionType type, double FuelSpent, double RestSpent){

		//If you run out of fuel or collapse from exhaustion, this node should fail.
		if(useFuel && FuelSpent > 1)
			return 0;
		if(useRest && RestSpent > 1)
			return 0;

		Double h = Heuristic(currentN, targetN, type);

		if(useFuel && currentN.canRefuel())
			h += RefuelWeight * RefuelValue(FuelSpent);
		if(useRest && currentN.canRest())
			h += RestWeight * RefuelValue(RestSpent);

		return h;
	}
	 */	

	// --------------------------------------
	// --- Auxiliars
	// --------------------------------------

	// Functions for checking the value of each parameter of the search.
	// Keeping EuclideanDistance public because it might have uses outside this class.

	private static double DistanceValue(Node currentN, Node targetN){
		//Equals its Euclidean Distance
		return EuclideanDistance(currentN, targetN);
	}

	private static double CostValue(Node currentN, Node targetN){
		double distanceLeft = EuclideanDistance(currentN, targetN);
		return distanceLeft * bestCostRatio;	
	}

	private static double DurationValue(Node currentN, Node targetN){
		double distanceLeft = EuclideanDistance(currentN, targetN);
		return distanceLeft * bestDurationRatio;
	}
	
	// public aux functions
	public static double EuclideanDistance(Node A, Node B){
		double dx = Math.abs(B.getXCord() - A.getXCord());
		double dy = Math.abs(B.getYCord() - A.getYCord());
		return Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * -DEPRECATED-
	 * Should be used on both Fuel and Resting needs. Returns practically 0 for any input below 0.60.
	 * 
	 * @param spentFuel should be a percentage based on maximum fuel
	 * @return Value ranges from 0 and -1.
	 * 
	 * @deprecated The idea behind this part of the Heuristic was not applicable to A* and could lead to it becoming incomplete.
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private static double RefuelValue(double spentFuel){
		double value = (spentFuel - 0.2);
		value = -4.77 * Math.exp(-1/(value*value));
		return value;
	}

}
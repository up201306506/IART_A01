package logic;

import logic.AStar.RestrictionType;

/**
 * @author Sunset(Pedro Carvalho) 
 *
 */
public class HeuristicsUtils {
	
	// --------------------------------------
	// --- Set Up
	// --------------------------------------
	
	// Important values to pay attention to before using Heuristics
	
	/** 
	 * Values for best cost per distance travelled and duration per distance unit travelled.
	 * This must be set when the graph is being built, because the A* algorithm must expect the best scenario in its predictions.
	 */
	public static Double bestCostRatio, bestDurationRatio;

	/**
	 * Choose wether to use Refueling Station Search and Resting Place Search
	 */
	public static boolean useFuel = false, useRest = false;

	/**
	 * Parameters that change how large the impact the requirements will have on the basic search heuristic.
	 * Require fine-tuning on a per-case basis. Too low and they'll be ignored, too high and they'll take over the search for a solution. 
	 */
	public static int DurationWeight = 1, CostWeight = 1, RefuelWeight = 1, RestWeight = 1;
	
	
	// --------------------------------------
	// --- Heuristic Functions
	// --------------------------------------
	
	// Summon Heuristic() as needed. 
	// Number or arguments grows incrementally with number of operations evaluated.
		
	public static double Heuristic(Node currentN, Node targetN){
		
		return DistanceValue(currentN, targetN);
		
	}
	
	public static double Heuristic(Node currentN, Node targetN, RestrictionType type){
		Double h = Heuristic(currentN, targetN);
				
		if (type == RestrictionType.DURATION || type == RestrictionType.DURATION_COST)
			if(bestDurationRatio != null)
				h += DurationWeight * DurationValue(currentN, targetN);
		
		if (type == RestrictionType.COST || type == RestrictionType.DURATION_COST)
			if(bestCostRatio != null)
				h += CostWeight * CostValue(currentN, targetN);

		return h;
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
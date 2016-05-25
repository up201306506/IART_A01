package logic;

public class HeuristicsUtils {
	
	// implementation of aux heuristics methods	

	public static int UnrestrictedHeuristic(Node currentN, Node targetN){
		Double h = 0.0;
		h += DistanceValue(currentN, targetN);
		
		
		return h.intValue();
	}
	
	public static double DistanceValue(Node A, Node B){
		//Equals its Euclidean Distance
		double dx = Math.abs(B.getXCord() - A.getXCord());
		double dy = Math.abs(B.getYCord() - A.getYCord());
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public static double CostValue(Node A, Node B){
		return 0;	
	}
	
	public static double DurationValue(Node A, Node B){
		return 0;
	}
	
	public static double DrowzinessValue(Node A, Node B){
		return 0;
	}
	
	public static double FuelValue(Node A, Node B){
		return 0;
	}
	
	
}
package logic;

import java.util.ArrayList;

import logic.AStar.RestrictionType;

public class AlgorithmSettings {

	// algorithm constants
	public float averageSpeed;
	public float averageGasConsume;

	// max values for fuel and travel time
	public int maxGasDeposit;
	public int maxTravelTime;

	// restrictions weight
	private float distanceWeight;
	private float costWeight;

	private float refuelWeight;
	private float restWeight;

	// var restrictions weight
	public float varDistanceWeight;
	public float varCostWeight;

	public float varRefuelWeight;
	public float varRestWeight;

	// var used to pass initial values
	// to next iterations of A*
	public int nextGasValue;
	public int nextTravelTime;

	// set values used in the algorithm
	public AlgorithmSettings(int initialGasVale, int initialTimeTravelValue, // initial values for algorithm
			float averageSpeed, float averageGasConsume, int maxGasDeposit, // refueling calculations
			int maxTravelTime, // resting calculations @maxTravelTime in minutes
			float distanceWeight, float costWeight, // algorithm restrictions weight
			float refuelWeight, float restWeight){

		nextGasValue = initialGasVale;
		nextTravelTime = initialTimeTravelValue;

		this.averageSpeed = averageSpeed;
		this.averageGasConsume = averageGasConsume;
		this.maxGasDeposit = maxGasDeposit;

		this.maxTravelTime = maxTravelTime;

		this.distanceWeight = distanceWeight;
		this.costWeight = costWeight;

		this.refuelWeight = refuelWeight;
		this.restWeight = restWeight;
	}

	public void setNewWeights(int gasValue, int travelTimeValue, ArrayList<RestrictionType> restrictionList){

		// if no fuel or rest restrictions are to be taken into account
		if(!restrictionList.contains(RestrictionType.REFUEL) && !restrictionList.contains(RestrictionType.REST)){
			varDistanceWeight = distanceWeight;
			varCostWeight = costWeight;

			varRefuelWeight = refuelWeight;
			varRestWeight = restWeight;
			
			return;
		}

		// if both fuel and rest restrictions are to be taken into account
		if(restrictionList.contains(RestrictionType.REFUEL) && restrictionList.contains(RestrictionType.REST)){
			float refuelPercentage = gasValue / maxGasDeposit;
			float newRefuelWeight = (float) getFuelWeightDiff(refuelPercentage);

			float timePercentage = travelTimeValue / maxTravelTime;
			float newRestWeight = (float) getRestWeightDiff(timePercentage);

			float restRefuelPercentage = 1 - refuelPercentage;
			float restTimePercentage = 1 - timePercentage;

			varDistanceWeight = distanceWeight - (restRefuelPercentage / 3) - (restTimePercentage / 3);
			varCostWeight = costWeight - (restRefuelPercentage / 3) - (restTimePercentage / 3);

			varRefuelWeight = newRefuelWeight - (restTimePercentage / 3);
			varRestWeight = newRestWeight - (restRefuelPercentage / 3);
			
			return;
		}

		// if a fuel or rest restriction is set
		if(restrictionList.contains(RestrictionType.REFUEL)){
			float percentage = gasValue / maxGasDeposit;
			float newRefuelWeight = (float) getFuelWeightDiff(percentage);
			varRefuelWeight = newRefuelWeight;

			float restPercentage = 1 - newRefuelWeight;

			varDistanceWeight = distanceWeight - (restPercentage / 3);
			varCostWeight = costWeight - (restPercentage / 3);

			varRestWeight = restWeight - (restPercentage / 3);
			
			return;
		}

		if(restrictionList.contains(RestrictionType.REST)){
			float percentage = travelTimeValue / maxTravelTime;
			float newRestWeight = (float) getRestWeightDiff(percentage);
			varRestWeight = newRestWeight;

			float restPercentage = 1 - newRestWeight;

			varDistanceWeight = distanceWeight - (restPercentage / 3);
			varCostWeight = costWeight - (restPercentage / 3);

			varRefuelWeight = refuelWeight - (restPercentage / 3);
			
			return;
		}
	}

	public static double getFuelWeightDiff(float percentage){
		double temp = percentage - 0.2;
		double result = 4.77 * Math.exp(-1 / (temp * temp));
		return result;
	}

	public static double getRestWeightDiff(float percentage){
		double temp = Math.exp(1) * percentage;
		temp = Math.exp(-9 / (Math.pow(1.5, 15)));
		return temp;
	}
}
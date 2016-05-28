package logic;

import java.util.ArrayList;

import logic.AStar.RestrictionType;

public class AlgorithmSettings {

	// algorithm constants
	public float averageDurationByDistance; // 1 / averageSpeed
	public float averageGasConsume;

	// max values for fuel and travel time
	public int maxGasDeposit;
	public int maxTravelTime;

	// restrictions weight
	public float distanceWeight;
	public float costWeight;

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
			float averageDurationByDistance, float averageGasConsume, int maxGasDeposit, // refueling calculations
			int maxTravelTime, // resting calculations @maxTravelTime in minutes
			float distanceWeight, float costWeight){ // algorithm restrictions weight

		this.nextGasValue = initialGasVale;
		this.nextTravelTime = initialTimeTravelValue;

		this.averageDurationByDistance = averageDurationByDistance;
		this.averageGasConsume = averageGasConsume;
		this.maxGasDeposit = maxGasDeposit;
		this.maxTravelTime = maxTravelTime;

		this.distanceWeight = distanceWeight;
		this.costWeight = costWeight;

		// assign values for vars
		this.varDistanceWeight = distanceWeight;
		this.varCostWeight = costWeight;

		this.varRefuelWeight = 0;
		this.varRestWeight = 0;
	}

	public void setNewWeights(int gasValue, int travelTimeValue, ArrayList<RestrictionType> restrictionList){

		// if no fuel or rest restrictions are to be taken into account
		if(!restrictionList.contains(RestrictionType.REFUEL) && !restrictionList.contains(RestrictionType.REST)){
			varDistanceWeight = distanceWeight;
			varCostWeight = costWeight;

			varRefuelWeight = 0;
			varRestWeight = 0;

			return;
		}
		
		if(restrictionList.contains(RestrictionType.REFUEL) && restrictionList.contains(RestrictionType.REST)){
			float refuelPercentage = gasValue / maxGasDeposit;
			float newRefuelWeight = (float) getFuelWeightDiff(refuelPercentage);

			float timePercentage = travelTimeValue / maxTravelTime;
			float newRestWeight = (float) getRestWeightDiff(timePercentage);

			float restPercentage = 1 - Math.max(newRefuelWeight, newRestWeight);

			varDistanceWeight = distanceWeight * restPercentage;
			varCostWeight = costWeight * restPercentage;

			varRefuelWeight = newRefuelWeight;
			varRestWeight = newRestWeight;
		}

		if(restrictionList.contains(RestrictionType.REFUEL)){
			float percentage = 1 - (float) gasValue / maxGasDeposit;
			float newRefuelWeight = (float) getFuelWeightDiff(percentage);
			varRefuelWeight = newRefuelWeight;

			float restPercentage = 1 - newRefuelWeight;

			varDistanceWeight = distanceWeight * restPercentage;
			varCostWeight = costWeight * restPercentage;

			varRestWeight = 0;

			return;
		}

		if(restrictionList.contains(RestrictionType.REST)){
			float percentage = 1 - (float) travelTimeValue / maxTravelTime;
			float newRestWeight = (float) getRestWeightDiff(percentage);
			varRestWeight = newRestWeight;

			float restPercentage = 1 - newRestWeight;

			varDistanceWeight = distanceWeight * restPercentage;
			varCostWeight = costWeight * restPercentage;

			varRefuelWeight = 0;

			return;
		}
	}

	public static double getFuelWeightDiff(float percentage){
		double temp = percentage - 0.2;
		double result = 4.77 * Math.exp(-1 / (temp * temp));
		return result;
	}

	public static double getRestWeightDiff(float percentage){
		double temp = 1.5 * percentage;
		double result = Math.exp(-9 / (Math.pow(temp, 15)));
		return result;
	}
}
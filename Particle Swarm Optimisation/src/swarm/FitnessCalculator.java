package swarm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public interface FitnessCalculator extends Serializable {
	
	public void setPersonalBest(double[][] personalBest);
	
	public double[][] getPersonalBest();
	
	public Map<Integer, Double> getFitness();
	
	public void calculateFitness();
	
	public int calculateGlobalBest();
	
	public void setPositions(double[][] positions);
	
	public void setFitness(Map<Integer, Double> fitness);
	
	public Function getObjectiveFunction();
	
	public void setObjectiveFunction(Function objectiveFunction);
	
	public void initialCalculateFitness();
}

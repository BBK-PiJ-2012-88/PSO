package swarm;

import java.io.Serializable;
import java.util.HashMap;

public interface FitnessCalculator extends Serializable {
	
	public void setPersonalBest(double[][] personalBest);
	
	public double[][] getPersonalBest();
	
	public HashMap<Integer, Double> getFitness();
	
	public void calculateFitness();
	
	public int calculateGlobalBest();
	
	public void setPositions(double[][] positions);
	
	public void setFitness(HashMap<Integer, Double> fitness);
	
	public Function getObjectiveFunction();
	
	public void setObjectiveFunction(Function objectiveFunction);
	
	public void initialCalculateFitness();
}

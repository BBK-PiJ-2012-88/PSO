package swarm;

import java.io.Serializable;

public interface GeneticOperator extends Serializable {
	
	public double[][] getPositions();
	
	public void setPositions(double[][] positions);
	
	public void performGeneticOperations();
	
	public Function getObjectiveFunction();
	
	public void setObjectiveFunction(Function objectiveFunction);
	
	public double getMutationProbability();
	
	public void setMutationProbability(double mutationProbability);
	
}

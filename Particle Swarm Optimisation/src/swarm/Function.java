package swarm;

import java.io.Serializable;
import java.util.Vector;

public interface Function extends Serializable{
	
	/*
	 * Returns the number of variables in the objective function
	 */
	int getVariables();
	
	/*
	 * Returns the fitness of a candidate solution
	 */
	double CalculateFitness(double[] candidateSolution);
	
	/*
	 * Returns the Fitness of a candidate solution 
	 */
	double CalculateFitness(Vector<Double> candidateSolution);
}

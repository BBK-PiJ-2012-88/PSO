package swarm;

public interface Function {
	
	/*
	 * Returns the number of variables in the objective function
	 */
	int getVariables();
	
	/*
	 * Returns the fitness of a candidate solution
	 */
	double CalculateFitness(double[] candidateSolution);
	//bla
}

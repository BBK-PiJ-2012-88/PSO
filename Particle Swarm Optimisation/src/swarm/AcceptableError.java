package swarm;
/**
 * This class determines when a solution to the Function has been found with an acceptable degree of error from
 * some solution, possibly the optimum.
 * <p>
 * The degree of error and the acceptable solution are both user defined and problem specific.
 * 
 * @author williamhogarth
 * @version 1
 */
public class AcceptableError implements HaltingCriteria {
	
	/**
	 * User defined acceptable degree of fitness. 
	 */
	private double solution;
	
	/**
	 * The maximum acceptable distance of the fitness of the global best solution found by the swarm 
	 * from the user defined acceptable solution.
	 */
	private double error;
	
	/**
	 * The fitness of the best solution found by the swarm so far
	 */
	private double gBest;
	
	/**
	 * 
	 * @param solution an acceptable solution to the function
	 * @param error the maximum acceptable error
	 */
	public AcceptableError(double solution, double error){
		this.solution = solution;
		this.error = error;
	}

	/**
	 * Returns the user defined acceptable solution
	 * 
	 * @return solution
	 */
	public double getSolution() {
		return solution;
	}

	/**
	 * Set the acceptable solution
	 * 
	 * @param solution
	 * @return void
	 */
	public void setSolution(double solution) {
		this.solution = solution;
	}

	/**
	 * Get the acceptable degree of error
	 * 
	 * @return error
	 */
	public double getError() {
		return error;
	}
	
	/**
	 * Set the acceptable degree of error
	 * 
	 * @param error
	 * @return void
	 */
	public void setError(double error) {
		this.error = error;
	}

	/**
	 * Updates the best solution found by the swarm. The iteration data is not used.
	 * 
	 * @param gbestFitness The fitness of the best solution found by the swarm
	 * @param iteration
	 */
	@Override
	public void updateData(double gbestFitness, int iteration) {
		this.gBest = gbestFitness;
	}

	/**
	 * Determines whether or not gbest falls within the acceptable range of solutions.
	 * <p>
	 * Gbest falls within the acceptable range if it is greater than or equal to solution subtract the absolute 
	 * value of error and less than or equal to solution add the absolute value of error.
	 * 
	 * @return true if gbest is in the acceptable range, else false.
	 */
	@Override
	public boolean halt() {
		if(gBest >= solution - Math.abs(error) && gBest <= solution + Math.abs(error)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @return the String representation of this object
	 */
	@Override 
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("AcceptableError, ");
		buff.append("solution = " + solution);
		buff.append(", error = " + error);
		return buff.toString();
	}
}

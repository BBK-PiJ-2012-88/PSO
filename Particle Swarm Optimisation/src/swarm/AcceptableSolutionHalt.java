package swarm;
/**
 * The class determines whether or not the best solution found by the swarm thus far is better than a user defined 
 * acceptable solution to the function being optimised.
 * 
 * @author williamhogarth
 * @version 1
 */
public class AcceptableSolutionHalt implements HaltingCriteria {
	
	/**
	 * Boolean value indicating whether the problem is a maximisation problem or a minimisation problem.
	 */
	private boolean maximum = false;
	
	/**
	 * The user defined acceptable solution to the function being optimised.
	 */
	private double acceptableSolution;
	
	/**
	 * The fitness value of the best solution found by the swarm thus far
	 */
	private double gBest;
	
	/**
	 * The default value for maximum in this case is false. The acceptable solution will need to be set or a
	 * NullPointerException will be thrown.
	 */
	public AcceptableSolutionHalt(){}
	
	/**
	 * The acceptable solution will need to be set or a NullPointerException will be thrown.
	 * 
	 * @param maximum the value of maximum is set to this.
	 */
	public AcceptableSolutionHalt(boolean maximum){
		this.maximum = maximum;
	}
	
	/**
	 * Maximum will be set to its default value, false
	 * 
	 * @param acceptableSolution an acceptable solution to the function being optimised.
	 */
	public AcceptableSolutionHalt(double acceptableSolution){
		this.acceptableSolution = acceptableSolution;
	}
	
	/**
	 * 
	 * @param maximum indicating if this is a minimisation or maximisation problem
	 * @param acceptableSolution an acceptable solution to the function being optimised.
	 */
	public AcceptableSolutionHalt(boolean maximum, double acceptableSolution){
		this.maximum = maximum;
		this.acceptableSolution = acceptableSolution;
	}
	
	/**
	 * Returns the boolean value of maximum
	 * 
	 * @return maximum
	 */
	public boolean isMaximum() {
		return maximum;
	}

	/**
	 * Sets the boolean value of maximum
	 * 
	 * @param maximum
	 */
	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
	}

	/**
	 * Returns the acceptable solution to the problem
	 * 
	 * @return acceptableSolution
	 */
	public double getAcceptableSolution() {
		return acceptableSolution;
	}
	
	/**
	 * Sets the acceptable solution
	 * 
	 * @param acceptableSolution
	 */
	public void setAcceptableSolution(double acceptableSolution) {
		this.acceptableSolution = acceptableSolution;
	}
	
	/**
	 * Updates the fitness value of the best solution found thus far. The iteration data is not used
	 * 
	 * @param gBestFitness
	 * @param iteration
	 */
	@Override
	public void updateData(double gbestFitness, int iteration) {
		gBest = gbestFitness;
		
	}

	/**
	 * Returns true if an acceptable solution has been found, false otherwise.
	 * <p>
	 * If the problem is a maximisation problem the method returns true if the fitness of the best solution
	 * found thus far is greater than or equal to the acceptable solution, else it returns false. If the problem is
	 * a minimisation problem the method returns true if the fitness of the the best solution found thus far is less
	 * than or equal to the acceptable solution.
	 * 
	 * @return true if gbest is acceptbale, false otherwise
	 */
	@Override
	public boolean halt() {
		if(maximum){
			if(gBest >= acceptableSolution){
				return true;
			}else{
				return false;
			}
		}else{
			if(gBest <= acceptableSolution){
				return true;
			}else{
				return false;
			}
		}
	}
	
	/**
	 * Returns a string representation of the object
	 * 
	 * @return string
	 */
	@Override
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("AcceptableSolutionHalt, ");
		buff.append("acceptable solution = " + acceptableSolution);
		buff.append(", maximum = " + maximum);
		return buff.toString();
	}
}

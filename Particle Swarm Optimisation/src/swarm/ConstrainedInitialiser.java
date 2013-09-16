package swarm;

/**
 * Initiates particles within the solution space. The solution space can be restricted.
 * 
 * @author williamhogarth
 *
 */
public interface ConstrainedInitialiser extends Initialiser {	
	
	/**
	 * 
	 * @return double[], the upper limit of the feasible solution space
	 */
	public double[] getMax();
	
	/**
	 * 
	 * @param max, the upper limit of the feasible solution space
	 */
	public void setMax(double[] max);
	
	/**
	 * 
	 * @return double[], the lower limit of the feasible solution space
	 */
	public double[] getMin();
	
	/**
	 * 
	 * @param min, the lower limit of the feasible solution space
	 */
	public void setMin(double[] min);
	
	/**
	 * Initiates the particle matrices so that the particles are within the feasible solution space.
	 * 
	 * @param objectiveFunction
	 * @param numberOfParticles
	 */
	public void constrainedInitialiseMatrices(Function objectiveFunction, int numberOfParticles);
}

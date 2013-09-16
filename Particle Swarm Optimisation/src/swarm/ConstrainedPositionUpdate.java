package swarm;
/**
 * Updates the particles' positions, and is able to limit them to the feasible search space.
 * 
 * @author williamhogarth
 *
 */
public interface ConstrainedPositionUpdate extends PositionUpdate {
	
	/**
	 * boolean indicating whether or particle positions need to be restricted to a feasible solution space
	 * 
	 * @return constraints
	 */
	public boolean isConstraints();
	
	/**
	 * Set the boolean constraints, indicating whether the particle positions need to be restricted to a feasible
	 * solution space
	 * 
	 * @param constraints
	 */
	public void setConstraints(boolean constraints);
	
	/**
	 * Set the constrainer. This limits particle positions to the feasible solution space
	 * 
	 * @param constrainer
	 */
	public void setConstrainer(Constrainer constrainer);
	
	/**
	 * The ConstrainedPositionUpdate's Constrainer, that restricts particle positions to the feasible
	 * solution space
	 * 
	 * @return Constrainer
	 */
	public Constrainer getConstrainer();
	
	/**
	 * Upper limit of the feasible solution space
	 * 
	 * @return double[] max
	 */
	public double[] getMaximum();
	
	/**
	 * Set the upper limit of the feasible solution space
	 * 
	 * @param maximum
	 */
	public void setMaximum(double[] maximum);
	
	/**
	 * The lower limit of the feasible solution space
	 * 
	 * @return double[] min
	 */
	public double[] getMinimum();
	
	/**
	 * Set the lower limit of the feasible solution space
	 * 
	 * @param minimum
	 */
	public void setMinimum(double[] minimum);
}

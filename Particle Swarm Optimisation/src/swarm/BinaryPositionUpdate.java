package swarm;

import java.util.Random;
/**
 * Updates the positions of the particles in binary space
 * <p>
 * The positions of the particles are randomly updated according to the following for particle i in
 * dimension j, if sig(vij(t)) > random(), xij(t) = 1, else xij(t) = 0. vij(t) is the particle's velocity 
 * at time step t, having been normalised using the sigmoid function (sig(x) = 1 / 1 + e ^ -x)
 * @author williamhogarth
 *
 */
public class BinaryPositionUpdate implements ConstrainedPositionUpdate {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4165216294679962913L;
	
	/**
	 * The particle's positions
	 */
	private double[][] positions;
	
	/**
	 * The particle's velocities
	 */
	private double[][] velocities;
	
	/**
	 * The sigmoid function used to normalise the velocities of the particles
	 */
	private SigmoidFunction sigmoidFunction = new SigmoidFunction();
	
	/**
	 * The constrainer used to restrict the positions of the particles to the feasible solution space
	 * if it is a constrained optimisation problem.
	 * 
	 * {@link BinaryConstrainer}
	 */
	private Constrainer constrainer = new BinaryConstrainer();
	
	/**
	 * boolean indication if it is a constrained optimisation process
	 */
	private boolean constraints = false;
	
	/**
	 * @return boolean constraints
	 */
	public boolean isConstraints() {
		return constraints;
	}
	
	/**
	 * @param boolean constraints
	 */
	public void setConstraints(boolean constraints) {
		this.constraints = constraints;
	}
	
	/**
	 * @return Constrainer
	 */
	public Constrainer getConstrainer() {
		return constrainer;
	}
	
	/**
	 * @param Constrainer constrainer
	 */
	public void setConstrainer(Constrainer constrainer){
		this.constrainer = constrainer;
	}
	
	/**
	 * @param double[][] positions, set the particle positions
	 */
	@Override
	public void setPositions(double[][] positions) {
		this.positions = positions;

	}
	
	/**
	 * @param double[][] velocities, set the particle velocties
	 */
	@Override
	public void setVelocities(double[][] velocities) {
		this.velocities = velocities;

	}
	
	/**
	 * @return double[][] positions, get the particle positions
	 */
	@Override
	public double[][] getPositions() {
		return positions;
	}
	
	/**
	 * @return double[][] velocities, get the particle velocities
	 */
	@Override
	public double[][] getVelocities() {
		return velocities;
	}
	/**
	 * Updates the particle positions.
	 * <p>
	 * For each position in the positions matrix, if the corresponding normalised velocity is greater than 
	 * random() the position = 1, else position = 0. The velocities of the particles aren't altered by the 
	 * normalisation process.
	 * <p>
	 * If the feasible solution space is constrained the positions of the particles are limited to this 
	 * space by the Constrainer.
	 */
	@Override
	public void updatePositions() {
		Random random = new Random();
		for(int i = 0; i < positions.length; i++){
			for(int k = 0; k < positions[i].length; k++){
				if(sigmoidFunction.normalise(velocities[i][k]) > random.nextDouble()){
					positions[i][k] = 1;
				}else{
					positions[i][k] = 0;
				}
			}
		}
		if(constraints){
			constrainer.setPositions(positions);
			constrainer.constrain();
			positions = constrainer.getPositions();
		}
	}
	
	/**
	 * Returns the array of values setting the upper limit of the feasible solution space. These are stored in
	 * the constrainer.
	 * 
	 * @return double[] max 
	 */
	@Override
	public double[] getMaximum() {
		return getConstrainer().getMaximum();
	}
	
	/**
	 * Set the upper limit of the feasible solution space. This is stored in the constrainer
	 * 
	 * @param double[] max
	 */
	@Override
	public void setMaximum(double[] maximum) {
		getConstrainer().setMaximum(maximum);
		
	}
	
	/**
	 * Get the lower limit of the feasible solution space. This is stored in the constrainer.
	 * 
	 * @return double[] min
	 */
	@Override
	public double[] getMinimum() {
		return getConstrainer().getMinimum();
	}
	
	/**
	 * Set the lower limit of the feasible solution space. This is stored in the constrainer.
	 * 
	 * @param double[] min
	 */
	@Override
	public void setMinimum(double[] minimum) {
		getConstrainer().setMinimum(minimum);
		
	}

}

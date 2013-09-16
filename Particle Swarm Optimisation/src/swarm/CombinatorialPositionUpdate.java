package swarm;

public class CombinatorialPositionUpdate implements PositionUpdate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1119134068866688082L;
	
	/**
	 * The particles' current positions
	 */
	private double[][] positions;
	
	/**
	 * The particles' velocities
	 */
	private double[][] velocities;
	
	/**
	 * The best solution found by each of the particles
	 */
	private double[][] personalBest;
	
	/**
	 * A value used to compare the equality of doubles
	 */
	private double epsilon = 0.00001;
	
	/**
	 * Neighbourhood used to calculate neighbourhood best
	 */
	private Neighbourhood neighbourhood;
	
	/**
	 * 
	 * @return Neighbourhood
	 */
	public Neighbourhood getNeighbourhood() {
		return neighbourhood;
	}
	
	/**
	 * 
	 * @param neighbourhood
	 */
	public void setNeighbourhood(Neighbourhood neighbourhood) {
		this.neighbourhood = neighbourhood;
	}
	
	/**
	 * 
	 * @return double[][], the particles' personal bests
	 */
	public double[][] getPersonalBest() {
		return personalBest;
	}
	
	/**
	 * 
	 * @param personalBest, the particles' personal best
	 */
	public void setPersonalBest(double[][] personalBest) {
		this.personalBest = personalBest;
	}
	
	/**
	 * @param double[][], the particles' positions
	 */
	@Override
	public void setPositions(double[][] positions) {
		this.positions = positions;
	}
	
	/**
	 * @param double[][], the particles' velocities
	 */
	@Override
	public void setVelocities(double[][] velocities) {
		this.velocities = velocities;
	}
	
	/**
	 * @return double[][], the particles' positions
	 */
	@Override
	public double[][] getPositions() {
		return positions;
	}
	
	/**
	 * @param double[][], the particles' velocities
	 */
	@Override
	public double[][] getVelocities() {
		return velocities;
	}
	
	/**
	 * Iterates through the particle velocities. If the cognitive velocity (contained in 0, numberOfParticles
	 * section of the matrix) is equal to 1 for particle i at dimension j the element at xij is swapped with
	 * the element in xij corresponding to the element in its personalBest at position j. The cognitve velocity
	 * is set to 0 to prevent cyclical behaviour. If the cognitive velocity < 1 then the element at xij is swapped
	 * with the element in xij corresponding to the element at j in the particle's neighbourhood best. This is determined
	 * by re-calculating the particle's neighbourhood best.
	 * 
	 */
	@Override
	public void updatePositions() {
		int rows = velocities.length / 2;
		for(int i = 0; i < rows; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(Math.abs(velocities[i][k] - 1.0) <= epsilon){
					double exchange = personalBest[i][k];
					for(int n = 0; n < positions[i].length; n++){
						if(Math.abs(positions[i][n] - exchange) <= epsilon){
							double temp = positions[i][k];
							positions[i][k] = positions[i][n];
							positions[i][n] = temp;
						}
					}
					velocities[i][k] = 0;
				}else{
					int index = neighbourhood.neighbourhoodBest(i);
					double exchange = personalBest[index][k];
					for(int n = 0; n < positions[i].length; n++){
						if(Math.abs(positions[i][n] - exchange) <= epsilon){
							double temp = positions[i][k];
							positions[i][k] = positions[i][n];
							positions[i][n] = temp;
						}
					}
				}
			}
		}
	}

}

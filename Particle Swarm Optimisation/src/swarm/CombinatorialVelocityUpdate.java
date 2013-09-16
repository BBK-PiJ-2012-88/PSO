package swarm;

/**
 * Updates the velocities of the particles in the combinatorial swarm.
 * <p>
 * Particle velocities are limited to the range [0,1]. Each particle has a social and cognitive velocity,
 * and either the social velocity = 1 or the cognitive velocity = 1.
 * @author williamhogarth
 *
 */
public class CombinatorialVelocityUpdate implements
		VelocityUpdate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2251199231549873146L;

	private double[][] velocities;
	
	private double[][] positions;
	
	private double[][] personalBest;
	
	private Neighbourhood neighbourhood = new TheRing();
	
	private double socialConstant = 2.025;
	
	private double cognitiveConstant = 2.025;

	private double weight = 0.8;
	
	private double epsilon = 0.00001;
	
	private boolean maximum = false;
	
	@Override
	public boolean isMaximum() {
		return maximum;
	}

	@Override
	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
		neighbourhood.setMaximum(maximum);
	}

	@Override
	public double[][] getPosition() {
		return positions;
	}

	@Override
	public void setPosition(double[][] positions) {
		this.positions = positions;
	}

	@Override
	public double[][] getPersonalBest() {
		return personalBest;
	}

	@Override
	public void setPersonalBest(double[][] personalBest) {
		this.personalBest = personalBest;
	}

	@Override
	public Neighbourhood getNeighbourhood() {
		return neighbourhood;
	}

	@Override
	public void setNeighbourhood(Neighbourhood neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * Control method that updates the particles' velocities.
	 * <p>
	 * @return double[][], the updated particle velocities.
	 */
	@Override
	public double[][] updateVelocities(){
		getNeighbourhood().setMaximum(maximum);
		updateSocialVelocity();
		updateCognitiveVelocity();
		normaliseVelocities();
		return getVelocities();
	}
	/**
	 * Restricts the particles' velocities to the range [0,1].
	 * <p>
	 * This is done by dividing the social and cognitive velocity by max{cognitive velocity, social velocity}
	 */
	private void normaliseVelocities() {
		int rows = velocities.length / 2;
		for(int i = 0; i < rows; i++){
			for(int k = 0; k < velocities[i].length; k++){
				double denominator = velocities[i + rows][k];
				if(velocities[i][k] > velocities[i + rows][k]){
					denominator = velocities[i][k];
				}
				velocities[i][k] = velocities[i][k] / denominator;
				velocities[i + rows][k] = velocities[i + rows][k] / denominator;
			}
		}
		
	}
	
	/**
	 * Updates the social velocity of the particles.
	 * <p>
	 * The index from number of particles (i.e. velocities.length / 2) to velocities.length - 1 are those of 
	 * the social velocities of the particles. These are updated by adding the normalised deviation distance
	 * metric of the particles current position from its neighbourhood position to the particle's previous velocity
	 */
	private void updateSocialVelocity() {
		int rows = velocities.length / 2;
		for(int i = rows; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				int socialBest = neighbourhood.neighbourhoodBest(i - rows);
				double normDevDist = calculateNormalisedDeviationDistance(k, positions[i - rows][k], personalBest[socialBest]);
				velocities[i][k] = weight * velocities[i][k] + socialConstant * normDevDist;
			}
		}
	}
	/**
	 * Updates the cognitive velocity of the particles.
	 * <p>
	 * The index from 0 to number of particles (i.e. velocities.length / 2) are those of 
	 * the cognitive velocities of the particles. These are updated by adding the normalised deviation distance
	 * metric of the particles current position from its best position to the particle's previous velocity
	 */
	private void updateCognitiveVelocity() {
		for(int i = 0; i < velocities.length / 2; i++){
			for(int k = 0; k < velocities[i].length; k++){
				double normDevDist = calculateNormalisedDeviationDistance(k, positions[i][k], personalBest[i]);
				velocities[i][k] = weight * velocities[i][k] + cognitiveConstant * normDevDist;
			}
		}
	}
	
	/**
	 * Calculates the normalised deviation distance metric.
	 * <p>
	 * The deviation distance between two arrays a1[i] = e,  is i-k where a2[k] = e. The distance is normalised
	 * by dividing the deviation distance by the length of a2 or a1. This method takes the element, its index in one array
	 * and another array (personal or neighbourhood best) and searches for the element in the array and then
	 * caluclates the normalised deviation distance metric according to the above formula.
	 * 
	 * @param elementIndex, index of element in particle current position
	 * @param element, element at current position
	 * @param array, neighbourhood or personal best solution 
	 * @return double, normalised devation distance metric
	 */
	private double calculateNormalisedDeviationDistance(int elementIndex, double element, double[] array){
		double distance = 0;
		for(int i = 0; i < array.length; i++){
			if(Math.abs(element - array[i]) < epsilon){
				distance = elementIndex - i;
			}
		}
		return Math.abs(distance)/(array.length - 1);
	}
	
	public double getSocialConstant() {
		return socialConstant;
	}

	public void setSocialConstant(double socialConstant) {
		this.socialConstant = socialConstant;
	}

	public double getCognitiveConstant() {
		return cognitiveConstant;
	}

	public void setCognitiveConstant(double cognitiveConstant) {
		this.cognitiveConstant = cognitiveConstant;
	}

	@Override
	public double[][] getVelocities() {
		return velocities;
	}

	@Override
	public void setVelocities(double[][] velocities) {
		this.velocities = velocities;
	}
	
	@Override
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append(this.getClass().toString() + ", ");
		buff.append("weight: " + weight + ", ");
		buff.append("neighbourhood: " + neighbourhood.getClass().toString());
		return buff.toString();
	}
		
}



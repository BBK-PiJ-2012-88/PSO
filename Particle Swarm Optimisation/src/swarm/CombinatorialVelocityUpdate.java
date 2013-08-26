package swarm;


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
	
	//need to add constants to this. Wonder if this will make a difference? 
	
	private double socialConstant = 2;
	
	private double cognitiveConstant = 2;

	private double weight = 1;
	
	private double epsilon = 0.00001;
	
	private boolean maximum = false;
	
	@Override
	public boolean isMaximum() {
		return maximum;
	}

	@Override
	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
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

	@Override
	public double[][] updateVelocities(){
		getNeighbourhood().setMaximum(maximum);
		updateSocialVelocity();
		updateCognitiveVelocity();
		normaliseVelocities();
		return getVelocities();
	}

	private void normaliseVelocities() {
		int rows = velocities.length / 2;
		for(int i = 0; i < rows; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(velocities[i][k] + velocities[i + rows][k] == 0){
					velocities[i][k] = Math.random();
					velocities[i + rows][k] = Math.random();
				}
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
	 * The social velocity is in the 'top' part of the matrix; 
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

	private void updateCognitiveVelocity() {
		for(int i = 0; i < velocities.length / 2; i++){
			for(int k = 0; k < velocities[i].length; k++){
				double normDevDist = calculateNormalisedDeviationDistance(k, positions[i][k], personalBest[i]);
				velocities[i][k] = weight * velocities[i][k] + cognitiveConstant * normDevDist;
			}
		}
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

	private double calculateNormalisedDeviationDistance(int elementIndex, double element, double[] array){
		double distance = 0;
		for(int i = 0; i < array.length; i++){
			if(Math.abs(element - array[i]) < epsilon){
				distance = elementIndex - i;
			}
		}
		return Math.abs(distance)/(array.length - 1);
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



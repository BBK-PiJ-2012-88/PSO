package swarm;


public class CombinatorialVelocityUpdate implements
		VelocityUpdate {

	private double[][] velocities;
	
	private double[][] positions;
	
	private double[][] personalBest;
	
	private Neighbourhood neighbourhood = new CuboidLattice();

	private double weight;
	
	public double[][] getPosition() {
		return positions;
	}

	public void setPosition(double[][] positions) {
		this.positions = positions;
	}

	public double[][] getPersonalBest() {
		return personalBest;
	}

	public void setPersonalBest(double[][] personalBest) {
		this.personalBest = personalBest;
	}

	public Neighbourhood getNeighbourhood() {
		return neighbourhood;
	}

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
		updateSocialVelocity();
		updateCognitiveVelocity();
		return getVelocities();
	}

	
	public void updateSocialVelocity() {
		for(int i = velocities.length / 2; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				int socialBest = neighbourhood.neighbourhoodBest(i);
				double normDevDist = calculateNormalisedDeviationDistance(k, positions[i][k], personalBest[socialBest]);
				velocities[i][k] = weight * velocities[i][k] + normDevDist;
			}
		}
	}

	public void updateCognitiveVelocity() {
		for(int i = 0; i < velocities.length / 2; i++){
			for(int k = 0; k < velocities[i].length; k++){
				double normDevDist = calculateNormalisedDeviationDistance(k, positions[i][k], personalBest[i]);
				velocities[i][k] = weight * velocities[i][k] + normDevDist;
			}
		}
	}
	
	private double calculateNormalisedDeviationDistance(int elementIndex, double element, double[] array){
		double distance = 0;
		for(int i = 0; i < array.length; i++){
			if(element == array[i]){
				distance = elementIndex - i;
			}
		}
		if(distance < 0){
			distance = distance * -1;
		}
		return distance/array.length - 1;
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



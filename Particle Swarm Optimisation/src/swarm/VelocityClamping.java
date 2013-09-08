package swarm;


public class VelocityClamping implements VelocityUpdate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2172004309246432443L;

	private double[]maxVelocity;
	
	private double [][] velocities;
	
	private double [][] personalBest;
	
	private double [][] position; 
	
	private Neighbourhood neighbourhood = new TheRing();
	
	private double socialConstant = 2;
	
	private double cognitiveConstant = 2;
	
	private double inertiaWeight = 1;
	
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

	public VelocityClamping(double[] maxVelocity){
		this.maxVelocity = maxVelocity;
	}
	
	private void limitVelocity() {
		assert(velocities != null);
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(velocities[i][k] < 0 - maxVelocity[k]){
					velocities[i][k] = 0 - maxVelocity[k];
				}else if(velocities[i][k] > maxVelocity[k]){
					velocities[i][k] = maxVelocity[k];
				}
			}
		}
	}

	@Override
	public double[][] updateVelocities() {
		getNeighbourhood().setMaximum(maximum);
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				velocities[i][k] = velocities[i][k] * inertiaWeight;
			}
		}
		calculateVelocities();
		limitVelocity();
		return getVelocities();
	}

	private void calculateVelocities() {
		double r1 = Math.random();
		double r2 = Math.random();
		for(int i = 0; i < position.length; i++){
			double[]cognitive = calculateCognitiveVelocity(i, r1);
			double[]social = calculateSocialVelocity(i, r2);
			for(int k = 0; k < position[i].length; k++){
				velocities[i][k] = velocities[i][k] + cognitive[k] + social[k];
			}
		}
		
	}

	private double[] calculateSocialVelocity(int particle, double r2) {
		double[] socialVelocity = new double[position[particle].length];
		int nBest = neighbourhood.neighbourhoodBest(particle);
		for(int k = 0; k < socialVelocity.length; k++){
			socialVelocity[k] = socialConstant * r2 * (personalBest[nBest][k] - position[particle][k]);
		}
		return socialVelocity;
	}

	private double[] calculateCognitiveVelocity(int particle, double r1) {
		double[] cognitiveVelocity = new double[position[particle].length];
		for(int k = 0; k < cognitiveVelocity.length; k++){
			cognitiveVelocity[k] = cognitiveConstant * r1 * (personalBest[particle][k] - position[particle][k]);
		}
		return cognitiveVelocity;
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
	public double[][] getPersonalBest() {
		return personalBest;
	}


	@Override
	public void setPersonalBest(double[][] personalBest) {
		this.personalBest = personalBest;
		
	}


	@Override
	public double[][] getPosition() {
		return position;
	}


	@Override
	public void setPosition(double[][] position) {
		this.position = position;
		
	}


	@Override
	public Neighbourhood getNeighbourhood() {
		return neighbourhood;
	}


	@Override
	public void setNeighbourhood(Neighbourhood neighbourhood) {
		this.neighbourhood = neighbourhood;	
	}

	public double[] getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(double[] maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public double getInertiaWeight() {
		return inertiaWeight;
	}

	public double getSocialConstant() {
		return socialConstant;
	}

	public double getCognitiveConstant() {
		return cognitiveConstant;
	}

	public void setConstants(double cognitiveConstant, double socialConstant,
			double inertiaWeight) {
		setCognitiveConstant(cognitiveConstant);
		setSocialConstant(socialConstant);
		setInertiaWeight(inertiaWeight);
	}

	private void setInertiaWeight(double inertiaWeight) {
		this.inertiaWeight = inertiaWeight;
	}

	private void setSocialConstant(double socialConstant) {
		this.socialConstant = socialConstant;
	}

	public void setCognitiveConstant(double cognitiveConstant) {
		this.cognitiveConstant = cognitiveConstant;
	}
	
	@Override
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append(this.getClass().toString() + ", ");
		buff.append("social constant: " + socialConstant + ", ");
		buff.append("cognitive constant: " + cognitiveConstant + ", ");
		buff.append("inertia weight: " + inertiaWeight + ", ");
		buff.append("neighbourhood: " + neighbourhood.getClass().toString());
		return buff.toString();
	}
}
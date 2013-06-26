package swarm;

import java.util.HashMap;

public class VelocityClamping implements VelocityUpdate {

	private double[]maxVelocity;
	
	private double [][] velocities;
	
	private double [][] personalBest;
	
	private double [][] position; 
	
	private Neighbourhood neighbourhood = new TheRing();
	
	private HashMap<Integer, Double> fitness;
	
	private double socialConstant = 2;
	
	private double cognitiveConstant = 2;
	
	private double inertiaWeight = 1;
	
	public VelocityClamping(double[] maxVelocity){
		this.maxVelocity = maxVelocity;
	}
	
	private void limitVelocity() {
		assert(velocities != null);
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(velocities[i][k] < 0 - maxVelocity[i]){
					velocities[i][k] = 0 - maxVelocity[i];
				}else if(velocities[i][k] > maxVelocity[i]){
					velocities[i][k] = maxVelocity[i];
				}
			}
		}

	}

	public double[][] updateVelocities() {
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
		neighbourhood.setSolutionFitness(fitness);
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
	public void updateData(double[][] personalBest, double[][] position,
			HashMap<Integer, Double> fitness) {
		this.personalBest = personalBest;
		this.position = position;
		this.fitness = fitness;
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


	@Override
	public HashMap<Integer, Double> getFitness() {
		return fitness;
	}


	@Override
	public void setFitness(HashMap<Integer, Double> fitness) {
		this.fitness = fitness;
		
	}


	@Override
	public double getSocialConstant() {
		return socialConstant;
	}


	@Override
	public double getCognitiveConstant() {
		return cognitiveConstant;
	}


	@Override
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

}
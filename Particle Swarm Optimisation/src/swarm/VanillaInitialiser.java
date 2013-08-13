package swarm;

public class VanillaInitialiser implements Initialiser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8213407787490957908L;

	private double[][] positions;
	
	private double[][] velocities;
	
	private double[][] personalBest;
	
	private double lowerLimit = 0;
	
	private double upperLimit = 1;
	
	public void initialiseMatrices(Function objectiveFunction, int numberOfParticles, double lowerLimit, double upperLimit){
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		initialiseMatrices(objectiveFunction, numberOfParticles);
	}
	
	@Override
	public void initialiseMatrices(Function objectiveFunction,
			int numberOfParticles) {
		assert lowerLimit > upperLimit;
		int columns = objectiveFunction.getVariables();
		positions = new double[numberOfParticles][columns];
		personalBest = new double[numberOfParticles][columns];
		velocities = new double[numberOfParticles][columns];
		double range = upperLimit - lowerLimit;
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < columns; k++){
				positions[i][k] = lowerLimit + (Math.random() * range);
				personalBest[i][k] = positions[i][k];
			}
		}
	}

	public double[][] getPersonalBest() {
		return personalBest;
	}

	public double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public double getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
	}

	@Override
	public double[][] getPositions() {
		return positions;
	}

	@Override
	public double[][] getVelocities() {
		return velocities;
	}

}

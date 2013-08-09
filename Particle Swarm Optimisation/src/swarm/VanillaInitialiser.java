package swarm;

public class VanillaInitialiser implements Initialiser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8213407787490957908L;

	private double[][] positions;
	
	private double[][] velocities;
	
	private double lowerLimit = 0;
	
	private double upperLimit = 1;
	
	@Override
	public void initialiseMatrices(Function objectiveFunction,
			int numberOfParticles) {
		int columns = objectiveFunction.getVariables();
		positions = new double[numberOfParticles][columns];
		velocities = positions.clone();
		double random = upperLimit - lowerLimit;
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < objectiveFunction.getVariables(); k++){
				double d = lowerLimit + (Math.random() * random);
				positions[i][k] = d;
				velocities[i][k] = 0;
			}
		}
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

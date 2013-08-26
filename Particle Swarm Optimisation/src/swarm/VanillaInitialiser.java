package swarm;

import java.util.Arrays;

public class VanillaInitialiser implements Initialiser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8213407787490957908L;

	private double[][] positions;
	
	private double[][] velocities;
	
	private double[] max;
	
	private double[] min;
	
	private double[][] personalBest;
	
	public double[] getMax() {
		return max;
	}

	public void setMax(double[] max) {
		this.max = max;
	}

	public double[] getMin() {
		return min;
	}

	public void setMin(double[] min) {
		this.min = min;
	}

	@Override
	public void initialiseMatrices(Function objectiveFunction,
			int numberOfParticles) {
		int columns = objectiveFunction.getVariables();
		positions = new double[numberOfParticles][columns];
		personalBest = new double[numberOfParticles][columns];
		velocities = new double[numberOfParticles][columns];
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < columns; k++){
				positions[i][k] = min[k] + (Math.random() * (max[k] - min[k]));
				personalBest[i][k] = positions[i][k];
			}
		}
	}

	public double[][] getPersonalBest() {
		return personalBest;
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

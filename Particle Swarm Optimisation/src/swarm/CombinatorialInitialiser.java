package swarm;

import java.io.Serializable;
import java.util.Random;

public class CombinatorialInitialiser implements Initialiser, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7706760673537245498L;
	
	private double[][] positions;
	
	private double[][] velocities;
	
	private double[][] personalBest;
	
	@Override
	public void initialiseMatrices(Function objectiveFunction,
			int numberOfParticles) {
		int columns = objectiveFunction.getVariables();
		Random rand = new Random();
		positions = new double[numberOfParticles][columns];
		personalBest = new double[numberOfParticles][columns];
		velocities = new double[numberOfParticles * 2][columns];
		for(int i = 0; i < numberOfParticles * 2; i++){
			for(int k = 0; k < columns; k++){
				velocities[i][k] = rand.nextDouble();
				if(i < numberOfParticles){
					positions[i][k] = k + 1;
				}
			}
		}
		for(int i = 0; i < 5; i++){
			shuffleParticles(positions);
		}
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < positions[i].length; k++){
				personalBest[i][k] = positions[i][k];
			}
		}
	}
	
	public void shuffleParticles(double[][]particles) {
		Random rand = new Random();
		int numberOfParticles = particles.length;
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < particles[i].length; k++){
				int index = i + (int)(rand.nextDouble() * ((particles.length - i) + 1));
				double temp = particles[i][k];
				particles[i][k] = particles[i][index];
				particles[i][index] = temp;
			}
		}
	}

	@Override
	public double[][] getPositions() {
		return positions;
	}

	@Override
	public double[][] getVelocities() {
		return velocities;
	}

	@Override
	public double[][] getPersonalBest() {
		return personalBest;
	}

}

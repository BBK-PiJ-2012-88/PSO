package swarm;

import java.io.Serializable;
import java.util.Random;

/**
 * Initialises the particles' velocity, position and personal best matrices.
 * 
 * @author williamhogarth
 *
 */
public class CombinatorialInitialiser implements Initialiser, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7706760673537245498L;
	
	/**
	 * The particles' positions
	 */
	private double[][] positions;
	
	/**
	 * The particle's velocities
	 */
	private double[][] velocities;
	
	/**
	 * The best positions found by the particles
	 */
	private double[][] personalBest;
	
	/**
	 * Intialises the positions, velocities and personal best of the particles.
	 * <p>
	 * The number of columns is set using the objectiveFunction to get the number of variables. The number of 
	 * particles is used to set the number of rows. As a distinct social and cognitive velocity is asscociated
	 * with each particle the velocities matrix has double the number of rows as the positions and personalBest
	 * matrices. The velocities are initialised to random numbers in the range [0,1]. The nodes in the position
	 * matrix are set to their column index + 1. These are then shuffled using the method shuffle(double[][] positions)
	 * and the personalBest are then set to the shuffled positions.
	 * <p>
	 * The particles are shuffled 5 times as this is approximately the optimum number of shuffles needed to
	 * generate randomness.
	 * 
	 * @param Function objectiveFunction
	 * @param int numberOfParticles
	 */
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
	
	/**
	 * Shuffles the particles using Knuth's shuffle
	 * 
	 * @param particles
	 */
	public void shuffleParticles(double[][]particles) {
		Random rand = new Random();
		int numberOfParticles = particles.length;
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < particles[i].length; k++){
				int index = k + (int)(rand.nextDouble() * (particles[i].length - k));
				double temp = particles[i][k];
				particles[i][k] = particles[i][index];
				particles[i][index] = temp;
			}
		}
	}

	/**
	 * @return double [][], the particles' positions
	 */
	@Override
	public double[][] getPositions() {
		return positions;
	}
	
	/**
	 * @return double[][], the particles' velocities
	 */
	@Override
	public double[][] getVelocities() {
		return velocities;
	}
	/**
	 * double[][], the particles' personal best
	 */
	@Override
	public double[][] getPersonalBest() {
		return personalBest;
	}

}

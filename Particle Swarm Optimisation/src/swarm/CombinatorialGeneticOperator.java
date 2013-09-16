package swarm;

import java.util.Random;
	/**
	 * The class acts as a mutation genetic operator on combinatorial particles. 
	 * 
	 * @author williamhogarth
	 *
	 */
public class CombinatorialGeneticOperator implements GeneticOperator {
	
	/**
	 * The positions of the particles
	 */
	private double[][] positions;
	
	/**
	 * The probability of the mutation operator being used
	 */
	private double mutationProbability = 0.1;
	
	/**
	 * This is not used but it is required by the interface GeneticOperator.
	 */
	private Function objectiveFunction;
	
	@Override
	public double[][] getPositions() {
		return positions;
	}

	@Override
	public void setPositions(double[][] positions) {
		this.positions = positions;
	}
	/**
	 * Stable interface method from the GeneticOperator interface. This method just calls the mutate()
	 * method.
	 */
	@Override
	public void performGeneticOperations() {
		mutate();
	}
	
	/**
	 * Iterates through the particle positions. If a random double in the range [0,1] is less than the mutation
	 * probability the swap() method is called. This swaps the value at the current position with a that at a
	 * randomly selected position within the particle.
	 */
	private void mutate() {
		Random rand = new Random();
		for(int i = 0; i < positions.length; i++){
			for(int k = 0; k < positions[i].length; k++){
				if(rand.nextDouble() < mutationProbability){
					swap(i,k, rand.nextInt(positions[i].length));
				}
			}
		}
	}
	
	/**
	 * Swaps the element at position k in particle i with the element at nextInt. It is possible that 
	 * k = nextInt.
	 * 
	 * @param i
	 * @param k
	 * @param nextInt
	 */
	private void swap(int i, int k, int nextInt) {
		for(int n = 0; n < positions[i].length; n++){
			if(Math.abs(positions[i][n] - nextInt) < 0.00001){
				double temp = positions[i][k];
				positions[i][k] = positions[i][n];
				positions[i][n] = temp;
			}
		}
		
	}
	/**
	 * @return double, the probability of mutation
	 */
	@Override
	public double getMutationProbability() {
		return mutationProbability;
	}
	
	/**
	 * @param double, the probability of mutation
	 */
	@Override
	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;

	}
	
	/**
	 * The function being optimised is not used.
	 * 
	 * @return Function objectiveFunction
	 */
	@Override
	public Function getObjectiveFunction() {
		return objectiveFunction;
	}
	
	/**
	 * The function being optimised is not used
	 * 
	 * @param Function 
	 */
	@Override
	public void setObjectiveFunction(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
		
	}


}

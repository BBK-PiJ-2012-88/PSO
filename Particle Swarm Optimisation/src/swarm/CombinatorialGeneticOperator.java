package swarm;

import java.util.Random;

public class CombinatorialGeneticOperator implements GeneticOperator {

	private double[][] positions;
	
	
	private double mutationProbability = 0.1;
	
	private Function objectiveFunction;
	
	@Override
	public double[][] getPositions() {
		return positions;
	}

	@Override
	public void setPositions(double[][] positions) {
		this.positions = positions;
	}

	@Override
	public void performGeneticOperations() {
		mutate();
	}
	
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

	private void swap(int i, int k, int nextInt) {
		for(int n = 0; n < positions[i].length; n++){
			if(Math.abs(positions[i][n] - nextInt) < 0.00001){
				double temp = positions[i][k];
				positions[i][k] = positions[i][n];
				positions[i][n] = temp;
			}
		}
		
	}

	@Override
	public Function getObjectiveFunction() {
		return objectiveFunction;
	}

	@Override
	public void setObjectiveFunction(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;

	}

	@Override
	public double getMutationProbability() {
		return mutationProbability;
	}

	@Override
	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;

	}


}

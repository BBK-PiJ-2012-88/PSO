package swarm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticOperatorImpl implements GeneticOperator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8604989241102534673L;

	private double[][] positions;
	
	private List<ParticleFitness> sortedFitness;
	
	private Function objectiveFunction;
	
	private boolean maximum = false;
	
	private double mutationProbability = 0.05;
	
	public GeneticOperatorImpl(){}
	
	public GeneticOperatorImpl(Function objectiveFunction){
		this.objectiveFunction = objectiveFunction;
	}
	
	public List<ParticleFitness> getSortedFitness() {
		return sortedFitness;
	}

	public void setSortedFitness(List<ParticleFitness> sortedFitness) {
		this.sortedFitness = sortedFitness;
	}

	@Override
	public Function getObjectiveFunction() {
		return objectiveFunction;
	}

	@Override
	public void setObjectiveFunction(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}

	public boolean isMaximum() {
		return maximum;
	}

	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
	}

	@Override
	public double getMutationProbability() {
		return mutationProbability;
	}

	@Override
	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	@Override
	public double[][] getPositions() {
		return positions;
	}

	@Override
	public void setPositions(double[][] positions) {
		this.positions = positions;
	}
	
	public void initiateSortedFitness(){
		sortedFitness = new ArrayList<ParticleFitness>();
		int numberOfParticles = positions.length;
		for(int i = 0; i < numberOfParticles; i++){
			ParticleFitness p = new ParticleFitness(i, objectiveFunction.CalculateFitness(positions[i]));
			sortedFitness.add(p);
		}
		Collections.sort(sortedFitness);
	}

	@Override
	public void performGeneticOperations() {
		assert objectiveFunction != null;
		initiateSortedFitness();
		crossover();
		mutate();
	}
	
	public void crossover(){
		Random rand = new Random();
		if(maximum){
			int best = sortedFitness.get(sortedFitness.size() - 1).getIndex();
			for(int i = 0; i < sortedFitness.size() / 2; i++){
				int crossoverPoint = rand.nextInt(objectiveFunction.getVariables());
				int particleIndex = sortedFitness.get(i).getIndex();
				for(int k = 0; k < crossoverPoint; k++){
					positions[particleIndex][k] = positions[best][k];
				}
			}
		}else{
			int best = sortedFitness.get(0).getIndex();
			for(int i = sortedFitness.size() - 1; i > sortedFitness.size() / 2 - 1; i--){
				int crossoverPoint = rand.nextInt(objectiveFunction.getVariables());
				int particleIndex = sortedFitness.get(i).getIndex();
				for(int k = 0; k < crossoverPoint; k++){
					positions[particleIndex][k] = positions[best][k];
				}
			}
		}
	}
	
	public void mutate(){
		for(int i = 0; i < positions.length; i++){
			for(int k = 0; k < positions[i].length; k++){
				if(Math.random() < mutationProbability){
					if(positions[i][k] < 1){
						positions[i][k] = 1;
					}else{
						positions[i][k] = 0;
					}
				}
			}
		}
	}
}

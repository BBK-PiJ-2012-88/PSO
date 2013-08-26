package swarm;

import java.util.LinkedHashMap;
import java.util.Map;

public class FitnessCalculatorImpl implements FitnessCalculator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7680498305433526800L;
	
	private double[][] personalBest;
	
	private Map<Integer, Double> fitness;
	
	private double[][] positions;
	
	private Function objectiveFunction;
	
	private boolean maximum = false;
	
	public FitnessCalculatorImpl(Function objectiveFunction, boolean maximum){
		this(objectiveFunction);
		this.maximum = maximum;
	}
	
	public boolean isMaximum() {
		return maximum;
	}

	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
	}
	
	public FitnessCalculatorImpl(Function objectiveFunction){
		this.objectiveFunction = objectiveFunction;
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
	public Map<Integer, Double> getFitness() {
		return fitness;
	}

	public double[][] getPositions() {
		return positions;
	}

	@Override
	public void calculateFitness() {
		assert objectiveFunction != null;
		int numberOfParticles = positions.length;
		for(int i = 0; i < numberOfParticles; i++){
			double currentfitness = objectiveFunction.CalculateFitness(positions[i]);
			if(maximum){
				if(currentfitness > fitness.get(i)){
					fitness.put(i, currentfitness);
					updatePersonalBest(i);
				}
			}else{
				if(currentfitness < fitness.get(i)){
					fitness.put(i, currentfitness);
					updatePersonalBest(i);
				}
			}
		}
	}

	private void updatePersonalBest(int row) {
		for(int k = 0; k < positions[row].length; k++){
			personalBest[row][k] = positions[row][k];
		}
	}
	
	@Override
	public void initialCalculateFitness(){
		fitness = new LinkedHashMap<Integer, Double>();
		int numberOfParticles = positions.length;
		for(int i = 0; i < numberOfParticles; i++){
			fitness.put(i, objectiveFunction.CalculateFitness(positions[i]));
		}
	}

	@Override
	public int calculateGlobalBest() {
		assert objectiveFunction != null;
		int best = 0;
		if(maximum){
			for(int i = 0; i < fitness.size(); i++){
				if(fitness.get(i) > fitness.get(best)){
					best = i;
				}
			}
		}else{
			for(int i = 0; i < fitness.size(); i++){
				if(fitness.get(i) < fitness.get(best)){
					best = i;
				}
			}
		}
		return best;
	}

	@Override
	public void setPositions(double[][] positions) {
		this.positions = positions;

	}

	@Override
	public void setFitness(Map<Integer, Double> fitness) {
		this.fitness = fitness;

	}

	@Override
	public double[][] getPersonalBest() {
		return personalBest;
	}

	@Override
	public void setPersonalBest(double[][] personalBest) {
		this.personalBest = personalBest;
	}

}

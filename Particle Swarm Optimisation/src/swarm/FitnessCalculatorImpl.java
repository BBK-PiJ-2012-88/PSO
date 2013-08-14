package swarm;

import java.util.HashMap;

public class FitnessCalculatorImpl implements FitnessCalculator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7680498305433526800L;
	
	private double[][] personalBest;
	
	private HashMap<Integer, Double> fitness;
	
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

	public Function getObjectiveFunction() {
		return objectiveFunction;
	}

	public void setObjectiveFunction(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}

	public HashMap<Integer, Double> getFitness() {
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
				//System.out.println(currentfitness + " cf " + fitness.get(i) + " " + i);
				if(currentfitness < fitness.get(i)){
					System.out.println(currentfitness + " " + i);
					fitness.put(i, currentfitness);
					updatePersonalBest(i);
				}
			}
		}
	}

	private void updatePersonalBest(int row) {
		System.out.println("update pb");
		for(int k = 0; k < positions[row].length; k++){
			personalBest[row][k] = positions[row][k];
		}
	}
	
	public void initialCalculateFitness(){
		fitness = new HashMap<Integer, Double>();
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
			//	System.out.println("best " + best + " i " + i);
			}
		}
		return best;
	}

	@Override
	public void setPositions(double[][] positions) {
		this.positions = positions;

	}

	@Override
	public void setFitness(HashMap<Integer, Double> fitness) {
		this.fitness = fitness;

	}

	public double[][] getPersonalBest() {
		return personalBest;
	}

	public void setPersonalBest(double[][] personalBest) {
		this.personalBest = personalBest;
	}

}
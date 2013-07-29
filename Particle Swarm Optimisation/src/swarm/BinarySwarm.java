package swarm;

import java.util.HashMap;
import java.util.Vector;

public class BinarySwarm implements Swarm {

	protected Function objectiveFunction;
	
	protected double [][] personalBest;
	
	protected double [][] position;
	
	protected double [][] velocities;
	
	protected SigmoidFunction sigmoidfunction;
	
	protected boolean maximum = false;
	
	protected VelocityUpdate velocityUpdate;
	
	protected int numberOfParticles = 20;
	
	protected int globalBest;
	
	protected HashMap<Integer, Double> fitness;
	
	protected HaltingCriteria haltingCriteria;
	
	
	@Override
	public Vector<Double> optimise() {
		assert objectiveFunction != null;
		int iteration = 0;
		do{
			iteration++;
			if(iteration == 1){
				initiateCandidateSolutions();
			}else{
				velocityUpdate.setPosition(position);
				velocityUpdate.setPersonalBest(personalBest);
				velocityUpdate.getNeighbourhood().setSolutionFitness(fitness);
				setVelocities(velocityUpdate.updateVelocities());
				updateParticlePositions();
			}
			calculateFitness();
			calculateGlobalBest();
			haltingCriteria.updateData(fitness.get(globalBest), iteration);
		}while(!haltingCriteria.halt());
		Vector<Double> result = new Vector<Double>();
		for(int i = 0; i < personalBest[globalBest].length; i++){
			result.add(personalBest[globalBest][i]);
		}
		return result;
	}

	protected void calculateGlobalBest() {
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
		globalBest = best;
	}

	protected void calculateFitness() {
		for(int i = 0; i < numberOfParticles; i++){
			double currentfitness = objectiveFunction.CalculateFitness(position[i]);
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

	protected void updatePersonalBest(int row) {
		for(int k = 0; k < position[row].length; k++){
			personalBest[row][k] = position[row][k];
		}
	}

	protected void updateParticlePositions() {
		double random = Math.random();
		for(int i = 0; i < position.length; i++){
			for(int k = 0; k < position[i].length; k++){
				if(sigmoidfunction.normalise(velocities[i][k]) > random){
					position[i][k] = 1;
				}else{
					position[i][k] = 0;
				}
			}
		}
		
	}

	protected void initiateCandidateSolutions() {
		int columns = objectiveFunction.getVariables();
		position = new double[numberOfParticles][columns];
		velocities = new double[numberOfParticles][columns];
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < columns; k++){
				velocities[i][k] = 0;
				if(Math.random() >= 0.5){
					position[i][k] = 0;
				}else{
					position[i][k] = 1;
				}
			}
		}
		personalBest = position.clone();
	}
	
	


	@Override
	public Vector<Double> optimise(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
		return optimise();
	}

	public void setVelocities(double[][] velocities) {
		this.velocities = velocities;
	}
}

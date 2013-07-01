package swarm;

import java.util.HashMap;
import java.util.Vector;


public class VanillaSwarm {
	
	/* 
	 * A matrix of candidate solutions
	 */
	private double[][] position;
	
	/*
	 * A matrix of the best solutions found by each particle
	 */
	private double[][] personalBest;
	
	/*
	 * A matrix containing the velocity of each particle
	 */
	private double [][] velocities;
	
	/* 
	 * A pointer to the index of the best solution found
	 * thus far by the swarm
	 */
	private int globalBest;
	
	/* 
	 * The function to be optimised
	 */
	private Function objectiveFunction; 
	
	/*
	 * Upper limit used to generate initial candidate solutions
	 */
	private double upperLimit = 1;
	
	/*
	 * Lower limit used to generate initial candidate solutions. Default value is 0
	 */
	private double lowerLimit = 0;

	/* The fitness measurement of the best solution found by each
	 * particle
	 */
	private HashMap <Integer, Double> fitness;
	
	/*
	 * The number of position in the Swarm. The default is 20.
	 */
	private int numberOfParticles = 20;
	
	/*
	 * Indicates whether or not the swarm is finding a maximum or minimum
	 */
	private boolean maximum = false;
	
	/*
	 * Mechanism to limit the velocity of position
	 */
	private VelocityUpdate velocityUpdate;
	
	/*
	 * An object that determines whether or halting conditions have been met.
	 */
	private HaltingCriteria haltingCriteria;
	
	public VanillaSwarm(Function objectiveFunction, VelocityUpdate velocityUpdate, HaltingCriteria haltingCriteria){
		this.objectiveFunction = objectiveFunction;
		this.velocityUpdate = velocityUpdate;
		this.haltingCriteria = haltingCriteria;
	//	this.fitness = new HashMap <Integer, Double>();
	}
	
	public VanillaSwarm(Function objectiveFunction, VelocityUpdate velocityUpdate, HaltingCriteria haltingCriteria, boolean maximum){
		this(objectiveFunction, velocityUpdate, haltingCriteria);
		this.maximum = maximum;
	}
	
	public VanillaSwarm(Function objectiveFunction, VelocityUpdate velocityUpdate, HaltingCriteria haltingCriteria, boolean maximum, int numberOfParticles){
		this(objectiveFunction, velocityUpdate, haltingCriteria, maximum);
		this.numberOfParticles = numberOfParticles;
	}
	
	public VanillaSwarm(Function objectiveFunction, VelocityUpdate velocityUpdate, HaltingCriteria haltingCriteria, boolean maximum, int numberOfParticles, int upperLimit, int lowerLimit){
		this(objectiveFunction, velocityUpdate, haltingCriteria, maximum, numberOfParticles);
		this.upperLimit = upperLimit;
		this.lowerLimit = lowerLimit;
	}
	//going to need to sort out the null thing or it
	//won't initiate solutions each time.
	public Vector<Double> optimise(){
		assert objectiveFunction != null;
		int iteration = 0;
		do{
			iteration++;
			if(position == null){
				initiateCandidateSolutions();
			}else{
				velocityUpdate.updateData(personalBest, position, fitness);
				setVelocities(velocityUpdate.updateVelocities());
				updateParticlePositions();
			}
			calculateFitness();
			calculateGlobalBest();
			haltingCriteria.updateData(globalBest, fitness.get(globalBest), iteration);
		}while(!haltingCriteria.halt());
		Vector<Double> result = new Vector<Double>();
		for(int i = 0; i < personalBest[globalBest].length; i++){
			result.add(personalBest[globalBest][i]);
		}
		return result;
	}
	
	public void setVelocities(double[][] velocities) {
		this.velocities = velocities;
	}

	public Vector<Double> optimise(Function objectiveFunction){
		this.objectiveFunction = objectiveFunction;
		return optimise();
	}
	
	private void updateParticlePositions() {
		for(int i = 0; i < position.length; i++){
			for(int k = 0; k < position[i].length; k++){
				position[i][k] = position[i][k] + velocities[i][k];
			}
		}
	}

	private void calculateFitness(){
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
	
	private void updatePersonalBest(int row){
		for(int k = 0; k < position[row].length; k++){
			personalBest[row][k] = position[row][k];
		}
	}
	
	private void calculateGlobalBest(){
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
	
	private void initiateCandidateSolutions() {
		int columns = objectiveFunction.getVariables();
		position = new double[numberOfParticles][columns];
		double random = upperLimit - lowerLimit;
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < objectiveFunction.getVariables(); k++){
				double d = lowerLimit + (Math.random() * random);
				position[i][k] = d;
			}
		}
		personalBest = position.clone();
		fitness = new HashMap <Integer, Double>();
		setFitness();
		velocities = new double[numberOfParticles][columns];
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < columns; k++){
				velocities[i][k] = 0;
			}
		}
		velocityUpdate.setVelocities(velocities);
	}
	
	private void setFitness() {
		for(int i = 0; i < numberOfParticles; i++){
			double currentfitness = objectiveFunction.CalculateFitness(position[i]);
			fitness.put(i, currentfitness);
		}
		
	}

	public double getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
	}

	public double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

}
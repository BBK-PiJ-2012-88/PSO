package swarm;

import java.util.HashMap;
import java.util.Vector;


public class VanillaSwarm implements Swarm {
	
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
	
	public VanillaSwarm(){
		velocityUpdate = new ConstrictionCoefficient();
		haltingCriteria = new IterationHalt();
	}
	
	public Function getObjectiveFunction() {
		return objectiveFunction;
	}

	public void setObjectiveFunction(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}

	public int getNumberOfParticles() {
		return numberOfParticles;
	}

	public void setNumberOfParticles(int numberOfParticles) {
		this.numberOfParticles = numberOfParticles;
	}

	public boolean isMaximum() {
		return maximum;
	}

	public VelocityUpdate getVelocityUpdate() {
		return velocityUpdate;
	}

	public void setVelocityUpdate(VelocityUpdate velocityUpdate) {
		this.velocityUpdate = velocityUpdate;
		this.velocityUpdate.getNeighbourhood().setMaximum(maximum);
	}

	public HaltingCriteria getHaltingCriteria() {
		return haltingCriteria;
	}

	public void setHaltingCriteria(HaltingCriteria haltingCriteria) {
		this.haltingCriteria = haltingCriteria;
	}

	public VanillaSwarm(Function objectiveFunction, VelocityUpdate velocityUpdate, HaltingCriteria haltingCriteria){
		this.objectiveFunction = objectiveFunction;
		this.velocityUpdate = velocityUpdate;
		this.haltingCriteria = haltingCriteria;
	}
	
	public VanillaSwarm(Function objectiveFunction, VelocityUpdate velocityUpdate, HaltingCriteria haltingCriteria, boolean maximum){
		this(objectiveFunction, velocityUpdate, haltingCriteria);
		this.maximum = maximum;
		velocityUpdate.getNeighbourhood().setMaximum(maximum);
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
	/* (non-Javadoc)
	 * @see swarm.Swarm#optimise()
	 */
	@Override
	public Vector<Double> optimise(){
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
	
	public void setVelocities(double[][] velocities) {
		this.velocities = velocities;
	}

	/* (non-Javadoc)
	 * @see swarm.Swarm#optimise(swarm.Function)
	 */
	@Override
	public Vector<Double> optimise(Function objectiveFunction){
		this.objectiveFunction = objectiveFunction;
		System.out.println(objectiveFunction.toString());
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

	@Override
	public boolean getMaximum() {
		return maximum;
	}
	
	@Override
	public void setMaximum(boolean maximum){
		this.maximum = maximum;
		velocityUpdate.getNeighbourhood().setMaximum(maximum);
	}

	@Override
	public void setNeighbourhood(Neighbourhood neighbourhood) {
		this.getVelocityUpdate().setNeighbourhood(neighbourhood);
		
	}
	
	@Override
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append(this.getClass().toString() + ", ");
		buff.append("Function: " + objectiveFunction.toString() + ", ");
		buff.append("Velocity Updater: " + velocityUpdate.getClass().toString() + ", ");
		buff.append("Neighbourhood: " + velocityUpdate.getNeighbourhood().getClass().toString() + ", ");
		buff.append("Halting Criteria: " + haltingCriteria.getClass().toString() + ", ");
		buff.append("Number of Particles: " + numberOfParticles + ", ");
		buff.append("Maximum: " + maximum);
		return buff.toString();
	}
}
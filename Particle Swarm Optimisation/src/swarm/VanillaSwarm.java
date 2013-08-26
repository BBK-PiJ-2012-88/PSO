package swarm;

import java.util.Arrays;
import java.util.Map;
import java.util.Vector;


public class VanillaSwarm implements Swarm, ConstrainedOptimisation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2507732118651641415L;

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
	
	private double[] max;
	
	private double[] min;

	/* The fitness measurement of the best solution found by each
	 * particle
	 */
	private Map <Integer, Double> fitness;
	
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
	
	private boolean constrainedOptimisation = true;
	
	private VanillaInitialiser init = new VanillaInitialiser();
	
	private FitnessCalculator calc;
	
	private PositionUpdate positionUpdate = new VanillaPositionUpdate();
	
	public VanillaSwarm(){
		velocityUpdate = new ConstrictionCoefficient();
		haltingCriteria = new IterationHalt();
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
	public int getNumberOfParticles() {
		return numberOfParticles;
	}

	@Override
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

	@Override
	public HaltingCriteria getHaltingCriteria() {
		return haltingCriteria;
	}

	@Override
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
	
	private void initiateSwarm(){
		calc = new FitnessCalculatorImpl(getObjectiveFunction(), getMaximum());
		if(max == null){
			max = new double[getObjectiveFunction().getVariables()];
			Arrays.fill(max, upperLimit);
			init.setMax(max);
		}else{
			init.setMax(max);
		}
		if(min == null){
			min = new double[getObjectiveFunction().getVariables()];
			Arrays.fill(min, lowerLimit);
			init.setMin(min);
		}else{
			init.setMin(min);
		}
		if(constrainedOptimisation){
			((VanillaPositionUpdate)getPositionUpdate()).setConstraints(constrainedOptimisation);
			Constrainer constrainer = new VanillaConstrainer();
			constrainer.setMaximum(max);
			constrainer.setMinimum(min);
			((VanillaPositionUpdate)getPositionUpdate()).setConstrainer(constrainer);
		}
		init.initialiseMatrices(getObjectiveFunction(), getNumberOfParticles());
		setPosition(init.getPositions());
		setPersonalBest(init.getPersonalBest());
		setVelocities(init.getVelocities());
		getVelocityUpdate().setVelocities(getVelocities());
		getVelocityUpdate().setMaximum(maximum);
		calc.setPositions(getPosition());
		calc.initialCalculateFitness();
		setFitness(calc.getFitness());
		globalBest = calc.calculateGlobalBest();
		haltingCriteria.updateData(fitness.get(globalBest), 0);
	}
	
	public void setInit(VanillaInitialiser init) {
		this.init = init;
	}

	public double[] getMax() {
		return max;
	}

	public void setMax(double[] max) {
		this.max = max;
	}

	public double[] getMin() {
		return min;
	}

	public void setMin(double[] min) {
		this.min = min;
	}
	
	@Override
	public Vector<Double> constrainedOptimise(Function objectiveFunction, double[] max, double[] min){
		this.min = min;
		this.max = max;
		this.objectiveFunction = objectiveFunction;
		return constrainedOptimise();
	}
	
	@Override
	public Vector<Double> constrainedOptimise(Function objectiveFunction, double upperLimit, double lowerLimit){
		max = null;
		min = null;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.objectiveFunction = objectiveFunction;
		return constrainedOptimise();
	}
	
	public Vector<Double> optimise(Function objectiveFunction, double upperLimit, double lowerLimit){
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		max = null;
		min = null;
		this.objectiveFunction = objectiveFunction;
		return optimise();
	}
	
	public Vector<Double> optimise(Function objectiveFunction, double[] max, double[] min){
		this.objectiveFunction = objectiveFunction;
		this.max = max;
		this.min = min;
		return optimise();
	}
	
	
	
	@Override
	public Vector<Double> optimise(){
		assert objectiveFunction != null;
		constrainedOptimisation = false;
		initiateSwarm();
		for(int i = 0; !haltingCriteria.halt(); i++){
			updateVelocities();
			updateParticlePositions();
			updateFitnessInformation();
			haltingCriteria.updateData(fitness.get(globalBest), i);
			System.out.println(i);
			System.out.println("value " + fitness.get(globalBest));
			System.out.println("index " + globalBest);
		}
		Vector<Double> result = new Vector<Double>();
		for(int i = 0; i < personalBest[globalBest].length; i++){
			result.add(personalBest[globalBest][i]);
		}
		return result;
	}
	
	public Vector<Double> constrainedOptimise(){
		assert objectiveFunction != null;
		constrainedOptimisation = true;
		initiateSwarm();
		for(int i = 0; !haltingCriteria.halt(); i++){
			updateVelocities();
			updateParticlePositions();
			updateFitnessInformation();
			haltingCriteria.updateData(fitness.get(globalBest), i);
			System.out.println(i);
			System.out.println("value " + fitness.get(globalBest));
			System.out.println("index " + globalBest);
		}
		Vector<Double> result = new Vector<Double>();
		for(int i = 0; i < personalBest[globalBest].length; i++){
			result.add(personalBest[globalBest][i]);
		}
		return result;
	}
	
	private void updateFitnessInformation() {
		calc.setFitness(fitness);
		calc.setPersonalBest(personalBest);
		calc.setPositions(position);
		calc.calculateFitness();
		setFitness(calc.getFitness());
		setPersonalBest(calc.getPersonalBest());
		setGlobalBest(calc.calculateGlobalBest());
	}

	private void updateVelocities() {
		velocityUpdate.setPosition(position);
		velocityUpdate.setPersonalBest(personalBest);
		velocityUpdate.getNeighbourhood().setSolutionFitness(fitness);
		setVelocities(velocityUpdate.updateVelocities());
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
		return optimise();
	}
	
	private void updateParticlePositions() {
		positionUpdate.setPositions(getPosition());
		positionUpdate.setVelocities(getVelocities());
		positionUpdate.updatePositions();
		setPosition(positionUpdate.getPositions());
	}

	public double[][] getPersonalBest() {
		return personalBest;
	}

	public void setPersonalBest(double[][] personalBest) {
		this.personalBest = personalBest;
	}

	public double[][] getVelocities() {
		return velocities;
	}

	public double[][] getPosition() {
		return position;
	}

	public void setPosition(double[][] position) {
		this.position = position;
	}

	public int getGlobalBest() {
		return globalBest;
	}

	public void setGlobalBest(int globalBest) {
		this.globalBest = globalBest;
	}

	public Map<Integer, Double> getFitness() {
		return fitness;
	}

	public void setFitness(Map<Integer, Double> fitness) {
		this.fitness = fitness;
	}

	public Initialiser getInit() {
		return init;
	}

	public void setInit(Initialiser init) {
		this.init = (VanillaInitialiser)init;
	}

	public FitnessCalculator getCalc() {
		return calc;
	}

	public void setCalc(FitnessCalculator calc) {
		this.calc = calc;
	}

	public PositionUpdate getPositionUpdate() {
		return positionUpdate;
	}

	public void setPositionUpdate(PositionUpdate positionUpdate) {
		this.positionUpdate = positionUpdate;
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
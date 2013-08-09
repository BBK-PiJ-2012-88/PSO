package swarm;

import java.util.HashMap;
import java.util.Vector;

public class BinarySwarm implements Swarm {

	private Function objectiveFunction;
	
	private double [][] personalBest;
	
	private double [][] position;
	
	private double [][] velocities;
	
	private boolean maximum = false;
	
	private VelocityUpdate velocityUpdate = new ConstrictionCoefficient();
	
	private int numberOfParticles = 20;
	
	private int globalBest;
	
	private HashMap<Integer, Double> fitness;
	
	private HaltingCriteria haltingCriteria;
	
	private GeneticOperator genOp;
	
	private Initialiser init = new BinaryInitialiser();
	
	private FitnessCalculator calc;
	
	private boolean geneticOptimisation = false;
	
	private PositionUpdate positionUpdate = new BinaryPositionUpdate();
	
	public BinarySwarm(){}
	
	public GeneticOperator getGenOp() {
		return genOp;
	}

	public void setGenOp(GeneticOperator genOp) {
		this.genOp = genOp;
	}

	public BinarySwarm(Function objectiveFunction, HaltingCriteria haltingCriteria, VelocityUpdate velocityUpdate){
		this.objectiveFunction = objectiveFunction;
		this.haltingCriteria = haltingCriteria;
		this.velocityUpdate = velocityUpdate;
	}
	
	public BinarySwarm(Function objectiveFunction, HaltingCriteria haltingCriteria, VelocityUpdate velocityUpdate, boolean maximum){
		this(objectiveFunction, haltingCriteria, velocityUpdate);
		this.maximum = maximum;
		this.velocityUpdate.getNeighbourhood().setMaximum(maximum);
	}
	
	private void initiateSwarm(){
		calc = new FitnessCalculatorImpl(objectiveFunction, maximum);
		init.initialiseMatrices(objectiveFunction, numberOfParticles);
		setPosition(init.getPositions());
		setPersonalBest(getPosition().clone());
		setVelocities(init.getVelocities());
		calc.setPositions(getPosition());
		calc.initialCalculateFitness();
		setFitness(calc.getFitness());
		globalBest = calc.calculateGlobalBest();
		getVelocityUpdate().setVelocities(velocities);
		getVelocityUpdate().getNeighbourhood().setMaximum(getMaximum());
		haltingCriteria.updateData(fitness.get(globalBest), 0);
		if(geneticOptimisation){
			if(genOp == null){
				genOp = new GeneticOperatorImpl(objectiveFunction);
			}
			genOp.setObjectiveFunction(objectiveFunction);
		}
		
	}
	
	public Vector<Double> geneticOptimise(){
		geneticOptimisation = true;
		return optimise();
	}
	
	public Vector<Double> geneticOptimise(Function objectiveFunction){
		geneticOptimisation = true;
		setObjectiveFunction(objectiveFunction);
		return optimise();
	}
	
	@Override
	public Vector<Double> optimise() {
		assert objectiveFunction != null;
		initiateSwarm();
		for(int i = 1; !haltingCriteria.halt(); i++){
			if(geneticOptimisation == true){
				genOp.setPositions(position);
				genOp.performGeneticOperations();
				setPosition(genOp.getPositions());
			}
			updateVelocities();
			updateParticlePositions();
			updateFitnessInformation();
			haltingCriteria.updateData(fitness.get(globalBest), i);
		}
		Vector<Double> result = new Vector<Double>();
		for(int i = 0; i < personalBest[globalBest].length; i++){
			result.add(personalBest[globalBest][i]);
		}
		geneticOptimisation = false;
		return result;
	}
	
	private void updateFitnessInformation() {
		calc.setFitness(fitness);
		calc.setPersonalBest(personalBest);
		calc.setPositions(position);
		calc.calculateFitness();
		setFitness(calc.getFitness());
		setPersonalBest(calc.getPersonalBest());
		globalBest = calc.calculateGlobalBest();
	}
	
	private void updateVelocities(){
		velocityUpdate.setPosition(position);
		velocityUpdate.setPersonalBest(personalBest);
		velocityUpdate.getNeighbourhood().setSolutionFitness(fitness);
		setVelocities(velocityUpdate.updateVelocities());
	}
	
	private void updateParticlePositions() {
		positionUpdate.setVelocities(velocities);
		positionUpdate.setPositions(position);
		positionUpdate.updatePositions();
		setPosition(positionUpdate.getPositions());		
	}

	public double[][] getPersonalBest() {
		return personalBest;
	}

	public void setPersonalBest(double[][] personalBest) {
		this.personalBest = personalBest;
	}

	public double[][] getPosition() {
		return position;
	}

	public void setPosition(double[][] position) {
		this.position = position;
	}

	public HashMap<Integer, Double> getFitness() {
		return fitness;
	}

	public void setFitness(HashMap<Integer, Double> fitness) {
		this.fitness = fitness;
	}

	public Initialiser getInit() {
		return init;
	}

	public void setInit(Initialiser init) {
		this.init = init;
	}

	public FitnessCalculator getCalc() {
		return calc;
	}

	public void setCalc(FitnessCalculator calc) {
		this.calc = calc;
	}

	public double[][] getVelocities() {
		return velocities;
	}

	public Function getObjectiveFunction() {
		return objectiveFunction;
	}

	public void setObjectiveFunction(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}

	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
		velocityUpdate.getNeighbourhood().setMaximum(maximum);
	}

	public VelocityUpdate getVelocityUpdate() {
		return velocityUpdate;
	}

	public void setVelocityUpdate(VelocityUpdate velocityUpdate) {
		this.velocityUpdate = velocityUpdate;
		this.velocityUpdate.getNeighbourhood().setMaximum(maximum);
	}

	public int getNumberOfParticles() {
		return numberOfParticles;
	}

	public void setNumberOfParticles(int numberOfParticles) {
		this.numberOfParticles = numberOfParticles;
	}

	public int getGlobalBest() {
		return globalBest;
	}

	public void setGlobalBest(int globalBest) {
		this.globalBest = globalBest;
	}

	public HaltingCriteria getHaltingCriteria() {
		return haltingCriteria;
	}

	public void setHaltingCriteria(HaltingCriteria haltingCriteria) {
		this.haltingCriteria = haltingCriteria;
	}

	@Override
	public Vector<Double> optimise(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
		return optimise();
	}

	public void setVelocities(double[][] velocities) {
		this.velocities = velocities;
	}

	@Override
	public boolean getMaximum() {
		return maximum;
	}

	@Override
	public void setNeighbourhood(Neighbourhood neighbourhood) {
		getVelocityUpdate().setNeighbourhood(neighbourhood);
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
		buff.append("Genetic operator" + genOp.toString());
		return buff.toString();
	}
}

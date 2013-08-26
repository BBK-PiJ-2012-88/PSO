package swarm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CombinatorialSwarm implements Swarm, GeneticSwarm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1179862233764569743L;

	private double[][] positions;
	
	private double[][] personalBest;
	
	private double[][] velocities;
	
	private GeneticOperator genOp = new CombinatorialGeneticOperator();
	
	private Function objectiveFunction;
	
	private int numberOfParticles = 20;
	
	private HaltingCriteria haltingCriteria = new IterationHalt(100);
	
	public Function getObjectiveFunction() {
		return objectiveFunction;
	}

	public void setObjectiveFunction(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}

	public PositionUpdate getPositionUpdate() {
		return positionUpdate;
	}

	public void setPositionUpdate(PositionUpdate positionUpdate) {
		this.positionUpdate = positionUpdate;
	}

	public Initialiser getInit() {
		return init;
	}

	public void setInit(Initialiser init) {
		this.init = init;
	}

	private Map<Integer, Double> fitness;
	
	private CombinatorialVelocityUpdate combinatorialVelocityUpdate = new CombinatorialVelocityUpdate();
	
	private PositionUpdate positionUpdate = new CombinatorialPositionUpdate();
	
	private Initialiser init = new CombinatorialInitialiser();
	
	private FitnessCalculator calc;
	
	private int globalBest;
	
	private boolean maximum = false;
	
	private void initiateSwarm(){
		calc = new FitnessCalculatorImpl(objectiveFunction, maximum);
		init.initialiseMatrices(objectiveFunction, numberOfParticles);
		setVelocities(init.getVelocities());
		setPositions(init.getPositions());
		setPersonalBest(init.getPersonalBest());
		calc.setPositions(getPositions());
		calc.initialCalculateFitness();
		setFitness(calc.getFitness());
		globalBest = calc.calculateGlobalBest();
		getVelocityUpdate().setVelocities(velocities);
		getVelocityUpdate().setMaximum(maximum);
		haltingCriteria.updateData(fitness.get(globalBest), 0);
	}

	public Map<Integer, Double> getFitness() {
		return fitness;
	}

	public void setFitness(Map<Integer, Double> fitness) {
		this.fitness = fitness;
	}

	public double[][] getPersonalBest() {
		return personalBest;
	}

	public void setPersonalBest(double[][] personalBest) {
		this.personalBest = personalBest;
	}

	public double[][] getPositions() {
		return positions;
	}

	public void setPositions(double[][] positions) {
		this.positions = positions;
	}
	
	@Override
	public Vector<Double> optimise() {
		initiateSwarm();
		for(int i = 1; !haltingCriteria.halt(); i++){
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
	
	private void updateParticlePositions() {
		positionUpdate.setPositions(positions);
		positionUpdate.setVelocities(velocities);
		((CombinatorialPositionUpdate)positionUpdate).setPersonalBest(personalBest);
		((CombinatorialPositionUpdate)positionUpdate).setNeighbourhood(getVelocityUpdate().getNeighbourhood());
		positionUpdate.updatePositions();
		setPositions(positionUpdate.getPositions());
		setVelocities(positionUpdate.getVelocities());
	}
	
	private void updateFitnessInformation(){
		calc.setFitness(getFitness());
		calc.setPersonalBest(getPersonalBest());
		calc.setPositions(getPositions());
		calc.calculateFitness();
		setFitness(calc.getFitness());
		combinatorialVelocityUpdate.getNeighbourhood().setSolutionFitness(getFitness());
		setPersonalBest(calc.getPersonalBest());
		setGlobalBest(calc.calculateGlobalBest());
	}

	public FitnessCalculator getCalc() {
		return calc;
	}

	public void setCalc(FitnessCalculator calc) {
		this.calc = calc;
	}

	public int getGlobalBest() {
		return globalBest;
	}

	public void setGlobalBest(int globalBest) {
		this.globalBest = globalBest;
	}

	private void updateVelocities(){
		combinatorialVelocityUpdate.setVelocities(getVelocities());
		combinatorialVelocityUpdate.setPosition(getPositions());
		combinatorialVelocityUpdate.setPersonalBest(getPersonalBest());
		combinatorialVelocityUpdate.getNeighbourhood().setSolutionFitness(getFitness());
		setVelocities(combinatorialVelocityUpdate.updateVelocities());
	}

	public CombinatorialVelocityUpdate getCombinatorialVelocityUpdate() {
		return combinatorialVelocityUpdate;
	}

	public void setCombinatorialVelocityUpdate(
			CombinatorialVelocityUpdate combinatorialVelocityUpdate) {
		this.combinatorialVelocityUpdate = combinatorialVelocityUpdate;
	}

	public double[][] getVelocities() {
		return velocities;
	}

	public void setVelocities(double[][] velocities) {
		this.velocities = velocities;
		
	}

	@Override
	public Vector<Double> optimise(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
		return optimise();
	}

	@Override
	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
		combinatorialVelocityUpdate.getNeighbourhood().setMaximum(maximum);
		
	}

	@Override
	public boolean getMaximum() {
		return maximum;
	}

	@Override
	public void setHaltingCriteria(HaltingCriteria haltingCriteria) {
		this.haltingCriteria = haltingCriteria;
		
	}

	@Override
	public HaltingCriteria getHaltingCriteria() {
		return haltingCriteria;
	}

	public CombinatorialVelocityUpdate getVelocityUpdate() {
		return combinatorialVelocityUpdate;
	}

	@Override
	public int getNumberOfParticles() {
		return numberOfParticles;
	}

	@Override
	public void setNumberOfParticles(int numberOfParticles) {
		this.numberOfParticles = numberOfParticles;
		
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
		buff.append("Velocity Updater: " + combinatorialVelocityUpdate.getClass().toString() + ", ");
		buff.append("Neighbourhood: " + combinatorialVelocityUpdate.getNeighbourhood().getClass().toString() + ", ");
		buff.append("Halting Criteria: " + haltingCriteria.getClass().toString() + ", ");
		buff.append("Number of Particles: " + numberOfParticles + ", ");
		buff.append("Maximum: " + maximum);
		return buff.toString();
	}

	@Override
	public GeneticOperator getGenOp() {
		return genOp;
	}

	@Override
	public void setGenOp(GeneticOperator geneticOperator) {
		this.genOp = geneticOperator;	
	}

	@Override
	public Vector<Double> geneticOptimise() {
		if(genOp == null){
			genOp = new CombinatorialGeneticOperator();
		}
		initiateSwarm();
		for(int i = 1; !haltingCriteria.halt(); i++){
			updateVelocities();
			updateParticlePositions();
			performGeneticOperations();
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
	
	

	private void performGeneticOperations() {
		genOp.setPositions(positions);
		genOp.performGeneticOperations();
		setPositions(genOp.getPositions());
	}

	@Override
	public Vector<Double> geneticOptimise(Function objectiveFunction) {
		setObjectiveFunction(objectiveFunction);
		return geneticOptimise();
	}
}

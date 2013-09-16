package swarm;

import java.util.Map;
import java.util.Vector;
/**
 * A controller class that manages its components to search combinatorial solution spaces
 * using the particle swarm algorithm. The combinatorial swarm does not have methods to search constrained 
 * solution spaces. It can use genetic operators in its search of the solution space.
 * 
 * @author williamhogarth
 *
 */
public class CombinatorialSwarm implements Swarm, GeneticSwarm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1179862233764569743L;
	/**
	 * The particles' current positions
	 */
	private double[][] positions;
	
	/**
	 * The best solution found by each particle
	 */
	private double[][] personalBest;
	
	/**
	 * The particles' velocities. Each particles' cognitive velocity is at the particle's index
	 * Its social velocity is at its index + number of particles
	 */
	private double[][] velocities;
	
	/**
	 * Genetic Operator that performs the genetic operations. The GeneticOperatorImpl cannot be used instead
	 * here.
	 */
	private GeneticOperator genOp = new CombinatorialGeneticOperator();
	
	/**
	 * the Function being opitmised
	 */
	private Function objectiveFunction;
	
	/**
	 * The number of particles, default is 27.
	 */
	private int numberOfParticles = 27;
	
	/**
	 * The HaltingCriteria. The default is an Iteration halt set to run for 100 iterations
	 */
	private HaltingCriteria haltingCriteria = new IterationHalt(100);
	
	/**
	 * Map mapping the particle index to the fitness of the best solution that it has found thus far.
	 */
	private Map<Integer, Double> fitness;
	
	/**
	 * VelocityUpdate responsible for updating the particles' velocities. VelocityClamping or 
	 * ConstrictionCoefficient cannot be used here.
	 */
	private VelocityUpdate velocityUpdate = new CombinatorialVelocityUpdate();
	
	/**
	 * PositionUpdate responsible for updating the positions of the particles
	 */
	private PositionUpdate positionUpdate = new CombinatorialPositionUpdate();
	
	/**
	 * Initialiser that initialises the particles' velocities, positions and personal best
	 */
	private Initialiser init = new CombinatorialInitialiser();
	
	/**
	 * Calculates the fitness of the solution found by the swarm. Initialised when the optimisation process
	 * begins.
	 */
	private FitnessCalculator calc;
	
	/**
	 * Index of the particle that has found the best solution thus far
	 */
	private int globalBest;
	
	/**
	 * boolean indicating whether a maximum or a minimum is being sought.
	 */
	private boolean maximum = false;
	
	/**
	 * The combinatorial swarm only has a no arguments constructor.
	 */
	public CombinatorialSwarm(){}
	
	/**
	 * An unconstrained genetic optimisation process that returns a Vector containing the best permutation found by the swarm.
	 * <p>
	 * The method sets the objective function and then returns the private method geneticOptimise() 
	 * 
	 * @param Function objectiveFunction
	 * @return Vector<Double>
	 */
	@Override
	public Vector<Double> geneticOptimise(Function objectiveFunction) {
		setObjectiveFunction(objectiveFunction);
		return geneticOptimise();
	}
	
	/**
	 * An unconstrained optimisation process that returns a vector of the best permutation found by the 
	 * swarm.
	 * <p>
	 * The method sets the objective function and then returns the private method optimise()
	 */
	@Override
	public Vector<Double> optimise(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
		return optimise();
	}

	/**
	 * 
	 * Calls the private method initiateSwarm(). Enters an optimisation loop using the boolean condition
	 * HaltingCriteria.halt(). Inside the loop the private methods updateVelocities(), 
	 * updateParticlePositions() and updateFitnessInformation() are called. The HaltingCriteria's data is
	 * then updated. Returns a Vector which is the best permutation found by the swarm.
	 * 
	 * @return Vector<Double>
	 * 
	 */
	@Override
	public Vector<Double> optimise() {
		initiateSwarm();
		for(int i = 1; !haltingCriteria.halt(); i++){
			updateVelocities();
			updateParticlePositions();
			updateFitnessInformation();
			haltingCriteria.updateData(fitness.get(globalBest), i);
		}
		Vector<Double> result = new Vector<Double>();
		for(int i = 0; i < personalBest[globalBest].length; i++){
			result.add(personalBest[globalBest][i]);
		}
		return result;
	}

	/**
	 * If the genetic operator is null it is initialised. The private method initiate swarm is called. Enters an 
	 * optimisation loop using the boolean condition HaltingCriteria.halt(). Inside the loop the private methods
	 * updateVelocities(), updateParticlePositions(), performGeneticOperations() and updateFitnessInformation()
	 * are called. The HaltingCriteria's data is then updated. A vector of the best permutation found is returned.
	 * 
	 * @return Vector<Double>
	 */
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
		}
		Vector<Double> result = new Vector<Double>();
		for(int i = 0; i < personalBest[globalBest].length; i++){
			result.add(personalBest[globalBest][i]);
		}
		return result;
	}
	
	/**
	 * Initiates the swarm
	 * <p>
	 * Creates a new FitnessCalculator and intialises the particle matrices. The fitness calculator performs
	 * an initial calculate fitness, and also calculates the index of the global best particle. The HaltingCriteria
	 * data is updated.
	 */
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
	
	/**
	 * updates the velocities of the particles.
	 * 
	 */
	private void updateVelocities(){
		velocityUpdate.setVelocities(getVelocities());
		velocityUpdate.setPosition(getPositions());
		velocityUpdate.setPersonalBest(getPersonalBest());
		velocityUpdate.getNeighbourhood().setSolutionFitness(getFitness());
		setVelocities(velocityUpdate.updateVelocities());
	}
	
	/**
	 * Performs genetic operations on the particles
	 */
	private void performGeneticOperations() {
		genOp.setPositions(positions);
		genOp.performGeneticOperations();
		setPositions(genOp.getPositions());
	}
	
	/**
	 * updates the particles' positions
	 */
	private void updateParticlePositions() {
		positionUpdate.setPositions(positions);
		positionUpdate.setVelocities(velocities);
		((CombinatorialPositionUpdate)positionUpdate).setPersonalBest(personalBest);
		((CombinatorialPositionUpdate)positionUpdate).setNeighbourhood(getVelocityUpdate().getNeighbourhood());
		positionUpdate.updatePositions();
		setPositions(positionUpdate.getPositions());
		setVelocities(positionUpdate.getVelocities());
	}
	
	/**
	 * Updates the fitness map, personal best of the particles and the global best index
	 */
	private void updateFitnessInformation(){
		calc.setFitness(getFitness());
		calc.setPersonalBest(getPersonalBest());
		calc.setPositions(getPositions());
		calc.calculateFitness();
		setFitness(calc.getFitness());
		velocityUpdate.getNeighbourhood().setSolutionFitness(getFitness());
		setPersonalBest(calc.getPersonalBest());
		setGlobalBest(calc.calculateGlobalBest());
	}
	
	public void setCombinatorialVelocityUpdate(
			CombinatorialVelocityUpdate velocityUpdate) {
		this.velocityUpdate = velocityUpdate;
	}

	public double[][] getVelocities() {
		return velocities;
	}

	public void setVelocities(double[][] velocities) {
		this.velocities = velocities;
		
	}
	
	public FitnessCalculator getFitnessCalculator() {
		return calc;
	}

	public void setFitnessCalculator(FitnessCalculator calc) {
		this.calc = calc;
	}

	public int getGlobalBest() {
		return globalBest;
	}

	public void setGlobalBest(int globalBest) {
		this.globalBest = globalBest;
	}

	@Override
	public GeneticOperator getGenOp() {
		return genOp;
	}

	@Override
	public void setGenOp(GeneticOperator geneticOperator) {
		this.genOp = geneticOperator;	
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
	public void setVelocityUpdate(VelocityUpdate velocityUpdate) {
		this.velocityUpdate = velocityUpdate;
		
	}

	@Override
	public VelocityUpdate getVelocityUpdate() {
		return velocityUpdate;
	}
	

	@Override
	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
		velocityUpdate.getNeighbourhood().setMaximum(maximum);
		
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
	public Function getObjectiveFunction() {
		return objectiveFunction;
	}

	@Override
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

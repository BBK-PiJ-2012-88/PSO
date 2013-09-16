package swarm;

import java.util.Arrays;
import java.util.Map;
import java.util.Vector;
/**
 * A controller class that manages its components to search binary and real-valued solution spaces
 * using the particle swarm algorithm. The binary swarm has methods to perform constrained and unconstrained 
 * searches of the solution space, as well as constrained and unconstrained searches of the solution space using 
 * genetic operators.
 * 
 * @author williamhogarth
 *
 */
public class BinarySwarm implements Swarm, GeneticSwarm, ConstrainedOptimisation, ConstrainedGeneticOptimisation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3796636603226088760L;
	
	/**
	 * Function to be optimised.
	 */
	private Function objectiveFunction;
	
	/**
	 * The best positions found by the particles
	 */
	private double [][] personalBest;
	
	/**
	 * The particles' current position
	 */
	private double [][] position;
	
	/**
	 * The particles' velocities
	 */
	private double [][] velocities;
	
	/**
	 * The upper limit of the feasible solution space
	 */
	private double[] upperLimit;
	
	/**
	 * The lower limit of the feasible solution space
	 */
	private double[] lowerLimit;
	
	/**
	 * boolean indicating if the solution space is constrained
	 */
	private boolean constrainedOptimisation = false;
	
	/**
	 * boolean indicating whether the swarm is hunting for a global minimum or maximum. Default is searching
	 * for a minimum
	 */
	private boolean maximum = false;
	
	/**
	 * VelocityUpdate responsible for updating the velocities of the particles. Default is a
	 * ConstrictionCoefficient.
	 * {@link VelocityUpdate}
	 * {@link ConstrictionCoefficient}
	 */
	private VelocityUpdate velocityUpdate = new ConstrictionCoefficient();
	
	/**
	 * Default number of particles
	 */
	private int numberOfParticles = 27;
	
	/**
	 * Index of the particle that has found the best solution thus far
	 */
	private int globalBest;
	
	/**
	 * Maps particle indexes to the fitness of the best solution they have found thus far
	 */
	private Map<Integer, Double> fitness;
	
	/**
	 * HaltingCriteria responsible for terminating the optimisation process. Default is an 
	 * IterationHalt that will halt the process after 100 iterations.
	 * 
	 * {@link HaltingCriter}
	 * {@link IterationHalt}
	 */
	private HaltingCriteria haltingCriteria = new IterationHalt(100);
	
	/**
	 * GeneticOperator responsible for performing the genetic operations on the particles. Defualt is a 
	 * GeneticOperatorImpl that performs mutation and crossover on the particles.
	 * 
	 * {@link GeneticOperator}
	 * {@link GeneticOperatorImpl}
	 */
	private GeneticOperator genOp = new GeneticOperatorImpl();
	
	/**
	 * ConstrainedInitialiser responsible for initialising the positions of the particles in binary space.
	 * 
	 * {@link ConstrainedInitialiser}
	 * {@link BinaryInitialiser}
	 */
	private ConstrainedInitialiser init = new BinaryInitialiser();
	
	/**
	 * FitnessCalculator responsible for calculating the fitness of the particles' solutions, updating their
	 * personal best positions and calculating the index of the particle that has found the best solution
	 * far. Initialised when an optimisation method is called.
	 * 
	 * {@link FitnessCalculator}
	 * {@link FitnessCalculatorImpl}
	 */
	private FitnessCalculator calc;
	
	/**
	 * ConstrainedPositionUpdate responsible for updating the positions of the particles
	 * 
	 * {@link ConstrainedPositionUpdate}
	 * {@link BinaryPositionUpdate}
	 */
	private ConstrainedPositionUpdate positionUpdate = new BinaryPositionUpdate();
	
	/**
	 * BinaryConverter contains the length of the binary encoding, and is responsible for converting
	 * binary values to real values and vice-versa.
	 */
	private BinaryConverter binaryConverter = new BinaryConverterImpl();
	
	/**
	 * No arguments constructor
	 */
	public BinarySwarm(){}
	
	/**
	 * Constructor sets the objective function, halting criteria and velocity update
	 * 
	 * @param objectiveFunction
	 * @param haltingCriteria
	 * @param velocityUpdate
	 */
	public BinarySwarm(Function objectiveFunction, HaltingCriteria haltingCriteria, VelocityUpdate velocityUpdate){
		this();
		this.objectiveFunction = objectiveFunction;
		this.haltingCriteria = haltingCriteria;
		this.velocityUpdate = velocityUpdate;
	}
	
	/**
	 * Constructor sets the objective function, halting criteria, velocity update and the boolean maximum
	 * @param objectiveFunction
	 * @param haltingCriteria
	 * @param velocityUpdate
	 * @param maximum
	 */
	public BinarySwarm(Function objectiveFunction, HaltingCriteria haltingCriteria, VelocityUpdate velocityUpdate, boolean maximum){
		this(objectiveFunction, haltingCriteria, velocityUpdate);
		this.maximum = maximum;
		this.velocityUpdate.getNeighbourhood().setMaximum(maximum);
	}
	
	/**
	 * An unconstrained genetic optimisation process that returns a binary vector that is the best solution 
	 * found by the swarm
	 * <p>
	 * The methods sets the objective function, sets constrainedOptimisation to false and returns the 
	 * private method geneticOptimisation
	 * 
	 * @param Function objectiveFunction, The function to be optimised
	 * 
	 * @return Vector<Double>
	 */
	@Override
	public Vector<Double> geneticOptimise(Function objectiveFunction){
		setObjectiveFunction(objectiveFunction);
		constrainedOptimisation = false;
		return geneticOptimisation();
	}
	
	/**
	 * An unconstrained opitmisation method that returns a binary vector that is the best solution found thus
	 * far by the swarm
	 * <p>
	 * The method sets constrained optimisation to false and returns the private method optimisation()
	 * 
	 * @return Vector<Double>
	 */
	@Override
	public Vector<Double> optimise(){
		constrainedOptimisation = false;
		return optimisation();
	}
	
	/**
	 * A constrained genetic optimisation process that returns a binary vector that is the best solution
	 * found by the swarm.
	 * <p>
	 * The method sets the objective function, the arrays of maximum and minimum values and the boolean
	 * constrained optimisation to true before returning geneticOptimisation
	 * 
	 * @param Function objectiveFunction
	 * @param double[] max
	 * @param double[] min
	 * 
	 * @return Vector<Double>
	 */
	@Override
	public Vector<Double> constrainedGeneticOptmise(Function objectiveFunction,
			double[] max, double[] min) {
		upperLimit = max;
		lowerLimit = min;
		constrainedOptimisation = true;
		this.objectiveFunction = objectiveFunction;
		return geneticOptimisation();
	}

	/**
	 * A constrained genetic optimisation method that returns a binary vector that is the best solution 
	 * found by the swarm
	 * <p>
	 * The method takes an upperlimit and lowerlimit value and uses them to generate arrays that will
	 * constrain  the solution space. It sets the function and constrained optimisation to true before returning
	 * the private method geneticOptimisation()
	 * 
	 * @param Function objectiveFunction
	 * @param double lowerLimit
	 * @param double upperLimit
	 * 
	 * @return Vector<Double>
	 */
	@Override
	public Vector<Double> constrainedGeneticOptimise(
			Function objectiveFunction, double upperLimit, double lowerLimit) {
		this.upperLimit = new double[objectiveFunction.getVariables() / binaryConverter.getBinaryEncoding()];
		Arrays.fill(this.upperLimit, upperLimit);
		this.lowerLimit = new double[objectiveFunction.getVariables() / binaryConverter.getBinaryEncoding()];
		Arrays.fill(this.lowerLimit, lowerLimit);
		setObjectiveFunction(objectiveFunction);
		constrainedOptimisation = true;
		return geneticOptimisation();
	}

	/**
	 * A constrained optimisation process that returns a binary vector that is the best solution
	 * found by the swarm.
	 * <p>
	 * The method sets the objective function, the arrays of maximum and minimum values and the boolean
	 * constrained optimisation to true before returning optimisation()
	 * 
	 * @param Function objectiveFunction
	 * @param double[] max
	 * @param double[] min
	 * 
	 * @return Vector<Double>
	 */
	@Override
	public Vector<Double> constrainedOptimise(Function objectiveFunction,
			double[] max, double[] min) {
		upperLimit = max; 
		lowerLimit = min;
		this.objectiveFunction = objectiveFunction;
		constrainedOptimisation = true;
		return optimisation();
	}

	/**
	 * A constrained optimisation method that returns a binary vector that is the best solution 
	 * found by the swarm
	 * <p>
	 * The method takes an upperlimit and lowerlimit value and uses them to generate arrays that will
	 * constrain  the solution space. It sets the function and constrained optimisation to true before returning
	 * the private method optimisation()
	 * 
	 * @param Function objectiveFunction
	 * @param double lowerLimit
	 * @param double upperLimit
	 * 
	 * @return Vector<Double>
	 */
	@Override
	public Vector<Double> constrainedOptimise(Function objectiveFunction,
			double upperLimit, double lowerLimit) {
		this.upperLimit = new double[objectiveFunction.getVariables() / binaryConverter.getBinaryEncoding()];
		Arrays.fill(this.upperLimit, upperLimit);
		this.lowerLimit = new double[objectiveFunction.getVariables() / binaryConverter.getBinaryEncoding()];
		Arrays.fill(this.lowerLimit, lowerLimit);
		this.objectiveFunction = objectiveFunction;
		constrainedOptimisation = true;
		return optimisation();
	}
	
	/**
	 * An unconstrained genetic optimisation method that returns a binary vector that is the best solution
	 * found by the swarm thus far.
	 * <p>
	 * The method sets constrained opitmisation to false and then returns the private method 
	 * geneticOptimisation()
	 * 
	 * @return Vector<Double>
	 */
	@Override
	public Vector<Double> geneticOptimise(){
		constrainedOptimisation = false;
		return geneticOptimisation();
	}
	
	/**
	 * An unconstrained optimisation method that returns a binary vector that is the best solution
	 * found by the swarm thus far.
	 * <p>
	 * The method sets the objective function and constrained opitmisation to false and then returns the private method 
	 * optimisation()
	 * 
	 * @return Vector<Double>
	 */
	 
	@Override
	public Vector<Double> optimise(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
		constrainedOptimisation = false;
		return optimisation();
	}
	
	/**
	 * Method that controls the pre-optimisation set-up process.
	 * <p>
	 * Initialises the FitnessCalculator and passes the boolean constrainedOptimisation to the PositionUpdate
	 * object. If the optimisation process is constrained the BinaryConverter is passed to the Initialiser and
	 * PositionUpdate as are the arrays of maximum and minimum values, and the particles are created within the 
	 * constrained feasible solution space. Else they are initialised in the binary space created by the number
	 * of particles and the binary encoding. The Fitness calculator caluclates the initial fitness of the particles 
	 * and the global best. The Halting criteria are updated.
	 */
	private void initiateSwarm(){
		calc = new FitnessCalculatorImpl(objectiveFunction, maximum);
		positionUpdate.setConstraints(constrainedOptimisation);
		if(constrainedOptimisation){
			((BinaryInitialiser)init).setBinaryConverter(getBinaryConveter());
			((BinaryConstrainer) ((BinaryPositionUpdate)positionUpdate).getConstrainer()).setBinaryConverter(getBinaryConveter());
			init.setMin(lowerLimit);
			init.setMax(upperLimit);
			positionUpdate.setMaximum(upperLimit);
			positionUpdate.setMinimum(lowerLimit);
			init.constrainedInitialiseMatrices(objectiveFunction, numberOfParticles);
		}else{
			init.initialiseMatrices(objectiveFunction, numberOfParticles);
		}
		setPosition(init.getPositions());
		setPersonalBest(init.getPersonalBest());
		setVelocities(init.getVelocities());
		calc.setPositions(getPosition());
		calc.initialCalculateFitness();
		setFitness(calc.getFitness());
		globalBest = calc.calculateGlobalBest();
		getVelocityUpdate().setVelocities(getVelocities());
		getVelocityUpdate().setMaximum(maximum);
		haltingCriteria.updateData(fitness.get(globalBest), 0);		
	}
	
	/**
	 * Intialises the genetic operator if it is null. Calls the private method initiateSwarm(). Enters an 
	 * optimisation loop using the boolean condition HaltingCriteria.halt(). Inside the loop the genetic operations are performed, then the optimisation process
	 * is constrained the Constrianer restricts the position of particles that have left the feasible solution 
	 * space. The private methods updateFitnessInformation(), updateVelocities(), updateParticlePositions(),
	 * updateFitnessInformation. Updated data is then passed to the halting criteria. A binary vector of the best solution found by the swarm is returned
	 * at the end of the optimisation process. 
	 * 
	 * @return Vector<Double>
	 */
	private Vector<Double> geneticOptimisation() {
		if(genOp == null){
			genOp = new GeneticOperatorImpl();
		}
		genOp.setObjectiveFunction(objectiveFunction);
		initiateSwarm();
		for(int i = 1; !haltingCriteria.halt(); i++){
			performGeneticOperations();
			updateFitnessInformation();
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
	 * Method that controls the execution of the genetic operations.
	 * <p>
	 * Passes the particle positions to the GeneticOperator and calls the method performGeneticOperations().
	 * If the feasible solution space is constrained a constrainer is used to limit the particle positions.
	 */
	public void performGeneticOperations(){
		genOp.setPositions(getPosition());
		genOp.performGeneticOperations();
		setPosition(genOp.getPositions());
		if(constrainedOptimisation){
			((BinaryPositionUpdate)getPositionUpdate()).getConstrainer().setPositions(position);
			((BinaryPositionUpdate)getPositionUpdate()).getConstrainer().constrain();
			setPosition(((BinaryPositionUpdate)getPositionUpdate()).getConstrainer().getPositions());
		}
	}
	
	/**
	 * Calls the private method initiateSwarm(). Enters an optimisation loop using the boolean condition
	 * HaltingCriteria.halt(). Inside the loop the private methods updateVelocities(), 
	 * updateParticlePositions() and updateFitnessInformation() are called. The HaltingCriteria's data is
	 * then updated. A binary vector of the best solution found by the swarm is returned
	 * at the end of the optimisation process. 
	 * 
	 * @return Vector<Double>
	 */
	private Vector<Double> optimisation() {
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
	 * Fitness calculator updates the fitness Map, global best index and particle positions
	 */
	private void updateFitnessInformation() {
		calc.setFitness(getFitness());
		calc.setPersonalBest(getPersonalBest());
		calc.setPositions(getPosition());
		calc.calculateFitness();
		setFitness(calc.getFitness());
		setPersonalBest(calc.getPersonalBest());
		setGlobalBest(calc.calculateGlobalBest());
	}
	
	/**
	 * Passes updated fitness map to the neighbourhood. Particle velocities are then updated.
	 */
	private void updateVelocities(){
		velocityUpdate.setVelocities(getVelocities());
		velocityUpdate.setPosition(getPosition());
		velocityUpdate.setPersonalBest(getPersonalBest());
		velocityUpdate.getNeighbourhood().setSolutionFitness(getFitness());
		setVelocities(velocityUpdate.updateVelocities());
	}
	
	/**
	 * Updates the particle's positions.
	 */
	private void updateParticlePositions() {
		positionUpdate.setVelocities(getVelocities());
		positionUpdate.setPositions(getPosition());
		positionUpdate.updatePositions();
		setVelocities(positionUpdate.getVelocities());
		setPosition(positionUpdate.getPositions());		
	}
	
	public BinaryConverter getBinaryConveter() {
		return binaryConverter;
	}

	public void setBinaryConveter(BinaryConverter binaryConveter) {
		this.binaryConverter = binaryConveter;
	}
	
	@Override
	public GeneticOperator getGenOp() {
		return genOp;
	}

	@Override
	public void setGenOp(GeneticOperator genOp) {
		this.genOp = genOp;
	}
	
	public PositionUpdate getPositionUpdate() {
		return positionUpdate;
	}

	public void setPositionUpdate(ConstrainedPositionUpdate positionUpdate) {
		this.positionUpdate = positionUpdate;
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

	public Map<Integer, Double> getFitness() {
		return fitness;
	}

	public void setFitness(Map<Integer, Double> fitness) {
		this.fitness = fitness;
	}

	public Initialiser getInit() {
		return init;
	}

	public void setInit(ConstrainedInitialiser init) {
		this.init = init;
	}

	public FitnessCalculator getFitnessCalculator() {
		return calc;
	}

	public void setFitnessCalculator(FitnessCalculator calc) {
		this.calc = calc;
	}

	public double[][] getVelocities() {
		return velocities;
	}

	@Override
	public Function getObjectiveFunction() {
		return objectiveFunction;
	}

	@Override
	public void setObjectiveFunction(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}
	
	/**
	 * Sets the boolean maximum. This value is also passed to the neighbourhood contained in the velocity update
	 * 
	 * @param boolean maximum
	 */
	@Override
	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
		velocityUpdate.getNeighbourhood().setMaximum(maximum);
	}

	public VelocityUpdate getVelocityUpdate() {
		return velocityUpdate;
	}

	public void setVelocityUpdate(VelocityUpdate velocityUpdate) {
		this.velocityUpdate = velocityUpdate;
		this.velocityUpdate.getNeighbourhood().setMaximum(getMaximum());
	}

	@Override
	public int getNumberOfParticles() {
		return numberOfParticles;
	}

	@Override
	public void setNumberOfParticles(int numberOfParticles) {
		this.numberOfParticles = numberOfParticles;
	}

	public int getGlobalBest() {
		return globalBest;
	}

	public void setGlobalBest(int globalBest) {
		this.globalBest = globalBest;
	}

	@Override
	public HaltingCriteria getHaltingCriteria() {
		return haltingCriteria;
	}

	@Override
	public void setHaltingCriteria(HaltingCriteria haltingCriteria) {
		this.haltingCriteria = haltingCriteria;
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

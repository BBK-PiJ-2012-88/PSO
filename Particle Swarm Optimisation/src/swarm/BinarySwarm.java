package swarm;

import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

public class BinarySwarm implements Swarm, GeneticSwarm, ConstrainedOptimisation, ConstrainedGeneticOptimisation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3796636603226088760L;

	private Function objectiveFunction;
	
	private double [][] personalBest;
	
	private double [][] position;
	
	private double [][] velocities;
	
	private double[] upperLimit;
	
	private double[] lowerLimit;
	
	private boolean constrainedOptimisation = false;

	private boolean maximum = false;
	
	private VelocityUpdate velocityUpdate = new ConstrictionCoefficient();
	
	private int numberOfParticles = 20;
	
	private int globalBest;
	
	private Map<Integer, Double> fitness;
	
	private HaltingCriteria haltingCriteria = new IterationHalt(100);
	
	private GeneticOperator genOp;
	
	private Initialiser init = new BinaryInitialiser();
	
	private FitnessCalculator calc;
	
	private PositionUpdate positionUpdate = new BinaryPositionUpdate();
	
	private BinaryConverter binaryConverter = new BinaryConverterImpl();
	
	public BinaryConverter getBinaryConveter() {
		return binaryConverter;
	}

	public void setBinaryConveter(BinaryConverter binaryConveter) {
		this.binaryConverter = binaryConveter;
	}

	public BinarySwarm(){}
	
	@Override
	public GeneticOperator getGenOp() {
		return genOp;
	}

	@Override
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
		if(constrainedOptimisation){
			((BinaryInitialiser)init).setBinaryConverter(getBinaryConveter());
			((BinaryConstrainer) ((BinaryPositionUpdate)positionUpdate).getConstrainer()).setBinaryConverter(getBinaryConveter());
			((BinaryInitialiser)init).setConstrainedInitialisation(constrainedOptimisation);
			((BinaryInitialiser)init).setLowerLimit(lowerLimit);
			((BinaryInitialiser)init).setUpperLimit(upperLimit);
			((BinaryConstrainer) ((BinaryPositionUpdate)positionUpdate).getConstrainer()).setMaximum(upperLimit);
			((BinaryConstrainer) ((BinaryPositionUpdate)positionUpdate).getConstrainer()).setMinimum(lowerLimit);
			((BinaryPositionUpdate)positionUpdate).setConstraints(constrainedOptimisation);
		}
		init.initialiseMatrices(objectiveFunction, numberOfParticles);
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
	
	@Override
	public Vector<Double> geneticOptimise(){
		constrainedOptimisation = false;
		return geneticOptimisation();
	}

	private Vector<Double> geneticOptimisation() {
		if(genOp == null){
			genOp = new GeneticOperatorImpl();
		}
		genOp.setObjectiveFunction(objectiveFunction);
		initiateSwarm();
		for(int i = 1; !haltingCriteria.halt(); i++){
			genOp.setPositions(getPosition());
			((GeneticOperatorImpl) genOp).setGlobalBest(globalBest);
			genOp.performGeneticOperations();
			setPosition(genOp.getPositions());
			if(constrainedOptimisation){
				((BinaryPositionUpdate)getPositionUpdate()).getConstrainer().setPositions(position);
				((BinaryPositionUpdate)getPositionUpdate()).getConstrainer().constrain();
				setPosition(((BinaryPositionUpdate)getPositionUpdate()).getConstrainer().getPositions());
			}
			updateFitnessInformation();
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
	
	@Override
	public Vector<Double> geneticOptimise(Function objectiveFunction){
		setObjectiveFunction(objectiveFunction);
		constrainedOptimisation = false;
		return geneticOptimisation();
	}
	
	@Override
	public Vector<Double> optimise(){
		constrainedOptimisation = false;
		return optimisation();
	}
	
	private Vector<Double> optimisation() {
		assert objectiveFunction != null;
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

	public PositionUpdate getPositionUpdate() {
		return positionUpdate;
	}

	public void setPositionUpdate(PositionUpdate positionUpdate) {
		this.positionUpdate = positionUpdate;
	}

	private void updateFitnessInformation() {
		calc.setFitness(getFitness());
		calc.setPersonalBest(getPersonalBest());
		calc.setPositions(getPosition());
		calc.calculateFitness();
		setFitness(calc.getFitness());
		setPersonalBest(calc.getPersonalBest());
		setGlobalBest(calc.calculateGlobalBest());
	}
	
	private void updateVelocities(){
		velocityUpdate.setVelocities(getVelocities());
		velocityUpdate.setPosition(getPosition());
		velocityUpdate.setPersonalBest(getPersonalBest());
		velocityUpdate.getNeighbourhood().setSolutionFitness(getFitness());
		setVelocities(velocityUpdate.updateVelocities());
	}
	
	private void updateParticlePositions() {
		positionUpdate.setVelocities(getVelocities());
		positionUpdate.setPositions(getPosition());
		positionUpdate.updatePositions();
		setVelocities(positionUpdate.getVelocities());
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

	@Override
	public Function getObjectiveFunction() {
		return objectiveFunction;
	}

	@Override
	public void setObjectiveFunction(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}

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

	@Override
	public Vector<Double> optimise(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
		constrainedOptimisation = false;
		return optimisation();
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

	@Override
	public Vector<Double> constrainedGeneticOptmise(Function objectiveFunction,
			double[] max, double[] min) {
		upperLimit = max;
		lowerLimit = min;
		constrainedOptimisation = true;
		this.objectiveFunction = objectiveFunction;
		return geneticOptimisation();
	}

	@Override
	public Vector<Double> constrainedGeneticOptimise(
			Function objectiveFunction, double upperLimit, double lowerLimit) {
		this.upperLimit = new double[objectiveFunction.getVariables() / binaryConverter.getBinaryEncoding()];
		Arrays.fill(this.upperLimit, upperLimit);
		this.lowerLimit = new double[objectiveFunction.getVariables() / binaryConverter.getBinaryEncoding()];
		Arrays.fill(this.lowerLimit, lowerLimit);
		constrainedOptimisation = true;
		return geneticOptimisation();
	}

	@Override
	public Vector<Double> constrainedOptimise(Function objectiveFunction,
			double[] max, double[] min) {
		upperLimit = max; 
		lowerLimit = min;
		this.objectiveFunction = objectiveFunction;
		constrainedOptimisation = true;
		return optimisation();
	}

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

}

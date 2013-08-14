package swarm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class CombinatorialSwarm implements Swarm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1179862233764569743L;

	private double[][] positions;
	
	private double[][] personalBest;
	
	private double[][] velocities;
	
	private double[][] prevPos;
	
	private double[][] prevPB;
	
	private double[][] prevVel;
	
	private Function objectiveFunction;
	
	private int numberOfParticles = 4;
	
	private HaltingCriteria haltingCriteria = new IterationHalt(6);
	
	private HashMap<Integer, Double> fitness;
	
	private HashMap<Integer, Double> prevFit;
	
	private CombinatorialVelocityUpdate combinatorialVelocityUpdate = new CombinatorialVelocityUpdate();
	
	private PositionUpdate positionUpdate = new CombinatorialPositionUpdate();
	
	private Initialiser init = new CombinatorialInitialiser();
	
	private FitnessCalculator calc;
	
	private int globalBest;
	
	private boolean maximum = false;
	
	private double epsilon = 0.00001;

	public void initiateCandidateSolutions(){
		int columns = objectiveFunction.getVariables();
		positions = new double[numberOfParticles][columns];
		velocities = new double[numberOfParticles * 2][columns];
		for(int i = 0; i < numberOfParticles * 2; i++){
			for(int k = 0; k < columns; k++){
				velocities[i][k] = 0;
				if(i < numberOfParticles){
					positions[i][k] = k + 1;
				}
			}
		}
		shuffleParticles(positions);
		personalBest = positions.clone();
		fitness = new HashMap<Integer, Double>();
		for(int i = 0; i < numberOfParticles; i++){
			fitness.put(i, objectiveFunction.CalculateFitness(positions[i]));
		}
	}
	
	private void initiateSwarm(){
		calc = new FitnessCalculatorImpl(objectiveFunction, maximum);
		init.initialiseMatrices(objectiveFunction, numberOfParticles);
		setVelocities(init.getVelocities());
		setPositions(init.getPositions());
		setPersonalBest(init.getPersonalBest());
		calc.setPositions(getPositions());
		calc.initialCalculateFitness();
		setFitness(calc.getFitness());
		//System.out.println("initial print");
	/*	for(int k = 0; k < positions[0].length; k++){
			System.out.print( positions[0][k] + ", ");
		}*/
		/*
		for(int i = 0; i < fitness.size(); i++){
			System.out.println(i + " initial " + fitness.get(i));
		}*/
		globalBest = calc.calculateGlobalBest();
		getVelocityUpdate().setVelocities(velocities);
		getVelocityUpdate().getNeighbourhood().setMaximum(getMaximum());
		haltingCriteria.updateData(fitness.get(globalBest), 0);
	}

	public HashMap<Integer, Double> getFitness() {
		return fitness;
	}

	public void setFitness(HashMap<Integer, Double> fitness) {
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

	private void shuffleParticles(double[][]particles) {
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < particles[i].length; k++){
				int index = i + (int)(Math.random() * ((particles.length - i) + 1));
				double temp = particles[i][k];
				particles[i][k] = particles[i][index];
				particles[i][index] = temp;
			}
		}
		
	}
	
	@Override
	public Vector<Double> optimise() {
		initiateSwarm();
		for(int i = 1; !haltingCriteria.halt(); i++){
			System.out.println("iteration: " + i);
			setPreviousVelocity();
			setPreviousPosition();
			setPreviousPersonalBest();
			setPreviousFitness();
			updateVelocities();
			updateParticlePositions();
			updateFitnessInformation();
			//System.out.println(i);
			/*for(int k = 0; k < positions[0].length; k++){
				System.out.print( positions[0][k] + ", ");
			}*/
		/*	for(int k = 0; k < fitness.size(); k++){
				System.out.println(k + " " + fitness.get(k));
			}*/
	
			check(i);
			/*for(int n = 0; n < velocities.length; n++){
				System.out.println(i);
				for(int k = 0; k < velocities[n].length; k++){
					System.out.print(velocities[n][k] + ", ");
				}
			}*/
			haltingCriteria.updateData(fitness.get(globalBest), i);
			System.out.println("gb value: " + fitness.get(globalBest));
			System.out.println("gb: " + globalBest);
		}
		Vector<Double> result = new Vector<Double>();
		for(int i = 0; i < personalBest[globalBest].length; i++){
			result.add(personalBest[globalBest][i]);
		}
		return result;
	}
	
	private void setPreviousFitness() {
		prevFit = new HashMap<Integer, Double>();
		for(int i = 0; i < fitness.size(); i++){
			prevFit.put(i, fitness.get(i));
		}
		
	}

	private void check(int i) {
		if(Arrays.deepEquals(prevPos, positions)){
			System.out.println("positions " + i);
		}
		if(Arrays.deepEquals(prevVel, velocities)){
			System.out.println("vel " + i);
		}
		if(Arrays.deepEquals(prevPB, personalBest)){
			System.out.println("PB " + i);
		}
		if(prevFit.equals(fitness)){
			System.out.println("fit " + i);
		} 
	}

	private void setPreviousPersonalBest() {
		prevPB = new double[numberOfParticles][objectiveFunction.getVariables()];
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < prevPB[i].length; k++){
				prevPB[i][k] = personalBest[i][k];
			}
		}
	}

	private void setPreviousPosition() {
		prevPos = new double[numberOfParticles][objectiveFunction.getVariables()];
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < prevPos[i].length; k++){
				prevPos[i][k] = positions[i][k];
			}
		}
	}

	private void setPreviousVelocity() {
		prevVel = new double[velocities.length][objectiveFunction.getVariables()];
		for(int i = 0; i < velocities.length; i++){
			//System.out.println();
			for(int k = 0; k < velocities[i].length; k++){
				prevVel[i][k] = velocities[i][k];
				//System.out.print(velocities[i][k] + " ");
			}
		}	
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
		combinatorialVelocityUpdate.getNeighbourhood().setSolutionFitness(calc.getFitness());
		setFitness(calc.getFitness());
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
	
	/*
	@Override
	public Vector<Double> optimise() {
		assert objectiveFunction != null;
		int iteration = 0;
		do{
			iteration++;
			if(iteration == 1){
				initiateCandidateSolutions();
			}else{
				combinatorialVelocityUpdate.setPosition(positions);
				combinatorialVelocityUpdate.setPersonalBest(personalBest);
				combinatorialVelocityUpdate.getNeighbourhood().setSolutionFitness(fitness);
				combinatorialVelocityUpdate.setVelocities(velocities);
				setVelocities(combinatorialVelocityUpdate.updateVelocities());
				normaliseVelocities();
				updateParticlePositions();
			}
			calculateFitness();
			calculateGlobalBest();
			System.out.println(globalBest + " " + fitness.get(globalBest));
			haltingCriteria.updateData(fitness.get(globalBest), iteration);
			}while(!haltingCriteria.halt());
			Vector<Double> result = new Vector<Double>();
			for(int i = 0; i < personalBest[globalBest].length; i++){
				result.add(personalBest[globalBest][i]);
			}
			return result;
	}*/

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

	private void updatePersonalBest(int row){
		for(int k = 0; k < positions[row].length; k++){
			personalBest[row][k] = positions[row][k];
		}
	}

	private void calculateFitness() {
		for(int i = 0; i < numberOfParticles; i++){
			double currentfitness = objectiveFunction.CalculateFitness(positions[i]);
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

	private void normaliseVelocities() {
		int rows = velocities.length / 2;
		for(int i = 0; i < velocities.length / 2; i++){
			for(int k = 0; k < velocities[i].length; k++){
				double denominator = 0;
				if(velocities[i][k] > velocities[i + rows][k]){
					denominator = velocities[i][k];
				}else{
					denominator = velocities[i + rows][k];
				}
				velocities[i][k] = velocities[i][k] / denominator;
				velocities[i + rows][k] = velocities[i + rows][k] / denominator;
			}
		}
		
	}
	
	/*
	private void updateParticlePositions() {
		for(int i = 0; i < velocities.length / 2; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(Math.abs(velocities[i][k] - 1.0) <= epsilon){
					double exchange = personalBest[i][k];
					for(int n = 0; n < positions[i].length; n++){
						if(Math.abs(positions[i][n] - exchange) <= epsilon){
							double temp = positions[i][k];
							positions[i][k] = positions[i][n];
							positions[i][n] = temp;
						}
					}
					velocities[i][k] = 0;
				}else{
					int index = combinatorialVelocityUpdate.getNeighbourhood().neighbourhoodBest(i);
					double exchange = personalBest[index][k];
					for(int n = 0; n < positions[i].length; n++){
						if(Math.abs(positions[i][n] - exchange) <= epsilon){
							double temp = positions[i][k];
							positions[i][k] = positions[i][n];
							positions[i][n] = temp;
						}
					}
					velocities[i + velocities.length / 2][k] = 0;
				}
			}
		}
		
	}*/

	private void setVelocities(double[][] velocities) {
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
}

package swarm;

import java.util.HashMap;
import java.util.Vector;

public class CombinatorialSwarm implements Swarm {
	
	private double[][] positions;
	
	private double[][] personalBest;
	
	private double[][] velocities;
	
	private Function objectiveFunction;
	
	private int numberOfParticles = 27;
	
	private HaltingCriteria haltingCriteria = new IterationHalt(4000);
	
	private HashMap<Integer, Double> fitness;
	
	private CombinatorialVelocityUpdate combinatorialVelocityUpdate = new CombinatorialVelocityUpdate();
	
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
			haltingCriteria.updateData(fitness.get(globalBest), iteration);
			Vector<Double> result = new Vector<Double>();
			for(int i = 0; i < personalBest[globalBest].length; i++){
				result.add(personalBest[globalBest][i]);
			}
			return result;
		}while(!haltingCriteria.halt());

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
		
	}

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

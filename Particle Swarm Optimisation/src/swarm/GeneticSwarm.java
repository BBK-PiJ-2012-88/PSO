package swarm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class GeneticSwarm extends BinarySwarm implements Swarm {
	//Need to check if the other swarms need a current and overall best fitness hashmaps
	//genetic swarm will need both.
	private double mutationProbability = 0.05;
	
	private List<ParticleFitness> sortedFitness;
	
	@Override
	public Vector<Double> optimise() {
		assert objectiveFunction != null;
		int iteration = 0;
		do{
			iteration++;
			if(position == null){
				initiateCandidateSolutions();
			}else{
				crossover();
				mutate();
				velocityUpdate.updateData(personalBest, position, fitness);
				setVelocities(velocityUpdate.updateVelocities());
				updateParticlePositions();
			}
			calculateFitness();
			//sort by fitness, global best is fittest.
			haltingCriteria.updateData(fitness.get(globalBest), iteration);
		}while(!haltingCriteria.halt());
		Vector<Double> result = new Vector<Double>();
		for(int i = 0; i < personalBest[globalBest].length; i++){
			result.add(personalBest[globalBest][i]);
		}
		return result;
	}
	
	@Override
	protected void calculateFitness(){
		for(int i = 0; i < numberOfParticles; i++){
			double currentfitness = objectiveFunction.CalculateFitness(position[i]);
			sortedFitness.get(i).setFitness(currentfitness);
			sortedFitness.get(i).setIndex(i);
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
		Collections.sort(sortedFitness);
	}
	
	private void mutate(){
		for(int i = 0; i < position.length; i++){
			for(int k = 0; k < position[i].length; k++){
				if(Math.random() < mutationProbability){
					if(position[i][k] < 1){
						position[i][k] = 1;
					}else{
						position[i][k] = 0;
					}
				}
			}
		}
	}
	
	private void crossover(){
		Random rand = new Random();
		if(maximum){
			for(int i = 0; i < sortedFitness.size() / 2; i++){
				int crossoverPoint = rand.nextInt(objectiveFunction.getVariables() + 1);
				int particleIndex = sortedFitness.get(i).getIndex();
				for(int k = 0; k < crossoverPoint; k++){
					position[particleIndex][k] = position[globalBest][k];
				}
			}
		}else{
			for(int i = sortedFitness.size(); i > sortedFitness.size() / 2; i--){
				int crossoverPoint = rand.nextInt(objectiveFunction.getVariables() + 1);
				int particleIndex = sortedFitness.get(i).getIndex();
				for(int k = 0; k < crossoverPoint; k++){
					position[particleIndex][k] = position[globalBest][k];
				}
			}
		}
	}
	
	@Override
	protected void initiateCandidateSolutions(){
		super.initiateCandidateSolutions();
		sortedFitness = new ArrayList<ParticleFitness>();
		for(int i = 0; i < numberOfParticles; i++){
			ParticleFitness p = new ParticleFitness(i, objectiveFunction.CalculateFitness(position[i]));
		}
	}

}

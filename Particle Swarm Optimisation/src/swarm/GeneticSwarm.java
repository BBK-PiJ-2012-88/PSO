package swarm;

import java.util.Vector;

public class GeneticSwarm extends BinarySwarm implements Swarm {

	private double mutationProbability = 0.05;
	
	@Override
	public Vector<Double> optimise() {
		assert objectiveFunction != null;
		int iteration = 0;
		do{
			iteration++;
			if(position == null){
				initiateCandidateSolutions();
			}else{
				velocityUpdate.updateData(personalBest, position, fitness);
				setVelocities(velocityUpdate.updateVelocities());
				updateParticlePositions();
			}
			calculateFitness();
			//sort by fitness, global best is fittest.
			haltingCriteria.updateData(fitness.get(globalBest), iteration);
			if(!haltingCriteria.halt()){
				crossover();
				mutate();
			}
		}while(!haltingCriteria.halt());
		Vector<Double> result = new Vector<Double>();
		for(int i = 0; i < personalBest[globalBest].length; i++){
			result.add(personalBest[globalBest][i]);
		}
		return result;
	}
	
	@Override
	protected void calculateFitness(){
		
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
		
	}

}

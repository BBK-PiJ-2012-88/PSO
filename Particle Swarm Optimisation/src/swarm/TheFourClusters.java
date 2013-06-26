package swarm;

import java.util.HashMap;

public class TheFourClusters implements Neighbourhood {

	private HashMap<Integer, Double> fitness;
	private TheWheel neighbourhood = new TheWheel();
	private boolean maximum = false;
	
	public TheFourClusters(){}
	
	public TheFourClusters(boolean maximum){
		this.maximum = maximum;
	}
	
	@Override
	public void setSolutionFitness(HashMap<Integer, Double> fitness) {
		assert(fitness.size() >= 16 && fitness.size() % 4 == 0);
		this.fitness = fitness;
	}

	@Override
	public int neighbourhoodBest(int particle){
		assert fitness != null;
		neighbourhood.setMaximum(maximum);
		int bottom = getBottomOfWheel(particle);
		neighbourhood.setSolutionFitness(getSubFitness(bottom, bottom + bottom));
		int result = neighbourhood.neighbourhoodBest(particle);
		return getFitness(bottom, result, particle);
	}
	
	private int getFitness(int bottom, int result, int particle) {
		if(bottom == 0){
			if(particle == 1 || particle == 2 || particle == 3){
				return compareFitness(result, particle + bottom);
			}else{
				return result;
			}
		}else if(bottom == fitness.size()){
			if(particle == bottom){
				return compareFitness(result, particle - bottom);
			}else if(particle == bottom + 2 || particle == bottom + 3){
				return compareFitness(result, particle + bottom);
			}else{
				return result;
			}
		}else if(bottom == fitness.size() * 2){
			if(particle == bottom || particle == bottom + 1){
				return compareFitness(result, particle - bottom);
			}else if(particle == bottom + 3){
				return compareFitness(result, particle + bottom);
			}else{
				return result;
			}
		}else{
			if(particle == bottom || particle == bottom + 1 || particle == bottom + 2){
				return compareFitness(result, particle - bottom);
			}else{
				return result;
			}
		}
	}

	private int getBottomOfWheel(int particle) {
		int segment = fitness.size() / 4;
		if(particle < segment){
			return 0;
		}else if(particle < segment * 2){
			return segment;
		}else if(particle < segment * 3){
			return segment * 2;
		}else{
			return segment * 3;
		}
	}

	private int compareFitness(int particle1, int particle2){
		double p1 = fitness.get(particle1);
		double p2 = fitness.get(particle2);
		if(maximum){
			if(p1 > p2){
				return particle1;
			}else{
				return particle2;
			}
		}else{
			if(p1 < p2){
				return particle1;
			}else{
				return particle2;
			}
		}
	}

	private HashMap<Integer, Double> getSubFitness(int bottom, int top) {
		HashMap<Integer, Double> result = new HashMap<Integer, Double>();
		for(int i = 0; bottom < top; i++){
			result.put(i, fitness.get(bottom));
			bottom++;
		}
		return result;
	}

}
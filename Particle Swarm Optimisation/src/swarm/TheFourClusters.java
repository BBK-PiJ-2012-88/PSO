package swarm;

import java.util.HashMap;
import java.util.Map;

public class TheFourClusters implements Neighbourhood {

	private Map<Integer, Double> fitness;
	private TheStar neighbourhood = new TheStar();
	private boolean maximum = false;
	
	public TheFourClusters(){}
	
	public TheFourClusters(boolean maximum){
		this.maximum = maximum;
		neighbourhood.setMaximum(maximum);
	}
	
	@Override
	public void setSolutionFitness(Map<Integer, Double> fitness) {
		assert(fitness.size() >= 16 && fitness.size() % 4 == 0);
		this.fitness = fitness;
	}

	@Override
	public int neighbourhoodBest(int particle){
		assert fitness != null;
		neighbourhood.setMaximum(maximum);
		int bottom = getBottomOfStar(particle);
		neighbourhood.setSolutionFitness(getSubFitness(bottom, bottom + fitness.size() / 4));
		int result = neighbourhood.neighbourhoodBest(particle) + bottom;
		return getFitness(bottom, result, particle);
	}
	
	private int getFitness(int bottom, int result, int particle) {
		int segment = fitness.size() / 4;
		if(bottom == 0){
			if(particle == 1 || particle == 2 || particle == 3){
				return compareFitness(result, segment * particle);
			}else{
				return result;
			}
		}else if(bottom == segment){
			if(particle == bottom){
				return compareFitness(result, 1);
			}else if(particle == bottom + 2 || particle == bottom + 3){
				return compareFitness(result, (particle - bottom) * segment + 1);
			}else{
				return result;
			}
		}else if(bottom == segment * 2){
			if(particle == bottom || particle == bottom + 1){
				return compareFitness(result, (particle - bottom) * segment + 2);
			}else if(particle == bottom + 3){
				return compareFitness(result, particle + segment);
			}else{
				return result;
			}
		}else{
			if(particle == bottom || particle == bottom + 1 || particle == bottom + 2){
				return compareFitness(result, (particle - bottom) * segment + 3);
			}else{
				return result;
			}
		}
	}

	private int getBottomOfStar(int particle) {
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

	@Override
	public boolean getMaximum() {
		return maximum;
	}

	@Override
	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
	}
	
	@Override
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append(this.getClass().toString() + ", ");
		buff.append("maximum: " + maximum);
		return buff.toString();
	}

}
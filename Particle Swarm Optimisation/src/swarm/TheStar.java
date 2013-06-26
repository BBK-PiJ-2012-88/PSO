package swarm;

import java.util.HashMap;

public class TheStar implements Neighbourhood {
	
	private HashMap<Integer, Double> solutionFitness;
	private boolean maximum = false;
	
	public TheStar(){}
	
	public TheStar(boolean maximum){
		this.maximum = maximum;
	}
	
	public void setMaximum(boolean maximum){
		this.maximum = maximum;
	}
	
	@Override
	public void setSolutionFitness(HashMap<Integer, Double> solutionFitness) {
		this.solutionFitness = solutionFitness;
	}

	@Override
	public int neighbourhoodBest(int particle) {
		int best = 0;
		for(int i = 1; i < solutionFitness.size(); i++){
			if(maximum){
				if(solutionFitness.get(i) > solutionFitness.get(best)){
					best = i;
				}
			}else{
				if(solutionFitness.get(i) < solutionFitness.get(best)){
					best = i;
				}
			}
		}
		return best;
	}

}
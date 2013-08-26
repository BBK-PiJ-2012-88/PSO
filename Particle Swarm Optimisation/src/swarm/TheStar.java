package swarm;

import java.util.Map;

public class TheStar implements Neighbourhood {
	
	private Map<Integer, Double> solutionFitness;
	private boolean maximum = false;
	
	public TheStar(){}
	
	public TheStar(boolean maximum){
		this.maximum = maximum;
	}
	
	@Override
	public void setMaximum(boolean maximum){
		this.maximum = maximum;
	}
	
	@Override
	public boolean getMaximum(){
		return maximum;
	}
	
	@Override
	public void setSolutionFitness(Map<Integer, Double> solutionFitness) {
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
	
	@Override
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append(this.getClass().toString() + ", ");
		buff.append("maximum: " + maximum);
		return buff.toString();
	}
}
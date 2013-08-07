package swarm;

import java.util.HashMap;

public class TheWheel implements Neighbourhood{
	
	private HashMap<Integer, Double> fitness;
	private boolean maximum = false;
	private int wheel = 0;
	
	public TheWheel(){}
	
	public TheWheel(boolean maximum){
		this.maximum = maximum;
	}
	
	@Override
	public void setSolutionFitness(HashMap<Integer, Double> fitness) {
		this.fitness = fitness;
	}

	@Override
	public int neighbourhoodBest(int particle) {
		if(particle == wheel){
			int best = 0;
			for(int i = 1; i < fitness.size(); i++){
				if(maximum){
					if(fitness.get(i) > fitness.get(best)){
						best = i;
					}
				}else{
					if(fitness.get(i) < fitness.get(best)){
						best = i;
					}
				}
			}
			return best;
		}else{
			if(maximum){
				if(fitness.get(particle) > fitness.get(wheel)){
					return particle;
				}else{
					return wheel;
				}
			}else if(fitness.get(particle) < fitness.get(wheel)){
				return particle;
			}else{
				return wheel;
			}
		}
	}
	
	public void setMaximum(boolean maximum){
		this.maximum = maximum;
	}
	
	public boolean getMaximum(){
		return maximum;
	}
	
	public int getWheel(){
		return wheel;
	}
	
	public void setWheel(int wheel){
		this.wheel = wheel;
	}
	
	@Override
	public String toString(){
		StringBuffer buff =  new StringBuffer();
		buff.append(this.getClass().toString() + ", ");
		buff.append("maximum: " + maximum);
		return buff.toString();
	}
}
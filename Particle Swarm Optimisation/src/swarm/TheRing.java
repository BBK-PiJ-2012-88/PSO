package swarm;

import java.util.Map;

public class TheRing implements Neighbourhood {
	
	private Map<Integer, Double> fitness;
	private boolean maximum = false;
	
	public TheRing(){}
	
	public TheRing(boolean maximum){
		this.maximum = maximum;
	}
	
	/* (non-Javadoc)
	 * @see swarm.Neighbourhood#setfitness(java.util.HashMap)
	 */
	@Override
	public void setSolutionFitness(Map<Integer, Double> fitness){
		this.fitness = fitness;
	}
	
	/* (non-Javadoc)
	 * @see swarm.Neighbourhood#neighbourhoodBest(int)
	 */
	@Override
	public int neighbourhoodBest(int particle){
		if(particle == fitness.size() - 1){
			return calculateNeighbourhoodBest(0, particle - 1, particle);
		}else if(particle == 0){
			return calculateNeighbourhoodBest(0, 1, fitness.size() - 1);
		}else{
			return calculateNeighbourhoodBest(particle - 1, particle, particle + 1);
		}
	}
	
	private int calculateNeighbourhoodBest(int i, int k, int j){
		if(maximum){
			return maxBest(i,j,k);
		}else{
			return minBest(i,j,k);
		}
	}
	
	private int maxBest(int i, int j, int k){
		double di = fitness.get(i);
		double dj = fitness.get(j);
		double dk = fitness.get(k);
		if(di >= dj && di >= dk){
			return i;
		}else if(dj >= di && dj >= dk){
			return j;
		}else{
			return k;
		}
	}
	
	private int minBest(int i, int j, int k){
		double di = fitness.get(i);
		double dj = fitness.get(j);
		double dk = fitness.get(k);
		if(di <= dj && di <= dk){
			return i;
		}else if(dj <= di && dj <= dk){
			return j;
		}else{
			return k;
		}
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
	public String toString(){
		StringBuffer buff =  new StringBuffer();
		buff.append(this.getClass().toString() + ", ");
		buff.append("maximum: " + maximum);
		return buff.toString();
	}
}

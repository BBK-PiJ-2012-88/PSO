package swarm;

import java.io.Serializable;

import java.util.Map;

public interface Neighbourhood extends Serializable {

	public abstract void setSolutionFitness(
			Map<Integer, Double> solutionFitness);

	public abstract int neighbourhoodBest(int particle);
	
	public abstract boolean getMaximum();
	
	public abstract void setMaximum(boolean maximum);

}
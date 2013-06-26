package swarm;

import java.util.HashMap;

public interface Neighbourhood {

	public abstract void setSolutionFitness(
			HashMap<Integer, Double> solutionFitness);

	public abstract int neighbourhoodBest(int particle);

}
package swarm;

import java.util.Vector;

public interface ConstrainedGeneticOptimisation {
	
	Vector<Double> constrainedGeneticOptmise(Function objectiveFunction, double[] max, double[] min);
	
	Vector<Double> constrainedGeneticOptimise(Function objectiveFunction, double upperLimit, double lowerLimit);
}
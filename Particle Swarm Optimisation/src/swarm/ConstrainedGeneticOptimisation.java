package swarm;

import java.io.Serializable;
import java.util.Vector;

public interface ConstrainedGeneticOptimisation extends Serializable, ConstrainedOptimisation, GeneticSwarm {
	
	Vector<Double> constrainedGeneticOptmise(Function objectiveFunction, double[] max, double[] min);
	
	Vector<Double> constrainedGeneticOptimise(Function objectiveFunction, double upperLimit, double lowerLimit);
}

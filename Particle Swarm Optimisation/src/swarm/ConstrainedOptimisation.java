package swarm;

import java.io.Serializable;
import java.util.Vector;

public interface ConstrainedOptimisation extends Serializable {
	
	Vector<Double> constrainedOptimise(Function objectiveFunction, double[] max, double[] min);
	
	Vector<Double> constrainedOptimise(Function objectiveFunction, double upperLimit, double lowerLimit);
	
}

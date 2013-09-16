package swarm;

import java.io.Serializable;
import java.util.Vector;
/**
 * Swarm optimisation methods for constrained optimisation (feasible solution space is restricted).
 * 
 * @author williamhogarth
 *
 */
public interface ConstrainedOptimisation extends Serializable, Swarm {
	
	/**
	 * Swarm attempts to find the best solution within the feasible search space. The feasible search space can 
	 * differ across the dimensions
	 * 
	 * @param objectiveFunction Function to be optimised
	 * @param max upper limit of the search space (dimension independent)
	 * @param min  lower limit of the search space (dimension independent)
	 * @return Vector<Double> best solution found by the swarm
	 */
	Vector<Double> constrainedOptimise(Function objectiveFunction, double[] max, double[] min);
	
	/**
	 * Swarm attempts to find the best solution within the feasible search space. The feasible search space is the 
	 * same in each dimension
	 * 
	 * @param objectiveFunction Function to be optimised
	 * @param upperLimit upper limit of search space
	 * @param lowerLimit lower limit of search space
	 * @return
	 */
	Vector<Double> constrainedOptimise(Function objectiveFunction, double upperLimit, double lowerLimit);
	
}

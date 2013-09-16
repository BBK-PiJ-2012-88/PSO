package swarm;

import java.io.Serializable;
import java.util.Vector;

/**
 * Swarm methods for constrained optimisation that also use genetic operators
 * 
 * @author williamhogarth
 */
public interface ConstrainedGeneticOptimisation extends Serializable, ConstrainedOptimisation, GeneticSwarm {
	
	/**
	 * Swarm attempts to find the best solution possible within the feasible search space using a hybrid particle
	 * swarm and genetic algorithm. The feasible solution space can differ in different dimensions.
	 * 
	 * @param objectiveFunction, function being optimised
	 * @param max, upper limit of the feasible solution space (dimension independent)
	 * @param min, lower limit of the feasible solution space (dimension independent)
	 * @return Vector<Double> the best solution found by the swarm in the feasible space
	 */
	Vector<Double> constrainedGeneticOptmise(Function objectiveFunction, double[] max, double[] min);
	
	/**
	 * Swarm attempts to find the best solution possible within the feasible search space using a hybrid particle
	 * swarm and genetic algorithm. the feasible solution space is the same in all dimensions.
	 * 
	 * @param objectiveFunction Function being optimised
	 * @param upperLimit, upper limit of the feasible solution space  
	 * @param lowerLimit lower limit of the feasible solution space
	 * @return Vector<Double> the best solution found by the swarm in the feasible space
	 */
	Vector<Double> constrainedGeneticOptimise(Function objectiveFunction, double upperLimit, double lowerLimit);
}

package swarm;

import java.io.Serializable;
import java.util.Vector;

public interface GeneticSwarm extends Swarm, Serializable{
	
	public GeneticOperator getGenOp();
	
	public void setGenOp(GeneticOperator geneticOperator);
	
	public Vector<Double> geneticOptimise();
	
	public Vector<Double> geneticOptimise(Function objectiveFunction);
}

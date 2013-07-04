package swarm;

import java.util.Vector;

public interface Swarm {

	//going to need to sort out the null thing or it
	//won't initiate solutions each time.
	public abstract Vector<Double> optimise();

	public abstract Vector<Double> optimise(Function objectiveFunction);

}
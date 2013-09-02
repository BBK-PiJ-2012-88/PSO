package swarm;

public interface ConstrainedInitialiser extends Initialiser {	
	
	public double[] getMax();
	
	public void setMax(double[] max);
	
	public double[] getMin();
	
	public void setMin(double[] min);
	
	public void constrainedInitialiseMatrices(Function objectiveFunction, int numberOfParticles);
}

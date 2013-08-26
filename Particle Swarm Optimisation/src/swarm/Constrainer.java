package swarm;

public interface Constrainer {
	
	public void setPositions(double[][] positions);
	
	public double[][] getPositions();
	
	public void constrain();
	
	public double[] getMaximum();
	
	public void setMaximum(double[] maximum);
	
	public double[] getMinimum();
	
	public void setMinimum(double[] minimum);
}

package swarm;

public interface ConstrainedPositionUpdate extends PositionUpdate {
	
	public boolean isConstraints();
	
	public void setConstraints(boolean constraints);
	
	public void setConstrainer(Constrainer constrainer);
	
	public Constrainer getConstrainer();
	
	public double[] getMaximum();
	
	public void setMaximum(double[] maximum);
	
	public double[] getMinimum();
	
	public void setMinimum(double[] minimum);
}

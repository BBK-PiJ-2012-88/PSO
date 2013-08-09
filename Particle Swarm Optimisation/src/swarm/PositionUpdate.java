package swarm;

import java.io.Serializable;

public interface PositionUpdate extends Serializable{
	
	public void setPositions(double[][] positions);
	
	public void setVelocities(double[][] velocities);
	
	public double[][] getPositions();
	
	public double[][] getVelocities();
	
	public void updatePositions();
}

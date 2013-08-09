package swarm;

import java.io.Serializable;

public interface Initialiser extends Serializable {
	
	public void initialiseMatrices(Function objectiveFunction, int numberOfParticles);
	
	public double[][] getPositions();
	
	public double[][] getVelocities();
	
}

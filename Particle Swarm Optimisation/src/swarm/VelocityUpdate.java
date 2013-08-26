package swarm;

import java.io.Serializable;

public interface VelocityUpdate extends Serializable {

	double[][] updateVelocities();
	
	double[][] getVelocities();
	
	void setVelocities(double[][] velocities);
	
	double[][] getPersonalBest();
	
	void setPersonalBest(double[][] personalBest);
	
	double[][] getPosition();
	
	void setPosition(double[][] position);
	
	Neighbourhood getNeighbourhood();
	
	void setNeighbourhood(Neighbourhood neighbourhood);
	
	boolean isMaximum();
	
	void setMaximum(boolean maximum);

}
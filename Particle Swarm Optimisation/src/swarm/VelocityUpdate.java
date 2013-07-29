package swarm;

import java.util.HashMap;

public interface VelocityUpdate {

	double[][] updateVelocities();
	
	double[][] getVelocities();
	
	void setVelocities(double[][] velocities);
	
	double[][] getPersonalBest();
	
	void setPersonalBest(double[][] personalBest);
	
	double[][] getPosition();
	
	void setPosition(double[][] position);
	
	Neighbourhood getNeighbourhood();
	
	void setNeighbourhood(Neighbourhood neighbourhood);

}
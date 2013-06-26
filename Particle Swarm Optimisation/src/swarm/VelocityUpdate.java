package swarm;

import java.util.HashMap;

public interface VelocityUpdate {

	double[][] updateVelocities();
	
	void updateData(double[][] personalBest, double[][] position,
			HashMap<Integer, Double> fitness);
	
	double[][] getVelocities();
	
	void setVelocities(double[][] velocities);
	
	double[][] getPersonalBest();
	
	void setPersonalBest(double[][] personalBest);
	
	double[][] getPosition();
	
	void setPosition(double[][] position);
	
	Neighbourhood getNeighbourhood();
	
	void setNeighbourhood(Neighbourhood neighbourhood);
	
	HashMap<Integer, Double> getFitness();
	
	void setFitness(HashMap<Integer, Double> fitness);
	
	double getSocialConstant();
	
	double getCognitiveConstant();
	
	void setConstants(double cognitiveConstant, double socialConstant, double K);

}
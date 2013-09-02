package swarm;

import java.io.Serializable;
import java.util.Vector;

public interface Swarm extends Serializable {

	public abstract Vector<Double> optimise();

	public abstract Vector<Double> optimise(Function objectiveFunction);
	
	public abstract void setMaximum(boolean maximum);
	
	public abstract boolean getMaximum();
	
	public abstract void setHaltingCriteria(HaltingCriteria haltingCriteria);
	
	public abstract HaltingCriteria getHaltingCriteria();
	
	public abstract int getNumberOfParticles();
	
	public abstract void setNumberOfParticles(int numberOfParticles);
	
	public abstract void setNeighbourhood(Neighbourhood neighbourhood);
	
	public abstract Function getObjectiveFunction();
	
	public abstract void setObjectiveFunction(Function objectiveFunction);
	
	public abstract VelocityUpdate getVelocityUpdate();
	
	public abstract void setVelocityUpdate(VelocityUpdate velocityUpdate);
	
	public abstract FitnessCalculator getFitnessCalculator();
	
	public abstract void setFitnessCalculator(FitnessCalculator fitnessCalculator);
}
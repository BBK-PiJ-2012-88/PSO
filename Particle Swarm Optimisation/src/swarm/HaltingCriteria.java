package swarm;

public interface HaltingCriteria {
	
	void updateData(double gbestFitness, int iteration);
	
	boolean halt();
}

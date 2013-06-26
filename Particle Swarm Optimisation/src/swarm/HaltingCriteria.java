package swarm;

public interface HaltingCriteria {
	
	void updateData(int gbest, double gbestFitness, int iteration);
	
	boolean halt();
}

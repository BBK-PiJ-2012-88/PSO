package swarm;

public class IterationHalt implements HaltingCriteria {

	private int currentIteration = 0;
	private int maxIterations = 200;
	
	public IterationHalt(int maxIterations){
		this.maxIterations = maxIterations;
	}
	
	public IterationHalt(){}
	
	@Override
	public void updateData(int gbest, double gbestFitness, int iteration) {
		currentIteration = iteration;
	}

	@Override
	public boolean halt() {
		if(currentIteration == maxIterations){
			return true;
		}else{
			return false;
		}
	}

}

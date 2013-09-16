package swarm;

public class IterationHalt implements HaltingCriteria {

	private int currentIteration = 0;
	private int maxIterations = 200;
	
	public IterationHalt(int maxIterations){
		this.maxIterations = maxIterations;
	}
	
	public IterationHalt(){}
	
	@Override
	public void updateData(double gbestFitness, int iteration) {
		currentIteration = iteration;
	}
	
	private void reset(){
		currentIteration = 0;
	}
	@Override
	public boolean halt() {
		if(currentIteration == maxIterations){
			reset();
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append(this.getClass().toString() + ", ");
		buff.append("max iterations: " + maxIterations);
		return buff.toString();
	}

}

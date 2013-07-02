package swarm;

public class AcceptableSolutionHalt implements HaltingCriteria {

	private boolean maximum = false;
	private double acceptableSolution;
	private double gBest;
	
	public AcceptableSolutionHalt(){}
	
	public AcceptableSolutionHalt(boolean maximum){
		this.maximum = maximum;
	}
	
	public boolean isMaximum() {
		return maximum;
	}

	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
	}

	public double getAcceptableSolution() {
		return acceptableSolution;
	}

	public void setAcceptableSolution(double acceptableSolution) {
		this.acceptableSolution = acceptableSolution;
	}

	@Override
	public void updateData(int gbest, double gbestFitness, int iteration) {
		gBest = gbestFitness;
		
	}

	@Override
	public boolean halt() {
		if(maximum){
			if(gBest >= acceptableSolution){
				return true;
			}else{
				return false;
			}
		}else{
			if(gBest <= acceptableSolution){
				return true;
			}else{
				return false;
			}
		}
	}

}

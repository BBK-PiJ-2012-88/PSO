package swarm;

public class AcceptableError implements HaltingCriteria {
	
	private double solution;
	private double error;
	private boolean maximum = false;
	private double gBest;
	
	public AcceptableError(double solution, double error){
		this.solution = solution;
		this.error = error;
	}
	
	public AcceptableError(double solution, double error, boolean maximum){
		this(solution, error);
		this.maximum = maximum;
	}

	public double getSolution() {
		return solution;
	}

	public void setSolution(double solution) {
		this.solution = solution;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public boolean isMaximum() {
		return maximum;
	}

	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
	}

	@Override
	public void updateData(int gbest, double gbestFitness, int iteration) {
		this.gBest = gbest;
		
	}

	@Override
	public boolean halt() {
		if(gBest >= solution - error && gBest <= solution + error){
			return true;
		}else{
			return false;
		}
	}

}

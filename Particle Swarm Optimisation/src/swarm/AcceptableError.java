package swarm;

public class AcceptableError implements HaltingCriteria {
	
	private double solution;
	private double error;
	private double gBest;
	
	public AcceptableError(double solution, double error){
		this.solution = solution;
		this.error = error;
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

	@Override
	public void updateData(double gbestFitness, int iteration) {
		this.gBest = gbestFitness;
		
	}

	@Override
	public boolean halt() {
		if(gBest >= solution - error && gBest <= solution + error){
			return true;
		}else{
			return false;
		}
	}
	
	@Override 
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("AcceptableError, ");
		buff.append("solution = " + solution);
		buff.append(", error = " + error);
		return buff.toString();
	}
}

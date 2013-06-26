package swarm;

public class GradientHalt implements HaltingCriteria {

	private double currentGBest;
	private Double previousGBest;
	private double error;
	
	public GradientHalt(double error){
		this.error = error;
	}
	
	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	@Override
	public void updateData(int gbest, double gbestFitness, int iteration) {
		previousGBest = currentGBest;
		currentGBest = gbestFitness;
	}

	@Override
	public boolean halt() {
		if(previousGBest != null){
			double gradient = (currentGBest - previousGBest) / currentGBest;
			if(gradient < 0){
				gradient = gradient * -1;
			}
			if(gradient <= error){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

}

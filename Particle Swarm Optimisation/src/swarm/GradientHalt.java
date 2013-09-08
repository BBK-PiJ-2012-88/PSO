package swarm;

public class GradientHalt implements HaltingCriteria {

	private double currentGBest;
	private Double previousGBest;
	private double gradient;
	
	public GradientHalt(double gradient){
		this.gradient = gradient;
	}
	
	public double getError() {
		return gradient;
	}

	public void setError(double gradient) {
		this.gradient = gradient;
	}

	@Override
	public void updateData(double gbestFitness, int iteration) {
		previousGBest = currentGBest;
		currentGBest = gbestFitness;
	}

	@Override
	public boolean halt() {
		if(previousGBest != null){
			double currentGradient = (currentGBest - previousGBest) / currentGBest;
			if(Math.abs(currentGradient) <= Math.abs(gradient)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	@Override
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append(this.getClass().toString() + ", ");
		buff.append("gradient: " + gradient);
		return buff.toString();
	}
}

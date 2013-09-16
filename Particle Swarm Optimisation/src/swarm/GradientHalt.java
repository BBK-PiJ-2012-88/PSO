package swarm;

public class GradientHalt implements HaltingCriteria {

	private double currentGBest;
	private Double previousGBest;
	private double gradient;
	private int iterations = 3;
	private int counter;
	
	public GradientHalt(double gradient){
		this.gradient = gradient;
	}
	
	public double getError() {
		return gradient;
	}
	
	public void setIterations(int iterations){
		this.iterations = iterations;
	}
	
	public int getIterations(){
		return iterations;
	}

	public void setError(double gradient) {
		this.gradient = gradient;
	}
	
	public int getCounter(){
		return counter;
	}

	@Override
	public void updateData(double gbestFitness, int iteration) {
		previousGBest = currentGBest;
		currentGBest = gbestFitness;
	}
	
	private void reset(){
		counter = 0;
		currentGBest = 0.0;
		previousGBest = null; 
	}

	@Override
	public boolean halt() {
		if(previousGBest != null){
			double currentGradient = (currentGBest - previousGBest) / currentGBest;
			if(Math.abs(currentGradient) <= Math.abs(gradient)){
				counter++;
				if(counter == iterations){
					reset();
					return true;
				}else{
					return false;
				}	
			}else{
				counter = 0;
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

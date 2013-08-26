package swarm;

public class VanillaConstrainer implements Constrainer {
	
	private double[][] positions;
	
	private double[] maximum;
	
	private double[] minimum;
	
	@Override
	public void setPositions(double[][] positions) {
		this.positions = positions;

	}

	@Override
	public double[][] getPositions() {
		return positions;
	}

	@Override
	public void constrain() {
		for(int i = 0; i < positions.length; i++){
			for(int k = 0; k < positions[i].length; k++){
				if(positions[i][k] > maximum[k]){
					positions[i][k] = maximum[k];
				}else if(positions[i][k] < minimum[k]){
					positions[i][k] = minimum[k];
				}
			}
		}

	}

	@Override
	public double[] getMaximum() {
		return maximum;
	}

	@Override
	public void setMaximum(double[] maximum) {
		this.maximum = maximum;

	}

	@Override
	public double[] getMinimum() {
		return minimum;
	}

	@Override
	public void setMinimum(double[] minimum) {
		this.minimum = minimum;
	}

}

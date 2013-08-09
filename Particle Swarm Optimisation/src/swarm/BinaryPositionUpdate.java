package swarm;

public class BinaryPositionUpdate implements PositionUpdate {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4165216294679962913L;

	private double[][] positions;
	
	private double[][] velocities;
	
	private SigmoidFunction sigmoidFunction = new SigmoidFunction();
	
	@Override
	public void setPositions(double[][] positions) {
		this.positions = positions;

	}

	@Override
	public void setVelocities(double[][] velocities) {
		this.velocities = velocities;

	}

	@Override
	public double[][] getPositions() {
		return positions;
	}

	@Override
	public double[][] getVelocities() {
		return velocities;
	}

	@Override
	public void updatePositions() {
		double random = Math.random();
		for(int i = 0; i < positions.length; i++){
			for(int k = 0; k < positions[i].length; k++){
				if(sigmoidFunction.normalise(velocities[i][k]) > random){
					positions[i][k] = 1;
				}else{
					positions[i][k] = 0;
				}
			}
		}
	}

}

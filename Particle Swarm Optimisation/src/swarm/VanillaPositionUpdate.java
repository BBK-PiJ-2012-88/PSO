package swarm;

public class VanillaPositionUpdate implements PositionUpdate {
	
	private double[][] positions;
	
	private double[][] velocities;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3703054233361770781L;

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
		for(int i = 0; i < positions.length; i++){
			for(int k = 0; k < positions[i].length; k++){
				positions[i][k] = positions[i][k] + velocities[i][k];
			}
		}
	}

}
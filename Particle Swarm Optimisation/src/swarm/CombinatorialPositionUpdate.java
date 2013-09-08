package swarm;

public class CombinatorialPositionUpdate implements PositionUpdate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1119134068866688082L;

	private double[][] positions;
	
	private double[][] velocities;
	
	private double[][] personalBest;
	
	private double epsilon = 0.00001;
	
	private Neighbourhood neighbourhood;
	
	public Neighbourhood getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(Neighbourhood neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public double[][] getPersonalBest() {
		return personalBest;
	}

	public void setPersonalBest(double[][] personalBest) {
		this.personalBest = personalBest;
	}

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
		int rows = velocities.length / 2;
		for(int i = 0; i < rows; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(Math.abs(velocities[i][k] - 1.0) <= epsilon){
					double exchange = personalBest[i][k];
					for(int n = 0; n < positions[i].length; n++){
						if(Math.abs(positions[i][n] - exchange) <= epsilon){
							double temp = positions[i][k];
							positions[i][k] = positions[i][n];
							positions[i][n] = temp;
						}
					}
					velocities[i][k] = 0;
				}else{
					int index = neighbourhood.neighbourhoodBest(i);
					double exchange = personalBest[index][k];
					for(int n = 0; n < positions[i].length; n++){
						if(Math.abs(positions[i][n] - exchange) <= epsilon){
							double temp = positions[i][k];
							positions[i][k] = positions[i][n];
							positions[i][n] = temp;
						}
					}
					//velocities[i + rows][k] = 0;
				}
			}
		}
	}

}

package swarm;

public class BinaryInitialiser implements Initialiser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7406903484522950144L;

	private double[][] position;
	
	private double[][] velocities;
	
	@Override
	public void initialiseMatrices(Function objectiveFunction, int numberOfParticles) {
		int columns = objectiveFunction.getVariables();
		position = new double[numberOfParticles][columns];
		velocities = new double[numberOfParticles][columns];
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < columns; k++){
				velocities[i][k] = 0;
				if(Math.random() >= 0.5){
					position[i][k] = 0;
				}else{
					position[i][k] = 1;
				}
			}
		}

	}

	@Override
	public double[][] getPositions() {
		return position;
	}

	@Override
	public double[][] getVelocities() {
		return velocities;
	}

}

package swarm;


public class BinaryInitialiser implements Initialiser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7406903484522950144L;

	private double[][] position;
	
	private double[][] velocities;
	
	private double[][] personalBest;
	
	private double[] upperLimit;
	
	private double[] lowerLimit;
	
	private BinaryConverter binaryConverter = new BinaryConverterImpl();

	public double[] getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(double[] upperLimit) {
		this.upperLimit = upperLimit;
	}

	public double[] getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(double[] lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public BinaryConverter getBinaryConverter() {
		return binaryConverter;
	}

	public void setBinaryConverter(BinaryConverter binaryConverter) {
		this.binaryConverter = binaryConverter;
	}

	public boolean isConstrainedInitialisation() {
		return constrainedInitialisation;
	}

	public void setConstrainedInitialisation(boolean constrainedInitialisation) {
		this.constrainedInitialisation = constrainedInitialisation;
	}
	
	private boolean constrainedInitialisation = false;
	
	@Override
	public void initialiseMatrices(Function objectiveFunction, int numberOfParticles) {
		if(constrainedInitialisation){
			constrainedInitialisation(objectiveFunction, numberOfParticles);
		}else{
			int columns = objectiveFunction.getVariables();
			position = new double[numberOfParticles][columns];
			velocities = new double[numberOfParticles][columns];
			personalBest = new double[numberOfParticles][columns];
			for(int i = 0; i < numberOfParticles; i++){
				for(int k = 0; k < columns; k++){
					if(Math.random() >= 0.5){
						position[i][k] = 0;
						personalBest[i][k] = 0;
					}else{
						position[i][k] = 1;
						personalBest[i][k] = 1;
					}
				}
			}
		}
	}
	
	private void constrainedInitialisation(Function objectiveFunction, int numberOfParticles){
		int columns = objectiveFunction.getVariables();
		position = new double[numberOfParticles][columns];
		velocities = new double[numberOfParticles][columns];
		personalBest = new double[numberOfParticles][columns];
		int binaryEncoding  = binaryConverter.getBinaryEncoding();
		for(int i = 0; i < position.length; i++){
			for(int k = 0, n = 0; k < position[i].length; k = k + binaryEncoding, n++){
				double[] temp = binaryConverter.convertRealToBinary(lowerLimit[n] + (Math.random() * (upperLimit[n] - lowerLimit[n])));
				for(int j = k, m = 0; j < k + binaryEncoding; j++, m++){
					position[i][j] = temp[m];
					personalBest[i][j] = temp[m];
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public double[][] getPersonalBest() {
		return personalBest;
	}
}

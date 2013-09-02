package swarm;
/**
 * The BinaryInitialiser intialises the position, personal best and velocity matrices.
 * <p>
 * The 
 * @author williamhogarth
 *
 */
public class BinaryInitialiser implements ConstrainedInitialiser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7406903484522950144L;

	private double[][] position;
	
	private double[][] velocities;
	
	private double[][] personalBest;
	
	private double[] max;
	
	private double[] min;
	
	private BinaryConverter binaryConverter = new BinaryConverterImpl();

	public BinaryConverter getBinaryConverter() {
		return binaryConverter;
	}

	public void setBinaryConverter(BinaryConverter binaryConverter) {
		this.binaryConverter = binaryConverter;
	}
	
	@Override
	public void initialiseMatrices(Function objectiveFunction, int numberOfParticles) {
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
	
	@Override
	public double[][] getPersonalBest() {
		return personalBest;
	}

	@Override
	public double[] getMax() {
		return max;
	}

	@Override
	public void setMax(double[] max) {
		this.max = max;
		
	}

	@Override
	public double[] getMin() {
		return min;
	}

	@Override
	public void setMin(double[] min) {
		this.min = min;
	}

	@Override
	public void constrainedInitialiseMatrices(Function objectiveFunction,
			int numberOfParticles) {
		int columns = objectiveFunction.getVariables();
		position = new double[numberOfParticles][columns];
		velocities = new double[numberOfParticles][columns];
		personalBest = new double[numberOfParticles][columns];
		int binaryEncoding  = binaryConverter.getBinaryEncoding();
		for(int i = 0; i < position.length; i++){
			for(int k = 0, n = 0; k < position[i].length; k = k + binaryEncoding, n++){
				double[] temp = binaryConverter.convertRealToBinary(min[n] + (Math.random() * (max[n] - min[n])));
				for(int j = k, m = 0; j < k + binaryEncoding; j++, m++){
					position[i][j] = temp[m];
					personalBest[i][j] = temp[m];
				}
			}
		}
	}
}

package swarm;
/**
 * The BinaryInitialiser intialises the position, personal best and velocity matrices.
 * <p>
 * The BinaryInitialiser either initialises particle positions randomly in Binary space, or randomly
 * within a feasible solution space.
 * 
 * @author williamhogarth
 *
 */
public class BinaryInitialiser implements ConstrainedInitialiser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7406903484522950144L;
	
	/**
	 * The positions of the particles after initialisation
	 */
	private double[][] position;
	
	/**
	 * The velocities of the particles after initialisation
	 */
	private double[][] velocities;
	
	/**
	 * The best position found by each particle thus far, a copy of the particle's positions
	 */
	private double[][] personalBest;
	
	/**
	 * An array of the maximum values that a binary string can encode in each dimension
	 */
	private double[] max;
	
   /**
	 * An array of the minimum values that a binary string can encode in each dimension
	 */
	private double[] min;
	
	/**
	 * A binary converter {@link BinaryConverter}
	 */
	private BinaryConverter binaryConverter = new BinaryConverterImpl();
	
	/**
	 * Returns the BinaryConverter being used by this object
	 * 
	 * @return BinaryConverter
	 */
	public BinaryConverter getBinaryConverter() {
		return binaryConverter;
	}
	/**
	 * Set the BinaryConveter to be used by this object
	 * 
	 * @param binaryConverter The BinaryConverter to be used
	 */
	public void setBinaryConverter(BinaryConverter binaryConverter) {
		this.binaryConverter = binaryConverter;
	}
	
	/**
	 * Generates the particles by randomly initialising them in binary space
	 * <p>
	 * Each position in the 2-d array is randomly set to the value 1 or 0. The number of rows is set to
	 * the number of particles. The number of columns is set to the number of variables contained in the function.
	 * Particle velocities are initialised to 0.
	 * 
	 * @param Function objectiveFunction, the function to be optimised
	 * @param int numberOfParticles, the number of particles in the swarm.
	 */
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
	
	/**
	 * @return double[][], the particle positions
	 */
	@Override
	public double[][] getPositions() {
		return position;
	}
	/**
	 * @return double[][], the particle velocities
	 */
	@Override
	public double[][] getVelocities() {
		return velocities;
	}
	
	/**
	 * @return the serial version ID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * @return double[][], the personal best position of the particles
	 */
	@Override
	public double[][] getPersonalBest() {
		return personalBest;
	}
	
	/**
	 * @return double[], the maximum value in each dimension
	 */
	@Override
	public double[] getMax() {
		return max;
	}
	/**
	 * @param double[], the maximum value in each dimension
	 */
	@Override
	public void setMax(double[] max) {
		this.max = max;
		
	}
	
	/**
	 * @return double[], the minimum value in each dimension
	 */
	@Override
	public double[] getMin() {
		return min;
	}
	
	/**
	 * @param double[] min, the minimum value in each dimension
	 */
	@Override
	public void setMin(double[] min) {
		this.min = min;
	}
	/**
	 * Randomly initialises the particles within a constrained solution space
	 * <p>
	 * The particle positions are initialised randomly within the feasible solution space defined by the real values
	 * contained in max and min. Each value randomly generated within the solution space is then converted to
	 * binary and added to the particle. The number of rows is set to
	 * the number of particles. The number of columns is set to the number of variables contained in the function.
	 * <p>
	 * The indexes of the arrays of maximum and minimum values map to the particle columns by multiplying them
	 * by the binaryEncoding (i.e. the length of the binary encoding). Thus, index n in maximum and minimum maps 
	 * to sub-array (n * binaryEncoding, n * binaryEncoding + binaryEncoding) in the particle. Max and min must
	 * be as long as the number of columns / binaryEncoding.
	 * 
	 * @param Function objectiveFunction, the function to be optimised
	 * @param int numberOfParticles, the number of particles in the swarm
	 */
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

package swarm;

import java.util.Arrays;
/**
 * Restricts particle positions to solution space defined by the array of maximum and the array of minimum values
 * <p>
 *
 * 
 * @author williamhogarth
 *
 */
public class BinaryConstrainer implements Constrainer {
	
	/**
	 * The current positions of the particles
	 */
	private double[][] positions;
	
	/**
	 * The maximum value that a particle may be in each dimension.
	 */
	private double[] maximum;
	
	/**
	 * The minimum value that a particle may be in each dimension
	 */
	private double[] minimum;
	
	/**
	 * The BinaryConverter converts binary representations of numbers to real numbers and vice-versa. It also 
	 * holds the integer value of the length of the binary encoding, binaryEncoding.
	 */
	private BinaryConverter binaryConverter = new BinaryConverterImpl();

	/**
	 * Sets the 2-D array holding the particles' positions
	 * 
	 * @return void
	 */
	@Override
	public void setPositions(double[][] positions) {
		this.positions = positions;

	}

	/**
	 * returns the particles' positions
	 * 
	 * @return positions
	 */
	@Override
	public double[][] getPositions() {
		return positions;
	}

	/**
	 * This method iterates through the array corresponding to each particles' position. It iterates through each
	 * array in blocks the length of binaryEncoding. Each block is converted to a real number using the binary
	 * converter. If the number is greater than the maximum value in that dimension or less than the minimum value
	 * in that dimension, that block is replaced by a binary representation of the value that was exceeded.
	 * <p>
	 * The indexes of the arrays of maximum and minimum values map to the particle positions by multiplying them
	 * by the binaryEncoding (i.e. the length of the binary encoding). Thus, index n in maximum and minimum maps 
	 * to sub-array (n * binaryEncoding, n * binaryEncoding + binaryEncoding). 
	 * 
	 * @return void
	 */
	@Override
	public void constrain() {
		int binaryEncoding = binaryConverter.getBinaryEncoding();
		for(int i = 0; i < positions.length; i++){
			for(int k = 0, n = 0; k < positions[i].length; k = k + binaryEncoding, n++){
				if(binaryConverter.convertBinaryToReal(Arrays.copyOfRange(positions[i], k, k + binaryEncoding)) > maximum[n]){
					double[] inRange = binaryConverter.convertRealToBinary(maximum[n]);
					int counter = k;
					int limit = k + binaryEncoding;
					for(int j = 0; counter < limit; j++, counter++){
						positions[i][counter] = inRange[j];
					}
				}else if(binaryConverter.convertBinaryToReal(Arrays.copyOfRange(positions[i], k, k + binaryEncoding)) < minimum[n]){
					double[] inRange = binaryConverter.convertRealToBinary(minimum[n]);
					int counter = k;
					int limit = k + 32;
					for(int j = 0; counter < limit; j++, counter++){
						positions[i][counter] = inRange[j];
					}
				}
			}
		}

	}

	/**
	 * Returns the BinaryConverter
	 * 
	 * @return BinaryConverter
	 */
	public BinaryConverter getBinaryConverter() {
		return binaryConverter;
	}

	/**
	 * Sets the BinaryConverter
	 * 
	 * @param binaryConverter
	 */
	public void setBinaryConverter(BinaryConverter binaryConverter) {
		this.binaryConverter = binaryConverter;
	}

	/**
	 * Returns the array holding the maximum values of the particles
	 * 
	 * @return Maximum
	 */
	@Override
	public double[] getMaximum() {
		return maximum;
	}

	/**
	 * Set the maximum values that the particles can have
	 * 
	 * @param maximum
	 */
	@Override
	public void setMaximum(double[] maximum) {
		this.maximum = maximum;

	}
	
	/**
	 * Get the minimum values that the particles can have
	 * 
	 * @return minimum
	 */
	@Override
	public double[] getMinimum() {
		return minimum;
	}

	/**
	 * Set the minimum values the particles can have
	 * 
	 * @param minimum
	 */
	@Override
	public void setMinimum(double[] minimum) {
		this.minimum = minimum;
	}

}

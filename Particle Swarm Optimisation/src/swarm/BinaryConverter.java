package swarm;
/**
 * BinaryConverters convert binary representations to real numbers and real numbers to binary representations.
 * 
 * @author williamhogarth
 */
public interface BinaryConverter {
	
	/**
	 * Returns the length of the binary representation of numbers being used.
	 * <p>
	 * For example if the binary representation being used was IEEE single precision floating point the
	 * integer returned would be 32.
	 * 
	 * @return the length of the binary encoding 
	 */
	int getBinaryEncoding();
	
	/**
	 * Sets the length of the binary encoding being used.
	 * 
	 * @param the length pf the binary encoding being used.
	 */
	void setBinaryEncoding(int binaryEncoding);
	
	/**
	 * Converts the binary representation of a number to a real number.
	 * 
	 * @param binaryNumber
	 * @return the real value of a binary number
	 */
	double convertBinaryToReal(double[] binaryNumber);
	
	/**
	 * Converts a real number to its binary representation
	 * 
	 * @param realNumber
	 * @return binary representation of the number 
	 */
	double[] convertRealToBinary(double realNumber);
}

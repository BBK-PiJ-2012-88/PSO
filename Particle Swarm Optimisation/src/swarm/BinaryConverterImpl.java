package swarm;
/*
 * undocumented because will not be included in library.
 */
public class BinaryConverterImpl implements BinaryConverter {

	private int binaryEncoding = 32;
	
	@Override
	public int getBinaryEncoding() {
		return binaryEncoding;
	}

	@Override
	public void setBinaryEncoding(int binaryEncoding) {
	}

	@Override
	public double convertBinaryToReal(double[] binaryNumber) {
		int sign = (int) Math.pow(-1, binaryNumber[0]);
		int exponent = calculateExponent(binaryNumber);
		exponent = exponent - 127;
		double mantissa = calculateMantissa(binaryNumber);
		
		return mantissa * sign * Math.pow(2, exponent);
	}

	@Override
	public double[] convertRealToBinary(double realNumber) {
		double[] result = new double[32];
		if(realNumber == 0){
			return result;
		}
		if(realNumber < 0){
			result[0] = 1;
		}else{
			result[0] = 0;
		}
		int power = 0;
		realNumber = Math.abs(realNumber);
		if(realNumber >= 1){
			while(realNumber - 1 > 1){
				realNumber = realNumber / 2;
				power++;
			}
		}else{
			while(realNumber < 1){
				realNumber = realNumber * 2;
				power--;
			}
		}
		realNumber = realNumber - 1;
		int exponent = 127 + power;
		for(int i = 9; i < result.length && realNumber != 0; i++){
			realNumber = realNumber * 2;
			result[i] = (int) realNumber;
			if(realNumber >= 1){
				realNumber = realNumber - 1;
			}
		}
		double[] e = convertToBinary(exponent);
		for(int i = 1; i < 9; i++){
			result[i] = e[i - 1];
		}
 		return result;
	}
	
	private double[] convertToBinary(int exponent) {
		double[] result = new double[8];
		for(int i = 7; i >= 0 && exponent > 0; i--){
			result[i] = exponent % 2;
			exponent = exponent / 2;
		}
		return result;
	}

	private double calculateMantissa(double[] binaryNumber) {
		double mantissa = 1;
		int power = -1;
		for(int i = 9; i < binaryNumber.length; i++){
			mantissa = mantissa + binaryNumber[i] * Math.pow(2, power);
			power--;
		}
		return mantissa;
	}

	private int calculateExponent(double[] binaryNumber) {
		int exponent = 0;
		for(int i = 1; i < 9; i++){
			exponent = exponent + (int) binaryNumber[i] * (int) Math.pow(2, 8 - i);
		}
		return exponent;
	}

}

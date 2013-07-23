package tests;

public class BinaryConverter {
	
	public double convertIEEE754ToReal(int[] binaryNumber){
		int sign = (int) Math.pow(-1, binaryNumber[0]);
		int exponent = calculateExponent(binaryNumber);
		exponent = exponent - 127;
		double mantissa = calculateMantissa(binaryNumber);
		
		return mantissa * sign * Math.pow(2, exponent);
	}

	private double calculateMantissa(int[] binaryNumber) {
		double mantissa = 1;
		int power = -1;
		for(int i = 9; i < binaryNumber.length; i++){
			mantissa = mantissa + binaryNumber[i] * Math.pow(2, power);
			power--;
		}
		return mantissa;
	}

	private int calculateExponent(int[] binaryNumber) {
		int exponent = 0;
		for(int i = 1; i < 9; i++){
			exponent = exponent + binaryNumber[i] * (int) Math.pow(2, 8 - i);
		}
		return exponent;
	}

}

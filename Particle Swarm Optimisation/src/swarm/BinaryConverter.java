package swarm;

public interface BinaryConverter {
	int getBinaryEncoding();
	
	void setBinaryEncoding(int binaryEncoding);
	
	double convertBinaryToReal(double[] binaryNumber);
	
	double[] convertRealToBinary(double realNumber);
}

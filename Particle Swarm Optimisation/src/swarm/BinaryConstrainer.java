package swarm;

import java.util.Arrays;

public class BinaryConstrainer implements Constrainer {
	
	private double[][] positions;
	
	private double[] maximum;
	
	private double[] minimum;
	
	private BinaryConverter binaryConverter = new BinaryConverterImpl();

	
	@Override
	public void setPositions(double[][] positions) {
		this.positions = positions;

	}

	@Override
	public double[][] getPositions() {
		return positions;
	}

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

	public BinaryConverter getBinaryConverter() {
		return binaryConverter;
	}

	public void setBinaryConverter(BinaryConverter binaryConverter) {
		this.binaryConverter = binaryConverter;
	}

	@Override
	public double[] getMaximum() {
		return maximum;
	}

	@Override
	public void setMaximum(double[] maximum) {
		this.maximum = maximum;

	}

	@Override
	public double[] getMinimum() {
		return minimum;
	}

	@Override
	public void setMinimum(double[] minimum) {
		this.minimum = minimum;
	}

}

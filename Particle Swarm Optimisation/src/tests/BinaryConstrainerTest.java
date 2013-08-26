package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import swarm.BinaryConstrainer;
import swarm.BinaryConverter;
import swarm.BinaryConverterImpl;
import swarm.Constrainer;

public class BinaryConstrainerTest {

	private Constrainer classUnderTest = new BinaryConstrainer();
	
	@Test
	public void testPositionRestrictions(){
		double[] max = {5.12, 5.12};
		double[] min = {-5.12, -5.12};
		classUnderTest.setMaximum(max);
		classUnderTest.setMinimum(min);
		double[][] position = new double[][]{
				{0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,1,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
		};
		classUnderTest.setPositions(position);
		classUnderTest.constrain();
		position = classUnderTest.getPositions();
		BinaryConverter bin = new BinaryConverterImpl();
		int binaryEncoding = bin.getBinaryEncoding();
		for(int i = 0; i < position.length; i++){
			for(int k = 0; k < position[i].length; k = k + binaryEncoding){
				assertTrue(bin.convertBinaryToReal(Arrays.copyOfRange(position[i], k, k + binaryEncoding)) >= -5.12 && bin.convertBinaryToReal(Arrays.copyOfRange(position[i], k, k + binaryEncoding)) <= 5.12);
			}
		}
	}
}

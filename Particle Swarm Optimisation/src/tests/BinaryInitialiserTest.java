package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import swarm.BinaryConverter;
import swarm.BinaryConverterImpl;
import swarm.BinaryInitialiser;
import swarm.Function;

public class BinaryInitialiserTest {
	
	private BinaryInitialiser classUnderTest = new BinaryInitialiser();
	
	private Function f = new BinaryDeJong1();
	
	private BinaryConverter bin = new BinaryConverterImpl();
	
	@Test
	public void testUnconstrainedInitialisation() {
		classUnderTest.initialiseMatrices(f, 10);
		double[][] positions = classUnderTest.getPositions();
		boolean result = true;
		for(int i = 0; i < positions.length; i++){
			for(int k = 0; k < positions[i].length; k++){
				if(positions[i][k] > 1.0 || positions[i][k] < 0.0 || (positions[i][k] > 0.0 && positions[i][k] < 1.0)){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void checkPositionAndPBSameUnconstrained(){
		classUnderTest.initialiseMatrices(f, 10);
		double[][] positions = classUnderTest.getPositions();
		double[][] pB = classUnderTest.getPersonalBest();
		assertTrue(Arrays.deepEquals(positions, pB));
	}
	
	@Test
	public void testConstrainedInitialisation(){
		classUnderTest.setConstrainedInitialisation(true);
		int binaryEncoding =  bin.getBinaryEncoding();
		double[] max = new double[f.getVariables() /  binaryEncoding];
		Arrays.fill(max, 17.236);
		double[] min = new double[f.getVariables() / binaryEncoding];
		Arrays.fill(min, -17.236);
		classUnderTest.setLowerLimit(min);
		classUnderTest.setUpperLimit(max);
		boolean result = true;
		classUnderTest.initialiseMatrices(f, 10);
		double[][] positions = classUnderTest.getPositions();
		for(int  i = 0; i < positions.length; i++){
			for(int k = 0; k < positions[i].length; k = k + binaryEncoding){
				double solution = bin.convertBinaryToReal(Arrays.copyOfRange(positions[i], k, k + binaryEncoding));
				if(solution > 17.236 || solution < -17.236){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
	@Test
	public void checkPositionAndPBSameConstrained(){
		classUnderTest.setConstrainedInitialisation(true);
		int binaryEncoding =  bin.getBinaryEncoding();
		double[] max = new double[f.getVariables() /  binaryEncoding];
		Arrays.fill(max, 17.236);
		double[] min = new double[f.getVariables() / binaryEncoding];
		Arrays.fill(min, -17.236);
		classUnderTest.setLowerLimit(min);
		classUnderTest.setUpperLimit(max);
		classUnderTest.initialiseMatrices(f, 10);
		double[][] positions = classUnderTest.getPositions();
		double[][] pB = classUnderTest.getPersonalBest();
		assertTrue(Arrays.deepEquals(positions, pB));
	}

}

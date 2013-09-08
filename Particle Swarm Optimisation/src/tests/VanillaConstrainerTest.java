package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import swarm.VanillaConstrainer;

public class VanillaConstrainerTest {
	
	private VanillaConstrainer classUnderTest = new VanillaConstrainer();
	
	private double[][]positions = new double[][]{
			{5,-6,7,-8},
			{1,-2,10,6},
			{-4,9,-1,7},
			{6,7,8,-9}
	};
	
	private double[][] expected = new double[][]{
			{5,-5,5,-5},
			{1,-2,5,5},
			{-4,5,-1,5},
			{5,5,5,-5}
	};
	
	@Test
	public void test() {
		classUnderTest.setPositions(positions);
		double[] max = new double[4];
		Arrays.fill(max, 5);
		double[] min = new double[4];
		Arrays.fill(min, -5);
		classUnderTest.setMaximum(max);
		classUnderTest.setMinimum(min);
		classUnderTest.constrain();
		assertTrue(Arrays.deepEquals(expected, positions));
	}

}

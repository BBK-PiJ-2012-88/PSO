package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import swarm.BinaryInitialiser;
import swarm.Function;

public class BinaryInitialiserTest {
	
	private BinaryInitialiser classUnderTest = new BinaryInitialiser();
	
	private Function f = new BinaryDeJong1();
	
	@Test
	public void test() {
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

}

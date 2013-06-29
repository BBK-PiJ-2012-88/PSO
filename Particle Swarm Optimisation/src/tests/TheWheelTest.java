package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import swarm.TheWheel;

public class TheWheelTest {
	
	private HashMap<Integer, Double> testSolutions = new HashMap<Integer, Double>();
	private TheWheel classUnderTest;
	
	@Before
	public void setUp(){
		
		classUnderTest = new TheWheel();
		for(int i = 0; i < 20; i++){
			testSolutions.put(i, (double) i);
		}
		classUnderTest.setSolutionFitness(testSolutions);
	}

	@Test
	public void testGetNeighbourhoodBest1Maximum(){
		boolean result = true;
		double testFitness = 19;
		classUnderTest.setMaximum(true);
		classUnderTest.setWheel(19);
		for(int i = 0; i < 20; i++){
			if(testSolutions.get(classUnderTest.neighbourhoodBest(i)) < testFitness){
				result = false;
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void testGetNeigbourhoodBest2Maximum(){
		classUnderTest.setMaximum(true);
		int result = classUnderTest.neighbourhoodBest(0);
		int expected = 19;
		assertEquals(result, expected);
		
	}
	
	@Test
	public void testGetNeighbourhoodBestMinimum(){
		boolean result = true;
		double testFitness = 0;
		for(int i = 0; i < 20; i++){
			if(testSolutions.get(classUnderTest.neighbourhoodBest(i)) < testFitness){
				result = false;
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void testGetNeighbourhoodBestMinimum2(){
		classUnderTest.setWheel(10);
		int result = classUnderTest.neighbourhoodBest(10);
		int expected = 0;
		assertEquals(result, expected);
	}

}
package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import swarm.TheStar;


public class TheStarTest {

	private HashMap<Integer, Double> testSolutions = new HashMap<Integer, Double>();
	private TheStar classUnderTest;
	
	@Before
	public void setUp(){
		for(int i = 0; i < 20; i++){
			if(i % 2 == 0){
				testSolutions.put(i, (double) i);
			}else{
				testSolutions.put(i, (double) i * 2);
			}
		}
		classUnderTest = new TheStar();
		classUnderTest.setSolutionFitness(testSolutions);
	}
	
	@Test
	public void testNeighbourhoodBestMaximum() {
		classUnderTest.setMaximum(true);
		int result = classUnderTest.neighbourhoodBest(5);
		int expected = 19;
		assertEquals(expected, result);
	}
	
	@Test
	public void testNeighbourhoodBest2Maximum(){
		classUnderTest.setMaximum(true);
		int result = classUnderTest.neighbourhoodBest(0);
		int expected = 19;
		assertEquals(expected, result);
	}

	@Test
	public void testNeighbourhoodBest3Maximum(){
		classUnderTest.setMaximum(true);
		int result = classUnderTest.neighbourhoodBest(19);
		int expected = 19;
		assertEquals(expected, result);
	}
	
	@Test
	public void testNeighbourhoodBestMinimum(){
		int result = classUnderTest.neighbourhoodBest(0);
		int expected = 0;
		assertEquals(expected, result);
	}
	
	@Test
	public void testNeighbourhoodBestMinimum2(){
		int result = classUnderTest.neighbourhoodBest(5);
		int expected = 0;
		assertEquals(expected, result);
	}
	
	@Test
	public void testNeighbourhoodBestMinimum3(){
		int result = classUnderTest.neighbourhoodBest(19);
		int expected = 0;
		assertEquals(expected, result);
	}
}

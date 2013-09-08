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
		boolean result = true;
		classUnderTest.setMaximum(true);
		for(int i = 0; i < testSolutions.size(); i++){
			if(classUnderTest.neighbourhoodBest(i) != 19){
				result = false;
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void testNeighbourhoodBestMinimum(){
		boolean result = true;
		for(int i = 0; i < testSolutions.size(); i++){
			if(classUnderTest.neighbourhoodBest(i) != 0){
				result = false;
			}
		}
		assertTrue(result);
	}
}

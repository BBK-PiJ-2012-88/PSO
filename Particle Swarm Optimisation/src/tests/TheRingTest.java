package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import swarm.TheRing;

public class TheRingTest {
	
	private TheRing classUnderTest = new TheRing();
	private HashMap<Integer, Double> solutionFitness = new HashMap<Integer, Double>();
	
	@Before
	public void setUp(){
		for(int i = 0; i < 20; i++){
			if(i % 2 == 0){
				solutionFitness.put(i, (double)i * 2);
			}else{
				solutionFitness.put(i, (double) i);
			}
		}
		classUnderTest.setSolutionFitness(solutionFitness);
	}

	@Test
	public void testNeighbourhoodBestMinimum() {
		int result = classUnderTest.neighbourhoodBest(0);
		int expected = 0;
		assertEquals(expected, result);
	}
	
	@Test
	public void testNeighbourhoodBestMinimum2(){
		int result = classUnderTest.neighbourhoodBest(19);
		int expected = 0;
		assertEquals(expected, result);
	}
	
	@Test
	public void testNeighbourhoodBestMinimum3(){
		int result = classUnderTest.neighbourhoodBest(10);
		int expected = 9;
		assertEquals(expected, result);
	}
	
	@Test
	public void testNeighbourhoodBestMinimum4(){
		int result = classUnderTest.neighbourhoodBest(11);
		int expected = 11;
		assertEquals(expected, result);
	}
	
	@Test
	public void testNeighbourhoodBestMaximum(){
		classUnderTest.setMaximum(true);
		int result = classUnderTest.neighbourhoodBest(0);
		int expected = 19;
		assertEquals(expected, result);
	}
	
	@Test
	public void testNeighbourhoodBestMaximum2(){
		classUnderTest.setMaximum(true);
		int result = classUnderTest.neighbourhoodBest(19);
		int expected = 18;
		assertEquals(expected, result);
	}
	
	@Test
	public void testNeighbourhoodBestMaximum3(){
		classUnderTest.setMaximum(true);
		int result = classUnderTest.neighbourhoodBest(10);
		int expected = 10;
		assertEquals(expected, result);
	}
	
	@Test
	public void testNeighbourhoodBestMaximum4(){
		classUnderTest.setMaximum(true);
		int result = classUnderTest.neighbourhoodBest(11);
		int expected = 12;
		assertEquals(expected, result);
	}

}

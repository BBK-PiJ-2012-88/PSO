package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import swarm.TheFourClusters;

public class TheFourClustersTest {

	private TheFourClusters classUnderTest = new TheFourClusters();
	private HashMap<Integer, Double> solutions = new HashMap<Integer, Double>();
	
	@Before
	public void setUp(){
		for(int i = 0; i < 20; i++){
			solutions.put(i,(double) i);
		}
		classUnderTest.setSolutionFitness(solutions);
	}
	
	@Test
	public void testFirstClusterMinimum() {
		int result = classUnderTest.neighbourhoodBest(0);
		int expected = 0;
		assertEquals(expected, result);
	}
	
	@Test
	public void testFirstClusterMinimum2(){
		int result = classUnderTest.neighbourhoodBest(1);
		int expected = 0;
		assertEquals(expected, result);
	}
	
	@Test
	public void testFirstClusterMinimum3(){
		int result = classUnderTest.neighbourhoodBest(2);
		int expected = 0;
		assertEquals(expected, result);
	}
	
	@Test
	public void testFirstClusterMinimum4(){
		int result = classUnderTest.neighbourhoodBest(3);
		int expected = 0;
		assertEquals(expected, result);
	}
	
	@Test
	public void testSecondClusterMinimum1(){
		int result = classUnderTest.neighbourhoodBest(5);
		int expected = 1;
		assertEquals(expected, result);
	}
	
	@Test
	public void testSecondClusterMinimum2(){
		int result = classUnderTest.neighbourhoodBest(6);
		int expected = 5;
		assertEquals(expected, result);
	}
	
	@Test
	public void testSecondClusterMinimum3(){
		int result = classUnderTest.neighbourhoodBest(7);
		int expected = 5;
		assertEquals(expected, result);
	}
	
	@Test
	public void testSecondClusterMinimum4(){
		int result = classUnderTest.neighbourhoodBest(8);
		int expected = 5;
		assertEquals(expected, result);
	}
	
	@Test
	public void testThirdClusterMinimum1(){
		int result = classUnderTest.neighbourhoodBest(10);
		int expected = 2;
		assertEquals(expected, result);
	}
	
	@Test
	public void testThirdClusterMinimum2(){
		int result = classUnderTest.neighbourhoodBest(11);
		int expected = 7;
		assertEquals(expected, result);
	}
	
	@Test
	public void testThirdClusterMinimum3(){
		int result = classUnderTest.neighbourhoodBest(12);
		int expected = 10;
		assertEquals(expected, result);
	}
	
	@Test
	public void testThirdClusterMinimum4(){
		int result = classUnderTest.neighbourhoodBest(13);
		int expected = 10;
		assertEquals(expected, result);
	}
	
	@Test
	public void testFourthClusterMinimum1(){
		int result = classUnderTest.neighbourhoodBest(15);
		int expected = 3;
		assertEquals(expected, result);
	}
	
	@Test
	public void testFourthClusterMinimum2(){
		int result = classUnderTest.neighbourhoodBest(16);
		int expected = 8;
		assertEquals(expected, result);
	}
	
	@Test
	public void testFourthClusterMinimum3(){
		int result = classUnderTest.neighbourhoodBest(17);
		int expected = 13;
		assertEquals(expected, result);
	}
	
	@Test
	public void testFourthClusterMinimum4(){
		int result = classUnderTest.neighbourhoodBest(18);
		int expected = 15;
		assertEquals(expected, result);
	}

}

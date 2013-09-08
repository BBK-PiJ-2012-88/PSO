package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import swarm.CuboidLattice;

public class CuboidLatticeTest {
	
	private CuboidLattice classUnderTest = new CuboidLattice();
	private HashMap<Integer, Double> fitness = new HashMap<Integer, Double>();
	
	@Before
	public void setUp(){
		for(int i = 0; i < 27; i++){
			fitness.put(i, (double)i);
		}
		classUnderTest.setSolutionFitness(fitness);
	}
	
	@Test
	public void testPosition0() {
		int expected = 0;
		int result = classUnderTest.neighbourhoodBest(0);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition1(){
		int expected = 0;
		int result = classUnderTest.neighbourhoodBest(1);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition2(){
		int expected = 1;
		int result = classUnderTest.neighbourhoodBest(2);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition3(){
		int expected = 0;
		int result = classUnderTest.neighbourhoodBest(3);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition4(){
		int expected = 1;
		int result = classUnderTest.neighbourhoodBest(4);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition5(){
		int expected = 2;
		int result = classUnderTest.neighbourhoodBest(5);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition6(){
		int expected = 3;
		int result = classUnderTest.neighbourhoodBest(6);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition7(){
		int expected = 4;
		int result = classUnderTest.neighbourhoodBest(7);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition8(){
		int expected = 5;
		int result = classUnderTest.neighbourhoodBest(8);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition9(){
		int expected = 0;
		int result = classUnderTest.neighbourhoodBest(9);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition10(){
		int expected = 1;
		int result = classUnderTest.neighbourhoodBest(10);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition11(){
		int expected = 2;
		int result = classUnderTest.neighbourhoodBest(11);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition12(){
		int expected = 3;
		int result = classUnderTest.neighbourhoodBest(12);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition13(){
		int expected = 4;
		int result = classUnderTest.neighbourhoodBest(13);
		assertEquals(expected,  result);
	}
	
	@Test
	public void testPosition14(){
		int expected = 5;
		int result = classUnderTest.neighbourhoodBest(14);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition15(){
		int expected = 6;
		int result = classUnderTest.neighbourhoodBest(15);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition16(){
		int expected = 7;
		int result = classUnderTest.neighbourhoodBest(16);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition17(){
		int expected = 8;
		int result = classUnderTest.neighbourhoodBest(17);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition18(){
		int expected = 9;
		int result = classUnderTest.neighbourhoodBest(18);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition19(){
		int expected = 10;
		int result = classUnderTest.neighbourhoodBest(19);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition20(){
		int expected = 11;
		int result = classUnderTest.neighbourhoodBest(20);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition21(){
		int expected = 12;
		int result = classUnderTest.neighbourhoodBest(21);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition22(){
		int expected = 13;
		int result = classUnderTest.neighbourhoodBest(22);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition23(){
		int expected = 14;
		int result = classUnderTest.neighbourhoodBest(23);
		assertEquals(expected, result);
	}
	@Test
	public void testPosition24(){
		int expected = 15;
		int result = classUnderTest.neighbourhoodBest(24);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition25(){
		int expected = 16;
		int result = classUnderTest.neighbourhoodBest(25);
		assertEquals(expected, result);
	}
	
	@Test
	public void testPosition26(){
		int expected = 17;
		int result = classUnderTest.neighbourhoodBest(26);
		assertEquals(expected, result);
	}
	
	@Test
	public void testMaximum1(){
		classUnderTest.setMaximum(true);
		int expected = 9;
		int result = classUnderTest.neighbourhoodBest(0);
		assertEquals(expected, result);
	}
	
	@Test
	public void testMaximum2(){
		classUnderTest.setMaximum(true);
		int expected = 12;
		int result = classUnderTest.neighbourhoodBest(3);
		assertEquals(expected, result);
	}
	
	@Test
	public void testMaximum3(){
		classUnderTest.setMaximum(true);
		int expected = 22;
		int result = classUnderTest.neighbourhoodBest(13);
		assertEquals(expected, result);
	}
	
	@Test
	public void testMaximum4(){
		classUnderTest.setMaximum(true);
		int expected = 26;
		int result = classUnderTest.neighbourhoodBest(25);
		assertEquals(expected, result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNonCuboidParticles(){
		fitness.clear();
		for(int i = 0; i < 12; i++){
			fitness.put(i, (double)i);
		}
		classUnderTest.setSolutionFitness(fitness);
	}
	
	@Test
	public void testIsCuboid1(){
		fitness.clear();
		fitness.put(1, 2.0);
		boolean result = classUnderTest.isCuboid(fitness);
		assertTrue(result);		
	}
	
	@Test
	public void testIsCuboid2(){
		boolean result = classUnderTest.isCuboid(fitness);
		assertTrue(result);
	}
	
	@Test
	public void testIsCuboid3(){
		for(int i = 0; i < 1728; i++){
			fitness.put(i, (double)i);
		}
		assertTrue(classUnderTest.isCuboid(fitness));
	}
	
	@Test
	public void testIsCuboidNotCube(){
		fitness.remove(5);
		assertTrue(classUnderTest.isCuboid(fitness) == false);
	}
}

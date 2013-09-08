package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import swarm.IterationHalt;

public class IterationHaltTest {

	private IterationHalt classUnderTest;
	
	@Before
	public void setUp(){
		classUnderTest = new IterationHalt(100);
	}
	
	@Test
	public void test1() {
		classUnderTest.updateData(3, 99);
		boolean result = classUnderTest.halt();
		assertTrue(result == false);
	}
	
	@Test
	public void test2(){
		classUnderTest.updateData(0, 100);
		boolean result = classUnderTest.halt();
		assertTrue(result);
	}
	
	@Test
	public void test3(){
		classUnderTest.updateData(0, 100);
		boolean result = classUnderTest.halt();
		assertTrue(result);
	}

}

package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import swarm.GradientHalt;

public class GradientHaltTest {

	public GradientHalt classUnderTest;
	
	@Before
	public void setUp(){
		classUnderTest = new GradientHalt(0.6);
	}
	
	@Test
	public void test1() {
		classUnderTest.updateData(2, 1);
		classUnderTest.updateData(4, 2);
		boolean result = classUnderTest.halt();
		assertTrue(result);
	}
	
	@Test
	public void test2(){
		classUnderTest.updateData(4, 1);
		classUnderTest.updateData(2.5, 2);
		boolean result = classUnderTest.halt();
		assertTrue(result);
	}
	
	@Test
	public void test3(){
		classUnderTest.updateData(4, 1);
		classUnderTest.updateData(2.2, 2);
		boolean result = classUnderTest.halt();
		assertTrue(!result);
	}
	
	@Test
	public void test4(){
		classUnderTest.updateData(4, 1);
		Boolean result = classUnderTest.halt();
		assertTrue(result == false);
	}

}
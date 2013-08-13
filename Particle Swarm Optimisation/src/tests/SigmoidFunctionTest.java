package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import swarm.SigmoidFunction;

public class SigmoidFunctionTest {
	
	private SigmoidFunction classUnderTest = new SigmoidFunction();
	
	@Test
	public void testNormalisation1() {
		double result = classUnderTest.normalise(0.5);
		System.out.println(result);
		assertTrue(result >= 0 && result <= 1);
	}
	
	@Test
	public void testNormalisation2() {
		double result = classUnderTest.normalise(1);
		System.out.println(result);
		assertTrue(result >= 0 && result <= 1);
	}
	
	@Test
	public void testNormalisation3() {
		double result = classUnderTest.normalise(5);
		System.out.println(result);
		assertTrue(result >= 0 && result <= 1);
	}
	
	@Test
	public void testNormalisation4() {
		double result = classUnderTest.normalise(10);
		System.out.println(result);
		assertTrue(result >= 0 && result <= 1);
	}
	
	@Test
	public void testNormalisation5() {
		double result = classUnderTest.normalise(-10);
		System.out.println(result);
		assertTrue(result >= 0 && result <= 1);
	}
	
	@Test
	public void testNormalisation6() {
		double result = classUnderTest.normalise(-0.5);
		System.out.println(result);
		assertTrue(result >= 0 && result <= 1);
	}
	
	@Test
	public void testNormalisation7() {
		double result = classUnderTest.normalise(100);
		System.out.println(result);
		assertTrue(result >= 0 && result <= 1);
	}
	
	@Test
	public void testNormalisation8() {
		double result = classUnderTest.normalise(-100);
		System.out.println(result);
		assertTrue(result >= 0 && result <= 1);
	}
}

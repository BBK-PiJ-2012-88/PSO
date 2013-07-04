package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import swarm.AcceptableError;

public class AcceptableErrorTest {
	
	private AcceptableError classUnderTest;
	
	@Before
	public void setUp(){
		classUnderTest = new AcceptableError(5,1);
	}
	
	@Test
	public void testInRange() {
		classUnderTest.updateData(4.5, 0);
		boolean result  = classUnderTest.halt();
		System.out.println(result);
		assertTrue(result);
	}
	
	@Test
	public void testInRange2(){
		classUnderTest.updateData(6, 0);
		boolean result = classUnderTest.halt();
		System.out.println(result);
		assertTrue(result);
	}
	
	@Test
	public void testOutOfRange(){
		classUnderTest.updateData(3.9, 0);
		boolean result = classUnderTest.halt();
		assertTrue(!result);
	}

}

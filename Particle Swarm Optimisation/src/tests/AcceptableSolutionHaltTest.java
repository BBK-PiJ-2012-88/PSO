package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import swarm.AcceptableSolutionHalt;

public class AcceptableSolutionHaltTest {

	private AcceptableSolutionHalt classUnderTest = new AcceptableSolutionHalt();
	
	@Test
	public void test1() {
		classUnderTest.setAcceptableSolution(10);
		classUnderTest.updateData(9, 0);
		assertTrue(classUnderTest.halt());
	}
	
	@Test
	public void test2() {
		classUnderTest.setAcceptableSolution(10);
		classUnderTest.updateData(10.1, 0);
		assertTrue(!classUnderTest.halt());
	}
	
	@Test
	public void test3(){
		classUnderTest.setAcceptableSolution(10);
		classUnderTest.updateData(10.0, 0);
		assertTrue(classUnderTest.halt());
	}
	
	@Test
	public void test4() {
		classUnderTest.setAcceptableSolution(10);
		classUnderTest.updateData(11, 0);
		classUnderTest.setMaximum(true);
		assertTrue(classUnderTest.halt());
	}
	
	@Test
	public void test5() {
		classUnderTest.setAcceptableSolution(10);
		classUnderTest.updateData(9.9, 0);
		classUnderTest.setMaximum(true);
		assertTrue(!classUnderTest.halt());

	}
	
	@Test
	public void test6(){
		classUnderTest.setAcceptableSolution(10);
		classUnderTest.updateData(10.0, 0);
		classUnderTest.setMaximum(true);
		assertTrue(classUnderTest.halt());
	}

}

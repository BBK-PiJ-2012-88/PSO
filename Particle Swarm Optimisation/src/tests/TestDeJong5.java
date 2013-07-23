package tests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

public class TestDeJong5 {
	
	private DeJong5 classUnderTest = new DeJong5();
	/*
	@Test
	public void testSingleInput() {
		Vector<Double> testVector = new Vector<Double>();
		testVector.add(32.1);
		double result = classUnderTest.CalculateFitness(testVector);
		assertTrue(result == 0.0);
	}*/
	
	@Test
	public void testMultipleInput(){
		Vector<Double> testVector = new Vector<Double>();
		testVector.add(32.0);
		testVector.add(32.0);
		double result = classUnderTest.CalculateFitness(testVector);
		System.out.println(result);
		assertTrue(result == 0.0);
	}
	
	@Test
	public void testMultipleInputDoesNotEqualZero(){
		Vector<Double> testVector = new Vector<Double>();
		testVector.add(45.0);
		testVector.add(-50.0);
		double result = classUnderTest.CalculateFitness(testVector);
		System.out.println(result);
		assertTrue(result != 0.0);
	}
	
}

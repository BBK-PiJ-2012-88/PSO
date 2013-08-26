package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import swarm.Function;

public class DeJongFunctionsTest {
	
	private Function f;
	
	private double epsilon = 0.000001;
	
	@Test
	public void testDeJong1Optmial() {
		double [] solution = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		f = new DeJong1(10);
		double result = f.CalculateFitness(solution);
		double expected = 0.0;
		assertEquals(expected, result, epsilon);
	}
	
	@Test
	public void testDeJong1CloseToOptimal(){
		double [] solution = {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
		f = new DeJong1(10);
		double result = f.CalculateFitness(solution);
		double expected = 0.1;
		assertEquals(expected, result, epsilon);
	}
	
	@Test
	public void testDeJong2Optmial(){
		double [] solution = {1.0, 1.0};
		f = new DeJong2();
		double result = f.CalculateFitness(solution);
		double expected = 0.0;
		assertEquals(expected, result, epsilon);
	}
	
	@Test
	public void testDeJong3Optimal(){
		double[] solution = {-5.12, -5.1, -5.12, -5, -5.02};
		f = new DeJong3();
		double result = f.CalculateFitness(solution);
		double expected = 0.0;
		assertEquals(expected, result, epsilon);
	}
	
	@Test
	public void testDeJong4(){
		double [] solution = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		f = new DeJong4();
		double result = f.CalculateFitness(solution);
		double max = 10;
		assertTrue(result < max);
	}
	
	@Test
	public void testDeJong4NonOptimal(){
		double[] solution = {1,1,1,1,1,1,1,1,1,1};
		f = new DeJong4();
		double result = f.CalculateFitness(solution);
		double max = 65;
		assertTrue(result < max);
	}
	
	@Test
	public void testDeJong5(){
		double[] solution = {-32, -32};
		f = new DeJong5();
		double result = f.CalculateFitness(solution);
		double expected = 1;
		assertEquals(expected, result, 0.1);
	}
}

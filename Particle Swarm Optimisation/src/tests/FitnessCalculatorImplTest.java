package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import swarm.FitnessCalculatorImpl;

public class FitnessCalculatorImplTest {
	
	private FitnessCalculatorImpl classUnderTest = new FitnessCalculatorImpl(new DeJong1(4));
	
	private Map<Integer, Double> fitness = new LinkedHashMap<Integer, Double>();
	
	private double[][] position = new double[][]{
			{0, 0, 0, 0},
			{1, -1, 2, 4},
			{3, 4, 5, 7},
			{1, 2, 3, 4}
	};
	
	private double[][] personalBest = new double[][]{
			{0, 0, 0, 0},
			{1, -1, 2, 4},
			{3, 4, 5, 7},
			{1, 2, 3, 4}
	};
	
	private double[][]initialFitness = new double[][]{
			{1, 0, 0, 0},
			{2, -1, 2, 4},
			{4, 4, 5, 7},
			{2, 2, 3, 4}
	};
	
	@Before
	public void setUp(){
		classUnderTest.setPositions(initialFitness);
		classUnderTest.initialCalculateFitness();
		fitness.put(0, (double) 0);
		fitness.put(1, 22.0);
		fitness.put(2, 99.0);
		fitness.put(3, 30.0);
	}
	
	@Test
	public void testInitialCalculateFitness(){
		classUnderTest.setPositions(position);
		classUnderTest.initialCalculateFitness();
		Map<Integer, Double> result = classUnderTest.getFitness();
		assertEquals(fitness, result);
	}
	
	@Test
	public void test() {
		classUnderTest.setPositions(position);
		classUnderTest.setPersonalBest(personalBest);
		classUnderTest.calculateFitness();
		Map<Integer, Double> result = classUnderTest.getFitness();
		assertEquals(result, fitness);
	}
	
	@Test
	public void test2(){
		classUnderTest.setPositions(position);
		classUnderTest.setPersonalBest(personalBest);
		classUnderTest.setMaximum(true);
		classUnderTest.calculateFitness();
		Map<Integer, Double> result = classUnderTest.getFitness();
		Map<Integer, Double> expected = new HashMap<Integer, Double>();
		expected.put(0, 1.0);
		expected.put(1, 25.0);
		expected.put(2, 106.0);
		expected.put(3, 33.0);
		assertEquals(expected, result);
	}
	
	@Test
	public void test3(){
		int expected = 0;
		int result = classUnderTest.calculateGlobalBest();
		assertEquals(expected, result);
	}
	
	@Test
	public void test4(){
		classUnderTest.setMaximum(true);
		int expected = 2;
		int result = classUnderTest.calculateGlobalBest();
		assertEquals(expected, result);
	}

}

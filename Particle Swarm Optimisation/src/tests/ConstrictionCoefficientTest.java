package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import swarm.ConstrictionCoefficient;
import swarm.FitnessCalculator;
import swarm.FitnessCalculatorImpl;
import swarm.Function;

public class ConstrictionCoefficientTest {
	
	private double[][] position = new double[][]{
			{1,2,3,4},
			{2,3,4,5},
			{1,2,2,4},
			{1,3,3,2},	
	};
		
	private double[][] pB = new double[][]{
				{1,2,3,5},
				{3,4,5,6},
				{1,2,3,4},
				{1,3,3,3}
	};
	
	private double[][] vel = new double[4][4];
	
	private Function f = new DeJong1(4);
	
	private FitnessCalculator fit = new FitnessCalculatorImpl(f);
	
	private ConstrictionCoefficient classUnderTest = new ConstrictionCoefficient();
	
	@Test
	public void test() {
		double[][] expected = new double[][]{
				{0, 1, 0, 0},
				{0, 0, 0, 0},
				{0, 1, 2, -1},
				{0, 0, 0, 2},
		};
		classUnderTest.setPersonalBest(pB);
		classUnderTest.setPosition(position);
		classUnderTest.setVelocities(vel);
		fit.setPositions(position);
		fit.initialCalculateFitness();
		Map<Integer, Double> fitness = fit.getFitness();
		classUnderTest.getNeighbourhood().setSolutionFitness(fitness);
		vel = classUnderTest.updateVelocities();
		assertTrue(Arrays.deepEquals(expected, vel));
	}

}

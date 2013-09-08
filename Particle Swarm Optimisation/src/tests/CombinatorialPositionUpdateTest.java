package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import swarm.CombinatorialPositionUpdate;
import swarm.Neighbourhood;
import swarm.TheRing;

public class CombinatorialPositionUpdateTest {
	
	private double[][] positions;
	
	private double[][] velocities;
	
	private double[][] personalBest;
	
	private HashMap<Integer, Double> fitness = new HashMap<Integer, Double>();
	
	private Neighbourhood neighbourhood;
	
	private CombinatorialPositionUpdate classUnderTest = new CombinatorialPositionUpdate();
	
	@Before
	public void setUp(){
		fitness.put(0, 4.0);
		fitness.put(1, 5.0);
		fitness.put(2, 1.0);
		fitness.put(3, 3.0);
		neighbourhood = new TheRing();
		neighbourhood.setSolutionFitness(fitness);
		
		personalBest = new double[][]{
				{4,2,3,1},
				{2,3,1,4},
				{4,3,1,2},
				{3,4,2,1},
		};
		
		positions = new double[][]{
				{1,2,3,4},
				{2,3,4,1},
				{4,3,2,1},
				{3,4,1,2},
		};
		
		velocities = new double[][]{
				{0, 0.3, 1, 0.5},
				{1, 0.6, 1, 0.4},
				{0, 0.3, 0.2, 1},
				{0.7, 1, 0.4, 0.2},
				{1, 1, 0.5, 1},
				{0.5, 1, 0.5, 1},
				{1, 1, 1, 0.4},
				{1, 0.3, 1, 1},
		};
		
		classUnderTest.setNeighbourhood(neighbourhood);
		classUnderTest.setPositions(positions);
		classUnderTest.setVelocities(velocities);
		classUnderTest.setPersonalBest(personalBest);
	}
	
	@Test
	public void testPositionsCorrect() {
		double[][] expected = new double[][]{
				{2,4,3,1},
				{4,3,1,2},
				{4,3,1,2},
				{3,4,1,2},
		};
		classUnderTest.updatePositions();
		positions = classUnderTest.getPositions();
		assertTrue(Arrays.deepEquals(expected, positions));
	}
	
	@Test
	public void testVelocitiesNormalisedCorrectly(){
		double[][] expected = new double[][]{
				/*
				{0, 0.3, 0, 0.5},
				{0, 0.6, 0, 0.4},
				{0, 0.3, 0.2, 0},
				{0.7, 0, 0.4, 0.2},
				{0, 0, 0.5, 0},
				{0.5, 0, 0.5, 0},
				{0, 0, 0, 0.4},
				{0, 0.3, 0, 0},	*/
				{0, 0.3, 0, 0.5},
				{0, 0.6, 0, 0.4},
				{0, 0.3, 0.2, 0},
				{0.7, 0, 0.4, 0.2},
				{1, 1, 0.5, 1},
				{0.5, 1, 0.5, 1},
				{1, 1, 1, 0.4},
				{1, 0.3, 1, 1},
		};
		classUnderTest.updatePositions();
		velocities = classUnderTest.getVelocities();
		assertTrue(Arrays.deepEquals(expected, velocities));
	}
}

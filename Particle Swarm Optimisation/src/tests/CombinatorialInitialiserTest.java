package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import swarm.CombinatorialInitialiser;
import swarm.Function;

public class CombinatorialInitialiserTest {
	
	private CombinatorialInitialiser classUnderTest = new CombinatorialInitialiser();
	
	private Function f = new DeJong1(5);
	
	private Set<Double> data = new LinkedHashSet<Double>();
	
	private double[][] positions = new double[4][5];
	
	@Before
	public void setUp(){
		for(int i = 1; i < 6; i++){
			data.add((double) i);
		}
	}
	
	public boolean contains(double d, double[] array){
		for(int i = 0; i < array.length; i++){
			if(Math.abs(array[i] - d) < 0.000001){
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void testMatrixCreation(){
		for(int i = 0; i < positions.length; i++){
			for(int k = 0; k < positions[i].length; k++){
				positions[i][k] = k + 1;
			}
		}
		double[][] expected = new double[][]{
				{1,2,3,4,5},
				{1,2,3,4,5},
				{1,2,3,4,5},
				{1,2,3,4,5},
		};
		assertTrue(Arrays.deepEquals(expected, positions));
		
	}
	
	@Test
	public void testShuffleParticles(){
		for(int i = 0; i < positions.length; i++){
			for(int k = 0; k < positions[i].length; k++){
				positions[i][k] = k + 1;
			}
		}
		classUnderTest.shuffleParticles(positions);
		boolean result = true;
		for(int i = 0; i < positions.length; i++){
			for(Double dub: data){
				if(!contains(dub, positions[i])){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void testInitialiseMatrices() {
		classUnderTest.initialiseMatrices(f, 4);
		double [][] positions = classUnderTest.getPositions();
		boolean result = true;
		for(int i = 0; i < positions.length; i++){
			for(Double dub: data){
				if(!contains(dub, positions[i])){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void testPositionsEqualsPB(){
		classUnderTest.initialiseMatrices(f, 4);
		double [][] positions = classUnderTest.getPositions();
		double [][] pB = classUnderTest.getPersonalBest();
		assertTrue(Arrays.deepEquals(positions, pB));
	}
	
	@Test
	public void testVelocitiesInRange(){
		classUnderTest.initialiseMatrices(f, 5);
		double[][] velocities = classUnderTest.getVelocities();
		boolean result = true;
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(velocities[i][k] > 1 || velocities[i][k] < 0){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
}

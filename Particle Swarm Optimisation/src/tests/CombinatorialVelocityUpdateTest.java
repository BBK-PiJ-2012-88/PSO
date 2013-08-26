package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import swarm.CombinatorialInitialiser;
import swarm.CombinatorialPositionUpdate;
import swarm.CombinatorialVelocityUpdate;
import swarm.Neighbourhood;
import swarm.TheRing;

public class CombinatorialVelocityUpdateTest {

	private double[][] velocities = new double[8][4];
	
	private Neighbourhood neighbourhood = new TheRing();
	
	private double[][] position;
	
	private double[][] personalBest; 
	
	private HashMap<Integer, Double> fitness = new HashMap<Integer, Double>();
	
	private CombinatorialVelocityUpdate classUnderTest = new CombinatorialVelocityUpdate();
	
	private CombinatorialPositionUpdate combPosUpdate = new CombinatorialPositionUpdate();
	
	private CombinatorialInitialiser combInit = new CombinatorialInitialiser();
	
	@Before
	public void setUp(){
		fitness.put(0, 4.0);
		fitness.put(1, 5.0);
		fitness.put(2, 1.0);
		fitness.put(3, 3.0);
		
		personalBest = new double[][]{
				{4,2,3,1},
				{2,3,1,4},
				{4,3,1,2},
				{3,4,2,1},
		};
		
		position = new double[][]{
				{1,2,3,4},
				{2,3,4,1},
				{4,3,2,1},
				{3,4,1,2},
		};
		
		Random rand = new Random();
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				velocities[i][k] = rand.nextDouble();
			}
		}
		
		neighbourhood.setSolutionFitness(fitness);
		classUnderTest.setNeighbourhood(neighbourhood);
		classUnderTest.setPosition(position);
		classUnderTest.setVelocities(velocities);
		classUnderTest.setPersonalBest(personalBest);
	}
	
	@Test
	public void testVelocitiesChanging() {
		double[][] previous = new double[8][4];
		for(int i = 0; i < previous.length; i++){
			for(int k = 0; k < previous[i].length; k++){
				previous[i][k] = velocities[i][k];
			}
		}
		velocities = classUnderTest.updateVelocities();
		assertTrue(!Arrays.deepEquals(previous, velocities));
	}
	
	@Test
	public void checkOneVelocityIsOne(){
		velocities = classUnderTest.updateVelocities();
		boolean result = true;
		int rows = velocities.length / 2;
		for(int i = 0; i < rows; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(Math.abs(velocities[i][k] - 1) > 0.00001 && Math.abs(velocities[i + rows][k] - 1) > 0.00001){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void testVelocitiesInRange(){
		boolean result = true;
		velocities = classUnderTest.updateVelocities();
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(velocities[i][k] < 0 || velocities[i][k] > 1){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void checkNoNan(){
		classUnderTest.updateVelocities();
		boolean result = true;
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(Double.isNaN(velocities[i][k])){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void testOneVelocityIsOneAfterMultipleUpdates(){
		for(int i = 0; i < 10; i++){
			classUnderTest.setNeighbourhood(neighbourhood);
			classUnderTest.setPosition(position);
			classUnderTest.setVelocities(velocities);
			classUnderTest.setPersonalBest(personalBest);
			velocities = classUnderTest.updateVelocities();
			combPosUpdate.setPositions(position);
			combPosUpdate.setVelocities(velocities);
			combPosUpdate.setPersonalBest(personalBest);
			combPosUpdate.setNeighbourhood(classUnderTest.getNeighbourhood());
			combPosUpdate.updatePositions();
			position = combPosUpdate.getPositions();
			velocities = combPosUpdate.getVelocities();
			//shuffleParticles(personalBest);
		}
		velocities = classUnderTest.updateVelocities();
		boolean result = true;
		int rows = velocities.length / 2;
		for(int i = 0; i < rows; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(Math.abs(velocities[i][k] - 1) > 0.00001 && Math.abs(velocities[i + rows][k] - 1) > 0.00001){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void testVelocitiesInRangeAfterMultipleIterations(){
		for(int i = 0; i < 10; i++){
			classUnderTest.setNeighbourhood(neighbourhood);
			classUnderTest.setPosition(position);
			classUnderTest.setVelocities(velocities);
			classUnderTest.setPersonalBest(personalBest);
			velocities = classUnderTest.updateVelocities();
			combPosUpdate.setPositions(position);
			combPosUpdate.setVelocities(velocities);
			combPosUpdate.setPersonalBest(personalBest);
			combPosUpdate.setNeighbourhood(classUnderTest.getNeighbourhood());
			combPosUpdate.updatePositions();
			position = combPosUpdate.getPositions();
			velocities = combPosUpdate.getVelocities();
		}
		boolean result = true;
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(velocities[i][k] < 0 || velocities[i][k] > 1){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
	
	
	@Test
	public void testVelocitiesInRangeAfterMultipleIterations2(){
		for(int i = 0; i < 10; i++){
			classUnderTest.setNeighbourhood(neighbourhood);
			classUnderTest.setPosition(position);
			classUnderTest.setVelocities(velocities);
			classUnderTest.setPersonalBest(personalBest);
			velocities = classUnderTest.updateVelocities();
			combPosUpdate.setPositions(position);
			combPosUpdate.setVelocities(velocities);
			combPosUpdate.setPersonalBest(personalBest);
			combPosUpdate.setNeighbourhood(classUnderTest.getNeighbourhood());
			combPosUpdate.updatePositions();
			position = combPosUpdate.getPositions();
			velocities = combPosUpdate.getVelocities();
		}
		boolean result = true;
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(velocities[i][k] >= 0 && velocities[i][k] <= 1){
					
				}else{
					result = false;
				}
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void nonNaNAfterMultipleUpdates(){
		for(int i = 0; i < 10; i++){
			classUnderTest.setNeighbourhood(neighbourhood);
			classUnderTest.setPosition(position);
			classUnderTest.setVelocities(velocities);
			classUnderTest.setPersonalBest(personalBest);
			velocities = classUnderTest.updateVelocities();
			combPosUpdate.setPositions(position);
			combPosUpdate.setVelocities(velocities);
			combPosUpdate.setPersonalBest(personalBest);
			combPosUpdate.setNeighbourhood(classUnderTest.getNeighbourhood());
			combPosUpdate.updatePositions();
			position = combPosUpdate.getPositions();
			velocities = combPosUpdate.getVelocities();
		}
		boolean result = true;
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				if(Double.isNaN(velocities[i][k])){
					result = false;
				}
			}
		}
		assertTrue(result);
	}

}

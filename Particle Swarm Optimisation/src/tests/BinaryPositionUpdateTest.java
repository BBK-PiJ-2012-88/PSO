package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import swarm.BinaryPositionUpdate;

public class BinaryPositionUpdateTest {
	
	private double[][] position = new double[][]{
		{0,1,1,0},
		{1,1,1,1},
		{0,1,1,1},
		{0,0,0,1},
	};
	
	private double[][] velocities = new double[4][4];
	
	private BinaryPositionUpdate update = new BinaryPositionUpdate();

	@Test
	public void testPositionsAreUpdated() {
		Random rand = new Random();
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				velocities[i][k] = rand.nextDouble();
			}
		}
		update.setPositions(position);
		update.setVelocities(velocities);
		update.updatePositions();
		double[][] notExpected = new double[][]{
				{0,1,1,0},
				{1,1,1,1},
				{0,1,1,1},
				{0,0,0,1},
		};
		position = update.getPositions();
		assertTrue(!Arrays.deepEquals(notExpected, position));
	}
	
	@Test
	public void testVelocitiesAreNormalised(){
		Random rand = new Random();
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				velocities[i][k] = rand.nextDouble() * 10;
			}
		}
		update.setPositions(position);
		update.setVelocities(velocities);
		update.updatePositions();
		velocities = update.getVelocities();
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

}

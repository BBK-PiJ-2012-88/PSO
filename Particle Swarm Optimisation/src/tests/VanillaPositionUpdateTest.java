package tests;

import static org.junit.Assert.*;

import java.lang.reflect.Array;

import org.junit.Test;

import swarm.VanillaPositionUpdate;

public class VanillaPositionUpdateTest {
	
	private VanillaPositionUpdate classUnderTest = new VanillaPositionUpdate();
	
	@Test
	public void test1() {
		double[][]position = new double[5][5];
		double[][]velocity = new double[5][5];
		for(int i = 0; i < velocity.length; i++){
			for(int k = 0; k < velocity[i].length; k++){
				velocity[i][k] = Math.random();
			}
		}
		classUnderTest.setPositions(position);
		classUnderTest.setVelocities(velocity);
		classUnderTest.updatePositions();
		position = classUnderTest.getPositions();
		assertTrue(java.util.Arrays.deepEquals(position, velocity));
	}
	
	@Test
	public void test2(){
		double[][]position = new double[5][5];
		double[][]velocity = new double[5][5];
		for(int i = 0; i < velocity.length; i++){
			for(int k = 0; k < velocity[i].length; k++){
				velocity[i][k] = Math.random() * 5;
			}
		}
		classUnderTest.setPositions(position);
		classUnderTest.setVelocities(velocity);
		classUnderTest.updatePositions();
		position = classUnderTest.getPositions();
		assertTrue(java.util.Arrays.deepEquals(position, velocity));
	}

}

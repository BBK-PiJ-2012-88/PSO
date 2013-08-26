package tests;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import swarm.VanillaPositionUpdate;

public class VanillaPositionUpdateTest {
	
	private VanillaPositionUpdate classUnderTest = new VanillaPositionUpdate();
	
	private double[] max;
	
	private double[] min;
	
	@Before
	public void setUp(){
		max = new double[5];
		Arrays.fill(max, 7);
		min = new double[5];
		Arrays.fill(min, -7);
	}
	
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
	
	@Test
	public void testConstrainedUpdateUpperConstraint(){
		classUnderTest.setConstraints(true);
		double[][] position = new double[][]{
				{2,3,4,5,6},
				{4,5,6,7,8},
				{1,4,5,6,9}
		};
		double[][] velocity = new double[3][5];
		for(int i = 0; i < velocity.length; i++){
			for(int k = 0; k < velocity[i].length; k++){
				velocity[i][k] = Math.random() * 10;
			}
		}
		classUnderTest.getConstrainer().setMaximum(max);
		classUnderTest.getConstrainer().setMinimum(min);
		classUnderTest.setPositions(position);
		classUnderTest.setVelocities(velocity);
		classUnderTest.updatePositions();
		position = classUnderTest.getPositions();
		boolean result = true;
		for(int i = 0; i < position.length; i++){
			for(int k = 0; k < position[i].length; k++){
				if(position[i][k] > 7){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void testConstrainedUpdateLowerLimit(){
		classUnderTest.setConstraints(true);
		double[][] position = new double[][]{
				{-2,-3,-4,-5,-6},
				{-4,-5,-6,-7,-8},
				{-1,-4,-5,-6,-9}
		};
		double[][] velocity = new double[3][5];
		for(int i = 0; i < velocity.length; i++){
			for(int k = 0; k < velocity[i].length; k++){
				velocity[i][k] = Math.random() * -10;
			}
		}
		
		classUnderTest.getConstrainer().setMaximum(max);
		classUnderTest.getConstrainer().setMinimum(min);
		classUnderTest.setPositions(position);
		classUnderTest.setVelocities(velocity);
		classUnderTest.updatePositions();
		position = classUnderTest.getPositions();
		boolean result = true;
		for(int i = 0; i < position.length; i++){
			for(int k = 0; k < position[i].length; k++){
				if(position[i][k] < -7){
					result = false;
				}
			}
		}
		assertTrue(result);
	}

}

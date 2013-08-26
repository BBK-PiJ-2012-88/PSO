package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import swarm.BinaryConverter;
import swarm.BinaryConverterImpl;
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
	
	@Test
	public void testConstrainedPositionUpdateTest(){
		double[] max = {5.12, 5.12};
		double[] min = {-5.12, -5.12};
		update.getConstrainer().setMaximum(max);
		update.getConstrainer().setMinimum(min);
		double[][] position = new double[][]{
				{0,1,0,0,0,1,0,1,0,0,1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,1,0,0,0,1,1,1,1,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0}
		};
		Random rand = new Random();
		velocities = new double[2][position[0].length];
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				velocities[i][k] = rand.nextDouble();
			}
		}
		update.setVelocities(velocities);
		update.setPositions(position);
		update.setConstraints(true);
		update.updatePositions();
		position = update.getPositions();
		BinaryConverter bin = new BinaryConverterImpl();
		int binaryEncoding = bin.getBinaryEncoding();
		for(int i = 0; i < position.length; i++){
			for(int k = 0; k < position[i].length; k = k + binaryEncoding){
				assertTrue(bin.convertBinaryToReal(Arrays.copyOfRange(position[i], k, k + binaryEncoding)) >= -5.12 && bin.convertBinaryToReal(Arrays.copyOfRange(position[i], k, k + binaryEncoding)) <= 5.12);
			}
		}
	}

}

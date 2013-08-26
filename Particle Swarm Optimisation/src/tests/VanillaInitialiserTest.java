package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import swarm.Function;
import swarm.VanillaInitialiser;

public class VanillaInitialiserTest {
	
	private VanillaInitialiser classUnderTest = new VanillaInitialiser();
	
	private Function f = new DeJong1();
	
	private int numberOfParticles = 10;


	@Test
	public void test1(){
		double[] upperLimit = new double[f.getVariables()];
		Arrays.fill(upperLimit, 5);
		double[] lowerLimit = new double[f.getVariables()];
		Arrays.fill(lowerLimit, -5);
		classUnderTest.setMax(upperLimit);
		classUnderTest.setMin(lowerLimit);
		classUnderTest.initialiseMatrices(f, numberOfParticles);
		boolean result = true;
		double[][] position = classUnderTest.getPositions();
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < f.getVariables(); k++){
				if(position[i][k] < -5 || position[i][k] > 5){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void test2(){
		double[] upperLimit = new double[f.getVariables()];
		Arrays.fill(upperLimit, 5);
		double[] lowerLimit = new double[f.getVariables()];
		Arrays.fill(lowerLimit, -5);
		classUnderTest.setMax(upperLimit);
		classUnderTest.setMin(lowerLimit);
		classUnderTest.initialiseMatrices(f, numberOfParticles);
		boolean result = false;
		double[][] position = classUnderTest.getPositions();
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < f.getVariables(); k++){
				if(position[i][k] != 0){
					result = true;
				}
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void test3(){
		double[] upperLimit = new double[f.getVariables()];
		Arrays.fill(upperLimit, 5.12);
		double[] lowerLimit = new double[f.getVariables()];
		Arrays.fill(lowerLimit, -5.12);
		classUnderTest.setMax(upperLimit);
		classUnderTest.setMin(lowerLimit);
		classUnderTest.initialiseMatrices(f, numberOfParticles);
		boolean result = true;
		double[][] position = classUnderTest.getPositions();
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < f.getVariables(); k++){
				if(position[i][k] < -5.12 || position[i][k] > 5.12){
					result = false;
				}
			}
		}
		assertTrue(result);
	}
}

package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import swarm.Function;
import swarm.VanillaInitialiser;

public class TestVanillaInitialiser {
	
	private VanillaInitialiser classUnderTest = new VanillaInitialiser();
	
	private Function f = new DeJong1();
	
	private int numberOfParticles = 10;
	
	@Test
	public void test1() {
		classUnderTest.initialiseMatrices(f, numberOfParticles);
		boolean result = true;
		double[][] position = classUnderTest.getPositions();
		for(int i = 0; i < numberOfParticles; i++){
			for(int k = 0; k < f.getVariables(); k++){
				if(position[i][k] < 0 || position[i][k] > 1){
					result = false;
				}
			}
		}
		assertTrue(result);
	}

	@Test
	public void test2(){
		classUnderTest.initialiseMatrices(f, numberOfParticles, -5, 5);
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
	public void test3(){
		classUnderTest.initialiseMatrices(f, numberOfParticles, -5, 5);
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
}

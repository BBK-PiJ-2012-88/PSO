package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

import swarm.FitnessCalculator;
import swarm.FitnessCalculatorImpl;
import swarm.Function;
import swarm.VelocityClamping;

public class VelClampingTest {
	
	private double[][] position = new double[][]{
		{1.5,2.5,3.5,4.5},
		{2.5,3.5,4.5,5.5},
		{1.5,2.5,2.5,4.5},
		{1.5,3.5,3.5,2.5},	
	};
	
	private double[][] pB = new double[][]{
			{1.1,2.1,3.1,5.1},
			{3.2,4.2,5.2,6.2},
			{1.3,2.3,3.3,4.3},
			{1.4,3.4,3.4,3.4}
	};
	
	private double[] max = new double[]{2,2,2,2};
	
	private double[][] vel = new double[4][4];
	
	private Function f = new DeJong1(4);
	
	private VelocityClamping velUp = new VelocityClamping(max);
	
	private FitnessCalculator fit = new FitnessCalculatorImpl(f);

	@Test
	public void test() {
		HashMap<Integer, Double> fitness;
		fit.setPositions(position);
		fit.initialCalculateFitness();
		fitness = fit.getFitness();
		Random rand = new Random();
		double[][] compVel = new double[4][4];
		for(int i = 0; i < vel.length; i++){
			for(int k = 0; k < vel[i].length; k++){
				vel[i][k] = rand.nextDouble();
				compVel[i][k] = vel[i][k];
			}
		}
		velUp.setVelocities(vel);
		velUp.setPersonalBest(pB);
		velUp.setPosition(position);
		velUp.getNeighbourhood().setSolutionFitness(fitness);
		boolean result = true;
		vel = velUp.updateVelocities();
		for(int i = 0; i < vel.length; i++){
			for(int k = 0; k < vel[i].length; k++){
				if((Math.abs(vel[i][k]) - max[i]) > 0.00001 || vel[i][k] == compVel[i][k]){
					result = false;
				}
			}
		}
		assertTrue(result);
	}

}

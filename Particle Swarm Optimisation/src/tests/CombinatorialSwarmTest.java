package tests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import swarm.CombinatorialSwarm;

public class CombinatorialSwarmTest {
	
	private CombinatorialFunction a280 = new CombinatorialFunction("/Users/williamhogarth/Downloads/a280.tsp");
	
	private CombinatorialSwarm classUnderTest = new CombinatorialSwarm();
	
	@Test
	public void test() {
		Vector<Double> result = new Vector<Double>();
		result = classUnderTest.optimise(a280);
		double fitness = a280.CalculateFitness(result);
		System.out.println(fitness);
	}

}

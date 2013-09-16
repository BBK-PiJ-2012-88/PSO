package tests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import swarm.CombinatorialSwarm;
import swarm.CombinatorialVelocityUpdate;
import swarm.IterationHalt;
import swarm.TheFourClusters;

public class CombinatorialSwarmTest {
	
	private CombinatorialFunction a280 = new CombinatorialFunction("/Users/williamhogarth/Downloads/a280.tsp");
	
	private CombinatorialSwarm classUnderTest = new CombinatorialSwarm();
	
	@Test
	public void test() {
		classUnderTest.setNumberOfParticles(20);
		classUnderTest.setNeighbourhood(new TheFourClusters());
		IterationHalt halt = new IterationHalt(1000);
		classUnderTest.setHaltingCriteria(halt);
		Vector<Double> result = new Vector<Double>();
		result = classUnderTest.optimise(a280);
		double fitness = a280.CalculateFitness(result);
		assertTrue(fitness < 50000);
	}

	@Test
	public void testGeneticUpdate() {
		classUnderTest.setNumberOfParticles(20);
		classUnderTest.setNeighbourhood(new TheFourClusters());
		IterationHalt halt = new IterationHalt(1000);
		classUnderTest.setHaltingCriteria(halt);
		Vector<Double> result = new Vector<Double>();
		result = classUnderTest.geneticOptimise(a280);
		double fitness = a280.CalculateFitness(result);
		assertTrue(fitness < 50000);
	}
}

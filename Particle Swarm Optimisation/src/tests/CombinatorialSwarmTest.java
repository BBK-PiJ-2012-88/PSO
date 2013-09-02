package tests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import swarm.CombinatorialSwarm;
import swarm.CombinatorialVelocityUpdate;
import swarm.TheFourClusters;

public class CombinatorialSwarmTest {
	
	private CombinatorialFunction a280 = new CombinatorialFunction("/Users/williamhogarth/Downloads/a280.tsp");
	
	private CombinatorialSwarm classUnderTest = new CombinatorialSwarm();
	
	@Test
	public void test() {
		classUnderTest.setNumberOfParticles(20);
		classUnderTest.setNeighbourhood(new TheFourClusters());
		CombinatorialVelocityUpdate v =(CombinatorialVelocityUpdate) classUnderTest.getVelocityUpdate();
		v.setWeight(0.1);
		Vector<Double> result = new Vector<Double>();
		result = classUnderTest.optimise(a280);
		double fitness = a280.CalculateFitness(result);
		assertTrue(fitness < 10000);
	}

	@Test
	public void testGeneticUpdate() {
		classUnderTest.setNumberOfParticles(20);
		classUnderTest.setNeighbourhood(new TheFourClusters());
		CombinatorialVelocityUpdate v =(CombinatorialVelocityUpdate) classUnderTest.getVelocityUpdate();
		v.setWeight(0.1);
		Vector<Double> result = new Vector<Double>();
		result = classUnderTest.geneticOptimise(a280);
		double fitness = a280.CalculateFitness(result);
		assertTrue(fitness < 10000);
	}
}

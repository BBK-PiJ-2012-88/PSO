package tests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import swarm.AcceptableSolutionHalt;
import swarm.ConstrictionCoefficient;
import swarm.Function;
import swarm.IterationHalt;
import swarm.VanillaSwarm;

public class VanillaSwarmTest {
	
	private VanillaSwarm classUnderTest;
	private AcceptableSolutionHalt halt = new AcceptableSolutionHalt();
	private Function function;
	private ConstrictionCoefficient velUpdate = new ConstrictionCoefficient();
	
	@Test
	public void testDeJong1() {
		function = new DeJong1(1);
		double acceptableSolution = 1;
		halt.setAcceptableSolution(acceptableSolution);
		velUpdate.setK(0.01);
		classUnderTest = new VanillaSwarm(function, velUpdate, halt);
		classUnderTest.setLowerLimit(-5.12);
		classUnderTest.setUpperLimit(5.12);
		Vector<Double> temp = classUnderTest.optimise();
		double result = function.CalculateFitness(temp);
		System.out.println(result);
		assertTrue(result <= acceptableSolution);
	}

}

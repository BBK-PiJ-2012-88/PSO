package tests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import swarm.AcceptableSolutionHalt;
import swarm.ConstrictionCoefficient;
import swarm.Function;
import swarm.GeneticSwarm;
import swarm.VelocityUpdate;

public class GeneticSwarmTest {

	private GeneticSwarm classUnderTest = new GeneticSwarm();
	private AcceptableSolutionHalt halt = new AcceptableSolutionHalt();
	private Function function = new BinaryDeJong1();
	private VelocityUpdate velUp = new ConstrictionCoefficient();
	
	@Test
	public void test() {
		double acceptableSolution = 1;
		halt.setAcceptableSolution(acceptableSolution);
		classUnderTest.setObjectiveFunction(function);
		classUnderTest.setVelocityUpdate(velUp);
		classUnderTest.setHaltingCriteria(halt);
		Vector<Double> binaryString = classUnderTest.optimise();
		double result = function.CalculateFitness(binaryString);
		assertTrue(result <= acceptableSolution);
	}
}

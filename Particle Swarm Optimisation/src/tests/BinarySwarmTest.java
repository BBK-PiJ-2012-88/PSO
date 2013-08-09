package tests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import swarm.AcceptableSolutionHalt;
import swarm.BinarySwarm;
import swarm.ConstrictionCoefficient;
import swarm.Function;
import swarm.VelocityUpdate;

public class BinarySwarmTest {

	private BinarySwarm classUnderTest = new BinarySwarm();
	private AcceptableSolutionHalt halt = new AcceptableSolutionHalt();
	private Function function = new BinaryDeJong1();
	private VelocityUpdate velUp = new ConstrictionCoefficient();
	
	@Test
	public void test() {
		double acceptableSolution = 0.00001;
		halt.setAcceptableSolution(acceptableSolution);
		classUnderTest.setObjectiveFunction(function);
		classUnderTest.setVelocityUpdate(velUp);
		classUnderTest.setHaltingCriteria(halt);
		Vector<Double> binaryString = classUnderTest.optimise();
		double result = function.CalculateFitness(binaryString);
		assertTrue(result <= acceptableSolution);
	}

}

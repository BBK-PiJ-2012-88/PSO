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
		Vector<Double> temp = classUnderTest.optimise(function, 5.12, -5.12);
		double result = function.CalculateFitness(temp);
		System.out.println(result);
		assertTrue(result <= acceptableSolution);
	}
	
	@Test
	public void testDeJong2() {
		function = new DeJong1(1);
		IterationHalt halt2 = new IterationHalt(100);
		velUpdate.setK(0.01);
		classUnderTest = new VanillaSwarm(function, velUpdate, halt2);
		Vector<Double> temp = classUnderTest.optimise(function, 5.12, -5.12);
		double result = function.CalculateFitness(temp);
		System.out.println(result);
		assertTrue(result <= 2);
	}
	
	@Test
	public void testConstrainedOptimisation(){
		function = new DeJong3();
		velUpdate.setK(0.01);
		classUnderTest = new VanillaSwarm();
		classUnderTest.setHaltingCriteria(new IterationHalt(100));
		classUnderTest.setVelocityUpdate(velUpdate);
		Vector<Double> temp = classUnderTest.constrainedOptimise(function, 5.12, -5.12);
		double result = function.CalculateFitness(temp);
		System.out.println(result);
		assertTrue(result >= 0 && result <= 50);
		
	}

}

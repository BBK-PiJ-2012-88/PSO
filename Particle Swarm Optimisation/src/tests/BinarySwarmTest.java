package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Vector;

import org.junit.Test;

import swarm.AcceptableSolutionHalt;
import swarm.BinarySwarm;
import swarm.ConstrictionCoefficient;
import swarm.Function;
import swarm.IterationHalt;
import swarm.VelocityClamping;
import swarm.VelocityUpdate;

public class BinarySwarmTest {

	private BinarySwarm classUnderTest = new BinarySwarm();
	private AcceptableSolutionHalt halt = new AcceptableSolutionHalt();
	private Function function = new BinaryDeJong1();
	private VelocityUpdate velUp;
	
	@Test
	public void test() {
		double acceptableSolution = 0.00001;
		halt.setAcceptableSolution(acceptableSolution);
		classUnderTest.setObjectiveFunction(function);
		velUp = new ConstrictionCoefficient();
		classUnderTest.setVelocityUpdate(velUp);
		classUnderTest.setHaltingCriteria(halt);
		Vector<Double> binaryString = classUnderTest.optimise();
		double result = function.CalculateFitness(binaryString);
		assertTrue(result <= acceptableSolution);
	}
	
	@Test
	public void test2(){
		double[] max = new double[function.getVariables()];
		Arrays.fill(max, 5);
		velUp = new VelocityClamping(max);
		IterationHalt itHalt = new IterationHalt(1000);
		classUnderTest.setObjectiveFunction(function);
		classUnderTest.setVelocityUpdate(velUp);
		classUnderTest.setHaltingCriteria(itHalt);
		Vector<Double> binaryString = classUnderTest.optimise();
		double result = function.CalculateFitness(binaryString);
		assertTrue(result < 10);
	}
	
	@Test
	public void testGeneticSwarm1(){
		double[] max = new double[function.getVariables()];
		Arrays.fill(max, 5);
		velUp = new VelocityClamping(max);
		classUnderTest.setGeneticOptimisation(true);
		IterationHalt itHalt = new IterationHalt(1000);
		classUnderTest.setObjectiveFunction(function);
		classUnderTest.setVelocityUpdate(velUp);
		classUnderTest.setHaltingCriteria(itHalt);
		Vector<Double> binaryString = classUnderTest.geneticOptimise();
		double result = function.CalculateFitness(binaryString);
		assertTrue(result < 10);
	}
	
	@Test
	public void testGeneticSwarm2(){
		double acceptableSolution = 0.00001;
		halt.setAcceptableSolution(acceptableSolution);
		classUnderTest.setObjectiveFunction(function);
		classUnderTest.setGeneticOptimisation(true);
		velUp = new ConstrictionCoefficient();
		classUnderTest.setVelocityUpdate(velUp);
		classUnderTest.setHaltingCriteria(halt);
		Vector<Double> binaryString = classUnderTest.geneticOptimise();
		double result = function.CalculateFitness(binaryString);
		assertTrue(result <= acceptableSolution);
	}

}

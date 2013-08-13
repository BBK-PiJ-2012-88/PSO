package tests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

import swarm.AcceptableSolutionHalt;
import swarm.BinarySwarm;
import swarm.CombinatorialSwarm;
import swarm.Function;
import swarm.GeneticSwarm;
import swarm.HaltingCriteria;
import swarm.Swarm;
import swarm.VanillaSwarm;

public class TestSwarmMaximums {
	
	private Swarm swarm;
	
	private HaltingCriteria acceptableSolutionHalt = new AcceptableSolutionHalt(true);
	
	private Function function;
	
	@Test
	public void testVanilaMaximum() {
		function = new DeJong1(10);
		swarm = new VanillaSwarm();
		((VanillaSwarm)swarm).setMaximum(true);
		((AcceptableSolutionHalt) acceptableSolutionHalt).setAcceptableSolution(10000);
		((VanillaSwarm)swarm).setHaltingCriteria(acceptableSolutionHalt);
		Vector<Double> resultVector = swarm.optimise(function);
		double result = function.CalculateFitness(resultVector);
		System.out.println(result);
		assertTrue(result >= 10000);
	}
	
	@Test
	public void testBinaryMaximum(){
		function = new BinaryDeJong1();
		swarm = new BinarySwarm();
		swarm.setMaximum(true);
		((AcceptableSolutionHalt) acceptableSolutionHalt).setAcceptableSolution(1000);
		((BinarySwarm)swarm).setHaltingCriteria(acceptableSolutionHalt);
		Vector<Double> resultVector = swarm.optimise(function);
		double result = function.CalculateFitness(resultVector);
		System.out.println(result);
		assertTrue(result >= 1000000000000000000000000000000000000000000000000000000000000000000000000000.0);
	}
	/*
	@Test
	public void testGeneticMaximum(){
		function = new BinaryDeJong1();
		swarm = new GeneticSwarm();
		swarm.setMaximum(true);
		((AcceptableSolutionHalt) acceptableSolutionHalt).setAcceptableSolution(1000);
		((GeneticSwarm)swarm).setHaltingCriteria(acceptableSolutionHalt);
		Vector<Double> resultVector = swarm.optimise(function);
		double result = function.CalculateFitness(resultVector);
		System.out.println(result);
		assertTrue(result >= 1000);
	}*/
	
	@Test
	public void testCombinatorialMaximum(){
		function = new CombinatorialFunction("/Users/williamhogarth/Downloads/a280.tsp");
		swarm = new CombinatorialSwarm();
		swarm.setMaximum(true);
		((AcceptableSolutionHalt) acceptableSolutionHalt).setAcceptableSolution(10000);
		((CombinatorialSwarm)swarm).setHaltingCriteria(acceptableSolutionHalt);
		Vector<Double> resultVector = swarm.optimise(function);
		double result = function.CalculateFitness(resultVector);
		System.out.println(result);
		assertTrue(result >= 10000);
	}

}

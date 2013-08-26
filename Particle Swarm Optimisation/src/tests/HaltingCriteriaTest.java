package tests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import swarm.AcceptableError;
import swarm.Function;
import swarm.GradientHalt;
import swarm.HaltingCriteria;
import swarm.IterationHalt;
import swarm.VanillaSwarm;

public class HaltingCriteriaTest {
	
	private VanillaSwarm vanillaSwarm = new VanillaSwarm();
	
	private Function function = new DeJong1(2);
	
	private HaltingCriteria halt;
	
	@Test
	public void testAcceptableError() {
		halt = new AcceptableError(2,4);
		vanillaSwarm.setHaltingCriteria(halt);
		Vector<Double> opt = vanillaSwarm.optimise(function);
		double result = function.CalculateFitness(opt);
		assertTrue(result >= 2 - 4 && result <= 2 + 4);
	}
	
	@Test
	public void testGradientHalt(){
		halt = new GradientHalt(0.6);
		halt.updateData(2, 1);
		halt.updateData(4, 2);
		assertTrue(halt.halt());
	}
	
	@Test
	public void testIterationHalt(){
		halt = new IterationHalt(100);
		halt.updateData(0, 100);
		assertTrue(halt.halt());
	}
	
	

}

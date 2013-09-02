package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import swarm.BinarySwarm;
import swarm.CombinatorialGeneticOperator;
import swarm.CombinatorialSwarm;
import swarm.GeneticOperator;
import swarm.GeneticOperatorFactory;
import swarm.GeneticOperatorFactoryImpl;
import swarm.GeneticOperatorImpl;

public class GeneticOperatorFactoryImplTest {

	private GeneticOperatorFactory classUnderTest = new GeneticOperatorFactoryImpl();
	
	@Test
	public void testBinary() {
		BinarySwarm b = new BinarySwarm();
		GeneticOperator result = classUnderTest.createGeneticOperator(b);
		assertTrue(result instanceof GeneticOperatorImpl);
	}
	
	@Test
	public void testCombinatorial(){
		CombinatorialSwarm c = new CombinatorialSwarm();
		GeneticOperator result = classUnderTest.createGeneticOperator(c);
		assertTrue(result instanceof CombinatorialGeneticOperator);
	}

}

package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import swarm.BinaryInitialiser;
import swarm.BinarySwarm;
import swarm.CombinatorialInitialiser;
import swarm.CombinatorialSwarm;
import swarm.Initialiser;
import swarm.InitialiserFactory;
import swarm.InitialiserFactoryImpl;
import swarm.VanillaInitialiser;
import swarm.VanillaSwarm;

public class InitialiserFactroyImplTest {

	private InitialiserFactory classUnderTest = new InitialiserFactoryImpl();
	
	@Test
	public void testVanilla() {
		VanillaSwarm v = new VanillaSwarm();
		Initialiser result = classUnderTest.createInitialiser(v);
		assertTrue(result instanceof VanillaInitialiser);
	}
	
	@Test
	public void testBinary(){
		BinarySwarm b = new BinarySwarm();
		Initialiser result = classUnderTest.createInitialiser(b);
		assertTrue(result instanceof BinaryInitialiser);
	}
	
	@Test
	public void testCombinatorial(){
		CombinatorialSwarm c = new CombinatorialSwarm();
		Initialiser result = classUnderTest.createInitialiser(c);
		assertTrue(result instanceof CombinatorialInitialiser);
	}
}

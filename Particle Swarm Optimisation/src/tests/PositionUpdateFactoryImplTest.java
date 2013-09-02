package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import swarm.BinaryPositionUpdate;
import swarm.BinarySwarm;
import swarm.CombinatorialPositionUpdate;
import swarm.CombinatorialSwarm;
import swarm.PositionUpdate;
import swarm.PositionUpdateFactory;
import swarm.PositionUpdateFactoryImpl;
import swarm.VanillaPositionUpdate;
import swarm.VanillaSwarm;

public class PositionUpdateFactoryImplTest {

	private PositionUpdateFactory classUnderTest = new PositionUpdateFactoryImpl();
	
	@Test
	public void testVanilla() {
		VanillaSwarm v = new VanillaSwarm();
		PositionUpdate result = classUnderTest.getPositionUpdate(v);
		assertTrue(result instanceof VanillaPositionUpdate);
	}
	
	@Test
	public void testBinary(){
		BinarySwarm b = new BinarySwarm();
		PositionUpdate result = classUnderTest.getPositionUpdate(b);
		assertTrue(result instanceof BinaryPositionUpdate);
	}
	
	@Test
	public void testCombinatorial(){
		CombinatorialSwarm c = new CombinatorialSwarm();
		PositionUpdate result = classUnderTest.getPositionUpdate(c);
		assertTrue(result instanceof CombinatorialPositionUpdate);
	}
	

}

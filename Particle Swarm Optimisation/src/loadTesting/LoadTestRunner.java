package loadTesting;

public class LoadTestRunner {
	public static void main(String[] args){
		/*NeighbourhoodLoadTests n = new NeighbourhoodLoadTests();
		n.run();
		BinaryLoadTests b = new BinaryLoadTests();
		b.run();
		VanillaLoadTesting v = new VanillaLoadTesting();
		v.run();
		VelocityUpdateLoadTests vel = new VelocityUpdateLoadTests();
		vel.run();*/
		CombinatorialLoadTests c = new CombinatorialLoadTests();
		c.run();
	}
}

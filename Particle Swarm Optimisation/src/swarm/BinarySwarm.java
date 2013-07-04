package swarm;

import java.util.Vector;

public class BinarySwarm implements Swarm {

	private Function objectiveFunction;
	
	private int [][] binaryPosition;
	
	private int [][] bestBinaryPosition;
	
	private double [][] position;
	
	private double [][] personalBest;
	
	private double [][] velocities;
	
	private SigmoidFunction sigmoidfunction;
	
	
	@Override
	public Vector<Double> optimise() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Double> optimise(Function objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
		return optimise();
	}

}

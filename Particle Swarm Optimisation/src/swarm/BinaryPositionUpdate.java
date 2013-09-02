package swarm;

import java.util.Random;

public class BinaryPositionUpdate implements ConstrainedPositionUpdate {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4165216294679962913L;

	private double[][] positions;
	
	private double[][] velocities;
	
	private SigmoidFunction sigmoidFunction = new SigmoidFunction();
	
	private Constrainer constrainer = new BinaryConstrainer();
	
	private boolean constraints = false;
	
	public boolean isConstraints() {
		return constraints;
	}

	public void setConstraints(boolean constraints) {
		this.constraints = constraints;
	}

	public Constrainer getConstrainer() {
		return constrainer;
	}
	
	public void setConstrainer(Constrainer constrainer){
		this.constrainer = constrainer;
	}
	
	@Override
	public void setPositions(double[][] positions) {
		this.positions = positions;

	}

	@Override
	public void setVelocities(double[][] velocities) {
		this.velocities = velocities;

	}

	@Override
	public double[][] getPositions() {
		return positions;
	}

	@Override
	public double[][] getVelocities() {
		return velocities;
	}

	@Override
	public void updatePositions() {
		normaliseVelocities();
		Random random = new Random();
		for(int i = 0; i < positions.length; i++){
			for(int k = 0; k < positions[i].length; k++){
				if(velocities[i][k] > random.nextDouble()){
					positions[i][k] = 1;
				}else{
					positions[i][k] = 0;
				}
			}
		}
		if(constraints){
			constrainer.setPositions(positions);
			constrainer.constrain();
			positions = constrainer.getPositions();
		}
	}
	

	private void normaliseVelocities() {
		for(int i = 0; i < velocities.length; i++){
			for(int k = 0; k < velocities[i].length; k++){
				velocities[i][k] = sigmoidFunction.normalise(velocities[i][k]);
			}
		}
	}

	@Override
	public double[] getMaximum() {
		return getConstrainer().getMaximum();
	}

	@Override
	public void setMaximum(double[] maximum) {
		getConstrainer().setMaximum(maximum);
		
	}

	@Override
	public double[] getMinimum() {
		return getConstrainer().getMinimum();
	}

	@Override
	public void setMinimum(double[] minimum) {
		getConstrainer().setMinimum(minimum);
		
	}

}

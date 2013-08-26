package tests;

import java.util.Vector;

import swarm.Function;

public class DeJong3 implements Function{

	private int variables = 5;
	
	public DeJong3(){}
	
	public void setVariables(int variables){
		this.variables = variables;
	}
	
	@Override
	public int getVariables() {
		return variables;
	}

	@Override
	public double CalculateFitness(double[] candidateSolution) {
		double result = 5 * variables;
		for(int i = 0; i < candidateSolution.length; i++){
			result = result + (int)candidateSolution[i];
		}
		return result;
	}

	@Override
	public double CalculateFitness(Vector<Double> candidateSolution) {
		double[] temp = new double[candidateSolution.size()];
		for(int i = 0;  i < candidateSolution.size(); i++){
			temp[i] = candidateSolution.get(i);
		}
		return CalculateFitness(temp);
	}
}

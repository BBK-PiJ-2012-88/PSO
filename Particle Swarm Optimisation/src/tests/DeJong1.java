package tests;

import java.util.Vector;

import swarm.Function;

public class DeJong1 implements Function {

	private int variables = 32;
	
	public DeJong1(){}
	
	public DeJong1(int variables){
		this.variables = variables;
	}
	
	public void setVariables(int variables) {
		this.variables = variables;
	}

	@Override
	public int getVariables() {
		return variables;
	}

	@Override
	public double CalculateFitness(double[] candidateSolution) {
		double result = 0;
		for(int i = 0; i < variables; i++){
			result = result + (candidateSolution[i] * candidateSolution[i]);
		}
		return result;
	}

	@Override
	public double CalculateFitness(Vector<Double> candidateSolution) {
		double[] convert = new double[candidateSolution.size()];
		for(int i = 0; i < convert.length; i++){
			convert[i] = candidateSolution.get(i);
		}
		return CalculateFitness(convert);
	}

}

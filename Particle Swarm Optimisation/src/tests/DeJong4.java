package tests;

import java.util.Vector;

import swarm.Function;

public class DeJong4 implements Function {

	private int variables;
	
	@Override
	public int getVariables() {
		return variables;
	}

	@Override
	public double CalculateFitness(double[] candidateSolution) {
		double result = 0;
		for(int i = 0; i < candidateSolution.length; i++){
			double temp = candidateSolution[i];
			temp = temp * temp * temp * temp;
			result = result + temp;
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

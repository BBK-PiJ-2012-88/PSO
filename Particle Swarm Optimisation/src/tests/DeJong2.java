package tests;

import java.util.Vector;

import swarm.Function;

public class DeJong2 implements Function{

	private int variables = 2;
	
	public DeJong2(){}
	
	public DeJong2(int variables){
		this.variables = variables;
	}
	
	public void setVariables(int variables){
		this.variables = variables;
	}
	
	@Override
	public int getVariables() {
		return variables;
	}

	@Override
	public double CalculateFitness(double[] candidateSolution) {
		double result = 0;
		for(int i = 0; i < candidateSolution.length; i = i + 2){
			result = result + (100 * Math.pow(Math.pow(candidateSolution[i], 2) - candidateSolution[i + 1], 2) + Math.pow(1 - candidateSolution[0], 2));
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

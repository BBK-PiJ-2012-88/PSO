package tests;

import java.util.Random;
import java.util.Vector;

import swarm.Function;

public class DeJong4 implements Function {

	private int variables = 10;
	
	public DeJong4(){}
	
	public DeJong4(int variables){
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
		Random rand = new Random();
		double result = 0;
		for(int i = 0; i < candidateSolution.length; i++){
			double temp = candidateSolution[i];
			result = result + (i * Math.pow(temp, 4) + rand.nextDouble());
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

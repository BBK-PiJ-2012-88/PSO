package tests;

import java.util.Vector;

import swarm.Function;

public class DeJong5 implements Function {
	
	private int variables;
	
	private int[][] matrix = {{-32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32,},
							  {-32, -32, -32, -32, -32, -16, -16, -16, -16, -16, 0, 0, 0, 0, 0, 16, 16, 16, 16, 16, 32, 32, 32, 32, 32} };
	
	@Override
	public int getVariables() {
		return variables;
	}

	@Override
	//need to check this
	public double CalculateFitness(double[] candidateSolution) {
		assert candidateSolution.length == 2;
		double result = 0;
		for(int i = 0; i < 25; i++){
			double denominator = 0;
			denominator = denominator + Math.pow(candidateSolution[0] - matrix [0][i], 6);
			denominator = denominator + Math.pow(candidateSolution[0] - matrix [1][i], 6);
			denominator = denominator + i + 1;
			//System.out.println("denominator =" + denominator + "result = " + result);
			result = result + 1 / denominator;
		}
		return 1 / (result + 0.002);
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

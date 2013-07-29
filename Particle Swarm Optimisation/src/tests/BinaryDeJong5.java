package tests;

import java.util.Vector;

import swarm.Function;

public class BinaryDeJong5 implements Function {

	private int variables;
	
	private DeJong5 deJong5 = new DeJong5();
	
	private BinaryConverter binaryConverter = new BinaryConverter();
	
	@Override
	public int getVariables() {
		return variables;
	}

	@Override
	public double CalculateFitness(double[] candidateSolution) {
		int[] binarySolution = new int[32];
		for(int i = 0; i < 32; i++){
			binarySolution[i] = (int)candidateSolution[i];
		}
		double realNumber1 = binaryConverter.convertIEEE754ToReal(binarySolution);
		for(int i = 0; i < 32; i++){
			binarySolution[i] = (int)candidateSolution[i + 32];
		}
		double realNumber2 = binaryConverter.convertIEEE754ToReal(binarySolution);
		double[] solution = {realNumber1, realNumber2};
		return deJong5.CalculateFitness(solution);
	}

	@Override
	public double CalculateFitness(Vector<Double> candidateSolution) {
		double[] result = new double[candidateSolution.size()];
		for(int i = 0; i < result.length; i++){
			result[i] = candidateSolution.get(i);
		}
		return CalculateFitness(result);
	}

}

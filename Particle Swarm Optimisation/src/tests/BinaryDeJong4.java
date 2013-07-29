package tests;

import java.util.Vector;

import swarm.Function;

public class BinaryDeJong4 implements Function {
	
	private int variables;
	
	private DeJong4 deJong4 = new DeJong4();
	
	private BinaryConverter binaryConverter = new BinaryConverter();
	
	@Override
	public int getVariables() {
		return variables;
	}

	@Override
	public double CalculateFitness(double[] candidateSolution) {
		int[] binarySolution = new int[candidateSolution.length];
		for(int i = 0; i < candidateSolution.length; i++){
			binarySolution[i] = (int)candidateSolution[i];
		}
		double realNumber = binaryConverter.convertIEEE754ToReal(binarySolution);
		double[] solution = {realNumber};
		return deJong4.CalculateFitness(solution);
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

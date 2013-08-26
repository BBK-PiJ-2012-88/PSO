package tests;

import java.util.Vector;

import swarm.BinaryConverter;
import swarm.BinaryConverterImpl;
import swarm.Function;

public class BinaryDeJong2 implements Function {
	
	private int variables;
	
	private DeJong2 deJong2 = new DeJong2();
	
	private BinaryConverter binaryConverter = new BinaryConverterImpl();
	
	public BinaryDeJong2(){
		variables = binaryConverter.getBinaryEncoding() * deJong2.getVariables();
	}
	
	@Override
	public int getVariables() {
		return variables;
	}

	@Override
	public double CalculateFitness(double[] candidateSolution) {
		int length = binaryConverter.getBinaryEncoding();
		double[] binarySolution = new double[length];
		for(int i = 0; i < length; i++){
			binarySolution[i] = candidateSolution[i];
		}
		double[] binarySolution2 = new double[length];
		for(int i = length; i < candidateSolution.length; i++){
			binarySolution2[i - length] = (int)candidateSolution[i];
		}
		double realNumber = binaryConverter.convertBinaryToReal(binarySolution);
		double realNumber2 =  binaryConverter.convertBinaryToReal(binarySolution2);
		double[] solution = {realNumber, realNumber2};
		return deJong2.CalculateFitness(solution);
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

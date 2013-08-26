package tests;

import java.util.Arrays;
import java.util.Vector;

import swarm.BinaryConverter;
import swarm.BinaryConverterImpl;
import swarm.Function;

public class BinaryDeJong5 implements Function {

	private int variables;
	
	private DeJong5 deJong5 = new DeJong5();
	
	private BinaryConverter binaryConverter = new BinaryConverterImpl();
	
	public BinaryDeJong5(){
		variables = deJong5.getVariables() * binaryConverter.getBinaryEncoding();
	}
	
	@Override
	public int getVariables() {
		return variables;
	}

	@Override
	public double CalculateFitness(double[] candidateSolution) {
		int length = binaryConverter.getBinaryEncoding();
		double[] solution = new double[variables / binaryConverter.getBinaryEncoding()];
		for(int i = 0, k = 0; i < candidateSolution.length; i = i + length, k++){
			double [] temp = Arrays.copyOfRange(candidateSolution, i, i + length);
			double realNumber = binaryConverter.convertBinaryToReal(temp);
			solution[k] = realNumber;
		}
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

package tests;

import java.util.Arrays;
import java.util.Vector;

import swarm.BinaryConverter;
import swarm.BinaryConverterImpl;
import swarm.Function;

public class BinaryDeJong3 implements Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5939624291481069005L;

	private int variables;
	
	private DeJong3 deJong3 =  new DeJong3();
	
	private BinaryConverter binaryConverter = new BinaryConverterImpl();
	
	public BinaryDeJong3(){
		variables = deJong3.getVariables() * binaryConverter.getBinaryEncoding();
	}
	
	@Override
	public int getVariables() {
		return variables;
	}
	
	@Override
	public double CalculateFitness(double[] candidateSolution) {
		int length = candidateSolution.length / 5;
		double[] solution = new double[variables / binaryConverter.getBinaryEncoding()];
		for(int i = 0, k = 0; i < candidateSolution.length; i = i + length, k++){
			double [] temp = Arrays.copyOfRange(candidateSolution, i, i + length);
			double realNumber = binaryConverter.convertBinaryToReal(temp);
			solution[k] = realNumber;
		}
		return deJong3.CalculateFitness(solution);
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

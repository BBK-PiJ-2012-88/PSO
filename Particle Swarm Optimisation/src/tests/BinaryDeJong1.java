package tests;

import java.util.Arrays;
import java.util.Vector;

import swarm.BinaryConverter;
import swarm.BinaryConverterImpl;
import swarm.Function;

public class BinaryDeJong1 implements Function {
	
	private int variables;
	
	private BinaryConverter binaryConverter = new BinaryConverterImpl();
	
	private DeJong1 deJong1;
	
	public BinaryDeJong1(int variables){
		deJong1 = new DeJong1(variables);
		this.variables = variables * binaryConverter.getBinaryEncoding();
	}
	
	public BinaryDeJong1(){
		deJong1 = new DeJong1();
		deJong1.setVariables(10);
		variables = deJong1.getVariables() * binaryConverter.getBinaryEncoding();
	}
	
	@Override
	public int getVariables() {
		return variables;
	}

	@Override
	public double CalculateFitness(double[] candidateSolution) {
		int length = candidateSolution.length / deJong1.getVariables();
		double[] solution = new double[variables / binaryConverter.getBinaryEncoding()];
		for(int i = 0, k = 0; i < candidateSolution.length; i = i + length, k++){
			double [] temp = Arrays.copyOfRange(candidateSolution, i, i + length);
			double realNumber = binaryConverter.convertBinaryToReal(temp);
			solution[k] = realNumber;
		}
		return deJong1.CalculateFitness(solution);
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

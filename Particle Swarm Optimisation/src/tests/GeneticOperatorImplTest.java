package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import swarm.FitnessCalculatorImpl;
import swarm.Function;
import swarm.GeneticOperator;
import swarm.GeneticOperatorImpl;
import swarm.ParticleFitness;

public class GeneticOperatorImplTest {
	
	private GeneticOperator classUnderTest = new GeneticOperatorImpl();
	
	private double[][] binaryString;
	
	private Function f = new BinaryDeJong1();
	
	private FitnessCalculatorImpl calc = new FitnessCalculatorImpl(f);
	
	@Before
	public void setUp(){
		Random rand = new Random();
		binaryString = new double[8][f.getVariables()];
		for(int i = 0; i < binaryString.length; i++){
			for(int k = 0; k < binaryString[i].length; k++){
				if(rand.nextDouble() < 0.5){
					binaryString[i][k] = 1;
				}else{
					binaryString[i][k] = 0; 
				}
			}
		}
		calc.setObjectiveFunction(f);
		calc.setMaximum(false);
		calc.setPositions(binaryString);
		calc.initialCalculateFitness();
		int globalBest = calc.calculateGlobalBest();
		classUnderTest.setObjectiveFunction(f);
		classUnderTest.setPositions(binaryString);
		((GeneticOperatorImpl) classUnderTest).setGlobalBest(globalBest);
	}
	
	private double[][] copyArray(double[][] array){
		int rows = array.length;
		double[][] result = new double[rows][array[0].length];
		for(int i = 0; i < rows; i++){
			for(int k = 0; k < array[i].length; k++){
				result[i][k] = array[i][k];
			}
		}
		return result;
	}
	
	@Test
	public void testSortedFitness() {
		((GeneticOperatorImpl) classUnderTest).initiateSortedFitness();
		List<ParticleFitness> sortedFitness = ((GeneticOperatorImpl) classUnderTest).getSortedFitness();
		ParticleFitness prev = sortedFitness.get(0);
		boolean result = true;
		for(int i = 1; i < sortedFitness.size(); i++){
			ParticleFitness current = sortedFitness.get(i);
			if(current.getFitness() < prev.getFitness()){
				result = false;
			}
			prev = current;
		}
		assertTrue(result);	
	}
	
	@Test
	public void testCrossOver(){
		double[][] previous = copyArray(binaryString);
		((GeneticOperatorImpl) classUnderTest).initiateSortedFitness();
		((GeneticOperatorImpl)classUnderTest).crossover();
		binaryString = classUnderTest.getPositions();
		int counter = 0;
		for(int i = 0; i < previous.length; i++){
			for(int k = 0; k < binaryString.length; k++){
				if(Arrays.equals(previous[i], binaryString[k])){
					counter++;
				}
			}
		}
		assertTrue(counter == 4);
	}
	
	@Test
	public void testMutate(){
		double[][] previous = copyArray(binaryString);
		((GeneticOperatorImpl) classUnderTest).initiateSortedFitness();
		((GeneticOperatorImpl) classUnderTest).mutate();
		assertTrue(!Arrays.deepEquals(previous, binaryString));
	}
}

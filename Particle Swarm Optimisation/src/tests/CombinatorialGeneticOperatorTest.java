package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import swarm.CombinatorialGeneticOperator;
import swarm.CombinatorialInitialiser;
import swarm.Function;
import swarm.GeneticOperator;

public class CombinatorialGeneticOperatorTest {

	private GeneticOperator classUnderTest = new CombinatorialGeneticOperator();
	
	private Function f = new CombinatorialFunction("/Users/williamhogarth/Downloads/a280.tsp");
	
	private CombinatorialInitialiser init = new CombinatorialInitialiser();
	
	private double[][] positions;
	
	@Before
	public void setUp(){
		init.initialiseMatrices(f, 10);
		positions = init.getPositions();
		classUnderTest.setObjectiveFunction(f);
	}
	
	@Test
	public void testMutation() {
		classUnderTest.setPositions(positions);
		double[][] previous = copyArray(positions);
		classUnderTest.performGeneticOperations();
		assertTrue(!Arrays.deepEquals(previous, positions));
	}
	
	@Test
	public void checkEachParticleContainsAllNodes(){
		List<Double> cities = new ArrayList<Double>();
		double sum = 0;
		for(int i = 1; i <= f.getVariables(); i++){
			cities.add((double) i);
			sum = sum + i;
		}
		classUnderTest.setPositions(positions);
		classUnderTest.performGeneticOperations();
		boolean result = true;
		for(int i = 0; i < positions.length; i++){
			for(Double d: cities){
				boolean contains = false;
				for(int k = 0; k < positions[i].length; k++){
					if(Math.abs(positions[i][k] - d) < 0.00001){
						contains = true;
					}
				}
				result = contains;
			}
		}
		for(int i = 0; i < positions.length; i++){
			double sumOfNodes = 0;
			for(int k = 0; k < positions[i].length; k++){
				sumOfNodes = sumOfNodes + positions[i][k];
			}
			if(Math.abs(sumOfNodes - sum) > 0.0001){
				result = false;
			}
		}
		assertTrue(result);
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
}

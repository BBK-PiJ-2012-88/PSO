package loadTesting;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import swarm.BinarySwarm;
import swarm.BinaryPositionUpdate;
import swarm.CuboidLattice;
import swarm.HaltingCriteria;
import swarm.IterationHalt;
import swarm.Neighbourhood;
import swarm.Function;
import tests.BinaryDeJong1;
import tests.BinaryDeJong2;
import tests.BinaryDeJong3;
import tests.BinaryDeJong4;
import tests.BinaryDeJong5;
import tests.IOManager;

public class BinaryLoadTests {

	private IOManager ioManager = new IOManager();
	
	private BinarySwarm binary = new BinarySwarm();
	
	private Neighbourhood neighbourhood = new CuboidLattice();
	
	private Function f;
	
	private HaltingCriteria halt = new IterationHalt(1000);
	
	private String location = "/Users/williamhogarth/Documents/ComSci/Project/Results/BinaryLoad64.txt";
	
	private File file;
	
	private List<Double> results = new ArrayList<Double>();
	
	public static void main(String[] args){
		BinaryLoadTests n = new BinaryLoadTests();
		n.run();
	}
	
	public void run(){
		file = ioManager.createNewFile(location);
		binary.setHaltingCriteria(halt);
		binary.setNeighbourhood(neighbourhood);
		binary.setNumberOfParticles(64);
		((BinaryPositionUpdate)binary.getPositionUpdate()).setConstraints(true);
		f = new BinaryDeJong1();
		testFunction(f, 5.12, -5.12);
		f = new BinaryDeJong2();
		testFunction(f, 2.048, -2.048);
		f = new BinaryDeJong3();
		testFunction(f, 5.12, -5.12);
		f = new BinaryDeJong4();
		testFunction(f, 5.12, -5.12);
		f = new BinaryDeJong5();
		testFunction(f, 65.536, -65.536);
		ioManager.writeTofile("Genetic", file);
		f = new BinaryDeJong1();
		genTestFunction(f, 5.12, -5.12);
		f = new BinaryDeJong2();
		genTestFunction(f, 2.048, -2.048);
		f = new BinaryDeJong3();
		genTestFunction(f, 5.12, -5.12);
		f = new BinaryDeJong4();
		genTestFunction(f, 1.28, -1.28);
		f = new BinaryDeJong5();
		genTestFunction(f, 65.536, -65.536);
	}
	
	private void genTestFunction(Function function, double upperLimit, double lowerLimit) {
		double[] max = new double[function.getVariables()];
		Arrays.fill(max, upperLimit);
		double[] min = new double[function.getVariables()];
		Arrays.fill(min, lowerLimit);
		/*
		((BinaryPositionUpdate)binary.getPositionUpdate()).getConstrainer().setMaximum(max);
		((BinaryPositionUpdate)binary.getPositionUpdate()).getConstrainer().setMinimum(min);
		*/
		double best = 0.0;
		double worst = 0.0;
		for(int i = 0; i < 100; i++){
			Vector<Double> solution = binary.constrainedGeneticOptmise(function, max, min);
			results.add(function.CalculateFitness(solution));
			if(i == 0){
				best = results.get(i);
				worst = results.get(i);
			}else if(best > results.get(i)){
				best = results.get(i);
			}else if(worst < results.get(i)){
				worst = results.get(i);
			}
		}
		ioManager.writeTofile(function.getClass().toString(), file);
		ioManager.writeTofile("mean: " + calculateAverage().toString(), file);
		ioManager.writeTofile("best :" + best, file);
		ioManager.writeTofile("worst :" + worst, file);
		ioManager.writeTofile("stdDev: " + calculateStandardDeviation(), file);
		results.clear();
	}

	private void testFunction(Function function, double upperLimit, double lowerLimit){
		double[] max = new double[function.getVariables()];
		Arrays.fill(max, upperLimit);
		double[] min = new double[function.getVariables()];
		Arrays.fill(min, lowerLimit);
		/*
		((BinaryPositionUpdate)binary.getPositionUpdate()).getConstrainer().setMaximum(max);
		((BinaryPositionUpdate)binary.getPositionUpdate()).getConstrainer().setMinimum(min);
		*/
		double best = 0.0;
		double worst = 0.0;
		for(int i = 0; i < 100; i++){
			Vector<Double> solution = binary.constrainedOptimise(function, max, min);
			results.add(function.CalculateFitness(solution));
			if(i == 0){
				best = results.get(i);
				worst = results.get(i);
			}else if(best > results.get(i)){
				best = results.get(i);
			}else if(worst < results.get(i)){
				worst = results.get(i);
			}
		}
		ioManager.writeTofile(function.getClass().toString(), file);
		ioManager.writeTofile("mean: " + calculateAverage().toString(), file);
		ioManager.writeTofile("best :" + best, file);
		ioManager.writeTofile("worst :" + worst, file);
		ioManager.writeTofile("stdDev: " + calculateStandardDeviation(), file);
		results.clear();
	}
	
	private double calculateStandardDeviation() {
		double sigma = 0.0;
		double mean = calculateAverage();
		for(int i = 0; i < results.size(); i++){
			sigma = sigma + Math.pow(results.get(i) - mean, 2);
		}
		sigma = Math.sqrt(sigma/results.size());
		return sigma;
	}

	private Double calculateAverage() {
		Double result = 0.0;
		for(int i = 0; i < results.size(); i++){
			result =  result + results.get(i);
		}
		return result / results.size();
	}
	
	
}

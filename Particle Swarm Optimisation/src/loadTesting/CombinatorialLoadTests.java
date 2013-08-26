package loadTesting;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import swarm.CombinatorialSwarm;
import swarm.CuboidLattice;
import swarm.HaltingCriteria;
import swarm.IterationHalt;
import swarm.Neighbourhood;
import swarm.Function;
import tests.CombinatorialFunction;

import tests.IOManager;

public class CombinatorialLoadTests {

	private IOManager ioManager = new IOManager();
	
	private CombinatorialSwarm comb = new CombinatorialSwarm();
	
	private Neighbourhood neighbourhood = new CuboidLattice();
	
	private Function f;
	
	private HaltingCriteria halt = new IterationHalt(200);
	
	private String location = "/Users/williamhogarth/Documents/ComSci/Project/Results/combinatorialLoad.txt";
	
	private File file;
	
	private List<Double> results = new ArrayList<Double>();
	
	public static void main(String[] args){
		CombinatorialLoadTests n = new CombinatorialLoadTests();
		n.run();
	}
	
	
	public void run(){
		file = ioManager.createNewFile(location);
		comb.setHaltingCriteria(halt);
		comb.setNeighbourhood(neighbourhood);
		comb.setNumberOfParticles(27);
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/a280.tsp");
		testFunction(f, "a280");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/ali535.tsp");
		testFunction(f, "ali535.tsp");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/att48.tsp");
		testFunction(f, "att48.tsp");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/eil101.tsp");
		testFunction(f, "eil101.tsp");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/kroA100.tsp");
		testFunction(f, "kroA100.tsp");
		ioManager.writeTofile("Genetic", file);
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/a280.tsp");
		testGeneticFunction(f, "a280");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/ali535.tsp");
		testGeneticFunction(f, "ali535.tsp");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/att48.tsp");
		testGeneticFunction(f, "att48.tsp");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/eil101.tsp");
		testGeneticFunction(f, "eil101.tsp");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/kroA100.tsp");
		testGeneticFunction(f, "kroA100.tsp");
	}
	
	private void testFunction(Function function, String str){
		double best = 0.0;
		double worst = 0.0;
		for(int i = 0; i < 100; i++){
			Vector<Double> solution = comb.optimise(function);
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
		ioManager.writeTofile(str, file);
		ioManager.writeTofile("mean: " + calculateAverage().toString(), file);
		ioManager.writeTofile("best :" + best, file);
		ioManager.writeTofile("worst :" + worst, file);
		ioManager.writeTofile("stdDev: " + calculateStandardDeviation(), file);
		results.clear();
	}
	
	private void testGeneticFunction(Function function, String str){
		double best = 0.0;
		double worst = 0.0;
		for(int i = 0; i < 100; i++){
			Vector<Double> solution = comb.geneticOptimise(function);
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
		ioManager.writeTofile(str, file);
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
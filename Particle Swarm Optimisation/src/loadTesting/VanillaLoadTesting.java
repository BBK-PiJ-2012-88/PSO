package loadTesting;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import swarm.CuboidLattice;
import swarm.HaltingCriteria;
import swarm.IterationHalt;
import swarm.Neighbourhood;
import swarm.TheFourClusters;
import swarm.TheRing;
import swarm.TheStar;
import swarm.TheWheel;
import swarm.VanillaPositionUpdate;
import swarm.VanillaSwarm;
import swarm.Function;
import tests.DeJong1;
import tests.DeJong2;
import tests.DeJong3;
import tests.DeJong4;
import tests.DeJong5;
import tests.IOManager;

public class VanillaLoadTesting {

	private IOManager ioManager = new IOManager();
	
	private VanillaSwarm vanilla = new VanillaSwarm();
	
	private Neighbourhood neighbourhood = new CuboidLattice();
	
	private Function f;
	
	private HaltingCriteria halt = new IterationHalt(200);
	
	private String location = "/Users/williamhogarth/Documents/ComSci/Project/Results/VanillaLoad.txt";
	
	private File file;
	
	private List<Double> results = new ArrayList<Double>();
	
	public static void main(String[] args){
		VanillaLoadTesting n = new VanillaLoadTesting();
		n.run();
	}
	
	public void run(){
		file = ioManager.createNewFile(location);
		vanilla.setHaltingCriteria(halt);
		vanilla.setNeighbourhood(neighbourhood);
		vanilla.setNumberOfParticles(27);
		VanillaPositionUpdate update = (VanillaPositionUpdate) vanilla.getPositionUpdate();
		update.setConstraints(true);
		f = new DeJong1(10);
		testFunction(5.12, -5.12, f);
		f = new DeJong2(10);
		testFunction(2.048, -2.048, f);
		f = new DeJong3();
		testFunction(5.12, -5.12, f);
		f = new DeJong4(10);
		testFunction(1.28, -1.28, f);
		f = new DeJong5();
		testFunction(65.536, -65.536, f);
	}
	
	private void testFunction(double upperLimit, double lowerLimit, Function function){
		vanilla.setLowerLimit(lowerLimit);
		vanilla.setUpperLimit(upperLimit);
		double[] max = new double[function.getVariables()];
		Arrays.fill(max, upperLimit);
		double min[] = new double[function.getVariables()];
		Arrays.fill(min, lowerLimit);
		//((VanillaPositionUpdate)vanilla.getPositionUpdate()).getConstrainer().setMaximum(max);
		//((VanillaPositionUpdate)vanilla.getPositionUpdate()).getConstrainer().setMinimum(min);
		double best = 0.0;
		double worst = 0.0;
		for(int i = 0; i < 100; i++){
			Vector<Double> solution = vanilla.constrainedOptimise(function, max, min);
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
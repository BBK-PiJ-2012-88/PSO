package loadTesting;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import swarm.ConstrictionCoefficient;
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
import swarm.VelocityClamping;
import swarm.VelocityUpdate;
import tests.DeJong1;
import tests.DeJong2;
import tests.DeJong3;
import tests.DeJong4;
import tests.DeJong5;
import tests.IOManager;

public class VelocityUpdateLoadTests {

	private IOManager ioManager = new IOManager();
	
	private VanillaSwarm vanilla = new VanillaSwarm();
	
	private HaltingCriteria halt = new IterationHalt(200);
	
	private String location = "/Users/williamhogarth/Documents/ComSci/Project/Results/velocityUpdate.txt";
	
	private File file;
	
	private List<VelocityUpdate> velocityUpdates = new ArrayList<VelocityUpdate>();
	
	private List<Double> results = new ArrayList<Double>();
	
	private double best;
	
	private double worst;
	
	private List<Function> functions = new ArrayList<Function>();
	
	public static void main(String[] args){
		VelocityUpdateLoadTests v = new VelocityUpdateLoadTests();
		v.run();
	}
	
	public void run(){
		generateVelocityUpdates();
		generateFunctions();
		vanilla.setHaltingCriteria(halt);
		((VanillaPositionUpdate)vanilla.getPositionUpdate()).setConstraints(true);
		file = ioManager.createNewFile(location);
		for(VelocityUpdate v: velocityUpdates){
			vanilla.setVelocityUpdate(v);
			best = 0.0;
			worst = 0.0;
			for(Function f: functions){
				double[] max;
				double[] min;
				if(f instanceof DeJong1 || f instanceof DeJong3){
					vanilla.setLowerLimit(-5.12);
					vanilla.setUpperLimit(5.12);
					max = new double[f.getVariables()];
					Arrays.fill(max, 5.12);
					min = new double[f.getVariables()];
					Arrays.fill(min, -5.12);
					//((VanillaPositionUpdate)vanilla.getPositionUpdate()).getConstrainer().setMaximum(max);
					//((VanillaPositionUpdate)vanilla.getPositionUpdate()).getConstrainer().setMinimum(min);
				}else if(f instanceof DeJong2){
					vanilla.setLowerLimit(-2.048);
					vanilla.setUpperLimit(2.048);
					max = new double[f.getVariables()];
					Arrays.fill(max, 2.048);
					min = new double[f.getVariables()];
					Arrays.fill(min, -2.048);
					//((VanillaPositionUpdate)vanilla.getPositionUpdate()).getConstrainer().setMaximum(max);
					//((VanillaPositionUpdate)vanilla.getPositionUpdate()).getConstrainer().setMinimum(min);
				}else if(f instanceof DeJong4){
					max = new double[f.getVariables()];
					Arrays.fill(max, 1.28);
					min = new double[f.getVariables()];
					Arrays.fill(min, -1.28);
					//((VanillaPositionUpdate)vanilla.getPositionUpdate()).getConstrainer().setMaximum(max);
					//((VanillaPositionUpdate)vanilla.getPositionUpdate()).getConstrainer().setMinimum(min);
					vanilla.setLowerLimit(-1.28);
					vanilla.setUpperLimit(1.28);
				}else{
					max = new double[f.getVariables()];
					Arrays.fill(max, 65.536);
					min = new double[f.getVariables()];
					Arrays.fill(min, -65.536);
					vanilla.setMax(max);
					vanilla.setMin(min);
					//((VanillaPositionUpdate)vanilla.getPositionUpdate()).getConstrainer().setMaximum(max);
					//((VanillaPositionUpdate)vanilla.getPositionUpdate()).getConstrainer().setMinimum(min);
					vanilla.setLowerLimit(-65.536);
					vanilla.setUpperLimit(65.536);
					double[] maxVel = {5,5};
					if(v instanceof VelocityClamping){
						((VelocityClamping) v).setMaxVelocity(maxVel);
					}
				}
				for(int i = 0; i < 100; i++){
					Vector<Double> solution = vanilla.constrainedOptimise(f,max,min);
					results.add(f.CalculateFitness(solution));
					if(i == 0){
						best = results.get(i);
						worst = results.get(i);
					}else if(best > results.get(i)){
						best = results.get(i);
					}else if(worst < results.get(i)){
						worst = results.get(i);
					}
				}
				ioManager.writeTofile(v.getClass().toString(), file);
				ioManager.writeTofile(f.getClass().toString(), file);
				ioManager.writeTofile("mean: " + calculateAverage().toString(), file);
				ioManager.writeTofile("best :" + best, file);
				ioManager.writeTofile("worst :" + worst, file);
				ioManager.writeTofile("stdDev: " + calculateStandardDeviation(), file);
				results.clear();
			}
		}
	}
	
	private void generateFunctions() {
		functions.add(new DeJong1(10));
		functions.add(new DeJong2());
		functions.add(new DeJong3());
		functions.add(new DeJong4());
		functions.add(new DeJong5());
		
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

	private void generateVelocityUpdates() {
		velocityUpdates.add(new ConstrictionCoefficient());
		double[] max = new double[]{2,2,2,2,2,2,2,2,2,2};
		velocityUpdates.add(new VelocityClamping(max));
	}

	private Double calculateAverage() {
		Double result = 0.0;
		for(int i = 0; i < results.size(); i++){
			result =  result + results.get(i);
		}
		return result / results.size();
	}
	
	
}

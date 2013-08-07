package tests;

import java.io.File;
import java.util.List;
import java.util.Vector;

import swarm.AcceptableError;
import swarm.AcceptableSolutionHalt;
import swarm.ConstrictionCoefficient;
import swarm.CuboidLattice;
import swarm.Function;
import swarm.GradientHalt;
import swarm.HaltingCriteria;
import swarm.IterationHalt;
import swarm.Neighbourhood;
import swarm.Swarm;
import swarm.TheFourClusters;
import swarm.TheRing;
import swarm.TheStar;
import swarm.VelocityClamping;
import swarm.VelocityUpdate;

public abstract class SwarmRunner {
	
	protected Swarm swarm;
	
	protected List <Neighbourhood> neighbourhood;
	
	protected List <VelocityUpdate> velocityUpdate;
	
	protected List <HaltingCriteria> haltingCriteria;
	
	protected IOManager manager = new IOManager();
	
	protected List<Function> function;
	
	protected int numberOfParticles = 27;
	
	public void generateHaltingCriteria(){
		haltingCriteria.add(new AcceptableError(0.5, 0.25));
		haltingCriteria.add(new AcceptableSolutionHalt(0.5));
		haltingCriteria.add(new GradientHalt(0.1));
		haltingCriteria.add(new IterationHalt(1000));
		haltingCriteria.add(new IterationHalt(5000));
		haltingCriteria.add(new IterationHalt(10000));
	}
	
	public void generateVeloctiyUpdate(){
		double[] maxVelocity = new double[32];
		for(int i = 0; i < maxVelocity.length; i++){
			maxVelocity[i] = 3;
		}
		velocityUpdate.add(new VelocityClamping(maxVelocity));
		velocityUpdate.add(new ConstrictionCoefficient());
	}
	
	public void generateNeighbourhoods(){
		neighbourhood.add(new TheRing());
		neighbourhood.add(new CuboidLattice());
		neighbourhood.add(new TheFourClusters());
		neighbourhood.add(new TheStar());
	}
	
	public abstract void generateFunction();
	
	public void testSwarm(File file){
		System.out.println("shit");
		swarm.setNumberOfParticles(numberOfParticles);
		System.out.println(haltingCriteria.size());
		int counter = 0;
		for(int i = 0; i < haltingCriteria.size(); i++){
			swarm.setHaltingCriteria(haltingCriteria.get(i));
			System.out.println("halt");
			counter++;
			for(int k = 0; k < velocityUpdate.size(); k++){
				setVelocityUpdate(velocityUpdate.get(k));
				System.out.println("vel");
				counter++;
				for(int n = 0; n < neighbourhood.size(); n++){
					System.out.println(n);
					swarm.setNeighbourhood(neighbourhood.get(n));
					for(int m = 0; m < function.size(); m++){
						Function f = function.get(m);
						Vector<Double> resultVector = swarm.optimise(f);
						System.out.println(resultVector.toString());
						double result = f.CalculateFitness(resultVector);
						System.out.println(result);
						System.out.println(swarm.toString());
						manager.writeTofile(swarm.toString(), file);
						System.out.println(resultVector.toString());
						manager.writeTofile("result Vector :" + resultVector.toString(), file);
						System.out.println(result);
						manager.writeTofile("result :" + result, file);
					}
				}
			}
		}
	}

	public abstract void setVelocityUpdate(VelocityUpdate velocity);
	/*
	public void createNewFile(){
		String str = swarm.getClass().toString();
		File file = manager.createNewFile(str);
	}*/

}

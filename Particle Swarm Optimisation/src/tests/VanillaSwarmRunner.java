package tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import swarm.Function;
import swarm.HaltingCriteria;
import swarm.Neighbourhood;
import swarm.VanillaSwarm;
import swarm.VelocityUpdate;

public class VanillaSwarmRunner extends SwarmRunner {
	
	
	public VanillaSwarmRunner(){
		swarm = new VanillaSwarm();
		neighbourhood = new ArrayList <Neighbourhood>();
		velocityUpdate = new ArrayList<VelocityUpdate>();
		haltingCriteria = new ArrayList<HaltingCriteria>();
		function = new ArrayList<Function>();
	}
	
	@Override
	public void generateFunction() {
		Function f = new DeJong1();
		function.add(f);
		f = new DeJong2();
		function.add(f);
		f = new DeJong3();
		function.add(f);
		f = new DeJong4();
		function.add(f);
		f = new DeJong5();
		function.add(f);
	}
	
	public static void main(String[] args){
		VanillaSwarmRunner vsr = new VanillaSwarmRunner();
		vsr.run();
	}
	
	public void run(){
		File file = new File("./VanillaSwarm.txt"); 
		try{
			file.createNewFile();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		this.generateHaltingCriteria();
		this.generateNeighbourhoods();
		this.generateVeloctiyUpdate();
		this.generateFunction();
		this.testSwarm(file);
	}

	@Override
	public void setVelocityUpdate(VelocityUpdate velocity) {
		((VanillaSwarm)swarm).setVelocityUpdate(velocity);
	}

}

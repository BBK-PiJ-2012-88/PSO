package aspects;

import swarm.*;


privileged public aspect PrintOut {
	
	pointcut getVanillaFitnessData(VanillaSwarm swarm):
		execution (void VanillaSwarm.updateFitnessInformation())
		&& target(swarm);
	
	pointcut getBinaryFitnessData(BinarySwarm swarm):
		execution (void BinarySwarm.updateFitnessInformation())
		&& target(swarm);
		
	pointcut getCombinatorialFitnessData(CombinatorialSwarm swarm):
		execution (void CombinatorialSwarm.updateFitnessInformation())
		&& target(swarm);
	
	pointcut getIteration(HaltingCriteria halt, double gbest, int iteration):
		execution (void HaltingCriteria.updateData(double, int))
		&& target (halt)
		&& args (iteration)
		&& args (gbest);
	
	public void HaltingCriteria.updateDate(double gbest, int i){
		System.out.println(i);
	}
	
	after(HaltingCriteria halt, int iteration, double gbest) returning: getIteration(halt, gbest, iteration){
		System.out.println("interation: " + iteration);
		System.out.println("gbest: " + gbest);
	}
	
	after(VanillaSwarm swarm) returning: getVanillaFitnessData(swarm){
		System.out.println("gbest index: " + swarm.getGlobalBest());
		System.out.println("gbest fitness: " + swarm.getFitness().get(swarm.getGlobalBest()));
	}
	
	after(BinarySwarm swarm) returning: getBinaryFitnessData(swarm){
		System.out.println("gbest index: " + swarm.getGlobalBest());
		System.out.println("gbest fitness: " + swarm.getFitness().get(swarm.getGlobalBest()));
	}
	
	after(CombinatorialSwarm swarm) returning: getCombinatorialFitnessData(swarm){
		System.out.println("gbest index: " + swarm.getGlobalBest());
		System.out.println("gbest fitness: " + swarm.getFitness().get(swarm.getGlobalBest()));
	}
}

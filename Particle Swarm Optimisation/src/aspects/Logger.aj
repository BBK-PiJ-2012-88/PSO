import swarm.FitnessCalculator;

public aspect Logger {
	
	private LogData logData = new LogData();

	pointcut updateGlobalBest(FitnessCalculator fit):
		execution (int FitnessCalculator.calculateGlobalBest())
		&& target(fit); 
	
	after(FitnessCalculator fit) returning (int gBest): updateGlobalBest(fit){
		logData.getSwarmBest().add(gBest);
		logData.getFitness().add(fit.getFitness().get(gBest));
	}
	
	public void clearLog(){
		logData = new LogData();
	}
	
	public LogData getLogData(){
		return logData;
	}	
}

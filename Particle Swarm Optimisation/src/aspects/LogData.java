import java.util.ArrayList;
import java.util.List;


public class LogData {
	
	private List<Double> fitness = new ArrayList<Double>();
	
	private List<Integer> swarmBest = new ArrayList<Integer>();

	public List<Double> getFitness() {
		return fitness;
	}

	public void setFitness(List<Double> fitness) {
		this.fitness = fitness;
	}

	public List<Integer> getSwarmBest() {
		return swarmBest;
	}

	public void setSwarmBest(List<Integer> swarmBest) {
		this.swarmBest = swarmBest;
	}
}
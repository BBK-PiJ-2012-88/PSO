import java.util.ArrayList;
import java.util.List;


public class DataAnalyser {
	
	private List<LogData> data = new ArrayList<LogData>();
	
	public void setData(List<LogData> data){
		this.data = data;
	}
	
	public List<LogData> getData(){
		return data;
	}
	
	public int calculateMostIndexChanges(){
		int changes = 0;
		for(LogData d: data){
			int temp = calculateGBestChanges(d.getSwarmBest());
			if(temp > changes){
				changes = temp;
			}
		}
		return changes;
	}
	
	public int calculateWorstIndexChanges(){
		int changes = calculateGBestChanges(data.get(0).getSwarmBest());
		for(LogData d: data){
			int temp = calculateGBestChanges(d.getSwarmBest());
			if(temp < changes){
				changes = temp;
			}
		}
		return changes;
	}
	
	public double calculateAvgIndexChanges(){
		List<Integer> changes = new ArrayList<Integer>();
		for(LogData d: data){
			changes.add(calculateGBestChanges(d.getSwarmBest()));
		}
		return calculateAverage(changes);
	}
	
	public double calculateStdDevIndexChanges(){
		List<Integer> changes = new ArrayList<Integer>();
		for(LogData d: data){
			changes.add(calculateGBestChanges(d.getSwarmBest()));
		}
		return calculateStdDeviation(changes);
	}
	
	public double calculateLeastImprovement(){
		double least = fitnessImprovement(data.get(0).getFitness());
		for(LogData d: data){
			double temp = fitnessImprovement(d.getFitness());
			if(temp < least){
				least = temp;
			}
		}
		return least;
	}
	
	public double calculateMostImprovement(){
		double most = fitnessImprovement(data.get(0).getFitness());
		for(LogData d: data){
			double temp = fitnessImprovement(d.getFitness());
			if(temp > most){
				most = temp;
			}
		}
		return most;
	}
	
	public double calculateAverageImprovement(){
		List<Double> improvement = new ArrayList<Double>();
		for(LogData d: data){
			improvement.add(fitnessImprovement(d.getFitness()));
		}
		return calculateAverage(improvement);
	}
	
	public double calculateImprovementStdDev(){
		List<Double> improvement = new ArrayList<Double>();
		for(LogData d: data){
			improvement.add(fitnessImprovement(d.getFitness()));
		}
		return calculateStdDeviation(improvement);
	}
	
	public double calculateAverage(List<? extends Number> values){
		Number total = 0;
		for(Number n: values){
			total = total.doubleValue() + n.doubleValue();
		}
		return total.doubleValue() / values.size();
	}
	
	public double calculateStdDeviation(List<? extends Number> values){
		double mean = calculateAverage(values);
		Number total = 0;
		for(Number n: values){
			total = total.doubleValue() + Math.pow(n.doubleValue() - mean, 2);
		}
		total = total.doubleValue() / values.size();
		return Math.sqrt(total.doubleValue());
	}
	
	private int calculateGBestChanges(List<Integer> gBests){
		int change = 0;
		int current = gBests.get(0);
		for(int i = 1; i < gBests.size(); i++){
			if(current != gBests.get(i)){
				current = gBests.get(i);
				change++;
			}
		}
		return change;
	}
	
	private double fitnessImprovement(List<Double> fitness){
		return Math.abs(fitness.get(0) - fitness.get(fitness.size() - 1));
	}
}
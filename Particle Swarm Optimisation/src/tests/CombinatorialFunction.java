package tests;

import java.util.List;
import java.util.Vector;

import swarm.Function;

public class CombinatorialFunction implements Function {

	private List<City> cities;
	
	private TravSalesParser tspParser;
	
	private int variables;
	
	private double epsilon = 0.00001;
	
	public CombinatorialFunction(List<City> cities){
		this.cities = cities;
		variables = cities.size();
	}
	
	public CombinatorialFunction(String xmlFile) {
			tspParser = new TravSalesParser(xmlFile);
			cities = tspParser.extractNodes();
			variables = cities.size();
	}
	
	@Override
	public int getVariables() {
		return variables;
	}

	@Override
	public double CalculateFitness(double[] candidateSolution) {
		double fitness = 0;
		for(int i = 0, k = 1; k < candidateSolution.length; i++, k++){
			City city1 = getCity(candidateSolution[i]);
			City city2 = getCity(candidateSolution[k]);
			double distance = 0;
			for(int n = 0; n < city1.getCoordinates().size(); n++){
				distance = distance + Math.pow(city1.getCoordinates().get(n) - city2.getCoordinates().get(n), 2);
			}
			fitness = fitness + Math.sqrt(distance);
		}
		return fitness;
	}

	private City getCity(double index) {
		for(int i = 0; i < cities.size(); i++){
			if(Math.abs(cities.get(i).getIndex() - index) <= epsilon){
				return cities.get(i);
			}
		}
		return null;
	}

	@Override
	public double CalculateFitness(Vector<Double> candidateSolution) {
		double[]array = new double[candidateSolution.size()];
		for(int i = 0; i < candidateSolution.size(); i++){
			array[i] = candidateSolution.get(i);
		}
		return CalculateFitness(array);
	}

}

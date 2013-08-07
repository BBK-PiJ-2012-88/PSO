package tests;

import java.util.ArrayList;
import java.util.List;

public class City {
	
	private List<Double> coordinates;
	private double index;
	
	public City(){
		this.coordinates = new ArrayList<Double>();
	}
	
	public List<Double> getCoordinates(){
		return coordinates;
	}
	
	public void setCoordinates(List<Double> coordinates){
		this.coordinates = coordinates;
	}
	
	public double getIndex() {
		return index;
	}
	public void setIndex(double index) {
		this.index = index;
	}
	
	
	
}

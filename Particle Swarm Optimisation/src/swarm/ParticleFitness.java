package swarm;

public class ParticleFitness implements Comparable<Object> {
	
	private double fitness;
	private int index;
	
	public ParticleFitness(int index, double fitness) {
		this.index = index;
		this.fitness = fitness;
	}
	
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public int compareTo(Object o) {
		Double particle = fitness;
		Double other = (((ParticleFitness)o).getFitness());
		int result = particle.compareTo(other);
		if(result != 0){
			return result;
		}else{
			Integer particleIndex = index;
			Integer otherIndex = ((ParticleFitness)o).getIndex();
			return particleIndex.compareTo(otherIndex);
		}
	}
	
}

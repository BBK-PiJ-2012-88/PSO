package swarm;

import java.util.HashMap;
import java.util.Vector;

public class CuboidLattice implements Neighbourhood {

	private HashMap<Integer, Double> solutionFitness;
	private boolean maximum = false;
	
	@Override
	public void setSolutionFitness(HashMap<Integer, Double> solutionFitness) {
		assert isCuboid(solutionFitness);
		this.solutionFitness = solutionFitness;

	}

	public boolean isCuboid(HashMap<Integer, Double> solutionFitness) {
		for(int i = 1; i < 11; i++){
			if(i * i * i == solutionFitness.size()){
				return true;
			}
		}
		return false;
	}

	@Override
	public int neighbourhoodBest(int particle) {
		int cubeRoot = getCubeRoot();
		if(cubeRoot == 1){
			return particle;
		}else if(particle < cubeRoot * cubeRoot){
			Vector<Integer> temp = particlesOnPlane(particle, cubeRoot);
			temp.add(particle);
			temp.add(particle + cubeRoot * cubeRoot);
			return getfittestParticle(temp);
		}else if(particle >= solutionFitness.size() - cubeRoot * cubeRoot){
			Vector<Integer> temp = particlesOnPlane(particle, cubeRoot);
			temp.add(particle);
			temp.add(particle - cubeRoot * cubeRoot);
			return getfittestParticle(temp);
		}else{
			Vector<Integer> temp = particlesOnPlane(particle, cubeRoot);
			temp.add(particle);
			temp.add(particle - cubeRoot * cubeRoot);
			temp.add(particle + cubeRoot * cubeRoot);
			return getfittestParticle(temp);
		}
	}


	private Vector<Integer> particlesOnPlane(int particle, int cubeRoot) {
		Vector<Integer> result = new Vector<Integer>();
		if(bottomLeftCorner(particle, cubeRoot)){
			result.add(particle + 1);
			result.add(particle + cubeRoot);
		}else if(bottomRightCorner(particle, cubeRoot)){
			result.add(particle - 1);
			result.add(particle + cubeRoot);
		}else if(topLeftCorner(particle, cubeRoot)){
			result.add(particle + 1);
			result.add(particle - cubeRoot);
		}else if(topRightCorner(particle, cubeRoot)){
			result.add(particle - 1);
			result.add(particle - cubeRoot);
		}else if(bottomEdge(particle, cubeRoot)){
			result.add(particle + 1);
			result.add(particle - 1);
			result.add(particle + cubeRoot);
		}else if(leftEdge(particle, cubeRoot)){
			result.add(particle + 1);
			result.add(particle + cubeRoot);
			result.add(particle - cubeRoot);
		}else if(rightEdge(particle, cubeRoot)){
			result.add(particle - 1);
			result.add(particle + cubeRoot);
			result.add(particle - cubeRoot);
		}else if(topEdge(particle, cubeRoot)){
			result.add(particle - 1);
			result.add(particle + 1);
			result.add(particle - cubeRoot);
		}else{
			result.add(particle - 1);
			result.add(particle + 1);
			result.add(particle + cubeRoot);
			result.add(particle - cubeRoot);
		}
		return result;
	}

	private boolean topEdge(int particle, int cubeRoot) {
		for(int i = 1; i < cubeRoot + 1; i++){
			if(particle < i * cubeRoot * cubeRoot - 1 && particle > i * cubeRoot * cubeRoot - cubeRoot){
				return true;
			}
		}
		return false;
	}

	private boolean rightEdge(int particle, int cubeRoot) {
		for(int i = 0; i < cubeRoot; i++){
			for(int k = 1; k < cubeRoot; k++){
				if(particle == i * cubeRoot * cubeRoot + cubeRoot - 1 + k * cubeRoot){
					return true;
				}
			}
		}
		return false;
	}

	private boolean leftEdge(int particle, int cubeRoot) {
		for(int i = 0; i < cubeRoot; i++){
			for(int k = 1; k < cubeRoot; k++){
				if(particle == i * cubeRoot *cubeRoot + k * cubeRoot){
					return true;
				}
			}
		}
		return false;
	}

	private boolean bottomEdge(int particle, int cubeRoot) {
		for(int i = 0; i < cubeRoot; i++){
			if(particle > i * cubeRoot * cubeRoot && particle < i * cubeRoot * cubeRoot + cubeRoot - 1){
				return true;
			}
		}
		return false;
	}

	private boolean topRightCorner(int particle, int cubeRoot) {
		for(int i = 1; i <= cubeRoot; i++){
			if(particle == i * cubeRoot * cubeRoot - 1){
				return true;
			}
		}
		return false;
	}

	private boolean topLeftCorner(int particle, int cubeRoot) {
		for(int i = 1; i < cubeRoot + 1; i++){
			if(particle == i * cubeRoot * cubeRoot - cubeRoot){
				return true;
			}
		}
		return false;
	}

	private boolean bottomRightCorner(int particle, int cubeRoot) {
		for(int i = 0; i < cubeRoot; i++){
			if(particle == cubeRoot * cubeRoot * i - 1 + cubeRoot){
				return true;
			}
		}
		return false;
	}

	private boolean bottomLeftCorner(int particle, int cubeRoot) {
		for(int i = 0; i < cubeRoot; i++){
			if(particle == i * cubeRoot * cubeRoot){
				return true;
			}
		}
		return false;
	}

	private int getfittestParticle(Vector<Integer> particles) {
		if(maximum){
			double fitness = solutionFitness.get(particles.get(0));
			int result = particles.get(0);
			for(int i = 1; i < particles.size(); i++){
				double temp = solutionFitness.get(particles.get(i));
				if(temp > fitness){
					fitness = temp;
					result = particles.get(i);
				}
			}
			return result;
		}else{
			double fitness = solutionFitness.get(particles.get(0));
			int result = particles.get(0);
			for(int i = 1; i < particles.size(); i++){
				double temp = solutionFitness.get(particles.get(i));
				if(temp < fitness){
					fitness = temp;
					result = particles.get(i);
				}
			}
			return result;
		}
	}

	public boolean getMaximum() {
		return maximum;
	}

	public void setMaximum(boolean maximum) {
		this.maximum = maximum;
	}

	private int getCubeRoot() {
		for(int i = 1; i < 11; i ++){
			if(i * i * i == solutionFitness.size()){
				return i;
			}
		}
		return 0;
	}
	
	@Override
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append(this.getClass().toString() + ", ");
		buff.append("maximum: " + maximum);
		return buff.toString();
	}
}

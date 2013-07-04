package swarm;

public class SigmoidFunction {
	
	public double Normalise(double value){
		return 1 / 1 + Math.pow(Math.E, - value);
	}
}

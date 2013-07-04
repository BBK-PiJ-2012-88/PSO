package swarm;

import java.util.HashMap;

public class ConstrictionCoefficient implements VelocityUpdate {

		private double [][] velocities;
		
		private double [][] personalBest;
		
		private double [][] position; 
		
		private Neighbourhood neighbourhood = new TheRing();
		
		private HashMap<Integer, Double> fitness;
		
		private double socialConstant = 2;
		
		private double cognitiveConstant = 2;
		
		private double K = 0.5;
		
		public ConstrictionCoefficient(){}
		
		public ConstrictionCoefficient(Neighbourhood neighbourhood){
			this.neighbourhood = neighbourhood;
		}
		
		public ConstrictionCoefficient(Neighbourhood neighbourhood, double socialConstant,
				double cognitiveConstant, double K){
			this(neighbourhood);
			setConstants(cognitiveConstant, socialConstant, K);
		}
		
		
		/* (non-Javadoc)
		 * @see swarm.VelocityUpdater#updateVelocities()
		 */
		@Override
		public double[][] updateVelocities(){
			assert(neighbourhood != null && velocities != null && position != null
					&& fitness != null);
			double constrictionCoefficient = calculateConstrictionCoefficient();
			calculateVelocities();
			for(int i = 0; i < velocities.length; i++){
				for(int k = 0; k < velocities[i].length; k++){
					velocities[i][k] = velocities[i][k] * constrictionCoefficient;
				}
			}
			return getVelocities();
		}
		
		private double calculateConstrictionCoefficient() {
			assert(socialConstant + cognitiveConstant >= 4 && K >= 0 && K <= 1);
			double numerator = 2 * K;
			double temp = socialConstant + cognitiveConstant;
			double denominator = 2 - temp - Math.sqrt(temp * (temp - 4));
			if(denominator < 0){
				denominator = denominator * - 1;
			}
			return numerator / denominator;
		}

		/* (non-Javadoc)
		 * @see swarm.VelocityUpdater#updateData(double[][], double[][], java.util.HashMap)
		 */
		@Override
		public void updateData(double[][] personalBest, double[][] position, HashMap<Integer, Double> fitness){
			setPersonalBest(personalBest);
			setPosition(position);
			setFitness(fitness);
		}
		
		private void calculateVelocities(){
			neighbourhood.setSolutionFitness(fitness);
			for(int i = 0; i < position.length; i++){
				double[]cognitive = calculateCognitiveVelocity(i);
				double[]social = calculateSocialVelocity(i);
				for(int k = 0; k < position[i].length; k++){
					velocities[i][k] = velocities[i][k] + cognitive[k] + social[k];
				}
			}
		}
		
		private double[] calculateCognitiveVelocity(int particle){
			double[] cognitiveVelocity = new double[position[particle].length];
			for(int k = 0; k < cognitiveVelocity.length; k++){
				cognitiveVelocity[k] = cognitiveConstant * (personalBest[particle][k] - position[particle][k]);
			}
			return cognitiveVelocity;
		}
		
		private double[] calculateSocialVelocity(int particle){
			double[] socialVelocity = new double[position[particle].length];
			int nBest = neighbourhood.neighbourhoodBest(particle);
			for(int k = 0; k < socialVelocity.length; k++){
				socialVelocity[k] = socialConstant * (personalBest[nBest][k] - position[particle][k]);
			}
			return socialVelocity;
		}

		public double[][] getVelocities() {
			return velocities;
		}

		public void setVelocities(double[][] velocities) {
			this.velocities = velocities;
		}

		public double[][] getPersonalBest() {
			return personalBest;
		}

		public void setPersonalBest(double[][] personalBest) {
			this.personalBest = personalBest;
		}

		public double[][] getPosition() {
			return position;
		}

		public void setPosition(double[][] position) {
			this.position = position;
		}

		public Neighbourhood getNeighbourhood() {
			return neighbourhood;
		}

		public void setNeighbourhood(Neighbourhood neighbourhood) {
			this.neighbourhood = neighbourhood;
		}

		public HashMap<Integer, Double> getFitness() {
			return fitness;
		}

		public void setFitness(HashMap<Integer, Double> fitness) {
			this.fitness = fitness;
		}

		public double getSocialConstant() {
			return socialConstant;
		}

		public double getCognitiveConstant() {
			return cognitiveConstant;
		}

		public double getK(){
			return K;
		}
		
		public void setConstants(double cognitiveConstant, double socialConstant, double K){
			assert(socialConstant + cognitiveConstant >= 4 && K >= 0 && K <= 1);
			this.socialConstant = socialConstant;
			this.K = K;
			this.cognitiveConstant = cognitiveConstant;
		}

		public void setK(double K) {
			assert K >= 0 && K <= 1;
			this.K = K;       
		}
}
package swarm;

public class ConstrictionCoefficient implements VelocityUpdate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4171071871217312256L;

		private double [][] velocities;
		
		private double [][] personalBest;
		
		private double [][] position; 
		
		private Neighbourhood neighbourhood = new TheRing();
		
		private boolean maximum = false;
		
		public void setSocialConstant(double socialConstant) {
			this.socialConstant = socialConstant;
		}

		public void setCognitiveConstant(double cognitiveConstant) {
			this.cognitiveConstant = cognitiveConstant;
		}

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
			assert(neighbourhood != null && velocities != null && position != null);
			getNeighbourhood().setMaximum(maximum);
			double constrictionCoefficient = calculateConstrictionCoefficient();
			calculateVelocities();
			for(int i = 0; i < velocities.length; i++){
				for(int k = 0; k < velocities[i].length; k++){
					velocities[i][k] = velocities[i][k] * constrictionCoefficient;
				}
			}
			return getVelocities();
		}
		
		@Override
		public boolean isMaximum() {
			return maximum;
		}

		@Override
		public void setMaximum(boolean maximum) {
			this.maximum = maximum;
		}

		private double calculateConstrictionCoefficient() {
			assert(socialConstant + cognitiveConstant >= 4 && K >= 0 && K <= 1);
			double numerator = 2 * K;
			double temp = socialConstant + cognitiveConstant;
			double denominator = 2 - temp - Math.sqrt(temp * (temp - 4));
			return numerator / Math.abs(denominator);
		}
		
		private void calculateVelocities(){
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

		@Override
		public double[][] getVelocities() {
			return velocities;
		}

		@Override
		public void setVelocities(double[][] velocities) {
			this.velocities = velocities;
		}

		@Override
		public double[][] getPersonalBest() {
			return personalBest;
		}

		@Override
		public void setPersonalBest(double[][] personalBest) {
			this.personalBest = personalBest;
		}

		@Override
		public double[][] getPosition() {
			return position;
		}

		@Override
		public void setPosition(double[][] position) {
			this.position = position;
		}

		@Override
		public Neighbourhood getNeighbourhood() {
			return neighbourhood;
		}

		@Override
		public void setNeighbourhood(Neighbourhood neighbourhood) {
			this.neighbourhood = neighbourhood;
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
		
		@Override
		public String toString(){
			StringBuffer buff = new StringBuffer();
			buff.append(this.getClass().toString() + ", ");
			buff.append("neighbourhood: " + this.neighbourhood.getClass().toString() + ", ");
			buff.append("social constant: " + socialConstant + ", ");
			buff.append("cognitive constant: " + cognitiveConstant + ", ");
			buff.append("K: " + K);
			return buff.toString();
		}
}
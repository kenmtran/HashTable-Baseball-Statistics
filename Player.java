
public class Player {
	// various data variables representing the player's name and statisics
		private String name;
		private String team;
		private double hit;
		private double out;
		private double strikeOut;
		private double walk;
		private double hitByPitch;
		private double sacrifice;
		private double atBat;
		private double error; // counts as "at plate appearance"
		// default constructor that initializes all variables to 0
		public Player() {
			this.name = "";
			this.hit = 0;
			this.out = 0;
			this.walk = 0;
			this.strikeOut = 0;
			this.hitByPitch = 0;
			this.sacrifice = 0;
		}
		// constructor that sets all values within the player object to that respective value
		public Player(String name, double hit, double out, double strikeOut, double walk, double hitByPitch, double sacrifice) {
			this.name = name;
			this.hit = hit;
			this.out = out;
			this.walk = walk;
			this.strikeOut = strikeOut;
			this.hitByPitch = hitByPitch;
			this.sacrifice = sacrifice;
		}
		// mutators
		public void setName(String name) {
			this.name = name;
		}
		public void setTeam(String team) {
			this.team = team;
		}
		public void setHits(double hit) {
			this.hit = hit;
		}
		public void setOuts(double out) {
			this.out = out;
		}
		public void setStrikeOuts(double strikeOut) {
			this.strikeOut = strikeOut;
		}
		public void setHitByPitch(double hbp) {
			this.hitByPitch = hbp;
		}
		public void setSacrifices(double sacrifice) {
			this.sacrifice = sacrifice;
		}
		public void setWalks(double walk) {
			this.walk = walk;
		}
		public void setError(double error) {
			this.error = error;
		}
		// accessors
		public String getName() {
			return name;
		}
		public String getTeam() {
			return team;
		}
		public double getHits() {
			return hit;
		}
		public double getOuts() {
			return out;
		}
		public double getStrikeOuts() {
			return strikeOut;
		}
		public double getHitByPitch() {
			return hitByPitch;
		}
		public double getSacrifices() {
			return sacrifice;
		}
		public double getWalks() {
			return walk;
		}
		public double getErrors() {
			return error;
		}
		public double getAtBat() {
			return hit + strikeOut + out;
		}
		
		public void incrementOuts() {
			out++;
		}
		public void incrementHits() {
			hit++;
		}
		public void incrementstrikeOut() {
			strikeOut++;
		}
		public void incrementWalks() {
			walk++;
		}
		public void incrementHitByPitch() {
			hitByPitch++;
		}
		public void incrementSacrifice() {
			sacrifice++;
		}
		public void incrementErrors() {
			error++;
		}
		// calculates bat avg using the values within the player object
		public double calculateBatAvg() 
		{
			// H + K + O = atBat
			double atBat = hit + strikeOut + out;
			// H / atBat = batting average
			double batAvg = hit / atBat;
			// makes the batting average 0 if the batter has no hits, no strikeouts, and no outs
			if (atBat == 0)
			{
				batAvg = 0;
			}
			
			return batAvg;
		}
		// calculates on base percentage using the values within the player object
		public double calculateOnBase()
		{
			// H + K + O = atBat
			double atBat = hit + strikeOut + out;
			// atBat + W + P + S = atPlate
			double atPlate = atBat + walk + hitByPitch + sacrifice + error;
			// (H + W + P) / atPlate = on base percentage
			double onBase = (hit + walk + hitByPitch) / atPlate;
			if (atPlate == 0) {
				onBase = 0;
			}
			
			return onBase;
		}
		// adds the stats from one player object to the current player object
		public void addDupeStats(Player p) {
			this.hit += p.hit;
			this.out += p.out;
			this.walk += p.walk;
			this.strikeOut += p.strikeOut;
			this.hitByPitch += p.hitByPitch;
			this.sacrifice += p.sacrifice;
		}
		public int compareTo2(double strikeOut) {
			if (this.strikeOut < strikeOut) {
				return -1;
			}
			else if (this.strikeOut > strikeOut) {
				return 1;
			}
			else {
				return 0;
			}
		}
		
		@Override
		// toString that prints out the player's name along with all the other stats and calculated stats in a formatted output
		public String toString() {
			return this.getName() + "\t" + String.format("%.0f\t%.0f\t%.0f\t%.0f\t%.0f\t%.0f\t%.3f\t%.3f", this.getAtBat(), this.getHits(), this.getWalks(), 
					this.getStrikeOuts(), this.getHitByPitch(), this.getSacrifices(), this.calculateBatAvg(), this.calculateOnBase());
		}
}

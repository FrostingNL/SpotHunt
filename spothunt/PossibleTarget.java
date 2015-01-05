package spothunt;

public class PossibleTarget {
	GoalSpot spot;
	int TPD;
	int SD;
	int arrayIndex;
	double penalty;
	int calcCost;
	int surThreat;
	int highestDanger;
	public double rating;
	
	public PossibleTarget(GoalSpot spot) {
		this.spot = spot;
	}
	
	public int getTPD() {
		return TPD;
	}
	public void setTPD(int tPD) {
		TPD = tPD;
	}
	public int getSD() {
		return SD;
	}
	public void setSD(int sD) {
		SD = sD;
	}
	public void setHighestDanger(int HD) {
		highestDanger = HD;
	}
	public double getPenalty() {
		return penalty;
	}
	public int getHighestDanger() {
		return highestDanger;
	}
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}
	public int getCalcCost() {
		return calcCost;
	}
	public void setCalcCost(int calcCost) {
		this.calcCost = calcCost;
	}
	public int getSurThreat() {
		return surThreat;
	}
	public void setSurThreat(int surThreat) {
		this.surThreat = surThreat;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public double getRating() {
		return this.rating;
	}
	
	public GoalSpot toGoalSpot() {
		return this.spot;
	}
	
	public String toString() {
		return "[" + spot.getX() + ", " + spot.getY() + "]";
	}
}

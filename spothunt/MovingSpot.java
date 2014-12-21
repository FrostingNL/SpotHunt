package spothunt;

import java.util.ArrayList;
import java.util.List;

/**
 * This class takes care of the <code>MovingSpot</code>.<br>
 * It controls a Player (e.g. <code>setLocation</code>, <code>getX</code>, <code>toString</code>) and 
 * it holds the value for <code>x</code> and <code>y</code>.<br>
 * <b>It implements Spot.</b>
 * @author Jeroen
 * @version 1.0
 *
 */
public class MovingSpot implements Spot {
	int x = 0;
	int y = 0;
	Playfield playfield;
	public static Factor[] factors = new Factor[]{Factor.FDC, Factor.SD, Factor.HD, Factor.TPD, Factor.ST};
	
	/**
	 * The constructor of <code>MovingSpot</code>. This will link the spot to a <code>Playfield</code> and set it to its start location <code>[0,0]</code>.
	 * @param playfield	the Playfield it is linked to.
	 */
	public MovingSpot(Playfield playfield) {
		assert playfield != null : "Playfield not found!";
		this.playfield = playfield;
			// Set MovingSpot at its start position at [0,0]
		playfield.cells[0][0].putSpot();
	}
	
	
	public void setLocation(int newX, int newY) {
		assert newY >= 0 && newX >= 0 : "NewX and newY have to be larger than 0! (NewX,NewY = " + newX + "," + newY + ")";
		assert newY < playfield.width && newX < playfield.height : "NewX and newY are not within the height and width of the field! (NewX,NewY = " + newX + "," + newY + ")";
		
			// Update cells
		playfield.cells[x][y].removeSpot();
		x = newX;
		y = newY;
		playfield.cells[x][y].putSpot();
	}
	
	/**
	 * <code>pickTarget</code> picks the best <code>GoalSpot</code> based on:<br>
	 * <ul>
	 * 		<li> TPD (Total Player Distance): How far are all players away from the <code>GoalSpot</code>?</li>
	 * 		<li> SD (Spot Distance): How far away is the MovingSpot away from the <code>GoalSpot</code>?</li>
	 * 		<li> FDC (Fastest Danger Cost): How dangerous is the fastest path to the <code>GoalSpot</code>?</li>
	 * 		<li> ST (Surround Threat): How dangerous is the area around the <code>GoalSpot</code>?</li>
	 * 		<li> HD (Highest Danger): What is the highest danger level while moving over the FDC-path to the <code>GoalSpot</code>?</li>
	 * </ul>
	 * @param allGoals	the array of all the GoalSpots on the Playfield
	 * @return target	the GoalSpot that is the best choice to move to
	 */
	public GoalSpot pickTarget(GoalSpot[] allGoals) {
			// Create the empty array that will contain all the PossibleTargets.
		PossibleTarget[] possibleTargets = new PossibleTarget[allGoals.length];
		
			// Create all the PossibleTargets (out of GoalSpots) and set the correct values for HD, TPD, SD, FDC and ST.
		for(int i = 0; i < allGoals.length; i ++) {
			System.out.println("\nGOAL " + i);
			GoalSpot current = allGoals[i];
			possibleTargets[i] = new PossibleTarget(current);
			PossibleTarget possible = possibleTargets[i];
			int highestDanger = 0;
			int calculatedCost = 0;
			double penalty = 1;
			
				// Calculate the Fastest Danger Cost to this spot
			calculatedCost = current.calculateDangerCost(x, y, possible);
			
			//TODO: Move to the calculateSurround() in GoalSpot
				// Check if the GoalSpot is located against the wall and add a penalty
			if(current.getX()==0 || current.getX()==playfield.width-1 
					|| current.getY()==playfield.height || current.getY()==0) {
				penalty = 1.6;
			}
			
				// Set all the values for the variables in PossibleTarget
			possible.setTPD(current.getTPD());
			possible.setSD(current.getSD());
			possible.setPenalty(penalty);
			possible.setCalcCost(calculatedCost);
			int weightedSurThreat = (int) (current.calculateSurround() * penalty);
			possible.setSurThreat(weightedSurThreat);
			System.out.println("HD: " + possible.getHighestDanger());
			System.out.println("TPD: " + current.getTPD());
			System.out.println("SD: " + current.getSD());
			System.out.println("ST: " + weightedSurThreat);
			System.out.println("FDC: " + calculatedCost);
		}
		
			// Loop through all factors and rate them for each of the PossibleTargets
		for(Factor factor : factors) {
			possibleTargets = rateFactor(possibleTargets, factor);
		}
		
		
			// Create a new list BestOptions to compare the ratings
		List<PossibleTarget> bestOptions = new ArrayList<PossibleTarget>();
			// Compare ratings between all the PossibleTargets
		bestOptions = compareRatings(possibleTargets);
		
			// Check if bestOptions.size() is 1, as that means there is only one best Spot -> return this spot
		if(bestOptions.size()==1) {
			System.out.println("Found directly!");
			return bestOptions.get(0).toGoalSpot();
		} // Else: Continue with all the best options left
		
			// Make new list to be rated on independent factors (bestOptions = backup)
		//List<PossibleTarget> compareOptions = bestOptions;
			// Loop through all the factors and compare them for all the remaining best options
		System.out.println("Compare again: ");
		for(Factor current : factors) {
			System.out.println(bestOptions.get(0).toString());
			System.out.println(bestOptions.get(1).toString());
			List <PossibleTarget> compareOptions =  compareFactor(current, bestOptions);
				// If the list has a size of 1 then, a result has been found and return this GoalSpot
			if(compareOptions.size()==1) {
				return compareOptions.get(0).toGoalSpot();
				// Else reset compareOptions with bestOptions and then try again for the next factor
			/*} else {
				compareOptions = bestOptions; */
			}
		}
		System.out.println("Random: ");
			//If all else fails, pick a random target from the original PossibleTargets
		return pickRandomGoal(bestOptions);
	}
	
	/**
	 * <code>rateFactor</code> will rate the given <code>factor</code> and will give the correct rating for each of the <code>PossibleTargets</code>.
	 * @param possibleTargets	an array of all the PossibleTargets on the Playfield
	 * @param factor			the name of the factor that will be rated
	 * @return possibleTargets	the updated array of all PossibleTargets with updated ratings
	 */
	private PossibleTarget[] rateFactor(PossibleTarget[] possibleTargets, Factor factor) {
			// Set the first possibleTarget as the current best target
		PossibleTarget best = possibleTargets[0];
			// Create a new Boolean[] that will contain the results of comparePossibleTargets
		Boolean[] compares = new Boolean[2];
			// Set the rating to the corresponding Factor rating
		double rating = factor.getRating();
		//System.out.println("rating: " + rating);
			// Create new list that will contain all the equals
		List<PossibleTarget> equals = new ArrayList<PossibleTarget>();
			// Add the current best to the list of equals
		equals.add(best);
		best.rating = best.rating + rating;
				// Loop through all the PossibleTargets and compare them with the current best
			for(int k=1; k<possibleTargets.length; k++) {
					// Set the current possibleTarget as 'current'
				PossibleTarget current = possibleTargets[k];
					// Compare the current with the best PossibleTarget, based on a factor
				//System.out.println("current " + current.toString());
				//System.out.println("best" + best.toString());
				compares = comparePossibleTargets(factor, current, best);
				//System.out.println("Compares: [0]=" + compares[0] + ", compares[1]=" + compares[1]);
				if(compares[0]==true) { // if current >/< than the best, remove the best from the list and set this as best and add to list
					for(int j=0; j < equals.size(); j++) {
						equals.get(j).rating = equals.get(j).rating - rating;
					}
					equals.clear();
					best = current;
					best.rating = best.rating + rating;
					equals.add(best);
				} else if (compares[1]) { // else (current==best), name this one as best and add this one also to the list
					best = current;
					best.rating = best.rating + rating;
					equals.add(best);
				}
			}
			/*for(PossibleTarget pt : possibleTargets) {
				System.out.println("Rating: " + pt.rating);
			}*/
		return possibleTargets;
	}
	
	/**
	 * <code>compareFactor</code> compares <code>Factor</code> values between two <code>PossibleSpot</code>s
	 * @param factor			the <code>Factor</code> that has to be compared
	 * @param compareOptions	the list of PossibleTargets that have to be compared
	 * @return equals			, the list of PossibleTargets (in this case the length is either 1 or 0)
	 */
	private List<PossibleTarget> compareFactor(Factor factor, List<PossibleTarget> compareOptions) {
			// Set the first possibleTarget as the current best target
		PossibleTarget best = compareOptions.get(0);
			// Create new list that will contain all the equals
		List<PossibleTarget> equals = new ArrayList<PossibleTarget>();
			// Add the current best to the list of equals
		equals.add(compareOptions.get(0));
			// Create a new Boolean[] that will contain the results of comparePossibleTargets
		Boolean[] compares = new Boolean[2];
			// Loop through all the PossibleTargets and compare the values of the factors
		for(int k=1; k<compareOptions.size(); k++) {
				// set the current PossibleTarget as 'current'
			PossibleTarget current = compareOptions.get(k);
				// Compare the current with the best PossibleTarget, based on a factor
			compares = comparePossibleTargets(factor, current, best);
			if(compares[0]) { // if current >/< than the best, remove the best from the list and set this as best and add to list
				equals.clear();
				best = current;
				equals.add(best);
			} else if (compares[1]){ // else (current==best), clear the list and break (as it turns out there are more than one with the same valuees)
				equals.clear();
				break;
			}
		}
		return equals;
	}
	
	/**
	 * <code>comparePossibleTargets</code> is used in <code>rateFactor</code> to compare two <code>PossibleTargets</code>'s factor values.
	 * @param factor	the factor of which the value has to be compared
	 * @param possible	the first PossibleSpot (the one that will be placed before the evaluation symbol)
	 * @param best		the second PossibleSpot (the one that will be placed behind the evaluation symbol)
	 * @return result	an Boolean[2] array. Index 0 will contain true/false for the >/< evaluator and Index 1 will contain the true/false for the == evaluator.
	 */
	private Boolean[] comparePossibleTargets(Factor factor, PossibleTarget possible, PossibleTarget best) {
			// Create a new Boolean[] that will contain the results of comparePossibleTargets
		Boolean[] result = new Boolean[2];
		//System.out.println("Spots: " + possible.toGoalSpot().toString() + ", " + best.toGoalSpot().toString());
		
			// Look at which factor has to be compared and compare them between two PossibleTargets
		switch(factor) {
			case TPD:
				result[0] = (possible.getTPD() > best.getTPD());
				result[1] = possible.getTPD() == best.getTPD();
				//System.out.println("result 0: " + result[0]);
				//System.out.println("result 1: " + result[1]);
				return result;
			case ST:
				result[0] = possible.getSurThreat() < best.getSurThreat();
				result[1] = possible.getSurThreat() == best.getSurThreat();
				return result;
			case SD:
				result[0] = possible.getSD() < best.getSD();
				result[1] = possible.getSD() == best.getSD();
				return result;
			case FDC:
				result[0] =	possible.getCalcCost() < best.getCalcCost();
				result[1] = possible.getCalcCost() == best.getCalcCost();
				return result;
			case HD: 
				result[0] = possible.getHighestDanger() < best.getHighestDanger();
				result[1] = possible.getHighestDanger() == best.getHighestDanger();
				return result;
		}
		
		return result;
	}
	
	/**
	 * <code>compareRatings</code> compares all the ratings of <code>PossibleTarget</code>s and retuns the best ones
	 * @param possibleTargets	an array of all the PossibleTargets
	 * @return bestOptions, the list of the best PossibleTargets
	 */
	private List<PossibleTarget> compareRatings(PossibleTarget[] possibleTargets) {
			// create list bestOptions, which will be all the PossibleTargets with the same and highest values
		List<PossibleTarget> bestOptions = new ArrayList<PossibleTarget>();
			// set the first possibleTarget as current best target
		PossibleTarget highestRating = possibleTargets[0];
			// Add this target to the list of bestOptions
		bestOptions.add(highestRating);
			// Loop through all the possibleTargets and compare their ratings
		for(int k = 1; k < possibleTargets.length; k++) {
			if(highestRating.rating < possibleTargets[k].rating) {	// if one with a higher rating is found, empty the list and add this as best
				bestOptions.clear();
				highestRating = possibleTargets[k];
				bestOptions.add(possibleTargets[k]);
			} else if (highestRating.rating == possibleTargets[k].rating) { // else (same ratings), add the current rating to bestOptions
				bestOptions.add(possibleTargets[k]);
			}
		}
		return bestOptions;
	}
	
	/**
	 * Picks a random <code>GoalSpot</code> from a given given <code>PossibleTarget</code>s aray
	 * @param possibleTargets the array containing the PossibleTargets
	 * @return target	, random GoalSpot
	 */
	private GoalSpot pickRandomGoal(List<PossibleTarget> bestOptions) {
		GoalSpot target = null;
			// Range is how many spots there are (-1, as the length is 1 higher than the amount of indexes used)
		int range = bestOptions.size()-1;
			// Math.random picks a number between 0 and 1, so balance that with the range and you should get a number between 0 and range (at least thats the idea..)
		int picked = (int) (Math.random()*range);
		System.out.println("Random picked: " + picked);
			// Pick the spot from possibleTargets that is the same as the random picked number
		target = bestOptions.get(picked).toGoalSpot();
		return target;
	}
	
	public List<Cell> findPath(GoalSpot goal) {
		System.out.println("MovingSpot: " + this.toString());
		System.out.println("GoalSpot: " + goal.toString());

		List<Cell>path = new ArrayList<Cell>();
		int xGoal = goal.getX();
		int yGoal = goal.getY();
		int xSpot = this.getX();
		int ySpot = this.getY();
		int xDirection;
		int yDirection;
		
		
		if(xGoal-xSpot==0) {
			xDirection = 0;
		} else if(xGoal-xSpot>0) {
			xDirection = -1;
		} else {
			xDirection = 1;
		}
		
		if(yGoal-ySpot==0) {
			yDirection = 0;
		} else if(yGoal-ySpot>0) {
			yDirection = -1;
		} else {
			yDirection = 1;
		}
		
		Cell nextMove = nextMove(xSpot, ySpot, xDirection, yDirection);
		path.add(nextMove);
		boolean foundTarget = false;
		while(!foundTarget) {
			nextMove = nextMove(path.get(path.size()-1).getX(), path.get(path.size()-1).getY(), xDirection, yDirection);
			path.add(nextMove);
			if(nextMove.getX()==goal.getX() && nextMove.getY()==goal.getY()) foundTarget = true;
		}
		
		return path;
	}
	
	public Cell nextMove (int x, int y, int xDirection, int yDirection) {
		int randomX = (int) (Math.random()*(playfield.width-1));
		int randomY = (int) (Math.random()*(playfield.height-1));
		Cell next = playfield.cells[randomX][randomY];
		System.out.println(next.getX() + ", " + next.getY());
		return next;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString() {
		return "[" + this.x + "," + this.y + "]";
	}
}

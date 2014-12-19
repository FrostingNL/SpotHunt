package spothunt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	// initialize the Map factors which will contain the rating and the mathematical symbol for each factor
	private static final Map<String, Map<String, Object>> factors;
    static {
        Map<String, Map<String, Object>> prefactors = new HashMap<String, Map<String, Object>>();
        Map<String, Object> values = new HashMap<String, Object>();
        
        //Set values for rating and mathSymbol for the TPD factor
        values.put("rating", 0.5);
        values.put("mathSymbol", ">");
        prefactors.put("TPD", values);

        //Set values for rating and mathSymbol for the Suround factor
        values.put("rating", 1);
        values.put("mathSymbol", "<");
        prefactors.put("Surround", values);

        //Set values for rating and mathSymbol for the SD factor
        values.put("rating", 0.5);
        values.put("mathSymbol", "<");
        prefactors.put("SD", values);

        //Set values for rating and mathSymbol for the Calc factor
        values.put("rating", 1);
        values.put("mathSymbol", "<");
        prefactors.put("Calc", values);

        //Set values for rating and mathSymbol for the HD factor
        values.put("rating", 1);
        values.put("mathSymbol", "<");
        prefactors.put("HD", values);
        
        factors = Collections.unmodifiableMap(prefactors);
    }
	
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
	
	public GoalSpot pickTarget(GoalSpot[] allGoals) {
		GoalSpot target = null;
		PossibleTarget[] possibleTargets = new PossibleTarget[allGoals.length];
		
		for(int i = 0; i < allGoals.length; i ++) {
			GoalSpot current = allGoals[i];
			possibleTargets[i] = new PossibleTarget(current);
			PossibleTarget possible = possibleTargets[i];
			int possibleX = current.getX() - this.x;
			int possibleY = current.getY() - this.y;
			int dangerCost = 0;
			int highestDanger = 0;
			int calculatedCost = 0;
			/*
			 * CLEANED UP QUICKEST DANGER PATH COST THING HERE! 
			 */
			
			double penalty = 1;
			if(current.getX()==0 || current.getX()==playfield.width-1 
					|| current.getY()==playfield.height || current.getY()==0) {
				penalty = 1.6;
			}
			possible.setHighestDanger(highestDanger);
			possible.setTPD(current.getTPD());
			possible.setSD(current.getSD());
			possible.setPenalty(penalty);
			possible.setCalcCost(calculatedCost);
			possible.setSurThreat(current.calculateSurround());
		}
		
		for(Map.Entry<String, Map<String, Object>> factor : factors.entrySet()) {
			possibleTargets = rateFactor(possibleTargets, factor.getKey());	
		}
		
		/*
		 * Further on -- still gotta implement.
		 */
		
		return target;
	}
	
	public PossibleTarget[] rateFactor(PossibleTarget[] possibleTargets, String factor) {
		PossibleTarget best = possibleTargets[0];
		Boolean[] compares = new Boolean[2];
		double rating = (double) factors.get(factor).get("Rating");
		List<PossibleTarget> equals = new ArrayList<PossibleTarget>();
		equals.add(best);
			for(int k = 1; k < possibleTargets.length; k++) {
				compares = compare(factors.get(factor).get("MathSymbol").toString(), possibleTargets[k], best);
				if(compares[0]) {
					for(int j=0; j < equals.size(); j++) {
						equals.get(j).rating = equals.get(j).rating - rating;
					}
					equals.clear();
					best = possibleTargets[k];
					best.rating = best.rating + rating;
					equals.add(best);
				} else if (compares[1]) {
					best = possibleTargets[k];
					best.rating = best.rating + rating;
					equals.add(best);
				}
			}
		return possibleTargets;
	}
	
	public Boolean[] compare(String factor, PossibleTarget possible, PossibleTarget best) {
		Boolean[] result = new Boolean[2];
		
		switch(factor) {
			case "TPD":
				result[0] = possible.getTPD() > best.getTPD();
				result[1] = possible.getTPD() == best.getTPD();
				break;
			case "Surround":
				result[0] = possible.getSurThreat() < best.getSurThreat();
				result[1] = possible.getSurThreat() == best.getSurThreat();
				break;
			case "SD":
				result[0] = possible.getSD() < best.getSD();
				result[1] = possible.getSD() == best.getSD();
				break;
			case "Calc":
				result[0] =	possible.getCalcCost() < best.getCalcCost();
				result[1] = possible.getCalcCost() == best.getCalcCost();
				break;
			case "HD": 
				result[0] = possible.getHighestDanger() < best.getHighestDanger();
				result[1] = possible.getHighestDanger() == best.getHighestDanger();
				break;
		}
		
		return result;
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

package spothunt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	public static void main(String arg[]) {
			// Create new playfield
		Playfield test = new Playfield(10,10, 2);
			// Add 3 players to playfield
		test.createPlayers(3);
			// Create array with 3 GoalSpots
		GoalSpot[] goals = new GoalSpot[] {new GoalSpot(test, 5, 1), new GoalSpot(test, 1, 5), new GoalSpot(test, 9,9)};
			// Add the goalSpots to the playfield
		test.setGoals(goals);
			// Move the MovingSpot
		test.moveSpot(1,1);
			// Move P'layer 0
		test.movePlayer(0, 5, 4);
			// Move Player 1
		test.movePlayer(1, 4, 5);
			// Move Player 2
		test.movePlayer(2, 4, 5);
			// Get information about the compoments
		test.getInformation();
			// Show field
		test.showPFValues(3);
		
		/* SUPER SECRET TEST AREA!! Not so secret anymore though */
		GoalSpot target = test.spot.pickTarget(goals);
		System.out.println("\nPicked: " + target.toString());
		
		test.spot.findPath(target);
		/*
		PossibleTarget[] possibleTargets = new PossibleTarget[test.goals.length];
		for(int k = 0; k < test.goals.length; k ++) {
			possibleTargets[k] = new PossibleTarget(test.goals[k]);
			System.out.println("GOAL " + k);
			int possibleX = test.goals[k].getX() - test.spot.getX();
			int possibleY = test.goals[k].getY() - test.spot.getY();
			int dangerCost = 0;
			int highestDanger = 0;
			double calculatedCost = 0;
			System.out.println("Y: " + possibleY);
			System.out.println("X: " + possibleX);
			System.out.println(possibleY==0);
			System.out.println(possibleX==0);
			
			if(possibleX==0) {
				System.out.println("in X==0");
				for(int i=1; i <= Math.abs(possibleY); i++) {
					if(possibleY>0) {
						int nextCost = test.cells[test.spot.getX()][test.spot.getY()+i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
					} else {
						int nextCost = test.cells[test.spot.getX()][test.spot.getY()-i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
					}
				}
			calculatedCost = (int)Math.pow(dangerCost, 2)/Math.abs(possibleY);
			}else if(possibleY==0) {
				System.out.println("in Y==0");
				for(int i=1; i <= Math.abs(possibleX); i++) {
					if(possibleX>0) {
						System.out.println("in Y==0 | if | i=" +i + " | Danger=" + test.cells[test.spot.getX()+i][test.spot.getY()].getDanger());
						int nextCost =  test.cells[test.spot.getX()+i][test.spot.getY()].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
					} else {
						System.out.println("in Y==0 | else | i="+i + " | Danger=" + test.cells[test.spot.getX()-i][test.spot.getY()].getDanger());
						int nextCost =  test.cells[test.spot.getX()-i][test.spot.getY()].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
					}
				}
			calculatedCost = (int)Math.pow(dangerCost, 2)/Math.abs(possibleX);
			}else if((double)possibleX/(double)possibleY==1.0) {
				System.out.println("in X/Y==1");
				//TODO: see if the last Else If and the last Else are correctly implemented! :)
				for(int i=1; i <= possibleY; i++) {
					if(possibleX>0 && possibleY>0) {
						int nextCost =  test.cells[test.spot.getX()+i][test.spot.getY()+i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
					} else if(possibleX<0 && possibleY<0) {
						int nextCost =  test.cells[test.spot.getX()-i][test.spot.getY()-i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
					} else if(possibleX<0 && possibleY>0) {
						int nextCost =  test.cells[test.spot.getX()-i][test.spot.getY()+i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
					} else {
						int nextCost =  test.cells[test.spot.getX()+i][test.spot.getY()-i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
					}
				}
			calculatedCost = (int)Math.pow(dangerCost, 2)/Math.abs(Math.sqrt(Math.pow(possibleX, 2)+Math.pow(possibleY, 2)));
			} else {
				System.out.println("PossibleX, PossibleY: " + possibleX + ", " + possibleY);
				if(possibleX>0 && possibleY>0) {
					if(possibleX > possibleY) {
						for(int i=1; i<possibleY+1; i++) {
							int nextCost =  test.cells[test.spot.getX()+i][test.spot.getY()+i].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()+i) + ", " + (test.spot.getY()+i));
							System.out.println("Cost: " + test.cells[test.spot.getX()+i][test.spot.getY()+i].getDanger());
						}
						int remaining = possibleX - possibleY + 1;
						for(int i = 1; i < remaining; i++) {
							int nextCost = test.cells[test.spot.getX()+possibleY+i][test.spot.getY()+possibleY].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()+possibleY+i) + ", " + (test.spot.getY()+possibleY));
							System.out.println("Cost: " + test.cells[test.spot.getX()+possibleY+i][test.spot.getY()+possibleY].getDanger());
						}
					} else {
						for(int i=1; i<possibleX+1; i++) {
							int nextCost =  test.cells[test.spot.getX()+i][test.spot.getY()+i].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()+i) + ", " + (test.spot.getY()+i));
							System.out.println("Cost: " + test.cells[test.spot.getX()+i][test.spot.getY()+i].getDanger());
						}
						int remaining = possibleY - possibleX + 1;
						for(int i = 1; i < remaining; i++) {
							int nextCost =  test.cells[test.spot.getX()+possibleX][test.spot.getY()+possibleX+1].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()+possibleX) + ", " + (test.spot.getY()+possibleX+i));
							System.out.println("Cost: " + test.cells[test.spot.getX()+possibleX][test.spot.getY()+possibleX+i].getDanger());
						}
					}
					calculatedCost = (int)Math.pow(dangerCost, 2)/Math.abs(Math.sqrt(Math.pow(possibleX, 2)+Math.pow(possibleY, 2)));
				} else if(possibleX<0 && possibleY<0) {
					//NEGATIEFF!!
					if(possibleX > possibleY) {
						possibleX = Math.abs(possibleX);
						possibleY = Math.abs(possibleY);
						for(int i=1; i<possibleX+1; i++) {
							int nextCost =  test.cells[test.spot.getX()-i][test.spot.getY()-i].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()-i) + ", " + (test.spot.getY()-i));
							System.out.println("Cost: " + test.cells[test.spot.getX()-i][test.spot.getY()-i].getDanger());
						}
						int remaining = possibleY - possibleX + 1;
						for(int i = 1; i < remaining; i++) {
							int nextCost =  test.cells[test.spot.getX()-possibleX][test.spot.getY()-possibleX-i].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()-possibleX) + ", " + (test.spot.getY()-possibleX-i));
							System.out.println("Cost: " + test.cells[test.spot.getX()-possibleX][test.spot.getY()-possibleX-i].getDanger());
						}
					} else {
						possibleX = Math.abs(possibleX);
						possibleY = Math.abs(possibleY);
						for(int i=1; i<possibleY+1; i++) {
							int nextCost =  test.cells[test.spot.getX()-i][test.spot.getY()-i].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()-i) + ", " + (test.spot.getY()-i));
							System.out.println("Cost: " + test.cells[test.spot.getX()-i][test.spot.getY()-i].getDanger());
						}
						int remaining = possibleX - possibleY + 1;
						for(int i = 1; i < remaining; i++) {
							int nextCost =  test.cells[test.spot.getX()-possibleY-i][test.spot.getY()-possibleY].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()-possibleY-i) + ", " + (test.spot.getY()-possibleY));
							System.out.println("Cost: " + test.cells[test.spot.getX()-possibleY-i][test.spot.getY()-possibleY].getDanger());
						}
					}
					calculatedCost = (int)Math.pow(dangerCost, 2)/Math.abs(Math.sqrt(Math.pow(possibleX, 2)+Math.pow(possibleY, 2)));
				} else if(possibleX<0 && possibleY>0) {
					if(Math.abs(possibleX) < Math.abs(possibleY)) {
						possibleX = Math.abs(possibleX);
						possibleY = Math.abs(possibleY);
						for(int i=1; i<possibleX+1; i++) {
							int nextCost =  test.cells[test.spot.getX()-i][test.spot.getY()+i].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()-i) + ", " + (test.spot.getY()+i));
							System.out.println("Cost: " + test.cells[test.spot.getX()-i][test.spot.getY()+i].getDanger());
						}
						int remaining = possibleY - possibleX + 1;
						for(int i = 1; i < remaining; i++) {
							int nextCost =  test.cells[test.spot.getX()-possibleX][test.spot.getY()+possibleX+i].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()-possibleX) + ", " + (test.spot.getY()+possibleX+i));
							System.out.println("Cost: " + test.cells[test.spot.getX()-possibleX][test.spot.getY()+possibleX+i].getDanger());
						}
					} else {
						possibleX = Math.abs(possibleX);
						possibleY = Math.abs(possibleY);
						for(int i=1; i<possibleY+1; i++) {
							int nextCost = test.cells[test.spot.getX()-i][test.spot.getY()+i].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()-i) + ", " + (test.spot.getY()+i));
							System.out.println("Cost: " + test.cells[test.spot.getX()-i][test.spot.getY()+i].getDanger());
						}
						int remaining = possibleX - possibleY + 1;
						System.out.println("Remaining: " + remaining);
						for(int i = 1; i < remaining; i++) {
							int nextCost = test.cells[test.spot.getX()-possibleY-i][test.spot.getY()+possibleY].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()-possibleY-i) + ", " + (test.spot.getY()+possibleY));
							System.out.println("Cost: " + test.cells[test.spot.getX()-possibleY-i][test.spot.getY()+possibleY].getDanger());
						}
					}
					calculatedCost = (int)Math.pow(dangerCost, 2)/Math.abs(Math.sqrt(Math.pow(possibleX, 2)+Math.pow(possibleY, 2)));
				} else { // possibleX>0 && possibleY<0
					if(Math.abs(possibleX) < Math.abs(possibleY)) {
						possibleX = Math.abs(possibleX);
						possibleY = Math.abs(possibleY);
						for(int i=1; i<possibleX+1; i++) {
							int nextCost = test.cells[test.spot.getX()+i][test.spot.getY()-i].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()+i) + ", " + (test.spot.getY()-i));
							System.out.println("Cost: " + test.cells[test.spot.getX()+i][test.spot.getY()-i].getDanger());
						}
						int remaining = possibleY - possibleX + 1;
						for(int i = 1; i < remaining; i++) {
							int nextCost = test.cells[test.spot.getX()+possibleX][test.spot.getY()+possibleX-i].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()+possibleX) + ", " + (test.spot.getY()-possibleX-i));
							System.out.println("Cost: " + test.cells[test.spot.getX()+possibleX][test.spot.getY()-possibleX-i].getDanger());
						}
					} else {
						possibleX = Math.abs(possibleX);
						possibleY = Math.abs(possibleY);
						System.out.println("posY" + possibleY);
						for(int i=1; i<possibleY+1; i++) {
							int nextCost = test.cells[test.spot.getX()+i][test.spot.getY()-i].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()+i) + ", " + (test.spot.getY()-i));
							System.out.println("Cost: " + test.cells[test.spot.getX()+i][test.spot.getY()-i].getDanger());
						}
						int remaining = possibleX - possibleY + 1;
						for(int i = 1; i < remaining; i++) {
							int nextCost =  test.cells[test.spot.getX()+possibleY+i][test.spot.getY()-possibleY].getDanger();
							if(nextCost > highestDanger) highestDanger = nextCost;
							dangerCost = dangerCost + nextCost;
							System.out.println("X,Y: " + (test.spot.getX()+possibleY+i) + ", " + (test.spot.getY()-possibleY));
							System.out.println("Cost: " + test.cells[test.spot.getX()+possibleY+i][test.spot.getY()-possibleY].getDanger());
						}
					}
					calculatedCost = (int)Math.pow(dangerCost, 2)/Math.abs(Math.sqrt(Math.pow(possibleX, 2)+Math.pow(possibleY, 2)));
				
				}
			}
			double extraPenalty = 1;
			if(test.goals[k].getX()==0 || test.goals[k].getX()==test.width-1 
					|| test.goals[k].getY()==test.height || test.goals[k].getY()==0) {
				extraPenalty = 1.6;
			}
			System.out.println("HighestDanger " + k + ": " + highestDanger);
			possibleTargets[k].setHighestDanger(highestDanger);
			possibleTargets[k].setTPD(test.goals[k].getTPD());
			possibleTargets[k].setSD(test.goals[k].getSD());
			System.out.println("Spot distance: " + test.goals[k].getSD());
			possibleTargets[k].setPenalty(extraPenalty);
			possibleTargets[k].setCalcCost((int) calculatedCost);
			possibleTargets[k].setSurThreat(goals[k].calculateSurround());
			String penalty = extraPenalty==1 ? "N" : "Y";
			System.out.println("Penalty: " + penalty);
			System.out.println("calculated Cost: " + calculatedCost);
			System.out.println("Surround threat: " + goals[k].calculateSurround());
		}
		double TPDrating = 0.5;
		double SurRating = 1;
		double SDRating = 0.5;
		double CalcRating = 1;
		double HDRating = 1;
		
		PossibleTarget bestTPD = possibleTargets[0];
		bestTPD.rating = bestTPD.rating + TPDrating;
		List<PossibleTarget> equalTPD = new ArrayList<PossibleTarget>();
		equalTPD.add(bestTPD);
		for(int k=1; k < possibleTargets.length; k++) {
			if(possibleTargets[k].getTPD() > bestTPD.getTPD()) {
				for(int j=0; j < equalTPD.size(); j++) {
					equalTPD.get(j).rating = equalTPD.get(j).rating - TPDrating;
				}
				equalTPD.clear();
				bestTPD = possibleTargets[k];
				bestTPD.rating = bestTPD.rating + TPDrating;
				equalTPD.add(bestTPD);
			} else if (possibleTargets[k].getTPD() == bestTPD.getTPD()) {
				bestTPD = possibleTargets[k];
				bestTPD.rating = bestTPD.rating + TPDrating;
				equalTPD.add(bestTPD);
			}
		}
		
		PossibleTarget bestSD = possibleTargets[0];
		bestSD.rating = bestSD.rating + SDRating;
		List<PossibleTarget> equalSD = new ArrayList<PossibleTarget>();
		equalSD.add(bestSD);
		for(int k=1; k < possibleTargets.length; k++) {
			if(possibleTargets[k].getSD() < bestSD.getSD()) {
				for(int j=0; j < equalSD.size(); j++) {
					equalSD.get(j).rating = equalSD.get(j).rating - SDRating;
				}
				equalSD.clear();
				bestSD = possibleTargets[k];
				bestSD.rating = bestSD.rating + SDRating;
				equalSD.add(bestSD);
			} else if (possibleTargets[k].getSD() == bestSD.getSD()) {
				bestSD = possibleTargets[k];
				bestSD.rating = bestSD.rating + SDRating;
				equalSD.add(bestSD);
			}
		}
		
		PossibleTarget bestCalc = possibleTargets[0];
		bestCalc.rating = bestCalc.rating + CalcRating;
		List<PossibleTarget> equalCalc = new ArrayList<PossibleTarget>();
		equalCalc.add(bestCalc);
		for(int k=1; k < possibleTargets.length; k++) {
			if(possibleTargets[k].getCalcCost() < bestCalc.getCalcCost()) {
				for(int j=0; j < equalCalc.size(); j++) {
					equalCalc.get(j).rating = equalCalc.get(j).rating - CalcRating;
				}
				equalCalc.clear();
				bestCalc = possibleTargets[k];
				bestCalc.rating = bestCalc.rating + CalcRating;
				equalCalc.add(bestCalc);
			} else if (possibleTargets[k].getCalcCost() == bestCalc.getCalcCost()) {
				bestCalc = possibleTargets[k];
				bestCalc.rating = bestCalc.rating + CalcRating;
				equalCalc.add(bestCalc);
			}
		}
		
		PossibleTarget bestHDanger = possibleTargets[0];
		bestHDanger.rating = bestHDanger.rating + HDRating;
		List<PossibleTarget> equalHD = new ArrayList<PossibleTarget>();
		equalHD.add(bestHDanger);
		for(int k=1; k < possibleTargets.length; k++) {
			if(possibleTargets[k].getHighestDanger() < bestHDanger.getHighestDanger()) {
				for(int j=0; j < equalHD.size(); j++) {
					equalHD.get(j).rating = equalHD.get(j).rating - HDRating;
				}
				equalHD.clear();
				bestHDanger = possibleTargets[k];
				bestHDanger.rating = bestHDanger.rating + HDRating;
				equalHD.add(bestHDanger);
			} else if (possibleTargets[k].getHighestDanger() == bestHDanger.getHighestDanger()) {
				bestHDanger = possibleTargets[k];
				bestHDanger.rating = bestHDanger.rating + HDRating;
				equalHD.add(bestHDanger);
			}
		}
		
		PossibleTarget leastThr = possibleTargets[0];
		leastThr.rating = leastThr.rating + SurRating;
		List<PossibleTarget> equalThr = new ArrayList<PossibleTarget>();
		equalThr.add(leastThr);
		for(int k=1; k < possibleTargets.length; k++) {
			double surThreat = possibleTargets[k].getPenalty() > 1 ? possibleTargets[k].getSurThreat()*possibleTargets[k].getPenalty() : possibleTargets[k].getSurThreat();
			double curThreat = leastThr.getPenalty() > 1 ? leastThr.getSurThreat()*leastThr.getPenalty() : leastThr.getSurThreat();
			if(surThreat < curThreat) {
				for(int j=0; j < equalThr.size(); j++) {
					equalThr.get(j).rating = equalThr.get(j).rating - SurRating;
				}
				equalThr.clear();
				leastThr = possibleTargets[k];
				leastThr.rating = leastThr.rating + SurRating;
				equalThr.add(leastThr);
			} else if (surThreat == curThreat) {
				leastThr = possibleTargets[k];
				leastThr.rating = leastThr.rating + SurRating;
				equalThr.add(leastThr);
			}
		}
		for(int k=0; k < possibleTargets.length; k++) {
			System.out.println("Goal " + k + ": " + possibleTargets[k].rating);
		}
		
		Map<Integer, PossibleTarget> bestOptions = new HashMap<Integer, PossibleTarget>();
		PossibleTarget currentOption = possibleTargets[0];
		bestOptions.put(0, currentOption);
		for(int k=1; k < possibleTargets.length; k++) {
			if(currentOption.rating < possibleTargets[k].rating) {
				bestOptions.clear();
				currentOption = possibleTargets[k];
				bestOptions.put(k, possibleTargets[k]);
			} else if (currentOption.rating == possibleTargets[k].rating) {
				bestOptions.put(k, possibleTargets[k]);
			}
		}
		if(bestOptions.size()==1) {
			System.out.println("Best option: " + bestOptions.toString());
		} else {
			List<Integer> remaining = new ArrayList<Integer>(bestOptions.keySet());
			PossibleTarget considering = bestOptions.get(remaining.get(0));
			List<PossibleTarget> finalTesting = new ArrayList<PossibleTarget>();
			finalTesting.add(considering);
			for(int k=1; k<remaining.size(); k++) {
				if(considering.getCalcCost() > bestOptions.get(remaining.get(k)).getCalcCost()) {
					finalTesting.clear();
					considering = bestOptions.get(remaining.get(k));
					finalTesting.add(considering);
				} else if (considering.getCalcCost() == bestOptions.get(remaining.get(k)).getCalcCost()){
					finalTesting.clear();
				}
			}
			if(finalTesting.size()==1) {
				System.out.println("After more testing (CalcCost), best option: " + finalTesting.get(0).spot.getX() + ", " + finalTesting.get(0).spot.getY());
				return;
			}
			//Not tested after this
			considering = bestOptions.get(remaining.get(0));
			finalTesting.clear(); finalTesting.add(considering);
			for(int k=1; k<remaining.size(); k++) {
				if(considering.getTPD() < bestOptions.get(remaining.get(k)).getTPD()) {
					finalTesting.clear();
					considering = bestOptions.get(remaining.get(k));
					finalTesting.add(considering);
				} else if (considering.getTPD() == bestOptions.get(remaining.get(k)).getTPD()){
					finalTesting.clear();
				}
			}
			if(finalTesting.size()==1) {
				System.out.println("After more testing (TPD), best option: " + finalTesting.get(0).spot.getX() + ", " + finalTesting.get(0).spot.getY());
				return;
			}
			
			considering = bestOptions.get(remaining.get(0));
			finalTesting.clear(); finalTesting.add(considering);
			for(int k=1; k<remaining.size(); k++) {
				if(considering.getSurThreat() > bestOptions.get(remaining.get(k)).getSurThreat()) {
					finalTesting.clear();
					considering = bestOptions.get(remaining.get(k));
					finalTesting.add(considering);
				} else if (considering.getSurThreat() == bestOptions.get(remaining.get(k)).getSurThreat()){
					finalTesting.clear();
				}
			}
			if(finalTesting.size()==1) {
				System.out.println("After more testing (SurThreat), best option: " + finalTesting.get(0).spot.getX() + ", " + finalTesting.get(0).spot.getY());
				return;
			}
			
			considering = bestOptions.get(remaining.get(0));
			finalTesting.clear(); finalTesting.add(considering);
			for(int k=1; k<remaining.size(); k++) {
				if(considering.getSD() < bestOptions.get(remaining.get(k)).getSD()) {
					finalTesting.clear();
					considering = bestOptions.get(remaining.get(k));
					finalTesting.add(considering);
				} else if (considering.getSD() == bestOptions.get(remaining.get(k)).getSD()){
					finalTesting.clear();
				}
			}
			if(finalTesting.size()==1) {
				System.out.println("After more testing (SD), best option: " + finalTesting.get(0).spot.getX() + ", " + finalTesting.get(0).spot.getY());
				return;
			}
			
			System.out.println("Still didn't find a good one :( - I'll pick random now as they're equally bad.");
		}
		*/
	}
}

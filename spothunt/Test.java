package spothunt;

public class Test {
	public static void main(String arg[]) {
			// Create new playfield
		Playfield test = new Playfield(10,10, 3);
			// Add 3 players to playfield
		test.createPlayers(3);
			// Create array with 3 GoalSpots
		GoalSpot[] goals = new GoalSpot[] {new GoalSpot(test, 8, 3), new GoalSpot(test, 5, 8), new GoalSpot(test, 2,9)};
			// Add the goalSpots to the playfield
		test.setGoals(goals);
			// Move the MovingSpot
		test.moveSpot(3,5);
			// Move P'layer 0
		test.movePlayer(0, 7, 5);
			// Move Player 1
		test.movePlayer(1, 4, 8);
			// Move Player 2
		test.movePlayer(2, 1, 3);
			// Get information about the compoments
		test.getInformation();
			// Show field
		test.showPFValues(3);
		
		/* SUPER SECRET TEST AREA!! Not so secret anymore though */
		GoalSpot target = test.spot.pickTarget(goals);
		System.out.println("\nPicked: " + target.toString());
		
		test.spot.findPath(target);
		
	}
}

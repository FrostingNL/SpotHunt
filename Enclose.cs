using UnityEngine;
//Class to check whether a spot is enclosed
public class Enclose : MonoBehaviour {

	Spot spotThis;
	TagPlayer player1;
	TagPlayer player2;
	TagPlayer player3;

	//TODO audio
	public AudioClip[] a_goalSounds;

	public Logger loggerScript;
	private GameObject mainCameraObject;
	//TODO: radius should be size of players radius
	float radius = 2.0;

	// Use this for initialization
	void isEnclosed(Spot spot) {
		/*if(spotTrigger.gameObject.transform.parent != null && spotTrigger.gameObject.transform.parent.name == "spot")
			string triggername = myTrigger.gameObject.transform.name;
			GameObject goalSpotObject = myTrigger.gameObject;
		if (triggername == "goalSpot")
		{
			loggerScript.LogLinePowerUp(spotThis.id + "\t" + triggername + "\n");
			Destroy(goalSpotObject, 0.0f);
			playCollectGoalSound(0);
			removeGoalSpot(goalSpotObject);
		}*/
		Collider2D[] spotCollidesWith = Physics2D.OverlapCircleAll(spot.transform.position, radius, Physics2D.DefaultRaycastLayers);

		//Check whether all player colliders are in playercollider
		//And whether colliders of each player touches the collider of the other two
		//if so then the spot has been enclosed
		if (spotCollidesWith.Length > 2) {
			if (Contains(spotCollidesWith, player1) && Contains(spotCollidesWith, player2) && Contains(spotCollidesWith, player3))
			{
				//Circle of spot overlaps all colliders of the players,
				// If the colliders touch each other, then the spot is enclosed.
				playersCollidersTouch();
			}

		}
	}

	static bool Contains(Collider2D[] arr, TagPlayer value)
	{
		for (int i = 0; i < arr.Length; i++)
		{
			if (arr[i] == value.collider2D)
			{
				return true;
			}
		}
		return false;
	}

	static bool playersCollidersTouch()
	{
		// radius of player should be same size as radius of their collider
		bool touching = false;
		Collider2D[] collidesWithPlayer1 = Physics2D.OverlapCircleAll(player1.transform.position, radius);
		Collider2D[] collidesWithPlayer2 = Physics2D.OverlapCircleAll(player2.transform.position, radius);
		//Only need to check 3 conditions, whether 1 touches 2 and 3 and whether 2 and 3 touch.
		// Otherwise you check on a lot of conditions that must be true
		// For example is 1 touches 2 then also 2 touches 1.
		if (Contains(collidesWithPlayer1, player2) && Contains(collidesWithPlayer1, player3)
			&& Contains(collidesWithPlayer2, player3))
		{
			touching = true;
		}

		return touching;
	}
}
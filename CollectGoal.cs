using UnityEngine;
using System.Collections.Generic;

public class CollectGoal : MonoBehaviour {
	
	Spot spotThis;
	
	//TODO audio
	public AudioClip[] a_goalSounds;
	
	public Logger loggerScript;
	private GameObject mainCameraObject;
	
	
	// Use this for initialization
	void Start () {
		countSoundsNr = 0;
		mainCameraObject = GameObject.FindGameObjectWithTag("MainCamera");
		loggerScript = mainCameraObject.GetComponent("Logger") as Logger;
		spotThis = this.transform.parent.GetComponent("Spot") as Spot;
	}
	
	// Update is called once per frame
	void Update () {
		
	}
	
	void OnTriggerEnter(Collider myTrigger)
	{
		//check on null to deal with no parent
		if(myTrigger.gameObject.transform.parent != null && myTrigger.gameObject.transform.parent.name == "spot")
			string triggername = myTrigger.gameObject.transform.name;
		GameObject goalSpotObject = myTrigger.gameObject;
		if (triggername == "goalSpot")
		{
			loggerScript.LogLinePowerUp(spotThis.id + "\t" + triggername + "\n");
			Destroy(goalSpotObject, 0.0f);
			playCollectGoalSound(0);
			removeGoalSpot(goalSpotObject);
		}
		
		
		//names as set in the method perhapsGeneratePowerUps in the KinectRigClient , the script of the main camera
		
		print("trigger name" +myTrigger.gameObject.transform.name);
	}
	
	void playCollectGoalSound(int type)
	{
		audio.clip = a_goalSounds[type];
		audio.Play();
	}

	//public List<GoalSpot> goalSpots;
	//TODO: removeGoalSpot should be in PLayfield class
	void removeGoalSpot(GameObject goalSpotObject)
	{
		goalSpots.Remove (goalSpotObject);
		if (goalSpots.Count == 0) {
			//Spot has won, it has collected the last goalSpot
			//TODO: Call a game over method
		}
	}
}
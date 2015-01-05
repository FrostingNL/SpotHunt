public sealed class Factor {
	private readonly string name;
	private readonly double rating;
	private readonly string mathSymbol;
	
	private Factor(string name, double rating, string mathSymbol) {
		this.name = name;
		this.rating = rating;
		this.mathSymbol = mathSymbol;
	}

	public static Factor TPD = new Factor("Total Player Distance", 0.5, ">");
	public static Factor ST = new Factor("Surround Threat", 1.0, "<");
	public static Factor SD = new Factor("Spot Distance", 0.5, "<");
	public static Factor FDC = new Factor ("Fastest Danger Cost", 1.0, "<");
	public static Factor HD = new Factor("Highest Danger", 1.0, "<");

	public double getRating() {
		return rating;
	}
	
	public string getName() {
		return name;
	}
	
	public string getSymbol() {
		return mathSymbol;
	}
}


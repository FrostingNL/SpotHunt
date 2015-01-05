package spothunt;

public enum Factor {
	TPD("Total Player Distance", 0.5, ">"),
	ST("Surround Threat", 1, "<"),
	SD("Spot Distance", 0.5, "<"),
	FDC("Fastest Danger Cost", 1, "<"),
	HD("Highest Danger", 1, "<");
	
	private String name;
	private double rating;
	private String mathSymbol;
	
	private Factor(String name, double rating, String mathSymbol) {
		this.name = name;
		this.rating = rating;
		this.mathSymbol = mathSymbol;
	}
	
	public double getRating() {
		return rating;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSymbol() {
		return mathSymbol;
	}
}

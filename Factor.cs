public class Factor {
	//public enum FactorEnum {
	public void newEnum(){
		var TPD = Tuple.New("Total Player Distance", 0.5, ">");
		var ST = Tuple.New ("Surround Threat", 1, "<");
		var SD = Tuple.New ("Spot Distance", 0.5, "<");
		var FDC = Tuple.New ("Fastest Danger Cost", 1, "<");
		var HD = Tuple.New ("Highest Danger", 1, "<");

	}
		
		private string name;
		private double rating;
		private string mathSymbol;
		
		private Factor(string name, double rating, string mathSymbol) {
			this.name = name;
			this.rating = rating;
			this.mathSymbol = mathSymbol;
		}
		
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



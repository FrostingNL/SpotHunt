public class Factor {
	public Tuple<string, double, string> TPD = new Tuple<string, double, string>("Total Player Distance", 0.5, ">");
	public Tuple<string, double, string> ST = new Tuple<string, double, string>("Surround Threat", 1.0, "<");
	public Tuple<string, double, string> SD = new Tuple<string, double, string>("Spot Distance", 0.5, "<");
	public Tuple<string, double, string> FDC = new Tuple<string, double, string>("Fastest Danger Cost", 1.0, "<");
	public Tuple<string, double, string> HD = new Tuple<string, double, string>("Highest Danger", 1.0, "<");

	public enum FactorEnum{
		TPD, ST, SD, FDC, HD
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



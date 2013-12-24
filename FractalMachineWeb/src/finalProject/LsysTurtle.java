package finalProject;

public class LsysTurtle {
	StringBuilder[] generationStrings;
	String initiator;
	String[] productionRules;
	double theta;
	int segmentLength;
	int scalingFactor;
	
	public LsysTurtle(double theta, int scalingFactor, String init, String[]productionRules){
		this.initiator = init;
		this.theta = theta;
		this.scalingFactor = scalingFactor;
		this.productionRules = productionRules;
		generationStrings = new StringBuilder[6];
		buildGenerations();
	}
	
	public void buildGenerations(){
		//creates the array for the commands of the turtle to do.
		//turtle can only make 5 generations
		boolean found = false;
		StringBuilder temp = new StringBuilder(initiator);
		StringBuilder finalString = new StringBuilder();
		generationStrings[0] = new StringBuilder(initiator);
		for(int i = 1; i < generationStrings.length; i++){
			for(int j = 0; j < temp.length(); j++){
				for(int k = 0; k < productionRules.length; k++){
					if(temp.charAt(j) == productionRules[k].charAt(0)){
						finalString.append(productionRules[k].substring(4, productionRules[k].length()));
						found = true;
					}
				}
				if(!found){
					finalString.append(temp.charAt(j));
				}
				found = false;
			}
			generationStrings[i] = finalString;
			temp = finalString;
			finalString = new StringBuilder();
		}
	}
	
}

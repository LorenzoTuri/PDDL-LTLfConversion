package PDDLFormulaContainer.SubComponents;

import FormulaComponents.SimpleFormula;

/**
 * Class used to store the Problem name of a PDDLProblem specification
 */
public class Problem extends SimpleFormula {
	private String name;
	public Problem(String name){
		this.name = name;
	}

	@Override
	public String toString() {
		return "Problem: "+name;
	}
	public String getProblemName(){
		return name;
	}
}

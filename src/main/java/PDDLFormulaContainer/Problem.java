package PDDLFormulaContainer;

import FormulaComponents.SimpleFormula;

/**
 * Created by loren on 05/09/2016.
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

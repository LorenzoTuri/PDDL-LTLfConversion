package PDDLFormulaContainer.SubComponents;

import FormulaComponents.SimpleFormula;

/**
 * Created by loren on 30/07/2016.
 */
public class Domain extends SimpleFormula {
	String domain;
	public Domain(String s){
		domain = s;
	}

	@Override
	public String toString() {
		return "Define domain:\n\t"+ domain;
	}

	public String getDomain(){return domain;}
}

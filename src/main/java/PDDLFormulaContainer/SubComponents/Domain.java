package PDDLFormulaContainer.SubComponents;

import FormulaComponents.SimpleFormula;

/**
 * Class used to store the domain of a PDDL Domain/Problem formula
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

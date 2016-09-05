package PDDLFormulaContainer;

import FormulaComponents.SimpleFormula;

/**
 * Created by loren on 30/07/2016.
 */
public class DefineFormula extends SimpleFormula {
	String define;
	public DefineFormula(String s){
		define = s;
	}

	@Override
	public String toString() {
		return "Define domain:\n\t"+define;
	}
}

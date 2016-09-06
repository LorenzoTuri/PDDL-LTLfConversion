package PDDLFormulaContainer.SubComponents;

import FormulaComponents.SimpleFormula;

/**
 * Created by loren on 30/07/2016.
 */
public class Domain extends SimpleFormula {
	String define;
	public Domain(String s){
		define = s;
	}

	@Override
	public String toString() {
		return "Define domain:\n\t"+define;
	}

	public String getDefine(){return define;}
}

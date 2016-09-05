package PDDLFormulaContainer;

import FormulaComponents.BaseComponents.PDDLFormula;
import FormulaComponents.SimpleFormula;

/**
 * Created by loren on 05/09/2016.
 */
public class GoalFormula extends SimpleFormula {
	PDDLFormula formula;
	public GoalFormula(PDDLFormula formula){
		this.formula = formula;
	}

	@Override
	public String toString() {
		return "Goal ->"+formula.toString();
	}
	public String toStringLTLf(){
		return "Goal ->"+formula.toStringLTLf();
	}
}

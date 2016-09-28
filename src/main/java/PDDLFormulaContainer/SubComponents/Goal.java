package PDDLFormulaContainer.SubComponents;

import FormulaComponents.BaseComponents.BASE_FORMULA;
import FormulaComponents.SimpleFormula;

/**
 * Class used to store the Goal formula of an PDDLProblem specification
 */
public class Goal extends SimpleFormula {
	BASE_FORMULA formula;
	public Goal(BASE_FORMULA formula){
		this.formula = formula;
	}

	@Override
	public String toString() {
		return "Goal ->"+formula.toString();
	}

	public BASE_FORMULA getGoal(){return formula;}
}

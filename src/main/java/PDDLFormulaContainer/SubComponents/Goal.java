package PDDLFormulaContainer.SubComponents;

import FormulaComponents.BaseComponents.BASE_FORMULA;
import FormulaComponents.SimpleFormula;

/**
 * Created by loren on 05/09/2016.
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
	public String toStringLTLf(){
		return "Goal ->"+formula.toStringLTLf();
	}

	public BASE_FORMULA getGoal(){return formula;}
}

package FormulaComponents;

import FormulaComponents.BaseComponents.BASE_FORMULA;

import java.util.List;

/**
 * Base component of both PDDL and LTLf formulas
 */
public class Formula extends SimpleFormula {
	BASE_FORMULA formula = null;

	/**
	 * Function used to set che current child formula.
	 * @param formula
	 */
	public void setFormula(BASE_FORMULA formula){this.formula = formula;}

	/**
	 * Function used to return che current formula
	 * @return current formula
	 */
	public BASE_FORMULA getFormula(){return formula;}

	/**
	 * Function used to get a List of predicates, contained in the child formula, as from PDDL specification
	 * @return List of PDDL predicates
	 */
	public List<SimplePredicate> getPredicates(){
		return formula.getPredicates();
	}
	@Override
	public String toString() {
		if (formula == null) return "";
		return formula.toString();
	}
}

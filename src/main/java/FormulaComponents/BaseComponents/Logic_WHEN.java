package FormulaComponents.BaseComponents;

import FormulaComponents.SimplePredicate;

import java.util.List;
import java.util.Map;

/**
 * Used to express a WHEN formula specifications
 */
public class Logic_WHEN extends BASE_FORMULA {
	public int NUMBER_FORMULA_1 = 0;
	public int NUMBER_FORMULA_2 = 1;
	BASE_FORMULA formula1 = null;
	BASE_FORMULA formula2 = null;
	int iterator = 0;

	/**
	 * adds a formula to the when. Iterator cycles so a third formula overwrites the first, a fourth the second and so on...
	 * @param formula
	 */
	public void addFormula(BASE_FORMULA formula) {
		if (iterator == 0) formula1 = formula;
		else if (iterator ==1)formula2 = formula;
		iterator++;
		if (iterator >1) iterator = 0;
	}

	/**
	 * returns a formula between the 2 saved formula
	 * @param which
	 * @return
	 */
	public BASE_FORMULA getFormula(int which){
		if (which == NUMBER_FORMULA_1) return formula1;
		else if (which == NUMBER_FORMULA_2) return formula2;
		else return null;
	}

	@Override
	public String toString() {
		if (formula1==null || formula2==null) return "";
		return "when ("+formula1.toString()+") ("+formula2.toString()+")";
	}

	@Override
	public String toStringLTLf(Map<SimplePredicate,String> map) {
		return "("+formula1.toStringLTLf(map)+") -> ("+formula2.toStringLTLf(map)+")";
	}

	@Override
	public List<SimplePredicate> getPredicates() {
		List<SimplePredicate> predicates = formula1.getPredicates();
		predicates.addAll(formula2.getPredicates());
		return null;
	}
}

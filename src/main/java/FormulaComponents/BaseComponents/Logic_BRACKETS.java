package FormulaComponents.BaseComponents;

import FormulaComponents.SimplePredicate;

import java.util.List;
import java.util.Map;

/**
 * Used to express a BRACKETS formula specification
 */
public class Logic_BRACKETS extends BASE_FORMULA {
	BASE_FORMULA formula = null;

	/**
	 * Used to add a formula to the brackets. Currently supports only a formula, so old formula is lost
	 * @param formula
	 */
	public void addFormula(BASE_FORMULA formula) {
		this.formula = formula;
	}

	/**
	 * return the current child formula
	 * @return
	 */
	public BASE_FORMULA getFormula() {
		return formula;
	}

	@Override
	public String toString() {
		if (formula==null) return "( )";
		return "( "+formula.toString()+" )";
	}

	@Override
	public String toStringLTLf(Map<SimplePredicate,String> map) {
		if (formula==null) return "( )";
		return "( "+formula.toStringLTLf(map)+" )";
	}

	@Override
	public List<SimplePredicate> getPredicates() {
		return formula.getPredicates();
	}
}

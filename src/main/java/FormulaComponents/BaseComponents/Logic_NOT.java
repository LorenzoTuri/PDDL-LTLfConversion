package FormulaComponents.BaseComponents;

import FormulaComponents.SimplePredicate;

import java.util.List;
import java.util.Map;

/**
 * Used to express a NOT formula specification
 */
public class Logic_NOT extends BASE_FORMULA {
	BASE_FORMULA formula = null;

	/**
	 * adds a formula to the not specification. Only 1 formula supported, so a second overwrites the first
	 * @param formula
	 */
	public void addFormula(BASE_FORMULA formula) {
		this.formula = formula;
	}

	/**
	 * returns the currently saved formula
	 * @return
	 */
	public BASE_FORMULA getFormula() {
		return formula;
	}


	@Override
	public String toString() {
		if (formula == null) return "";
		return "not("+formula+")";
	}

	@Override
	public String toStringLTLf(Map<SimplePredicate,String> map) {
		if (formula==null) return "";
		return "!("+formula.toStringLTLf(map)+")";
	}

	@Override
	public List<SimplePredicate> getPredicates() {
		return formula.getPredicates();
	}
}

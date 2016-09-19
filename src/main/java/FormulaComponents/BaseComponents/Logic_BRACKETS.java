package FormulaComponents.BaseComponents;

import FormulaComponents.SimplePredicate;

import java.util.List;
import java.util.Map;

/**
 * Created by loren on 05/08/2016.
 */
public class Logic_BRACKETS extends BASE_FORMULA {
	BASE_FORMULA formula = null;
	public void addFormula(BASE_FORMULA formula) {
		this.formula = formula;
	}

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

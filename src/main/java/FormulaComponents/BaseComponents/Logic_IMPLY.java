package FormulaComponents.BaseComponents;

import FormulaComponents.SimplePredicate;

import java.util.List;
import java.util.Map;

/**
 * Created by loren on 05/08/2016.
 */
public class Logic_IMPLY extends BASE_FORMULA {
	public int NUMBER_FORMULA_1 = 0;
	public int NUMBER_FORMULA_2 = 1;
	BASE_FORMULA formula1 = null;
	BASE_FORMULA formula2 = null;
	int iterator = 0;

	public void addFormula(BASE_FORMULA formula) {
		if (iterator == 0) formula1 = formula;
		else if (iterator ==1)formula2 = formula;
		iterator++;
		if (iterator >1) iterator = 0;
	}

	public BASE_FORMULA getFormula(int which){
		if (which == NUMBER_FORMULA_1) return formula1;
		else if (which == NUMBER_FORMULA_2) return formula2;
		else return null;
	}

	@Override
	public String toString() {
		if (formula1==null || formula1==null) return "";
		return "imply ("+formula1+") ("+formula2+")";
	}

	@Override
	public String toStringLTLf(Map<SimplePredicate,String> map) {
		String br = "";String br2 = "";String br3="";String br4="";
		if (formula1 instanceof Logic_PREDICATE) {br = "(";br2 = ")";}
		if (formula2 instanceof Logic_PREDICATE) {br3 = "(";br4 = ")";}
		if (formula1==null || formula2 == null) return "";
		return br+formula1.toStringLTLf(map)+br2+" -> "+br3+formula2.toStringLTLf(map)+br4;
	}

	@Override
	public List<SimplePredicate> getPredicates() {
		List<SimplePredicate> predicates = formula1.getPredicates();
		predicates.addAll(formula2.getPredicates());
		return predicates;
	}
}

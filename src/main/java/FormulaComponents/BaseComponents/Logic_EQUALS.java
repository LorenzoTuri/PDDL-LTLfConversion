package FormulaComponents.BaseComponents;

import FormulaComponents.SimplePredicate;

import java.util.List;
import java.util.Map;

/**
 * Used to express a EQUALS formula specification
 */
public class Logic_EQUALS extends BASE_FORMULA {
	public int NUMBER_FORMULA_1 = 0;
	public int NUMBER_FORMULA_2 = 1;
	BASE_FORMULA formula1 = null;
	BASE_FORMULA formula2 = null;
	int iterator = 0;

	/**
	 * adds a formula to the specification. Currently supports only 2 formula. The collection cycles so if a third formula
	 * Should be inserted the first is lost, if a fouth is inserted the second is lost and so on...
	 * @param formula
	 */
	public void addFormula(BASE_FORMULA formula) {
		if (iterator == 0) formula1 = formula;
		else if (iterator ==1)formula2 = formula;
		iterator++;
		if (iterator >1) iterator = 0;
	}

	/**
	 * returns a formula between the 2 saved formulas, or null if the iterator is different from
	 *      NUMBER_FORMULA_1 or NUMBER_FORMULA_2
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
		return " = ("+formula1.toString()+") ("+formula2.toString()+")";
	}

	@Override
	public String toStringLTLf(Map<SimplePredicate,String> map) {
		String br = "";String br2 = "";String br3=""; String br4="";
		if (formula1 instanceof Logic_PREDICATE) {br = "(";br2 = ")";}
		if (formula2 instanceof Logic_PREDICATE) {br3= "(";br4 = ")";}
		if (formula1==null || formula2==null) return "";
		String result = "("+br+formula1.toStringLTLf(map)+br2+" && "+br3+formula2.toStringLTLf(map)+br4+")";
		result+= "&& (!("+formula1.toStringLTLf(map)+") && !("+formula2.toStringLTLf(map)+"))";
		return result;
	}

	@Override
	public List<SimplePredicate> getPredicates() {
		List<SimplePredicate> predicates = formula1.getPredicates();
		predicates.addAll(formula2.getPredicates());
		return predicates;
	}
}

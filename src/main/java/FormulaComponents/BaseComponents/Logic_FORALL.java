package FormulaComponents.BaseComponents;

import FormulaComponents.SimplePredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by loren on 05/08/2016.
 */
public class Logic_FORALL extends BASE_FORMULA {
	List<String> simplenames = new ArrayList<String>();
	BASE_FORMULA formula;

	/**
	 * Adds a formula to the forall...currently supports only a single formula, so if a second formula should
	 * be inserted the first is lost
	 * @param formula
	 */
	public void addFormula(BASE_FORMULA formula) { this.formula = formula; }

	/**
	 * adds a Simplename (should be transformed in predicates in next updates) to the forall
	 * @param formula
	 */
	public void addSimplename(String formula){ simplenames.add(formula); }

	/**
	 * returns the currently saved formula
	 * @return
	 */
	public BASE_FORMULA getFormula() {
		return formula;
	}

	/**
	 * returns a Simplename(should be updated to predicate). Generate exception if outOfBounds
	 * @param iterator index of the simplename
	 * @return
	 */
	public String getSimplename(int iterator){ return simplenames.get(iterator); }

	@Override
	public String toString() {
		String result = "forall (";
		for (int i=0;i<simplenames.size();i++)result+=simplenames.get(i).toString();
		if (formula == null) return ""; else result+=")("+formula.toString()+")";
		return result;
	}

	@Override
	public String toStringLTLf(Map<SimplePredicate,String> map) {
		String result = "";
		if (formula != null) {
			result+="( "+simplenames.get(0)+" -> "+formula.toStringLTLf(map)+" )";
			for (int i = 1; i < simplenames.size(); i++)
				result += "&& (" + simplenames.get(i) + " -> " + formula.toStringLTLf(map) + ")";
		}else{
			result+="( "+simplenames.get(0)+" -> true )";
			for (int i = 1; i < simplenames.size(); i++)
				result += "&& (" + simplenames.get(i) + " -> true)";

		}
		return result;
	}

	@Override
	public List<SimplePredicate> getPredicates() {
		return formula.getPredicates();
	}
}

package FormulaComponents.BaseComponents;

import FormulaComponents.SimplePredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by loren on 05/08/2016.
 */
public class Logic_OR extends BASE_FORMULA {
	List<BASE_FORMULA> formulas = new ArrayList<BASE_FORMULA>();

	public void addFormula(BASE_FORMULA formula) {
		formulas.add(formula);
	}

	public BASE_FORMULA getFormula(int iterator){return formulas.get(iterator);}

	@Override
	public String toString(){
		String result = "or ";
		for (int i=0;i<formulas.size();i++) result+="("+formulas.get(i).toString()+")";
		return result;
	}

	@Override
	public String toStringLTLf(Map<SimplePredicate,String> map) {
		String result = "("+formulas.get(0).toStringLTLf(map)+")";
		for (int i=1;i<formulas.size();i++) result+=" || ("+formulas.get(i).toStringLTLf(map)+")";
		return result;
	}

	@Override
	public List<SimplePredicate> getPredicates() {
		ArrayList<SimplePredicate> predicates = new ArrayList<SimplePredicate>();
		for (BASE_FORMULA formula:formulas) predicates.addAll(formula.getPredicates());
		return predicates;
	}
}

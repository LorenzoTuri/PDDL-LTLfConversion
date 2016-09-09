package FormulaComponents.BaseComponents;

import FormulaComponents.SimplePredicate;

import java.util.ArrayList;
import java.util.List;

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
	public String toStringLTLf() {
		String result = "("+formulas.get(0).toStringLTLf()+")";
		for (int i=1;i<formulas.size();i++) result+=" || ("+formulas.get(i).toStringLTLf()+")";
		return result;
	}

	@Override
	public List<SimplePredicate> getPredicates() {
		ArrayList<SimplePredicate> predicates = new ArrayList<SimplePredicate>();
		for (BASE_FORMULA formula:formulas) predicates.addAll(formula.getPredicates());
		return predicates;
	}
}

package FormulaComponents.BaseComponents;

import FormulaComponents.SimplePredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by loren on 05/08/2016.
 */
public class Logic_AND extends BASE_FORMULA {
	List<BASE_FORMULA> formulas = new ArrayList<BASE_FORMULA>();

	public void addFormula(BASE_FORMULA formula) {
		formulas.add(formula);
	}

	public BASE_FORMULA getFormula(int iterator){return formulas.get(iterator);}

	@Override
	public String toString() {
		String result = "and ";
		for (int i=0;i<formulas.size();i++) result+="("+formulas.get(i)+")";
		return result;
	}

	@Override
	public String toStringLTLf(Map<SimplePredicate,String> map) {
		String br = "";String br2 = "";
		if (formulas.get(0) instanceof Logic_PREDICATE) {br = "(";br2 = ")";}
		String result = br+formulas.get(0).toStringLTLf(map)+br2;
		for (int i=1;i<formulas.size();i++) {
			br="";br2="";
			if (formulas.get(i) instanceof Logic_PREDICATE) {br = "(";br2 = ")";}
			result+= " && "+br+formulas.get(i).toStringLTLf(map)+br2;
		}
		return result;
	}

	@Override
	public List<SimplePredicate> getPredicates() {
		List<SimplePredicate> predicates = new ArrayList<SimplePredicate>();
		for(BASE_FORMULA formula:formulas){
			predicates.addAll(formula.getPredicates());
		}
		return predicates;
	}
}

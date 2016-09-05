package PDDLFormulaContainer;

import FormulaComponents.SimpleFormula;
import FormulaComponents.SimplePredicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 18/07/2016.
 */
public class PredicatesFormula extends SimpleFormula {
	List<SimplePredicate> predicates = new ArrayList<SimplePredicate>();

	@Override
	public String toString() {
		String result = "Predicates:\n";
		for (SimplePredicate p:predicates) result += "\t"+p.toString()+"\n";
		return result;
	}

	public void addPredicate(SimplePredicate pre){
		predicates.add(pre);
	}
}

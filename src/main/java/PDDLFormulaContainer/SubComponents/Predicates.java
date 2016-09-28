package PDDLFormulaContainer.SubComponents;

import FormulaComponents.SimpleFormula;
import FormulaComponents.SimplePredicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to Store the predicates of a PDDLDomain specification
 */
public class Predicates extends SimpleFormula {
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

	public List<SimplePredicate> getPredicates(){return predicates;}
}

package FormulaComponents.BaseComponents;

import FormulaComponents.SimpleFormula;
import FormulaComponents.SimplePredicate;

import java.util.List;
import java.util.Map;

/**
 * Base component of a PDDL/LTLf Formula
 */
public abstract class BASE_FORMULA extends SimpleFormula{
	public abstract String toString();

	/**
	 * Function used to retrieve a LTLf specification, translating to string the PDDL predicates
	 * @param map map from predicate to string ( -> proposition identifying the predicate )
	 * @return LTLf specification as String
	 */
	public abstract String toStringLTLf(Map<SimplePredicate, String> map);

	/**
	 * Function used to retrieve a List containing the existent PDDL predicates in this formula
	 * @return
	 */
	public abstract List<SimplePredicate> getPredicates();
}

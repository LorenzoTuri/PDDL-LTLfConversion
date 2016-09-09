package FormulaComponents.BaseComponents;

import FormulaComponents.SimpleFormula;
import FormulaComponents.SimplePredicate;

import java.util.List;

/**
 * Created by loren on 05/08/2016.
 */
public abstract class BASE_FORMULA extends SimpleFormula{
	public abstract String toString();
	public abstract String toStringLTLf();
	public abstract List<SimplePredicate> getPredicates();
}

package Exceptions;

import FormulaComponents.BaseComponents.Logic_PREDICATE;
import FormulaComponents.SimplePredicate;

/**
 * Created by loren on 06/09/2016.
 */
public class NonExistentPredicateException extends Exception {
	SimplePredicate predicate;
	public NonExistentPredicateException(SimplePredicate predicate){
		this.predicate = predicate;
	}

	public NonExistentPredicateException(Logic_PREDICATE predicate) { this.predicate = predicate.getPredicates().get(0);}

	@Override
	public String getMessage() {
		return "This predicate doesn't exists: "+predicate;
	}

	@Override
	public String getLocalizedMessage() {
		return "This predicate doesn't exists: "+predicate;
	}
}

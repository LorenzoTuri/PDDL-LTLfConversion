package Exceptions;

import FormulaComponents.BaseComponents.Logic_PREDICATE;
import FormulaComponents.SimplePredicate;

/**
 * Exception used to tell the user that the predicate he's using doesn't exists
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

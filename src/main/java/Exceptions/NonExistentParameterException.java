package Exceptions;

import FormulaComponents.SimplePredicate;
import FormulaComponents.SimpleVariable;

import java.util.List;

/**
 * Exception used to tell the user he has used a parameter that doeasn't exist in the current scope of the PDDL action/predicate
 */
public class NonExistentParameterException extends Exception {
	SimplePredicate predicate;
	SimpleVariable var;
	List<SimpleVariable> list;
	public NonExistentParameterException(SimplePredicate predicate, SimpleVariable var, List<SimpleVariable> list){
		this.predicate = predicate;
		this.var = var;
		this.list = list;
	}

	@Override
	public String getLocalizedMessage() {
		return "Variable ["+var+"] of ["+predicate+"]doesn't exists in this moment. Current variable are: "+list;
	}
}

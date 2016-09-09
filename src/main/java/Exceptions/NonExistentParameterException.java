package Exceptions;

import FormulaComponents.SimplePredicate;
import FormulaComponents.SimpleVariable;

import java.util.List;

/**
 * Created by loren on 06/09/2016.
 */
public class NonExistentParameterException extends Exception {
	SimplePredicate predicate;
	SimpleVariable var;
	List<String> list;
	public NonExistentParameterException(SimplePredicate predicate, SimpleVariable var, List<String> list){
		this.predicate = predicate;
		this.var = var;
		this.list = list;
	}

	@Override
	public String getLocalizedMessage() {
		return "Variable ["+var+"] of ["+predicate+"]doesn't exists in this moment. Current variable are: "+list;
	}
}

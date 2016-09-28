package FormulaComponents;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to express a PDDL predicate.
 * Contains his name and list of variables, eventually typed
 */
public class SimplePredicate {
	public String name;
	public List<SimpleVariable> variables = new ArrayList<SimpleVariable>();

	@Override
	public String toString() {
		return name+" "+variables.toString();
	}

	/**
	 * Function used to get the number of paraters
	 * @return counter
	 */
	public int getVariableCount(){return variables.size();}

	/**
	 * Function used to get the predicate's name
	 * @return name
	 */
	public String getName(){return name;}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SimplePredicate){
			return (this.toString().equals(obj.toString()));
		}
		return false;
	}
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}

package FormulaComponents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class used to express a PDDL Action. As from PDDL specification this contains action name, parameters,
 * precondition and effects.
 */
public class SimpleAction {
	String name;
	List<SimpleVariable> parameters = new ArrayList<SimpleVariable>();
	Formula preconditions;
	Formula effects;

	@Override
	public String toString() {
		return "Action name: "+name+"\n"+
				"\tParams: "+parameters.toString()+"\n"+
				"\tPrecondition: "+preconditions+"\n"+
				"\tEffects: "+effects+"\n";
	}

	/**
	 * Function used to set the action's name
	 * @param name
	 */
	public void setName(String name){this.name = name.replace("//s+","");}

	/**
	 * Function used to add a parameter to the list
	 * @param variable
	 */
	public void addParameter(SimpleVariable variable){parameters.add(variable);}

	/**
	 * Function used to ad a collection of variable to the list
	 * @param variable
	 */
	public void addAllParameter(Collection<SimpleVariable> variable){
		parameters.addAll(variable);
	}

	/**
	 * Function used to set the the action's preconditions
	 * @param preconditions
	 */
	public void setPreconditions(Formula preconditions){this.preconditions = preconditions;}

	/**
	 * Function used to set the action's effects
	 * @param effects
	 */
	public void setEffects(Formula effects){this.effects = effects;}

	/**
	 * Function that return the action's name
	 * @return name
	 */
	public String getName() {return name;}

	/**
	 * Function used to get the action's parameters
	 * @return parameters
	 */
	public Collection<SimpleVariable> getParameters(){return parameters;}

	/**
	 * Function used to get the action's preconditions
	 * @return preconditions formula
	 */
	public Formula getPreconditions(){return preconditions;}

	/**
	 * Function used to get the action's effects
	 * @return effects formula
	 */
	public Formula getEffects(){return effects;}
}

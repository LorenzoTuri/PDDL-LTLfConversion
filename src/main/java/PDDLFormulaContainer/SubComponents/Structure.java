package PDDLFormulaContainer.SubComponents;

import FormulaComponents.SimpleAction;
import FormulaComponents.SimpleFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to store the main part of a PDDL Domain specification. Most of this is composed from actions
 */
public class Structure extends SimpleFormula {
	List<SimpleAction> actions = new ArrayList<SimpleAction>();

	@Override
	public String toString() {
		String result = "";
		for (SimpleAction s:actions) result += s.toString()+"\n";
		return result;
	}

	public void addAction(SimpleAction s){actions.add(s);}

	public List<SimpleAction> getActions(){return actions;}
}

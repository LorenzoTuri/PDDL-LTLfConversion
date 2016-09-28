package PDDLFormulaContainer.SubComponents;

import FormulaComponents.SimpleVariable;
import FormulaComponents.SimpleFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to store the constants of a PDDL domain formula
 */
public class Constants extends SimpleFormula {
	List<SimpleVariable> constants = new ArrayList<SimpleVariable>();

	public void addConstant(SimpleVariable s){constants.add(s);}

	@Override
	public String toString() {
		return "Constants:\n"+constants.toString();
	}

	public List<SimpleVariable> getConstants(){return constants;}
}

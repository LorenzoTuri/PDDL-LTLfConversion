package PDDLFormulaContainer;

import FormulaComponents.SimpleVariable;
import FormulaComponents.SimpleFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 18/07/2016.
 */
public class ConstantsFormula extends SimpleFormula {
	List<SimpleVariable> constants = new ArrayList<SimpleVariable>();

	public void addConstant(SimpleVariable s){constants.add(s);}

	@Override
	public String toString() {
		return "Constants:\n"+constants.toString();
	}
}
package PDDLFormulaContainer;

import FormulaComponents.SimpleAction;
import FormulaComponents.SimpleFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 30/07/2016.
 */
public class StructureFormula extends SimpleFormula {
	List<SimpleAction> actions = new ArrayList<SimpleAction>();

	@Override
	public String toString() {
		String result = "";
		for (SimpleAction s:actions) result += s.toString()+"\n";
		return result;
	}

	public void addAction(SimpleAction s){actions.add(s);}
}

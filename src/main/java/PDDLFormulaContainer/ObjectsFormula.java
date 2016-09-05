package PDDLFormulaContainer;

import FormulaComponents.SimpleFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 05/09/2016.
 */
public class ObjectsFormula extends SimpleFormula {
	List<String> list;
	public ObjectsFormula(){list = new ArrayList<String>();}
	public void addObject(String obj){list.add(obj);}

	@Override
	public String toString() {
		return "Objects:\n\t"+list.toString();
	}
}

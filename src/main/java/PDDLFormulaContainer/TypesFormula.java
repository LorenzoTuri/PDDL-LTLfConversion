package PDDLFormulaContainer;

import FormulaComponents.SimpleFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 18/07/2016.
 */
public class TypesFormula extends SimpleFormula {
	List<String> types = new ArrayList<String>();

	@Override
	public String toString() {
		return "Types:\n\t"+types.toString();
	}

	public void addType(String type){
		types.add(type);
	}
}
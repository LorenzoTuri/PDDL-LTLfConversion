package PDDLFormulaContainer.SubComponents;

import FormulaComponents.SimpleFormula;
import FormulaComponents.SimpleVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to store the existent objects in a PDDLProblem specification
 */
public class Objects extends SimpleFormula {
	List<SimpleVariable> objects;

	public Objects(){objects = new ArrayList<SimpleVariable>();}

	public void addObject(SimpleVariable obj){
		objects.add(obj);
	}

	@Override
	public String toString() {
		return "Objects:\n\t"+objects.toString();
	}

	public List<SimpleVariable> getObjects(){return objects;}
}

package PDDLFormulaContainer.SubComponents;

import FormulaComponents.SimpleFormula;
import FormulaComponents.SimpleVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 05/09/2016.
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

package PDDLFormulaContainer.SubComponents;

import FormulaComponents.SimpleFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 05/09/2016.
 */
public class Objects extends SimpleFormula {
	List<String> list;
	public Objects(){list = new ArrayList<String>();}
	public void addObject(String obj){list.add(obj);}

	@Override
	public String toString() {
		return "Objects:\n\t"+list.toString();
	}

	public List<String> getObjects(){return list;}
}

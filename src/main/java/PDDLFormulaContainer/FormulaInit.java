package PDDLFormulaContainer;

import FormulaComponents.SimpleFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 05/09/2016.
 */
public class FormulaInit extends SimpleFormula {
	List<String> init;
	public FormulaInit(){init = new ArrayList<String>();}
	public void addInitialization(String element){init.add(element);}

	@Override
	public String toString() {
		return "Initialization: "+init;
	}
}

package PDDLFormulaContainer.SubComponents;

import FormulaComponents.BaseComponents.Logic_PREDICATE;
import FormulaComponents.SimpleFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 05/09/2016.
 */
public class Initialization extends SimpleFormula {
	List<Logic_PREDICATE> init;
	public Initialization(){init = new ArrayList<Logic_PREDICATE>();}
	public void addInitialization(Logic_PREDICATE element){init.add(element);}

	@Override
	public String toString() {
		return "Initialization: "+init;
	}

	public List<Logic_PREDICATE> getInit(){return init;}
}

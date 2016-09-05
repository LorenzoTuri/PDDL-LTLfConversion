package PDDLFormulaContainer;

import FormulaComponents.SimpleFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 18/07/2016.
 */
public class RequirementsFormula extends SimpleFormula {
	List<String> requirements;
	public RequirementsFormula(){requirements = new ArrayList<String>();}

	@Override
	public String toString() {
		return "Requirements:\n\t"+requirements.toString();
	}
	public void addRequirement(String requirement){requirements.add(requirement);}
}

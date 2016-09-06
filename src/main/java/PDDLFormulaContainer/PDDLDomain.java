package PDDLFormulaContainer;

import FormulaComponents.SimpleFormula;
import PDDLFormulaContainer.SubComponents.*;

/**
 * Created by loren on 30/07/2016.
 */
public class PDDLDomain extends SimpleFormula {
	Domain domain;
	Types types;
	Constants constants;
	Predicates predicates;
	Requirements requirements;
	Structure structure;

	@Override
	public String toString() {
		String result = domain.toString()+"\n";
		if (types!=null) result += types.toString()+"\n";
		if (constants!=null) result += constants.toString()+"\n";
		if (predicates!=null) result += predicates.toString()+"\n";
		if (requirements!=null) result += requirements.toString()+"\n";
		result += structure.toString();
		return result;
	}

	public void setDomain(Domain domain){this.domain = domain;}
	public void setConstants(Constants constants){this.constants=constants;}
	public void setPredicates(Predicates predicates){this.predicates=predicates;}
	public void setRequirements(Requirements requirements){this.requirements=requirements;}
	public void setStructure(Structure structure){this.structure=structure;}
	public void setTypes(Types types){this.types = types;}
}

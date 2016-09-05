package PDDLFormulaContainer;

import FormulaComponents.SimpleFormula;

/**
 * Created by loren on 30/07/2016.
 */
public class PDDLDomain extends SimpleFormula {
	DefineFormula define;
	TypesFormula types;
	ConstantsFormula constants;
	PredicatesFormula predicates;
	RequirementsFormula requirements;
	StructureFormula structure;

	@Override
	public String toString() {
		String result = define.toString()+"\n";
		if (types!=null) result += types.toString()+"\n";
		if (constants!=null) result += constants.toString()+"\n";
		if (predicates!=null) result += predicates.toString()+"\n";
		if (requirements!=null) result += requirements.toString()+"\n";
		result += structure.toString();
		return result;
	}

	public void setDefine(DefineFormula define){this.define = define;}
	public void setConstants(ConstantsFormula constants){this.constants=constants;}
	public void setPredicates(PredicatesFormula predicates){this.predicates=predicates;}
	public void setRequirements(RequirementsFormula requirements){this.requirements=requirements;}
	public void setStructure(StructureFormula structure){this.structure=structure;}
	public void setTypes(TypesFormula types){this.types = types;}
}

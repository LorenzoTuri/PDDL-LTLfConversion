package PDDLFormulaContainer;

import FormulaComponents.SimpleFormula;

/**
 * Created by loren on 02/08/2016.
 */
public class PDDLProblem extends SimpleFormula{
	Problem problem = null;
	DefineFormula domain = null;
	RequirementsFormula requirements = null;
	ObjectsFormula objects = null;
	FormulaInit init = null;
	GoalFormula goal = null;

	public void setProblem(Problem p){
		problem = p;
	}
	public void setDomain(DefineFormula d){
		domain = d;
	}
	public void setRequirements(RequirementsFormula r){
		requirements = r;
	}
	public void setObjects(ObjectsFormula o){
		objects = o;
	}
	public void setInit(FormulaInit i){
		init = i;
	}
	public void setGoal(GoalFormula g){
		goal = g;
	}
}

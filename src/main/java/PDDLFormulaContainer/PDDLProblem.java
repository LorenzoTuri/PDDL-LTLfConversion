package PDDLFormulaContainer;

import FormulaComponents.SimpleFormula;
import PDDLFormulaContainer.SubComponents.*;

/**
 * Class used to store the complete PDDLProblem file. Should be used to generate a PDDLWorldDescritpion
 */
public class PDDLProblem extends SimpleFormula{
	Problem problem = null;
	Domain domain = null;
	Requirements requirements = null;
	Objects objects = null;
	Initialization init = null;
	Goal goal = null;

	public void setProblem(Problem p){
		problem = p;
	}
	public void setDomain(Domain d){
		domain = d;
	}
	public void setRequirements(Requirements r){
		requirements = r;
	}
	public void setObjects(Objects o){
		objects = o;
	}
	public void setInit(Initialization i){
		init = i;
	}
	public void setGoal(Goal g){
		goal = g;
	}

	@Override
	public String toString() {
		String result = "";
		if (problem!=null) result+= problem+"\n";
		if (domain!=null) result+= domain+"\n";
		if (requirements!=null) result+=requirements+"\n";
		if (objects!=null) result+=objects+"\n";
		if (init!=null) result+=init+"\n";
		if (goal!=null) result+=goal;
		return result;
	}
}

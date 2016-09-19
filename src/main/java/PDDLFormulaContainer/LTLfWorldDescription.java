package PDDLFormulaContainer;

import net.sf.tweety.logics.pl.syntax.PropositionalSignature;

/**
 * Created by loren on 14/09/2016.
 */
public abstract class LTLfWorldDescription {
	public abstract String getInitFormula();
	public abstract String getWorldRulesFormula();
	public abstract String getAgentRuleFormula();
	public abstract String getActionsFormula();
	public abstract String getGoalFormula();

	public abstract PropositionalSignature getAgentSet();
	public abstract PropositionalSignature getEnvironmentSet();
	public abstract PropositionalSignature getHiddenSet();

	@Override
	public String toString() {
		/*
		( WORLDRULES ) -> (
			( INITRULES ) -> X(
				( G( WORLDRULES && AGENTRULES ) && G(ACTIONSFORMULA) ) -> F( GOALFORMULA )
			)
		)
		 */
		String result = "";
		result+= "( "+getWorldRulesFormula()+" ) -> ( ";
		result+= "\n\t( "+getInitFormula()+") -> X ( ";
		result+= "\n\t\tG ( "+getWorldRulesFormula()+" && "+getAgentRuleFormula()+" ) && G ( "+getActionsFormula()+" ) ";
		result+= "\n\t\t) -> F ("+getGoalFormula()+" ) ";
		result+= "\n\t) ";
		result+= "\n)";
		return result;
	}
}

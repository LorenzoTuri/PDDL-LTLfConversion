package PDDLFormulaContainer;

import FormulaComponents.BaseComponents.PDDLFormula;
import FormulaComponents.SimpleFormula;

/**
 * Created by loren on 30/07/2016.
 */
public class Formula extends SimpleFormula {
	PDDLFormula formula = null;
	public void setFormula(PDDLFormula formula){this.formula = formula;}
	public PDDLFormula getFormula(){return formula;}

	@Override
	public String toString() {
		if (formula == null) return "";
		return formula.toString();
	}
	public String toStringLTLf(){
		if (formula == null) return "";
		return formula.toStringLTLf();
	}
}

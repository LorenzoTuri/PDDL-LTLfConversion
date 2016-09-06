package FormulaComponents;

import FormulaComponents.BaseComponents.BASE_FORMULA;

/**
 * Created by loren on 30/07/2016.
 */
public class Formula extends SimpleFormula {
	BASE_FORMULA formula = null;
	public void setFormula(BASE_FORMULA formula){this.formula = formula;}
	public BASE_FORMULA getFormula(){return formula;}

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

package FormulaComponents;

import FormulaComponents.BaseComponents.BASE_FORMULA;

import java.util.List;

/**
 * Created by loren on 30/07/2016.
 */
public class Formula extends SimpleFormula {
	BASE_FORMULA formula = null;
	public void setFormula(BASE_FORMULA formula){this.formula = formula;}
	public BASE_FORMULA getFormula(){return formula;}

	public List<SimplePredicate> getPredicates(){
		return formula.getPredicates();
	}
	@Override
	public String toString() {
		if (formula == null) return "";
		return formula.toString();
	}
}

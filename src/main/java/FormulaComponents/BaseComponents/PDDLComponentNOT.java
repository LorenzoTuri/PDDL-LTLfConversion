package FormulaComponents.BaseComponents;

/**
 * Created by loren on 05/08/2016.
 */
public class PDDLComponentNOT extends PDDLFormula {
	PDDLFormula formula = null;

	public void addFormula(PDDLFormula formula) {
		this.formula = formula;
	}

	public PDDLFormula getFormula() {
		return formula;
	}


	@Override
	public String toString() {
		if (formula == null) return "";
		return "not("+formula+")";
	}

	@Override
	public String getFormulaType() {
		return "not";
	}

	@Override
	public String toStringLTLf() {
		if (formula==null) return "";
		return "!("+formula.toStringLTLf()+")";
	}
}

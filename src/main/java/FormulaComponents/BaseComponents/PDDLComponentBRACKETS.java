package FormulaComponents.BaseComponents;

/**
 * Created by loren on 05/08/2016.
 */
public class PDDLComponentBRACKETS extends PDDLFormula{
	PDDLFormula formula = null;
	public void addFormula(PDDLFormula formula) {
		this.formula = formula;
	}

	public PDDLFormula getFormula() {
		return formula;
	}

	@Override
	public String toString() {
		if (formula==null) return "( )";
		return "( "+formula.toString()+" )";
	}

	@Override
	public String getFormulaType() {
		return "( )";
	}

	@Override
	public String toStringLTLf() {
		if (formula==null) return "( )";
		return "( "+formula.toStringLTLf()+" )";
	}
}

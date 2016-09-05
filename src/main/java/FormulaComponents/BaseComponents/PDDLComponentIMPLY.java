package FormulaComponents.BaseComponents;

/**
 * Created by loren on 05/08/2016.
 */
public class PDDLComponentIMPLY extends PDDLFormula{
	public int NUMBER_FORMULA_1 = 0;
	public int NUMBER_FORMULA_2 = 1;
	PDDLFormula formula1 = null;
	PDDLFormula formula2 = null;
	int iterator = 0;

	public void addFormula(PDDLFormula formula) {
		if (iterator == 0) formula1 = formula;
		else if (iterator ==1)formula2 = formula;
		iterator++;
		if (iterator >1) iterator = 0;
	}

	public PDDLFormula getFormula(int which){
		if (which == NUMBER_FORMULA_1) return formula1;
		else if (which == NUMBER_FORMULA_2) return formula2;
		else return null;
	}

	@Override
	public String toString() {
		if (formula1==null || formula1==null) return "";
		return "imply ("+formula1+") ("+formula2+")";
	}

	@Override
	public String getFormulaType() {
		return "imply";
	}

	@Override
	public String toStringLTLf() {
		if (formula1==null || formula2 == null) return "";
		return "("+formula1.toStringLTLf()+") -> ("+formula2.toStringLTLf()+")";
	}
}

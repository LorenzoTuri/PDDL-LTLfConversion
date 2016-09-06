package FormulaComponents.BaseComponents;

/**
 * Created by loren on 05/08/2016.
 */
public class Logic_IMPLY extends BASE_FORMULA {
	public int NUMBER_FORMULA_1 = 0;
	public int NUMBER_FORMULA_2 = 1;
	BASE_FORMULA formula1 = null;
	BASE_FORMULA formula2 = null;
	int iterator = 0;

	public void addFormula(BASE_FORMULA formula) {
		if (iterator == 0) formula1 = formula;
		else if (iterator ==1)formula2 = formula;
		iterator++;
		if (iterator >1) iterator = 0;
	}

	public BASE_FORMULA getFormula(int which){
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
	public String toStringLTLf() {
		if (formula1==null || formula2 == null) return "";
		return "("+formula1.toStringLTLf()+") -> ("+formula2.toStringLTLf()+")";
	}
}
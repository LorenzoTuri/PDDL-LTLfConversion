package FormulaComponents.BaseComponents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 05/08/2016.
 */
public class PDDLComponentOR extends PDDLFormula {
	List<PDDLFormula> formulas = new ArrayList<PDDLFormula>();

	public void addFormula(PDDLFormula formula) {
		formulas.add(formula);
	}

	public PDDLFormula getFormula(int iterator){return formulas.get(iterator);}

	@Override
	public String toString(){
		String result = "or ";
		for (int i=0;i<formulas.size();i++) result+="("+formulas.get(i).toString()+")";
		return result;
	}

	@Override
	public String toStringLTLf() {
		String result = "("+formulas.get(0).toStringLTLf()+")";
		for (int i=1;i<formulas.size();i++) result+=" || ("+formulas.get(i).toStringLTLf()+")";
		return result;
	}

	@Override
	public String getFormulaType() {
		return "or";
	}
}

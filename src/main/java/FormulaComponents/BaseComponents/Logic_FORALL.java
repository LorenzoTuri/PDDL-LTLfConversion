package FormulaComponents.BaseComponents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 05/08/2016.
 */
public class Logic_FORALL extends BASE_FORMULA {
	List<String> simplenames = new ArrayList<String>();
	BASE_FORMULA formula;

	public void addFormula(BASE_FORMULA formula) { this.formula = formula; }

	public void addSimplename(String formula){ simplenames.add(formula); }

	public BASE_FORMULA getFormula() {
		return formula;
	}

	public String getSimplename(int iterator){ return simplenames.get(iterator); }

	public BASE_FORMULA getFormula(int iterator){ return formula; }

	@Override
	public String toString() {
		String result = "forall (";
		for (int i=0;i<simplenames.size();i++)result+=simplenames.get(i).toString();
		if (formula == null) return ""; else result+=")("+formula.toString()+")";
		return result;
	}

	@Override
	public String toStringLTLf() {
		String result = "";
		if (formula == null) {
			result+="( "+simplenames.get(0)+" -> "+formula.toStringLTLf()+" )";
			for (int i = 1; i < simplenames.size(); i++)
				result += "&& (" + simplenames.get(i) + " -> " + formula.toStringLTLf() + ")";
		}else{
			result+="( "+simplenames.get(0)+" -> true )";
			for (int i = 1; i < simplenames.size(); i++)
				result += "&& (" + simplenames.get(i) + " -> true)";

		}
		return result;
	}
}

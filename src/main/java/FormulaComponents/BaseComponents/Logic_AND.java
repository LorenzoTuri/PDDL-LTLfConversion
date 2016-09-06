package FormulaComponents.BaseComponents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 05/08/2016.
 */
public class Logic_AND extends BASE_FORMULA {
	List<BASE_FORMULA> formulas = new ArrayList<BASE_FORMULA>();

	public void addFormula(BASE_FORMULA formula) {
		formulas.add(formula);
	}

	public BASE_FORMULA getFormula(int iterator){return formulas.get(iterator);}

	@Override
	public String toString() {
		String result = "and ";
		for (int i=0;i<formulas.size();i++) result+="("+formulas.get(i)+")";
		return result;
	}

	@Override
	public String toStringLTLf() {
		String result = "("+formulas.get(0).toStringLTLf()+")";
		for (int i=1;i<formulas.size();i++) result+= " && ("+formulas.get(i).toStringLTLf()+")";
		return result;
	}
}

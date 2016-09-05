package FormulaComponents.BaseComponents;

import FormulaComponents.SimpleFormula;

/**
 * Created by loren on 05/08/2016.
 */
public abstract class PDDLFormula extends SimpleFormula{
	public abstract String toString();
	public abstract String getFormulaType();
	public abstract String toStringLTLf();
}

package FormulaComponents.BaseComponents;

/**
 * Created by loren on 05/08/2016.
 */
public class PDDLComponentNAME extends PDDLFormula {
	String name = "";

	public void setName(String name){this.name = name;}
	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getFormulaType() {
		return "name";
	}

	@Override
	public String toStringLTLf() {
		return name;
	}
}

package FormulaComponents.BaseComponents;

/**
 * Created by loren on 05/08/2016.
 */
public class Logic_NAME extends BASE_FORMULA {
	String name = "";

	public void setName(String name){this.name = name;}
	@Override
	public String toString() {
		return name;
	}

	@Override
	public String toStringLTLf() {
		return name;
	}
}

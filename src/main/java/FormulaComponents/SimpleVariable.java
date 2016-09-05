package FormulaComponents;

/**
 * Created by loren on 30/07/2016.
 */
public class SimpleVariable {
	String name;
	String type = "";
	public SimpleVariable(String name, String type){this.name = name; this.type = type;}

	@Override
	public String toString() {
		return name+" "+type;
	}
}

package FormulaComponents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 30/07/2016.
 */
public class SimplePredicate {
	public String name;
	public List<SimpleVariable> variables = new ArrayList<SimpleVariable>();

	@Override
	public String toString() {
		return name+" "+variables.toString();
	}

	public int getVariableCount(){return variables.size();}
	public String getName(){return name;}
}

package FormulaComponents.BaseComponents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 05/08/2016.
 */
public class Logic_PREDICATE extends BASE_FORMULA {
	String name = "";
	List<String> variables = new ArrayList<String>();

	public void setName(String name){this.name = name;}
	public void addVariable(String var){variables.add(var);}

	@Override
	public String toString() {
		String result = name;
		for (int i =0;i<variables.size();i++) result += " "+variables.get(i).toString();
		return result;
	}

	@Override
	public String toStringLTLf() {
		String result = "name_"+name+"_var_";
		for (int i=0;i<variables.size();i++) result+="_"+variables.get(i);
		return result;
	}
}

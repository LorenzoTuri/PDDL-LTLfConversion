package FormulaComponents;

import org.antlr.v4.runtime.misc.NotNull;

/**
 * Created by loren on 30/07/2016.
 */
public class SimpleVariable {
	String name;
	String type = "";
	public SimpleVariable(@NotNull String name, String type){
		this.name = name.replaceAll("//s+","");
		if (type!=null)this.type = type.replaceAll("//s+","");
	}

	@Override
	public String toString() {
		String result = name;
		if (!type.equals("")) result+=" "+type;
		return result;
	}

	public String getName(){return name;}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SimpleVariable) {
			if (((SimpleVariable) obj).getName().equals(this.getName())) return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}

package FormulaComponents.BaseComponents;

import FormulaComponents.SimplePredicate;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<SimplePredicate> getPredicates() {
		return new ArrayList<SimplePredicate>();
	}
}

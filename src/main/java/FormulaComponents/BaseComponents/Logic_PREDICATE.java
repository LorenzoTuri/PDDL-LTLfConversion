package FormulaComponents.BaseComponents;

import FormulaComponents.SimplePredicate;
import FormulaComponents.SimpleVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by loren on 05/08/2016.
 */
public class Logic_PREDICATE extends BASE_FORMULA {
	SimplePredicate predicate = new SimplePredicate();

	public void setName(String name){predicate.name = name;}
	public void addVariable(SimpleVariable variable){
		predicate.variables.add(variable);
	}

	@Override
	public String toString() {
		String result = predicate.name;
		for (int i =0;i<predicate.variables.size();i++) result += " "+predicate.variables.get(i).toString();
		return result;
	}

	@Override
	public String toStringLTLf(Map<SimplePredicate,String> map) {
		return map.get(predicate);
	}

	@Override
	public List<SimplePredicate> getPredicates() {
		ArrayList<SimplePredicate> predicates = new ArrayList<SimplePredicate>();
		predicates.add(predicate);
		return predicates;
	}
}

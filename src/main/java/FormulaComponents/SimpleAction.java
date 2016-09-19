package FormulaComponents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by loren on 30/07/2016.
 */
public class SimpleAction {
	String name;
	List<SimpleVariable> parameters = new ArrayList<SimpleVariable>();
	Formula preconditions;
	Formula effects;

	@Override
	public String toString() {
		return "Action name: "+name+"\n"+
				"\tParams: "+parameters.toString()+"\n"+
				"\tPrecondition: "+preconditions+"\n"+
				"\tEffects: "+effects+"\n";
	}

	public void setName(String name){this.name = name.replace("//s+","");}
	public void addParameter(SimpleVariable variable){parameters.add(variable);}
	public void addAllParameter(Collection<SimpleVariable> variable){
		parameters.addAll(variable);
	}
	public void setPreconditions(Formula preconditions){this.preconditions = preconditions;}
	public void setEffects(Formula effects){this.effects = effects;}
	public String getName() {return name;}

	public Collection<SimpleVariable> getParameters(){return parameters;}
	public Formula getPreconditions(){return preconditions;}
	public Formula getEffects(){return effects;}
}

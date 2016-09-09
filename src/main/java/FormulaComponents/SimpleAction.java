package FormulaComponents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by loren on 30/07/2016.
 */
public class SimpleAction {
	String name;
	List<String> parameters = new ArrayList<String>();
	Formula preconditions;
	Formula effects;

	@Override
	public String toString() {
		return "action name: "+name+"\n"+
				"\tparam: "+parameters.toString()+"\n"+
				"\tPDDL precondition: "+preconditions+"\n"+
				"\tLTLf precondition: "+preconditions.toStringLTLf()+"\n"+
				"\tPDDL effects: "+effects+"\n"+
				"\tLTLf effects: "+effects.toStringLTLf();
	}

	public void setName(String name){this.name = name.replace("//s+","");}
	public void addParameter(String variable){parameters.add(variable.replace("//s+",""));}
	public void addAllParameter(Collection<String> variable){
		for (String s:variable) {
			s = s.replace("//s+", "");
			parameters.add(s);
		}
	}
	public void setPreconditions(Formula preconditions){this.preconditions = preconditions;}
	public void setEffects(Formula effects){this.effects = effects;}

	public Collection<String> getParameters(){return parameters;}
	public Formula getPreconditions(){return preconditions;}
	public Formula getEffects(){return effects;}
}

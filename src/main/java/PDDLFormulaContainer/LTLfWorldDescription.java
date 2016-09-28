package PDDLFormulaContainer;

import net.sf.tweety.logics.pl.syntax.PropositionalSignature;

import java.util.List;

/**
 * Created by loren on 14/09/2016.
 */
public abstract class LTLfWorldDescription {
	public abstract InitRules getInitRules();
	public abstract WorldRules getWorldRules();
	public abstract AgentRules getAgentRule();
	public abstract ActionRules getActionsRules();
	public abstract GoalRules getGoalRules();

	public abstract void initialize();

	public abstract PropositionalSignature getAgentSet();
	public abstract PropositionalSignature getEnvironmentSet();
	public abstract PropositionalSignature getHiddenSet();

	public abstract PropositionalSignature getPropositionalSignature();

	@Override
	public String toString() {
		/*
		INIT -> (
			(
				(WORLDRULES && AGENTRULES) -> ACTIONS
			) -> F(GOAL)
		)
		INIT && X(
			(REGOLEMONDO && AGENTRULES) && ACTIONS
		)
		INIT = (A && B && ...)
		WORLDRULES = (G(A) && G(B) && ...)
		AGENTRULES = (G(A) && G(B) && ...)
		ACTIONS = (G(A->X(B)) && G(C->X(D)) && ...)
		GOAL = (A && B && ...
		 */
		String result = "";
		result+="Init:"+getInitRules()+"\n";
		result+="World: "+getWorldRules()+"\n";
		result+="Agent: "+getAgentRule()+"\n";
		result+="Goal: "+getGoalRules()+"\n";
		result+="Action: "+getActionsRules()+"\n";
		return result;
	}

	public class InitRules implements formulaInterface{
		String string = "";
		InitRules(String s){ string = s;}
		public String getLTLfFormula(){
			return string;
		}
		public String toString(){
			return getLTLfFormula();
		}
	}

	public class WorldRules implements formulaInterface{
		public int size() { return strings.size();}
		public String get(int i){ return strings.get(i);}
		List<String> strings;
		WorldRules(List<String> strings){ this.strings = strings;}
		public String getLTLfFormula(){
			if (strings.size() == 0) return "";
			String result = "("+strings.get(0)+")";
			for (int i=1;i<strings.size();i++) result+=" && ("+strings.get(i)+")";
			return result;
		}
		public String toString(){
			return getLTLfFormula();
		}
	}

	public class AgentRules implements formulaInterface{
		String string = "";
		public AgentRules(String s) {
			string = s;
		}
		public String getLTLfFormula(){
			return string;
		}
		public String toString(){
			return getLTLfFormula();
		}
	}

	public class ActionRules implements formulaInterface{
		public int size() { return strings.size();}
		public String get(int i){ return strings.get(i);}
		List<String> strings;
		ActionRules(List<String> strings){
			this.strings = strings;
		}
		public String getLTLfFormula(){
			if (strings.size() == 0) return "";
			String result = "("+strings.get(0)+")";
			for (int i=1;i<strings.size();i++) result+=" && ("+strings.get(i)+")";
			return result;
		}
		public String toString(){
			return getLTLfFormula();
		}
	}

	public class GoalRules implements formulaInterface{
		String string;
		public GoalRules(String s){
			this.string = s;
		}
		public String getLTLfFormula(){
			return string;
		}
		public String toString(){
			return getLTLfFormula();
		}
	}

	private abstract interface formulaInterface{
		public String getLTLfFormula();
	}
}

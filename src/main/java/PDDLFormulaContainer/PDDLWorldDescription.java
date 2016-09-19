package PDDLFormulaContainer;

import Exceptions.NonExistentParameterException;
import Exceptions.NonExistentPredicateException;
import Exceptions.RequirementException;
import Exceptions.WrongDomainException;
import FormulaComponents.BaseComponents.BASE_FORMULA;
import FormulaComponents.BaseComponents.Logic_PREDICATE;
import FormulaComponents.SimpleAction;
import FormulaComponents.SimplePredicate;
import FormulaComponents.SimpleVariable;
import net.sf.tweety.logics.pl.syntax.Proposition;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;

import java.util.*;

/**
 * Created by loren on 06/09/2016.
 */
public class PDDLWorldDescription {
	PDDLDomain domain = null;
	PDDLProblem problem = null;

	List<String> domainRequirements = null;
	List<String> problemRequirements = null;

	List<String> existentTypes = null;
	List<SimpleVariable> constants = null;
	List<SimplePredicate> predicates = null;
	List<SimpleAction> actions = null;

	List<SimpleVariable> objects = null;
	List<SimplePredicate> initialization = null;
	BASE_FORMULA goal = null;

	Map<SimplePredicate,String> newPredicates = new HashMap<SimplePredicate, String>();

	public PDDLWorldDescription(PDDLDomain domain, PDDLProblem problem) throws RequirementException, WrongDomainException, NonExistentPredicateException, NonExistentParameterException {
		this.domain = domain;
		this.problem = problem;

		if (domain.requirements!=null) domainRequirements = domain.requirements.getRequirements();
		else domainRequirements = new ArrayList<String>();
		if (domain.types!=null) existentTypes = domain.types.getTypes();
		else existentTypes = new ArrayList<String>();
		if (domain.constants!=null) constants = domain.constants.getConstants();
		else constants = new ArrayList<SimpleVariable>();
		if (domain.predicates!=null) predicates = domain.predicates.getPredicates();
		else predicates = new ArrayList<SimplePredicate>();
		if (domain.structure!=null) actions = domain.structure.getActions();
		else actions = new ArrayList<SimpleAction>();

		if (problem.requirements!=null) problemRequirements = problem.requirements.getRequirements();
		else problemRequirements = new ArrayList<String>();
		if (problem.objects!=null) objects = problem.objects.getObjects();
		else objects = new ArrayList<SimpleVariable>();
		initialization = new ArrayList<SimplePredicate>();
		for (Logic_PREDICATE p:problem.init.getInit()) {
			initialization.add(p.getPredicates().get(0));
		}
		goal = problem.goal.getGoal();

		validate();
	}

	private void validate() throws RequirementException, WrongDomainException, NonExistentPredicateException, NonExistentParameterException {
		//First Step: problem requirements must be contained in domainRequirements
		for (String s:problemRequirements) if (!domainRequirements.contains(s))
			throw new RequirementException(s);
		System.out.println("Requirements match!");
		//Second Step: domain do not match
		if (!(problem.domain.getDomain().equals(domain.domain.getDomain())))
			throw new WrongDomainException(problem.domain.getDomain(),domain.domain.getDomain());
		System.out.println("Domains match!");
		//Third step: validation of the elements of the domain
		//Third.1 : Predicates of actions exists and use correct parameters
		for (SimpleAction action: actions){
			List<SimpleVariable> parameters = new ArrayList<SimpleVariable>();
			parameters.addAll(action.getParameters());

			List<SimplePredicate> predicates = new ArrayList<SimplePredicate>();
			predicates.addAll(action.getPreconditions().getPredicates());
			predicates.addAll(action.getEffects().getPredicates());

			//verifica firma predicati:
			for (SimplePredicate p:predicates) {
				boolean exists = false;
				for (SimplePredicate p2 : this.predicates)
					if (p.getName().equals(p2.getName()) && p.getVariableCount()==p2.getVariableCount()) exists = true;
				if (!exists) throw new NonExistentPredicateException(p);

				//verifica utilizzo parametri appropriati
				for (SimpleVariable var:p.variables){
					if (!parameters.contains(var)){
						throw new NonExistentParameterException(p,var,parameters);
					}
				}
			}
		}
		System.out.println("Actions signature match!");
		//Fourth: Validation of the initialization of the problem
		for (SimplePredicate p:initialization){
			boolean exists = false;
			for (SimplePredicate predicate:predicates){
				if (predicate.getName().equals(p.getName()) && predicate.getVariableCount() == p.getVariableCount())
					exists = true;
			}
			if (!exists) {
				throw new NonExistentPredicateException(p);
			}
			for (SimpleVariable param:p.variables){
				if (!objects.contains(param))
					throw new NonExistentParameterException(p,param,new ArrayList<SimpleVariable>());
			}
		}
		System.out.println("Problem's initialization predicates match!");
		//Fifth: Validation of the goal of the problem
		for (SimplePredicate p:goal.getPredicates()){
			boolean exists = false;
			for (SimplePredicate pred:predicates)
				if (p.getName().equals(pred.getName()) && p.getVariableCount() == pred.getVariableCount()) exists = true;
			if (!exists) throw new NonExistentPredicateException(p);

			for (SimpleVariable param:p.variables){
				if (!objects.contains(param))
					throw new NonExistentParameterException(p,param,objects);
			}
		}
		System.out.println("Problem's goal formula is correct and her predicates match!");
		//Sixth: Creation of newPredicates Map:
		for(SimplePredicate pred:predicates){
			List<List<SimpleVariable>> temp = getCombination(pred.getVariableCount(),objects);
			for (List<SimpleVariable> var: temp){
				SimplePredicate newP = new SimplePredicate();
				newP.name = pred.name;
				newP.variables = var;
				String newName = "pred_"+ pred.getName()+"_var";
				for (SimpleVariable v:var) newName+="_"+v.getName();
				newPredicates.put(newP,newName);
			}
		}
		System.out.println("Predicates Map generated!");
	}

	@Override
	public String toString() {
		String result = "PDDL World Definition: \n";
		result+="Requirements: \n"+domainRequirements + "\n";
		result+="Existent Types: \n"+existentTypes + "\n";
		result+="Declared Constants: \n"+constants + "\n";
		result+="Declared Predicates: \n"+predicates + "\n";
		result+="Declared Actions: \n"+actions + "\n";
		result+="Existent Objects: \n"+objects + "\n";
		result+="Objects Initialization: \n"+initialization + "\n";
		result+="Declared Goal: \n"+goal + "\n";
		return result;
	}
	private List<List<SimpleVariable>> getCombination(int num,List<SimpleVariable> list){
		List<List<SimpleVariable>> result = new ArrayList<List<SimpleVariable>>();
		if (num>1){
			Object[] variables = list.toArray();
			for(Object var:variables){
				list.remove(var);
				List<List<SimpleVariable>> prevResult = getCombination(num-1,list);
				for (List<SimpleVariable> l:prevResult) l.add((SimpleVariable) var);
				result.addAll(prevResult);
				list.add((SimpleVariable) var);
			}
		}
		else {
			for (SimpleVariable var:list){
				List<SimpleVariable> temp = new ArrayList<SimpleVariable>();
				temp.add(var);
				result.add(temp);
			}
		}
		return result;
	}

	public LTLfWorldDescription toLTLfFormula(){

		LTLfWorldDescription result = new LTLfWorldDescription() {
			PropositionalSignature agentSet = new PropositionalSignature();
			PropositionalSignature environmentSet = new PropositionalSignature();
			PropositionalSignature hiddenSet = new PropositionalSignature();
			public void initialize(){
				//inizializzo l'insieme dell'agente con i nomi delle azioni
				for (SimpleAction a:actions) agentSet.add(new Proposition(a.getName()));
				//inizializzo l'insieme dell'environment con

				//inizializzo l'insieme invisibile con

			}

			@Override
			public String getInitFormula() {
				String init = newPredicates.get(initialization.get(0));
				for (int i = 1;i<initialization.size();i++){
					init+= " && "+newPredicates.get(initialization.get(i));
				}
				List<SimplePredicate> negatedInitialization = new ArrayList<SimplePredicate>(newPredicates.keySet());
				negatedInitialization.removeAll(initialization);
				for (int i=0;i<negatedInitialization.size();i++){
					init+= " && !"+newPredicates.get(negatedInitialization.get(i));
				}
				return init;
			}

			@Override
			public String getWorldRulesFormula() {
				/*  World rules are simple: for each action, and for every combination of possible parameters,
					two rules are created:
						- actionName && parameters && (proposition) -> X( proposition)
						- actionName && parameters && !(proposition) -> X( !proposition)
					where proposition is "each proposition not changed in action effect"
					Possible proposition are the predicates(with their parameters).
					with those rules we can maintain in LTLf the truth value of proposition that aren't changed
					during the execution of the action.
					TODO:
						forse devo aggiungere la regola che dice che un numero maggiore di parametri rispetto al numero di
						parametri dell'azione è automaticamente invalido.
						una volta aggiunto il controllo dei tipi devo aggiungere le regole che permettono di eliminare la
						possibilità di utilizzare tale azione con i parametri sbagliati.
					Every other rule is already written in the behaviour of each action (e.g. precondition)
				*/
				String result = "( \n";

				for (SimpleAction action:actions){
					List<SimplePredicate> usedPred = action.getEffects().getPredicates();
					List<List<SimpleVariable>> possibleParameters = getCombination(action.getParameters().size(),objects);
					for (List<SimpleVariable> currentParameters:possibleParameters){

						//creo una mappa dai parametri generici alla selezione di parametri in analisi al momento.
						Map<SimpleVariable,SimpleVariable> map = new HashMap<SimpleVariable,SimpleVariable>();
						for (int i=0;i<action.getParameters().size();i++){
							SimpleVariable variable = (SimpleVariable) action.getParameters().toArray()[i];
							SimpleVariable secondVariable =
									new SimpleVariable(((ArrayList<SimpleVariable>)currentParameters).get(i).getName(),null);
							map.put(variable,secondVariable);
						}

						Set<SimplePredicate> newPred = new HashSet<SimplePredicate>();
						for (SimplePredicate p:usedPred){
							SimplePredicate temp = new SimplePredicate();
							temp.name = p.name;
							for (int i=0;i<p.getVariableCount();i++) temp.variables.add(map.get(p.variables.get(i)));
							newPred.add(temp);
						}
						List<SimplePredicate> notUsedPredicates = new ArrayList<SimplePredicate>(newPredicates.keySet());
						notUsedPredicates.removeAll(newPred);
						for (SimplePredicate p:notUsedPredicates){
							result+= " ( ( ";
							result+= action.getName();
							for (SimpleVariable s:action.getParameters()) result+=" && "+map.get(s);
							result+=" && "+newPredicates.get(p);
							result+= " ) -> ";
							result+= "X( "+newPredicates.get(p)+" )";
							result+= " )\n";
							result+= " ( ( ";
							result+= action.getName();
							for (SimpleVariable s:action.getParameters()) result+=" && "+map.get(s);
							result+=" && !"+newPredicates.get(p);
							result+= " ) -> ";
							result+= "X( !"+newPredicates.get(p)+" )";
							result+= " )\n";
						}
					}
				}
				result+=" )\n";
				return result;
			}

			@Override
			public String getAgentRuleFormula() {
				//At least one action every turn
				String result = "( ( "+actions.get(0).getName();
				for (int i=1;i<actions.size();i++) {
					result+=" || "+actions.get(i).getName();
				}
				result +=" ) ";
				//at most one action every turn
				result+= " && ";
				String[]name = new String[actions.size()];
				for (int i=0;i<actions.size();i++)name[i] = actions.get(i).getName();
				for (int i=0;i<name.length;i++)
					for (int j=i+1;j<name.length;j++){
						if (i!=j) result+=" !("+name[i]+" && "+name[j]+" )";
					}
				result+= " ) ";

				return result;
			}

			@Override
			public String getActionsFormula() {
				return "ACTIONS";
			}

			@Override
			public String getGoalFormula() {
				return goal.toStringLTLf(newPredicates);
			}

			@Override
			public PropositionalSignature getAgentSet() {
				return agentSet;
			}

			@Override
			public PropositionalSignature getEnvironmentSet() {
				return environmentSet;
			}

			@Override
			public PropositionalSignature getHiddenSet() {
				return hiddenSet;
			}
		};
		return result;
	}
}

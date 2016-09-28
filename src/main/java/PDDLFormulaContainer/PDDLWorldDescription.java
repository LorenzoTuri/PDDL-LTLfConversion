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
 * Class that merges the Domain and Problem file of a PDDL specification. Many controls are made to ensure the input files
 * are coherent
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

	/**
	 * Only constuctor for this object
	 * @param domain
	 * @param problem
	 * @throws RequirementException
	 * @throws WrongDomainException
	 * @throws NonExistentPredicateException
	 * @throws NonExistentParameterException
	 */
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

	/**
	 * Function used from the costructor to ensure coherency between the two input files
	 * @throws RequirementException
	 * @throws WrongDomainException
	 * @throws NonExistentPredicateException
	 * @throws NonExistentParameterException
	 */
	private void validate() throws RequirementException, WrongDomainException, NonExistentPredicateException, NonExistentParameterException {
		//First Step: problem requirements must be contained in domainRequirements
		for (String s:problemRequirements) if (!domainRequirements.contains(s))
			throw new RequirementException(s);
		//System.out.println("Requirements match!");
		//Second Step: domain do not match
		if (!(problem.domain.getDomain().equals(domain.domain.getDomain())))
			throw new WrongDomainException(problem.domain.getDomain(),domain.domain.getDomain());
		//System.out.println("Domains match!");
		//Third step: validation of the elements of the domain
		//Third.1 : Predicates of actions exists and use correct parameters
		for (SimpleAction action: actions){
			List<SimpleVariable> parameters = new ArrayList<SimpleVariable>();
			parameters.addAll(action.getParameters());

			List<SimplePredicate> predicates = new ArrayList<SimplePredicate>();
			if (action.getPreconditions()!=null) predicates.addAll(action.getPreconditions().getPredicates());
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
		//System.out.println("Actions signature match!");
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
		//System.out.println("Problem's initialization predicates match!");
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
		//System.out.println("Problem's goal formula is correct and her predicates match!");
		//Sixth: Creation of newPredicates Map:
		for(SimplePredicate pred:predicates){
			List<List<SimpleVariable>> temp = getCombination(pred.getVariableCount(),objects);
			for (List<SimpleVariable> var: temp){
				SimplePredicate newP = new SimplePredicate();
				newP.name = pred.name;
				newP.variables = var;
				String newName = "p_"+ pred.getName()+"_v";
				for (SimpleVariable v:var) newName+="_"+v.getName();
				newPredicates.put(newP,newName);
			}
		}
		//System.out.println("Predicates Map generated!");
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

	/**
	 * Function used to generate a list of combinations of the input list, with "num" length
	 * @param num
	 * @param list
	 * @return
	 */
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
			result.add(new ArrayList<SimpleVariable>());
		}
		return result;
	}

	/**
	 * Returns an object containing the LTLf description of the PDDL file. it's already decomposed in
	 * SubFormulas to ensure that even slow translator can handle those formulas
	 * @return
	 */
	public LTLfWorldDescription toLTLfFormula(){

		LTLfWorldDescription result = new LTLfWorldDescription() {
			PropositionalSignature agentSet = new PropositionalSignature();
			PropositionalSignature environmentSet = new PropositionalSignature();
			PropositionalSignature hiddenSet = new PropositionalSignature();
			public void initialize(){
				//inizializzo l'insieme dell'agente con i nomi delle azioni
				for (SimpleAction a:actions) agentSet.add(new Proposition(a.getName()));
				//inizializzo l'insieme dell'environment con
				for (String s:newPredicates.values()) environmentSet.add(new Proposition(s));
				int maxVar = 0;
				for (SimpleAction a:actions) if (a.getParameters().size()>maxVar) maxVar = a.getParameters().size();
				for (SimpleVariable var:objects)
						for (int i=0;i<maxVar;i++)
							environmentSet.add(new Proposition("var"+i+var));
				//inizializzo l'insieme invisibile con
			}

			@Override
			public InitRules getInitRules() {
				String init = "( "+newPredicates.get(initialization.get(0));
				for (int i = 1;i<initialization.size();i++){
					init+= " && "+newPredicates.get(initialization.get(i));
				}
				List<SimplePredicate> negatedInitialization = new ArrayList<SimplePredicate>(newPredicates.keySet());
				negatedInitialization.removeAll(initialization);
				for (int i=0;i<negatedInitialization.size();i++){
					init+= " && !"+newPredicates.get(negatedInitialization.get(i));
				}
				init+=" )";
				return new InitRules(init);
			}

			@Override
			public WorldRules getWorldRules() {
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
				List<String> result = new ArrayList<String>();

				//aggiunta regola addizionale: per ogni oggetto, varX(oggetto) -> !varX(ognialtrooggetto)
				int maxParam = 0;
				for(SimpleAction a:actions) if (a.getParameters().size()>maxParam)maxParam=a.getParameters().size();
				for (SimpleVariable var:objects)
					for (int i=0;i<maxParam;i++)
					for (SimpleVariable var2:objects) if (!var.equals(var2)) {
						result.add("( var"+i+var+" -> !var"+i+var2+" )");
					}
				//regole base:
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
							String tmpres = "";
							tmpres+= " ( ( ";
							tmpres+= action.getName();
							for (int i=0;i<action.getParameters().size();i++)
								tmpres+=" && var"+i+map.get(action.getParameters().toArray()[i]);
							tmpres+=" && "+newPredicates.get(p);
							tmpres+= " ) -> ";
							tmpres+= "X( "+newPredicates.get(p)+" )";
							tmpres+= " )";
							String tmpres2 = "";
							tmpres2+= " ( ( ";
							tmpres2+= action.getName();
							for (int i=0;i<action.getParameters().size();i++)
								tmpres2+=" && var"+i+map.get(action.getParameters().toArray()[i]);
							tmpres2+=" && !"+newPredicates.get(p);
							tmpres2+= " ) -> ";
							tmpres2+= "X( !"+newPredicates.get(p)+" )";
							tmpres2+= " )";
							result.add(tmpres);
							result.add(tmpres2);
						}
					}
				}
				return new WorldRules(result);
			}

			@Override
			public AgentRules getAgentRule() {
				//At least one action every turn
				String result = "( ( "+actions.get(0).getName();
				for (int i=1;i<actions.size();i++) {
					result+=" || "+actions.get(i).getName();
				}
				result +=" ) ";
				//at most one action every turn
				for (int i=0;i<actions.size();i++){
					result += " && (";
					result += actions.get(i).getName()+" -> (";
					List<String> temp = new ArrayList<String>();
					for (int j=0;j<actions.size();j++) temp.add(actions.get(j).getName());
					temp.remove(actions.get(i).getName());
					result += "!"+temp.get(0);
					for (int j=1;j<temp.size();j++) result += " && !"+temp.get(j);
					result += ")";
					result += ")";
				}
				result+=")";
				return new AgentRules(result);
			}

			@Override
			public ActionRules getActionsRules() {
				List<String> res = new ArrayList<String>();
				for (SimpleAction action:actions){
					for (List<SimpleVariable> currentVariables:getCombination(action.getParameters().size(),objects)){
						String result = "";
						//creo una mappa dalle variabili precedenti a quelle nuove
						Map<SimpleVariable,SimpleVariable> newVar = new HashMap<SimpleVariable, SimpleVariable>();
						for (int i=0;i<currentVariables.size();i++)
							newVar.put((SimpleVariable) action.getParameters().toArray()[i],currentVariables.get(i));

						//creo una mappa dai vecchi predicati a quelli con le variabili correnti
						Map<SimplePredicate,String> map = new HashMap<SimplePredicate, String>();
						if (action.getPreconditions()!=null)
							for (SimplePredicate p:action.getPreconditions().getPredicates()){
								String newPred = "";
								newPred+="p_"+p.getName()+"_v";
								for (SimpleVariable var:p.variables) newPred+="_"+newVar.get(var).getName();
								map.put(p,newPred);
							}
						for (SimplePredicate p:action.getEffects().getPredicates()){
							String newPred = "";
							newPred+="p_"+p.getName()+"_v";
							for (SimpleVariable var:p.variables) newPred+="_"+newVar.get(var).getName();
							map.put(p,newPred);
						}
						result+=" ( "+action.getName();
						for(int i=0;i<currentVariables.size();i++) result+=" && var"+i+currentVariables.get(i);

						//inserimento precondizioni
						if (action.getPreconditions()!=null) {
							result += " && (";
							result += action.getPreconditions().getFormula().toStringLTLf(map);
							result += " )";
						}

						result+=" ) -> X("; //chiusura parentesi prima parte implicazione
						result+=action.getEffects().getFormula().toStringLTLf(map);
						result+=" )";//chiusura parentesi seconda parte implicazione
						res.add(result);
					}
				}
				return new ActionRules(res);
			}

			@Override
			public GoalRules getGoalRules() {
				return new GoalRules("( "+goal.toStringLTLf(newPredicates)+" )");
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

			@Override
			public PropositionalSignature getPropositionalSignature() {
				PropositionalSignature sig = new PropositionalSignature();
				sig.addAll(agentSet);
				sig.addAll(hiddenSet);
				sig.addAll(environmentSet);
				return sig;
			}
		};
		result.initialize();
		return result;
	}
}

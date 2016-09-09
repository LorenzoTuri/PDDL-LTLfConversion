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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 06/09/2016.
 */
public class PDDLWorldDescription {
	PDDLDomain domain;
	PDDLProblem problem;

	List<String> domainRequirements;
	List<String> problemRequirements;

	List<String> existentTypes;
	List<SimpleVariable> constants;
	List<SimplePredicate> predicates;
	List<SimpleAction> actions;

	List<String> objects;
	List<Logic_PREDICATE> initialization;
	BASE_FORMULA goal;

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
		else objects = new ArrayList<String>();
		initialization = problem.init.getInit();
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
			List<String> parameters = new ArrayList<String>();
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
					if (!parameters.contains(var.getName())){
						throw new NonExistentParameterException(p,var,parameters);
					}
				}
			}
		}
		System.out.println("Actions signature match!");
		//Fourth: Validation of the initialization of the problem
		for (Logic_PREDICATE p:initialization){
			boolean exists = false;
			SimplePredicate pred = p.getPredicates().get(0);
			for (SimplePredicate predicate:predicates){
				if (predicate.getName().equals(pred.getName()) && predicate.getVariableCount() == pred.getVariableCount())
					exists = true;
			}
			if (!exists) {
				throw new NonExistentPredicateException(p);
			}
			for (SimpleVariable param:pred.variables){
				if (!objects.contains(param.getName()))
					throw new NonExistentParameterException(pred,param,new ArrayList<String>());
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
				if (!objects.contains(param.getName()))
					throw new NonExistentParameterException(p,param,new ArrayList<String>());
			}
		}
		System.out.println("Problem's goal formula is correct and her predicates match!");
	}
}

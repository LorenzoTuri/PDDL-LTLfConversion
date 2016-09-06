package PDDLFormulaContainer;

import Exceptions.RequirementException;
import Exceptions.WrongDomainException;
import FormulaComponents.BaseComponents.BASE_FORMULA;
import FormulaComponents.BaseComponents.Logic_PREDICATE;
import FormulaComponents.SimpleAction;
import FormulaComponents.SimplePredicate;
import FormulaComponents.SimpleVariable;

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

	PDDLWorldDescription(PDDLDomain domain,PDDLProblem problem) throws RequirementException, WrongDomainException {
		this.domain = domain;
		this.problem = problem;

		domainRequirements = domain.requirements.getRequirements();
		problemRequirements = problem.requirements.getRequirements();
		existentTypes = domain.types.getTypes();
		constants = domain.constants.getConstants();
		predicates = domain.predicates.getPredicates();
		actions = domain.structure.getActions();

		objects = problem.objects.getObjects();
		initialization = problem.init.getInit();
		goal = problem.goal.getGoal();

		validate();
	}

	private void validate() throws RequirementException, WrongDomainException {
		//First Step: problem requirements must be contained in domainRequirements
		for (String s:problemRequirements) if (!domainRequirements.contains(s)) throw new RequirementException(s);
		//Second Step: domain do not match
		if (problem.domain!=domain.domain) throw new WrongDomainException(problem.domain.getDefine(),domain.domain.getDefine());

	}
}

package Main;

import Exceptions.NonExistentParameterException;
import Exceptions.NonExistentPredicateException;
import Exceptions.RequirementException;
import Exceptions.WrongDomainException;
import PDDLFormulaContainer.LTLfWorldDescription;
import PDDLFormulaContainer.PDDLDomain;
import PDDLFormulaContainer.PDDLProblem;
import PDDLFormulaContainer.PDDLWorldDescription;

import java.io.IOException;

/**
 * Created by loren on 18/07/2016.
 */
public class main {
	main(String domainPath, String problemPath) throws IOException, RequirementException, WrongDomainException, NonExistentPredicateException, NonExistentParameterException {

		//Tree creation of PDDL's Domain file
		String domainFile = (new loadResources(domainPath)).getResource();
		DomainVisit domainVisit = new DomainVisit(domainFile);
		PDDLDomain domain = domainVisit.visit();

		//Tree creation of PDDL's Problem File
		String problemFile = (new loadResources(problemPath)).getResource();
		ProblemVisit problemVisit = new ProblemVisit(problemFile);
		PDDLProblem problem = problemVisit.visit();

		PDDLWorldDescription world = new PDDLWorldDescription(domain,problem);
		System.out.println("\n"+world);

		LTLfWorldDescription ltlfWorld = world.toLTLfFormula();
		System.out.println("ACTION\t"+ltlfWorld.getActionsFormula());
		System.out.println("AGENT\t"+ltlfWorld.getAgentRuleFormula());
		System.out.println("GOAL\t"+ltlfWorld.getGoalFormula());
		System.out.println("INIT\t"+ltlfWorld.getInitFormula());
		System.out.println("WORLD\t"+ltlfWorld.getWorldRulesFormula());
	}

	public static void main(String[] args){
		try{new main("PDDLdoubleMurphyDomainFormula.txt", "PDDLdoubleMurphyProblemFormula.txt");}
		catch (Exception e){e.printStackTrace();}
	}
}

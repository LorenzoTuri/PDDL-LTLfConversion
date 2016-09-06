package Main;

import PDDLFormulaContainer.PDDLDomain;
import PDDLFormulaContainer.PDDLProblem;

import java.io.IOException;

/**
 * Created by loren on 18/07/2016.
 */
public class main {
	main(String domainPath, String problemPath)throws IOException {

		//Tree creation of PDDL's Domain file
		String domainFile = (new loadResources(domainPath)).getResource();
		DomainVisit domainVisit = new DomainVisit(domainFile);
		PDDLDomain domain = domainVisit.visit();
		System.out.println(domain);

		//Tree creation of PDDL's Problem File
		String problemFile = (new loadResources(problemPath)).getResource();
		ProblemVisit problemVisit = new ProblemVisit(problemFile);
		PDDLProblem problem = problemVisit.visit();
		System.out.println(problem);
	}

	public static void main(String[] args){
		try{new main("PDDLExampleWumpusDomain.txt", "PDDLExampleWumpusProblem.txt");}
		catch (Exception e){e.printStackTrace();}
	}
}

package Main;

import Exceptions.NonExistentParameterException;
import Exceptions.NonExistentPredicateException;
import Exceptions.RequirementException;
import Exceptions.WrongDomainException;
import PDDLFormulaContainer.LTLfWorldDescription;
import PDDLFormulaContainer.PDDLDomain;
import PDDLFormulaContainer.PDDLProblem;
import PDDLFormulaContainer.PDDLWorldDescription;
import formula.ldlf.LDLfFormula;
import formula.ltlf.LTLfFormula;
import rationals.Automaton;
import rationals.transformations.Mix;
import rationals.transformations.Reducer;
import utils.AutomatonUtils;
import utils.ParserUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static main.Main.ltlfFormula2Aut;

/**
 * Created by loren on 18/07/2016.
 */
public class mainInterface {
	mainInterface(String domainPath, String problemPath) throws IOException, RequirementException, WrongDomainException, NonExistentPredicateException, NonExistentParameterException {

		//Tree creation of PDDL's Domain file
		String domainFile = (new loadResources(domainPath)).getResource();
		DomainVisit domainVisit = new DomainVisit(domainFile);
		PDDLDomain domain = domainVisit.visit();

		//Tree creation of PDDL's Problem File
		String problemFile = (new loadResources(problemPath)).getResource();
		ProblemVisit problemVisit = new ProblemVisit(problemFile);
		PDDLProblem problem = problemVisit.visit();

		PDDLWorldDescription world = new PDDLWorldDescription(domain,problem);
		//System.out.println("\n"+world);

		LTLfWorldDescription ltlfWorld = world.toLTLfFormula();
		//System.out.println("ACTION\t"+ltlfWorld.getActionsFormula());
		//System.out.println("AGENT\t"+ltlfWorld.getAgentRuleFormula());
		//System.out.println("GOAL\t"+ltlfWorld.getGoalFormula());
		//System.out.println("INIT\t"+ltlfWorld.getInitFormula());
		//System.out.println("WORLD\t"+ltlfWorld.getWorldRulesFormula());

		List<String> formulas = ltlfWorld.getWorldRulesFormula();
		Automaton aut[] = new Automaton[formulas.size()];
		for (int i=0;i<formulas.size();i++){
			System.out.println("Sto generando l'automa per la formula "+i+": "+formulas.get(i));

			LTLfFormula formula = ParserUtils.parseLTLfFormula(formulas.get(i));
			LDLfFormula ldlff = formula.toLDLf();
			aut[i] = (ltlfFormula2Aut(formula, null, false, true, false, true, false)).getAutomaton();
			printAutomaton(aut[i],"output/automaton"+i+".gv");
		}
		for (int i=1;i<aut.length;i++){
			System.out.println("Sto unendo l'automa finale con l'automa "+i);
			aut[0] = (new Mix()).transform(aut[0],aut[i]);
			aut[0] = (new Reducer()).transform(aut[0]);
			printAutomaton(aut[0],"output/resultUntil"+i+".gv");
		}
	}

	public static void main(String[] args){
		try{new mainInterface("PDDLdoubleMurphyDomainFormula.txt", "PDDLdoubleMurphyProblemFormula.txt");}
		catch (Exception e){e.printStackTrace();}
	}

	public void printAutomaton(Automaton automaton, String path){
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(path);
			PrintStream ps = new PrintStream(fos);
			ps.println(AutomatonUtils.toDot(automaton));
			ps.flush();
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

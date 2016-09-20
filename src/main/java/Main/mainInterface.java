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
		System.out.println(world);

		LTLfWorldDescription ltlfWorld = world.toLTLfFormula();
		System.out.println("ACTION\t"+ltlfWorld.getActionsFormula());
		System.out.println("AGENT\t"+ltlfWorld.getAgentRuleFormula());
		System.out.println("GOAL\t"+ltlfWorld.getGoalFormula());
		System.out.println("INIT\t"+ltlfWorld.getInitFormula());
		System.out.println("WORLD\t"+ltlfWorld.getWorldRulesFormula());

		List<String> formulas = ltlfWorld.getWorldRulesFormula();
		Automaton aut[] = new Automaton[formulas.size()];
		for (int i=0;i<formulas.size();i++){
			System.out.println("Sto generando l'automa per la formula "+i+": "+formulas.get(i));

			LTLfFormula formula = ParserUtils.parseLTLfFormula(formulas.get(i));
			LDLfFormula ldlff = formula.toLDLf();
			aut[i] = (ltlfFormula2Aut(formula, null, false, true, false, true, false)).getAutomaton();
			printAutomaton(aut[i],"output/automaton"+i+".gv");
		}
		System.out.println("Sto unendo le esclusioni");
		Automaton exclusionRules = (new Mix()).transform(aut[0],aut[1]);
		exclusionRules = (new Mix()).transform(exclusionRules,aut[2]);
		exclusionRules = (new  Mix()).transform(exclusionRules,aut[3]);
		exclusionRules = (new Reducer()).transform(exclusionRules);
		AutomatonUtils.printAutomaton(exclusionRules,"output/exclusionRules.gv");
		System.out.println("Sto unendo le move");
		Automaton moveAutomaton = (new Mix()).transform(aut[4],aut[5]);
		moveAutomaton = (new Mix()).transform(moveAutomaton,aut[6]);
		moveAutomaton = (new Mix()).transform(moveAutomaton,aut[7]);
		moveAutomaton = (new Reducer()).transform(moveAutomaton);
		AutomatonUtils.printAutomaton(moveAutomaton,"output/moveRules.gv");
		System.out.println("Sto unendo move e esclusioni");
		Automaton moveAndExclusion = (new Mix()).transform(exclusionRules,moveAutomaton);
		moveAndExclusion = (new Reducer()).transform(moveAndExclusion);
		AutomatonUtils.printAutomaton(moveAndExclusion,"output/exclusionAndMoveRules.gv");
		System.out.println("Sto unendo le clean");
		Automaton cleanAutomaton = (new Mix()).transform(aut[8],aut[9]);
		cleanAutomaton = (new Reducer()).transform(cleanAutomaton);
		cleanAutomaton = (new Mix()).transform(cleanAutomaton,aut[10]);
		cleanAutomaton = (new Reducer()).transform(cleanAutomaton);
		cleanAutomaton = (new Mix()).transform(cleanAutomaton,aut[11]);
		cleanAutomaton = (new Reducer()).transform(cleanAutomaton);
		cleanAutomaton = (new Mix()).transform(cleanAutomaton,aut[12]);
		cleanAutomaton = (new Reducer()).transform(cleanAutomaton);
		cleanAutomaton = (new Mix()).transform(cleanAutomaton,aut[13]);
		cleanAutomaton = (new Reducer()).transform(cleanAutomaton);
		cleanAutomaton = (new Mix()).transform(cleanAutomaton,aut[14]);
		cleanAutomaton = (new Reducer()).transform(cleanAutomaton);
		cleanAutomaton = (new Mix()).transform(cleanAutomaton,aut[15]);
		cleanAutomaton = (new Reducer()).transform(cleanAutomaton);
		cleanAutomaton = (new Mix()).transform(cleanAutomaton,aut[16]);
		cleanAutomaton = (new Reducer()).transform(cleanAutomaton);
		cleanAutomaton = (new Mix()).transform(cleanAutomaton,aut[17]);
		cleanAutomaton = (new Reducer()).transform(cleanAutomaton);
		cleanAutomaton = (new Mix()).transform(cleanAutomaton,aut[18]);
		cleanAutomaton = (new Reducer()).transform(cleanAutomaton);
		cleanAutomaton = (new Mix()).transform(cleanAutomaton,aut[19]);
		cleanAutomaton = (new Reducer()).transform(cleanAutomaton);
		AutomatonUtils.printAutomaton(cleanAutomaton,"output/cleanRules.gv");
		System.out.println("Sto unendo clean e esclusioni");
		Automaton cleanAndExclusion = (new Mix()).transform(exclusionRules,cleanAutomaton);
		cleanAndExclusion = (new Reducer()).transform(cleanAndExclusion);
		AutomatonUtils.printAutomaton(cleanAndExclusion,"output/exclusionAndCleanRules.gv");
		System.out.println("Sto unendo l'automa completo");
		Automaton completeAutomaton = (new Mix()).transform(moveAndExclusion,cleanAndExclusion);
		completeAutomaton = (new Reducer()).transform(completeAutomaton);
		AutomatonUtils.printAutomaton(completeAutomaton,"output/exclusionCompleteRules.gv");
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

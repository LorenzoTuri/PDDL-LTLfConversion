package Main;

import AutomatonThings.Mix;
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
import main.Main;
import rationals.Automaton;
import rationals.transformations.Reducer;
import utils.AutomatonUtils;
import utils.ParserUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static main.Main.ltlfFormula2Aut;

/**
 * Created by loren on 18/07/2016.
 */
public class mainInterface {
	mainInterface(String domainPath, String problemPath) throws IOException, RequirementException, WrongDomainException, NonExistentPredicateException, NonExistentParameterException {
		long timer = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis());
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
		System.out.println("ACTION\t"+ltlfWorld.getActionsRules());
		System.out.println("AGENT\t"+ltlfWorld.getAgentRule());
		System.out.println("GOAL\t"+ltlfWorld.getGoalRules());
		System.out.println("INIT\t"+ltlfWorld.getInitRules());
		System.out.println("WORLD\t"+ltlfWorld.getWorldRules());

		System.out.print("Generazione formula init - ");
		LTLfFormula initformula = ParserUtils.parseLTLfFormula(ltlfWorld.getInitRules().getLTLfFormula());
		LDLfFormula initldlff = initformula.toLDLf();
		Automaton init = Main.ldlfFormula2Aut(initldlff, null, false, true, false, true, false).getAutomaton();
		init = new Reducer().transform(init);
		System.out.println(((float)(System.currentTimeMillis()-timer))/1000);

		System.out.print("Generazione formule agent - ");
		LTLfFormula agentformula = ParserUtils.parseLTLfFormula(ltlfWorld.getAgentRule().getLTLfFormula());
		LDLfFormula agentldlff = agentformula.toLDLf();
		Automaton agentRules = Main.ldlfFormula2Aut(agentldlff, null, false, true, false, true, false).getAutomaton();
		agentRules = new Reducer().transform(agentRules);
		System.out.println(((float)(System.currentTimeMillis()-timer))/1000);

		System.out.println("Generazione formule azioni");
		Automaton[] actionFormula = new Automaton[ltlfWorld.getActionsRules().size()];
		for (int i=0;i<ltlfWorld.getActionsRules().size();i++){
			System.out.print("\tprogress: "+(i+1)+"/"+ltlfWorld.getActionsRules().size()+" - ");
			LTLfFormula actionForm = ParserUtils.parseLTLfFormula(ltlfWorld.getActionsRules().get(i));
			LDLfFormula actionldlf = actionForm.toLDLf();
			actionFormula[i] = Main.ldlfFormula2Aut(actionldlf, null, false, true, false, true, false).getAutomaton();
			actionFormula[i] = new Reducer().transform(actionFormula[i]);
			System.out.println(((float)(System.currentTimeMillis()-timer))/1000);
		}
		System.out.println("Unione delle azioni");
		for (int i=1;i<actionFormula.length;i++) {
			System.out.print("\tprogress: "+i+"/"+actionFormula.length+" - ");
			actionFormula[0] = new Mix().transform(actionFormula[0],actionFormula[i]);
			System.out.println(((float)(System.currentTimeMillis()-timer))/1000);
		}

		System.out.println("Generazione formule regole");
		LTLfWorldDescription.WorldRules formulas = ltlfWorld.getWorldRules();
		Automaton aut[] = new Automaton[formulas.size()];
		for (int i=0;i<formulas.size();i++){
			System.out.print("\tprogress: "+(i+1)+"/"+formulas.size()+" - ");
			LTLfFormula formula = ParserUtils.parseLTLfFormula(formulas.get(i));
			LDLfFormula ldlff = formula.toLDLf();
			aut[i] = (ltlfFormula2Aut(formula, null, false, true, false, true, false)).getAutomaton();
			System.out.println(((float)(System.currentTimeMillis()-timer))/1000);
		}
		Automaton completeAutomaton = aut[0];
		for (int i=1;i<aut.length;i++){
			completeAutomaton = new Mix().transform(completeAutomaton,aut[i]);
			completeAutomaton = new Reducer().transform(completeAutomaton);
		}

		System.out.print("Sto unendo le regole con init - ");
		Automaton FinalAutomaton = new Mix().transform(completeAutomaton,init);
		FinalAutomaton = new Reducer().transform(FinalAutomaton);
		System.out.println(((float)(System.currentTimeMillis()-timer))/1000);

		System.out.print("Sto unendo anche le agentrules - ");
		FinalAutomaton = new Mix().transform(FinalAutomaton,agentRules);
		FinalAutomaton = new Reducer().transform(FinalAutomaton);
		System.out.println(((float)(System.currentTimeMillis()-timer))/1000);

		System.out.print("Infine unisco le azioni - ");
		FinalAutomaton = new Mix().transform(actionFormula[0],FinalAutomaton);
		System.out.println(((float)(System.currentTimeMillis()-timer))/1000);

		System.out.println("ALPHABET : "+FinalAutomaton.alphabet());
		System.out.println("N° STATES: "+FinalAutomaton.states().size());
		System.out.println("INITIALS : "+FinalAutomaton.initials());
		System.out.println("TERMINALS: "+FinalAutomaton.terminals());
		printAutomaton(FinalAutomaton,"output/FinalAutomaton.gv");

		FileOutputStream fos;
		try {
			fos = new FileOutputStream("output/prova.txt");
			PrintStream ps = new PrintStream(fos);
			ps.println(AutomatonUtils.toDot(FinalAutomaton));
			ps.flush();
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		try{new mainInterface("PDDLdoubleMurphyDomainFormula2.txt", "PDDLdoubleMurphyProblemFormula2.txt");}
		catch (Exception e){e.printStackTrace();}
	}

	public static void printAutomaton(Automaton automaton, String path){
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

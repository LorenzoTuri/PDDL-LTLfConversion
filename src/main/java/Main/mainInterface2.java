package Main;

import AutomatonThings.AutomatonCrawler;
import AutomatonThings.Mix;
import main.Main;
import net.sf.tweety.logics.pl.syntax.Proposition;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;
import rationals.Automaton;
import rationals.transformations.Reducer;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by loren on 20/09/2016.
 */
public class mainInterface2 {
	mainInterface2(){
		GregorianCalendar time = new GregorianCalendar(){
			@Override
			public String toString() {
				this.setTime(new Date(System.currentTimeMillis()));
				return this.getTime().toString();
			}
		};
		time.setTime(new Date(System.currentTimeMillis()));/*
		//Tree creation of PDDL's Domain file
		String domainFile = (new loadResources("PDDLdoubleMurphyDomainFormula2.txt")).getResource();
		DomainVisit domainVisit = new DomainVisit(domainFile);
		PDDLDomain domain = domainVisit.visit();

		//Tree creation of PDDL's Problem File
		String problemFile = (new loadResources("PDDLdoubleMurphyProblemFormula2.txt")).getResource();
		ProblemVisit problemVisit = new ProblemVisit(problemFile);
		PDDLProblem problem = problemVisit.visit();

		PDDLWorldDescription world = null;
		try {
			world = new PDDLWorldDescription(domain,problem);
		} catch (Exception e){
			e.printStackTrace();
		}

		LTLfWorldDescription ltlfWorld = world.toLTLfFormula();
		System.out.println(ltlfWorld);

		String initFormula = "true";
		if (ltlfWorld.getInitRules().getLTLfFormula().length()>0)
			initFormula = ltlfWorld.getInitRules().getLTLfFormula();
		String worldFormula = "true";
		if (ltlfWorld.getWorldRules().getLTLfFormula().length()>0)
			worldFormula = ltlfWorld.getWorldRules().getLTLfFormula();
		String agentFormula = "true";
		if (ltlfWorld.getAgentRule().getLTLfFormula().length()>0)
			agentFormula = ltlfWorld.getAgentRule().getLTLfFormula();
		String actionFormula = "true";
		if (ltlfWorld.getActionsRules().getLTLfFormula().length()>0)
			actionFormula = ltlfWorld.getActionsRules().getLTLfFormula();
		String goalFormula = "true";
		if (ltlfWorld.getGoalRules().getLTLfFormula().length()>0)
			goalFormula = ltlfWorld.getGoalRules().getLTLfFormula();

		System.out.println("AAASD");
		for (int i=0;i<ltlfWorld.getActionsRules().size();i++)System.out.println(ltlfWorld.getActionsRules().get(i));
		System.out.println("\n\n\n\n");
		String formula =
				"( "+initFormula + " && (" +
					"(" +
						"G( "+worldFormula + ") &&" +
						"G("+actionFormula+ ") && G("+agentFormula+")" +
					")" +
				"))";// -> F("+goalFormula+")";
		System.out.println(formula);

		Automaton rulesAutomaton = Main.ltlfString2Aut(
				"G("+worldFormula+")",
				ltlfWorld.getPropositionalSignature(),
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
		rulesAutomaton = new Reducer().transform(rulesAutomaton);
		System.out.println(time+"RulesAutomaton Generated");

		Automaton agentRulesAutomaton = Main.ltlfString2Aut(
				"G("+agentFormula+")",
				ltlfWorld.getPropositionalSignature(),
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
		rulesAutomaton = new Reducer().transform(agentRulesAutomaton);
		System.out.println(time+"AgentRulesAutomaton Generated");

		Automaton actionsAutomaton = Main.ltlfString2Aut(
				"G("+actionFormula+")",
				ltlfWorld.getPropositionalSignature(),
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
		actionsAutomaton = new Reducer().transform(actionsAutomaton);
		System.out.println(time+"ActionAutomaton Generated");

		//next Automaton is actionAutomaton && agentRulesAutomaton
		Automaton actionAgentRulesAutomaton = new Mix().transform(actionsAutomaton,agentRulesAutomaton);
		actionAgentRulesAutomaton = new Reducer().transform(actionAgentRulesAutomaton);

		Automaton goalAutomaton = Main.ltlfString2Aut(
				goalFormula,
				ltlfWorld.getPropositionalSignature(),
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
		System.out.println(time+"GoalAutomatonGenerated");

		//next automaton is rulesAutomaton -> actionsAgentRulesAutomaton,
		// !rulesAutomaton || (rulesAutomaton && actionsRulesAutomaton)
		//we call !rulesAutomaton negatedRulesAutomaton, (rulesAutomaton && actionsAgentRulesAutomaton) bodyAutomaton and
		//the whole automaton domainAutomaton.

		Automaton negatedRulesAutomaton = rulesAutomaton.clone();
		Set<State> newTerminal = new HashSet<State>(negatedRulesAutomaton.states());
		newTerminal.removeAll(negatedRulesAutomaton.terminals());
		negatedRulesAutomaton.terminals().clear();
		for(State s:newTerminal) negatedRulesAutomaton.terminals().add(s);
		System.out.println(time+"NegatedRulesAutomaton Generated");

		Automaton bodyAutomaton = new Mix().transform(rulesAutomaton,actionAgentRulesAutomaton);
		bodyAutomaton = new Reducer().transform(bodyAutomaton);
		System.out.println(time+"BodyAutomaton Generated");

		Automaton domainAutomaton = new Union().transform(bodyAutomaton,negatedRulesAutomaton);
		domainAutomaton = new Reducer().transform(domainAutomaton);
		System.out.println(time+"DomainAutomaton Generated");

		Automaton initAutomaton = Main.ltlfString2Aut(
				initFormula,
				ltlfWorld.getPropositionalSignature(),
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
		initAutomaton = new Reducer().transform(initAutomaton);
		System.out.println(time+"InitAutomaton Generated");

		Automaton completeAutomaton = new Mix().transform(domainAutomaton,initAutomaton);
		completeAutomaton = new Reducer().transform(completeAutomaton);
		System.out.println(time+"CompleteAutomaton Generated");

		System.out.println("\n\n\n"+time+completeAutomaton);
		mainInterface.printAutomaton(completeAutomaton,"output/123Complete.gv");

		//AutomatonCrawler crw = new AutomatonCrawler(completeAutomaton);
*/

		String init = "(isona && dirtya && dirtyb)";
		String exclusive =
				"G(" +
				"(move || clean ) &&" +
				"(move -> !clean) &&" +
				"(clean -> !move)   " +
				")";
		String actionMove = "G(" +
				"((move &&  isona &&  dirtya && dirtyb ) -> X(!isona &&  dirtya && dirtyb)) &&" +
				"((move &&  isona &&  dirtya && !dirtyb) -> X(!isona &&  dirtya && (dirtyb || !dirtyb))) &&" +
				"((move &&  isona && !dirtya && dirtyb ) -> X(!isona && !dirtya && dirtyb)) &&" +
				"((move &&  isona && !dirtya && !dirtyb) -> X(!isona && !dirtya && (dirtyb || !dirtyb))) &&" +
				"((move && !isona &&  dirtya && dirtyb ) -> X( isona && dirtya &&  dirtyb)) &&" +
				"((move && !isona &&  dirtya && !dirtyb) -> X( isona && dirtya && !dirtyb)) &&" +
				"((move && !isona && !dirtya && dirtyb ) -> X( isona && (dirtya || !dirtya) &&  dirtyb)) &&" +
				"((move && !isona && !dirtya && !dirtyb) -> X( isona && (dirtya || !dirtya) && !dirtyb))   " +
				")";
		String actionClean = "G(" +
				"((clean &&  isona &&  dirtya &&  dirtyb ) -> X( isona && !dirtya &&  dirtyb)) &&" +
				"((clean &&  isona &&  dirtya && !dirtyb ) -> X( isona && !dirtya && !dirtyb)) &&" +
				"((clean &&  isona && !dirtya &&  dirtyb ) -> X( isona &&  dirtya &&  dirtyb)) &&" +
				"((clean &&  isona && !dirtya && !dirtyb ) -> X( isona &&  dirtya && !dirtyb)) &&" +
				"((clean && !isona &&  dirtya &&  dirtyb ) -> X(!isona &&  dirtya && !dirtyb)) &&" +
				"((clean && !isona &&  dirtya && !dirtyb ) -> X(!isona &&  dirtya &&  dirtyb)) &&" +
				"((clean && !isona && !dirtya &&  dirtyb ) -> X(!isona && !dirtya && !dirtyb)) &&" +
				"((clean && !isona && !dirtya && !dirtyb ) -> X(!isona && !dirtya &&  dirtyb))   " +
				")";
		String objectives = "(!dirtya && !dirtyb)";
		String formula = init+" && "+exclusive+" && "+actionMove+" && "+actionClean+" && F"+objectives;
/*
		Automaton aut = Main.ltlfString2Aut(
				formula,
				null,
				false,
				true,
				true,
				false,
				false
		).getAutomaton();

		*/
		System.out.println("init \t"+init);
		System.out.println("exclusive \t"+exclusive);
		System.out.println("actionmove \t"+actionMove);
		System.out.println("actionclean \t"+actionClean);
		System.out.println("formula \t"+formula);

		PropositionalSignature signature = new PropositionalSignature();
		signature.add(new Proposition("move"));
		signature.add(new Proposition("clean"));
		signature.add(new Proposition("isona"));
		signature.add(new Proposition("dirtya"));
		signature.add(new Proposition("dirtyb"));

		Automaton initAut = Main.ltlfString2Aut(
				init,
				signature,
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
		mainInterface.printAutomaton(initAut,"output/1.initAut.gv");
		System.out.println(time+" initAut created!");

		Automaton exclAut = Main.ltlfString2Aut(
				exclusive,
				signature,
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
		mainInterface.printAutomaton(exclAut,"output/2.exclAut.gv");
		System.out.println(time+" exclAut created!");

		Automaton moveAut = Main.ltlfString2Aut(
				actionMove,
				signature,
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
		mainInterface.printAutomaton(moveAut,"output/3.moveAut.gv");
		System.out.println(time+" moveAut created!");

		Automaton cleanAut = Main.ltlfString2Aut(
				actionClean,
				signature,
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
		mainInterface.printAutomaton(cleanAut,"output/4.cleanAut.gv");
		System.out.println(time+" cleanAut created!");

		Automaton objectiveAut =Main.ltlfString2Aut(
				objectives,
				signature,
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
		mainInterface.printAutomaton(objectiveAut,"output/5.objectivesAut.gv");
		System.out.println(time+" objectivesAut created!");

		Automaton completeAutomaton = new Mix().transform(initAut,exclAut);
		completeAutomaton = new Reducer().transform(completeAutomaton);
		mainInterface.printAutomaton(completeAutomaton,"output/6.initAndExcl.gv");
		System.out.println(time+" First Mix done!");

		completeAutomaton = new Mix().transform(completeAutomaton,moveAut);
		completeAutomaton = new Reducer().transform(completeAutomaton);
		mainInterface.printAutomaton(completeAutomaton,"output/7.aut6withMove.gv");
		System.out.println(time+" Second Mix done!");

		completeAutomaton = new Mix().transform(completeAutomaton,cleanAut);
		completeAutomaton = new Reducer().transform(completeAutomaton);
		mainInterface.printAutomaton(completeAutomaton,"output/8.aut7withClean.gv");
		System.out.println(time+" Third Mix done!");

		completeAutomaton = new Mix().transform(completeAutomaton,objectiveAut);
		completeAutomaton = new Reducer().transform(completeAutomaton);
		mainInterface.printAutomaton(completeAutomaton,"output/9.aut8withObjectives.gv");
		System.out.println(time+" Completed!");

		new AutomatonCrawler(completeAutomaton).start();
	}
	public static void main(String[] args){
		File outputFolder = new File("output");
		for (File file:outputFolder.listFiles()) file.delete();
		new mainInterface2();
	}

}

package Visitor;

import ANTLRgenerated.PDDLProblemGrammarBaseVisitor;
import ANTLRgenerated.PDDLProblemGrammarParser;
import FormulaComponents.BaseComponents.*;
import PDDLFormulaContainer.*;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created by loren on 05/09/2016.
 */
public class PDDLProblemVisitor<SimpleFormula> extends PDDLProblemGrammarBaseVisitor<SimpleFormula> {
	@Override
	public SimpleFormula visitStart(PDDLProblemGrammarParser.StartContext ctx) {
		return visitPddlproblemfile(ctx.pddlproblemfile());
	}

	@Override
	public SimpleFormula visitPddlproblemfile(PDDLProblemGrammarParser.PddlproblemfileContext ctx) {
		PDDLProblem result = new PDDLProblem();
		result.setProblem((Problem) visitFormulaproblem(ctx.formulaproblem()));
		result.setDomain((DefineFormula) visitFormuladomain(ctx.formuladomain()));
		result.setRequirements((RequirementsFormula) visitFormularequirements(ctx.formularequirements()));
		result.setObjects((ObjectsFormula) visitFormulaobjects(ctx.formulaobjects()));
		result.setInit((FormulaInit) visitFormulainit(ctx.formulainit()));
		result.setGoal((GoalFormula) visitFormulagoal(ctx.formulagoal()));
		//result.setLengthSpec(visitFormulalengthspec(ctx.formulalengthspec()));
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitFormulaproblem(PDDLProblemGrammarParser.FormulaproblemContext ctx) {
		System.out.println("VISITFORMULA PROBLEM -> "+ctx.SIMPLENAME().toString());
		return (SimpleFormula) new Problem(ctx.SIMPLENAME().toString());
	}

	@Override
	public SimpleFormula visitFormuladomain(PDDLProblemGrammarParser.FormuladomainContext ctx) {
		System.out.println("VISITFORMULA DOMAIN -> "+ctx.SIMPLENAME().toString());
		return (SimpleFormula) new DefineFormula(ctx.SIMPLENAME().toString());
	}

	@Override
	public SimpleFormula visitFormularequirements(PDDLProblemGrammarParser.FormularequirementsContext ctx) {
		System.out.println("VISITFORMULA REQUIREMENTS -> "+ctx.REQUIREMENTTYPES().toString());
		RequirementsFormula result = new RequirementsFormula();
		for(TerminalNode t:ctx.REQUIREMENTTYPES()) result.addRequirement(t.toString());
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitFormulaobjects(PDDLProblemGrammarParser.FormulaobjectsContext ctx) {
		System.out.println("VISITFORMULA OBJECTS -> "+ctx.SIMPLENAME().toString());
		ObjectsFormula result = new ObjectsFormula();
		for (TerminalNode t:ctx.SIMPLENAME())result.addObject(t.toString());
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitFormulainit(PDDLProblemGrammarParser.FormulainitContext ctx) {
		System.out.print("VISITFORMULA INIT -> ");

		FormulaInit result = new FormulaInit();
		for (int i = 0;i<ctx.initelement().size();i++){
			System.out.print(visitInitelement(ctx.initelement(i)));
			result.addInitialization((String) visitInitelement(ctx.initelement(i)));
		}
		System.out.println();

		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitInitelement(PDDLProblemGrammarParser.InitelementContext ctx) {
		return (SimpleFormula) ctx.SIMPLENAME().toString();
	}

	@Override
	public SimpleFormula visitFormulagoal(PDDLProblemGrammarParser.FormulagoalContext ctx) {
		return visitGoalelement(ctx.goalelement());
	}

	@Override
	public SimpleFormula visitGoalelement(PDDLProblemGrammarParser.GoalelementContext ctx) {
		return (SimpleFormula) new GoalFormula((PDDLFormula) visitFormula(ctx.formula()));
	}

	@Override
	public SimpleFormula visitFormula(PDDLProblemGrammarParser.FormulaContext ctx) {
		PDDLFormula result = null;
		if (ctx.AND()!=null){
			//la formula può esser scomposta in "and formula*"
			PDDLComponentAND formula = new PDDLComponentAND();
			for (int i=0;i<ctx.formula().size();i++){
				PDDLFormula formula2 = (PDDLFormula) visitFormula(ctx.formula(i));
				formula.addFormula(formula2);
			}
			result = formula;
		}else if (ctx.OR() != null){
			//la formula può esser scomposta in "or formula*"
			PDDLComponentOR formula = new PDDLComponentOR();
			for (int i=0;i<ctx.formula().size();i++){
				PDDLFormula formula2 = (PDDLFormula) visitFormula(ctx.formula(i));
				formula.addFormula(formula2);
			}
			result = formula;
		}else if (ctx.IMPLY() != null){
			//la formula può esser scomposta in "imply formula formula"
			PDDLComponentIMPLY formula = new PDDLComponentIMPLY();
			formula.addFormula((PDDLFormula) visitFormula(ctx.formula(0)));
			formula.addFormula((PDDLFormula) visitFormula(ctx.formula(1)));
			result = formula;
		}else if (ctx.FORALL() != null){
			//la formula può esser scomposta in "forall Simplename+ formula"
			PDDLComponentFORALL formula = new PDDLComponentFORALL();
			for (TerminalNode t:ctx.SIMPLENAME()) formula.addSimplename(t.toString());
			formula.addFormula((PDDLFormula) visitFormula(ctx.formula(0)));
			result = formula;
		}else if (ctx.NOT() != null){
			//la formula può esser scomposta in "not formula"
			PDDLComponentNOT formula = new PDDLComponentNOT();
			formula.addFormula((PDDLFormula) visitFormula(ctx.formula(0)));
			result = formula;
		}else if (ctx.predicate() != null){
			//la formula è un predicato
			PDDLComponentPREDICATE formula = (PDDLComponentPREDICATE) visitPredicate(ctx.predicate());
			result = formula;
		}else{
			//ultimo caso: la formula è semplicemente contornata da parentesi
			PDDLComponentBRACKETS formula = new PDDLComponentBRACKETS();
			formula.addFormula((PDDLFormula) visitFormula(ctx.formula(0)));
			result = formula;
		}
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitPredicate(PDDLProblemGrammarParser.PredicateContext ctx) {
		PDDLComponentPREDICATE result = new PDDLComponentPREDICATE();
		result.setName((String) visitName(ctx.name()));
		for (TerminalNode t:ctx.SIMPLENAME()) result.addVariable(t.toString());
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitName(PDDLProblemGrammarParser.NameContext ctx) {
		return (SimpleFormula) ctx.SIMPLENAME().toString();
	}
}

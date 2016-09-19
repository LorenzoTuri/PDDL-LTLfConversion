package Visitor;

import ANTLRGenerated.PDDLProblemGrammarBaseVisitor;
import ANTLRGenerated.PDDLProblemGrammarParser;
import FormulaComponents.BaseComponents.*;
import FormulaComponents.SimpleVariable;
import PDDLFormulaContainer.PDDLProblem;
import PDDLFormulaContainer.SubComponents.*;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Class generated to Visit Problem Definition File in a PDDL Domain.
 * Should be used in combo with PDDLDomain to generate a complete PDDL translation to java classes
 */
public class PDDLProblemVisitor<SimpleFormula> extends PDDLProblemGrammarBaseVisitor<SimpleFormula> {
	@Override
	public SimpleFormula visitStart(PDDLProblemGrammarParser.StartContext ctx) {
		return visitPddlproblemfile(ctx.pddlproblemfile());
	}

	@Override
	public SimpleFormula visitPddlproblemfile(PDDLProblemGrammarParser.PddlproblemfileContext ctx) {
		return visitBody(ctx.body());
	}

	@Override
	public SimpleFormula visitBody(PDDLProblemGrammarParser.BodyContext ctx) {
		PDDLProblem result = new PDDLProblem();
		result.setProblem((Problem) visitFormulaproblem(ctx.formulaproblem()));
		result.setDomain((Domain) visitFormuladomain(ctx.formuladomain()));
		if (ctx.formularequirements()!=null)
			result.setRequirements((Requirements) visitFormularequirements(ctx.formularequirements()));
		if (ctx.formulaobjects()!=null)
			result.setObjects((Objects) visitFormulaobjects(ctx.formulaobjects()));
		result.setInit((Initialization) visitFormulainit(ctx.formulainit()));
		result.setGoal((Goal) visitFormulagoal(ctx.formulagoal()));
		//if () result.setLengthSpec(visitFormulalengthspec(ctx.formulalengthspec()));
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitFormulaproblem(PDDLProblemGrammarParser.FormulaproblemContext ctx) {
		return (SimpleFormula) new Problem(ctx.SIMPLENAME().toString());
	}

	@Override
	public SimpleFormula visitFormuladomain(PDDLProblemGrammarParser.FormuladomainContext ctx) {
		return (SimpleFormula) new Domain(ctx.SIMPLENAME().toString());
	}

	@Override
	public SimpleFormula visitFormularequirements(PDDLProblemGrammarParser.FormularequirementsContext ctx) {
		Requirements result = new Requirements();
		for(TerminalNode t:ctx.REQUIREMENTTYPES()) result.addRequirement(t.toString());
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitFormulaobjects(PDDLProblemGrammarParser.FormulaobjectsContext ctx) {
		Objects result = new Objects();
		for (PDDLProblemGrammarParser.VariableContext var:ctx.variable()){
			SimpleVariable variable = (SimpleVariable) visitVariable(var);
			result.addObject(variable);
		}
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitVariable(PDDLProblemGrammarParser.VariableContext ctx) {
		String name = ctx.SIMPLENAME().get(0).getText();
		String type = null;
		if (ctx.SIMPLENAME().size()==2) type = ctx.SIMPLENAME().get(1).getText();
		SimpleVariable result = new SimpleVariable(name,type);
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitFormulainit(PDDLProblemGrammarParser.FormulainitContext ctx) {

		Initialization result = new Initialization();
		for (int i = 0;i<ctx.initelement().size();i++){
			result.addInitialization((Logic_PREDICATE) visitInitelement(ctx.initelement(i)));
		}

		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitInitelement(PDDLProblemGrammarParser.InitelementContext ctx) {
		Logic_PREDICATE result = (Logic_PREDICATE) visitPredicate(ctx.predicate());
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitFormulagoal(PDDLProblemGrammarParser.FormulagoalContext ctx) {
		return visitGoalelement(ctx.goalelement());
	}

	@Override
	public SimpleFormula visitGoalelement(PDDLProblemGrammarParser.GoalelementContext ctx) {
		Goal result = new Goal((BASE_FORMULA) visitFormula(ctx.formula()));
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitFormula(PDDLProblemGrammarParser.FormulaContext ctx) {
		BASE_FORMULA result;
		if (ctx.AND()!=null){
			//la formula può esser scomposta in "and formula*"
			Logic_AND formula = new Logic_AND();
			for (int i=0;i<ctx.formula().size();i++){
				BASE_FORMULA formula2 = (BASE_FORMULA) visitFormula(ctx.formula(i));
				formula.addFormula(formula2);
			}
			result = formula;
		}else if (ctx.OR() != null){
			//la formula può esser scomposta in "or formula*"
			Logic_OR formula = new Logic_OR();
			for (int i=0;i<ctx.formula().size();i++){
				BASE_FORMULA formula2 = (BASE_FORMULA) visitFormula(ctx.formula(i));
				formula.addFormula(formula2);
			}
			result = formula;
		}else if (ctx.IMPLY() != null){
			//la formula può esser scomposta in "imply formula formula"
			Logic_IMPLY formula = new Logic_IMPLY();
			formula.addFormula((BASE_FORMULA) visitFormula(ctx.formula(0)));
			formula.addFormula((BASE_FORMULA) visitFormula(ctx.formula(1)));
			result = formula;
		}else if (ctx.FORALL() != null){
			//la formula può esser scomposta in "forall Simplename+ formula"
			Logic_FORALL formula = new Logic_FORALL();
			for (TerminalNode t:ctx.SIMPLENAME()) formula.addSimplename(t.toString());
			formula.addFormula((BASE_FORMULA) visitFormula(ctx.formula(0)));
			result = formula;
		}else if (ctx.NOT() != null){
			//la formula può esser scomposta in "not formula"
			Logic_NOT formula = new Logic_NOT();
			formula.addFormula((BASE_FORMULA) visitFormula(ctx.formula(0)));
			result = formula;
		}else if (ctx.predicate() != null){
			//la formula è un predicato
			result = (Logic_PREDICATE) visitPredicate(ctx.predicate());
		}else{
			//ultimo caso: la formula è semplicemente contornata da parentesi
			Logic_BRACKETS formula = new Logic_BRACKETS();
			formula.addFormula((BASE_FORMULA) visitFormula(ctx.formula(0)));
			result = formula;
		}
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitPredicate(PDDLProblemGrammarParser.PredicateContext ctx) {
		Logic_PREDICATE result = new Logic_PREDICATE();
		result.setName((String) visitName(ctx.name()));
		for (TerminalNode t:ctx.SIMPLENAME()) {
			SimpleVariable variable = new SimpleVariable(t.toString().replace("//s+",""),"");
			result.addVariable(variable);
		}
		return (SimpleFormula) result;
	}

	@Override
	public SimpleFormula visitName(PDDLProblemGrammarParser.NameContext ctx) {
		return (SimpleFormula) ctx.SIMPLENAME().toString();
	}
}

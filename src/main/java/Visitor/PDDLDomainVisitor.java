package Visitor;

import ANTLRGenerated.PDDLDomainGrammarBaseVisitor;
import ANTLRGenerated.PDDLDomainGrammarParser.*;
import FormulaComponents.BaseComponents.*;
import FormulaComponents.Formula;
import FormulaComponents.SimpleAction;
import FormulaComponents.SimplePredicate;
import FormulaComponents.SimpleVariable;
import PDDLFormulaContainer.*;
import PDDLFormulaContainer.SubComponents.*;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created by loren on 20/07/2016.
 */
public class PDDLDomainVisitor<SimpleFormula> extends PDDLDomainGrammarBaseVisitor<SimpleFormula> {
	@Override
	public SimpleFormula visitStart(StartContext ctx) {
		return visitPddlfile(ctx.pddlfile());
	}

	@Override
	public SimpleFormula visitPddlfile(PddlfileContext ctx) {
		PDDLDomain formula = new PDDLDomain();
		formula.setDomain((Domain) visitFormuladefine(ctx.formuladefine()));
		if (ctx.formularequirements()!=null)
			formula.setRequirements((Requirements) visitFormularequirements(ctx.formularequirements()));
		if (ctx.formulatypes()!=null)
			formula.setTypes((Types) visitFormulatypes(ctx.formulatypes()));
		if (ctx.formulaconstants()!=null)
			formula.setConstants((Constants) visitFormulaconstants(ctx.formulaconstants()));
		if (ctx.formulapredicates()!=null)
			formula.setPredicates((Predicates) visitFormulapredicates(ctx.formulapredicates()));
		formula.setStructure((Structure) visitFormulastructure(ctx.formulastructure()));
		return (SimpleFormula) formula;
	}

	@Override
	public SimpleFormula visitFormuladefine(FormuladefineContext ctx) {
		return (SimpleFormula) new Domain(ctx.SIMPLENAME().toString());
	}

	@Override
	public SimpleFormula visitFormularequirements(FormularequirementsContext ctx) {
		Requirements r = new Requirements();
		for (TerminalNode n:ctx.REQUIREMENTTYPES()) {
			r.addRequirement(n.getText());
			System.out.println(n.getText());
		}
		return (SimpleFormula) r;
	}

	@Override
	public SimpleFormula visitFormulatypes(FormulatypesContext ctx) {
		Types t = new Types();
		for (TerminalNode n:ctx.SIMPLENAME()){
			t.addType(n.getText());
			System.out.println(n.getText());
		}
		return (SimpleFormula) t;
	}

	@Override
	public SimpleFormula visitFormulaconstants(FormulaconstantsContext ctx) {
		Constants c = new Constants();
		for (int i = 0;i<ctx.variables().size();i++){
			SimpleVariable con = (SimpleVariable) visitVariables(ctx.variables(i));
			c.addConstant(con);
			System.out.println(con);
		}
		return (SimpleFormula) c;
	}

	@Override
	public SimpleFormula visitVariables(VariablesContext ctx) {
		SimpleVariable con = new SimpleVariable(ctx.VARIABLE().getText(),ctx.VARIABLETYPE().getText());
		return (SimpleFormula) con;
	}

	@Override
	public SimpleFormula visitFormulapredicates(FormulapredicatesContext ctx) {
		Predicates p = new Predicates();
		for(int i = 0;i<ctx.supportTagPredicates().size();i++){
			p.addPredicate((SimplePredicate) visitSupportTagPredicates(ctx.supportTagPredicates().get(i)));
		}
		return (SimpleFormula) p;
	}

	@Override
	public SimpleFormula visitSupportTagPredicates(SupportTagPredicatesContext ctx) {
		SimplePredicate p = new SimplePredicate();
		p.name = ctx.SIMPLENAME().getText();
		for (VariablesContext n:ctx.variables()){
			String name = n.VARIABLE().getText();
			String type = "";
			if (n.getChildCount()>1) type = n.VARIABLETYPE().getText();
			SimpleVariable var = new SimpleVariable(name, type);
			p.variables.add(var);
		}
		return (SimpleFormula) p;
	}

	@Override
	public SimpleFormula visitFormulastructure(FormulastructureContext ctx) {
		Structure structure = new Structure();
		for (ActiondefinitionContext n:ctx.actiondefinition()) {
			SimpleAction action = (SimpleAction) visitActiondefinition(n);
			structure.addAction(action);
		}
		return (SimpleFormula) structure;
	}

	@Override
	public SimpleFormula visitActiondefinition(ActiondefinitionContext ctx) {
		SimpleAction action = new SimpleAction();
		action.setName(ctx.SIMPLENAME().getText());

		if (ctx.parametersdefinition()!=null) {
			SimpleAction temp = (SimpleAction) visitParametersdefinition(ctx.parametersdefinition());
			if (temp!=null) action.addAllParameter(temp.getParameters());
		}
		Formula formula;
		if (ctx.preconditiondefinition()!=null) {
			formula = (Formula) visitPreconditiondefinition(ctx.preconditiondefinition());
			if (formula != null) action.setPreconditions(formula);
		}

		formula = (Formula) visitEffectsdefinition(ctx.effectsdefinition());
		if (formula != null) action.setEffects(formula);

		return (SimpleFormula) action;
	}

	@Override
	public SimpleFormula visitParametersdefinition(ParametersdefinitionContext ctx) {
		SimpleAction action = new SimpleAction();
		for (TerminalNode t:ctx.VARIABLE()) {
			String temp = t.getText().substring(1,t.getText().length());
			temp = temp.replace("//s+","");
			action.addParameter(new SimpleVariable(temp,null));
		}
		return (SimpleFormula) action;
	}

	@Override
	public SimpleFormula visitPreconditiondefinition(PreconditiondefinitionContext ctx) {
		Formula formula = new Formula();
		formula.setFormula((BASE_FORMULA) visitFormula(ctx.formula()));
		return (SimpleFormula) formula;
	}

	@Override
	public SimpleFormula visitEffectsdefinition(EffectsdefinitionContext ctx) {
		Formula formula = new Formula();
		formula.setFormula((BASE_FORMULA) visitFormula(ctx.formula()));
		return (SimpleFormula) formula;
	}

	@Override
	public SimpleFormula visitFormula(FormulaContext ctx) {
		BASE_FORMULA formula = null;

		if (ctx.LOGIC_NOT()!=null){
			Logic_NOT not = new Logic_NOT();
			not.addFormula((BASE_FORMULA) visitFormula(ctx.formula(0)));
			formula = not;

		}else if (ctx.LOGIC_AND()!=null){
			Logic_AND and = new Logic_AND();
			for (int i =0;i<ctx.formula().size();i++) and.addFormula((BASE_FORMULA) visitFormula(ctx.formula(i)));
			formula = and;

		}else if (ctx.LOGIC_EQUALS()!=null) {
			Logic_EQUALS equals = new Logic_EQUALS();
			equals.addFormula((BASE_FORMULA) visitFormula(ctx.formula(0)));
			equals.addFormula((BASE_FORMULA) visitFormula(ctx.formula(1)));
			formula = equals;

		}else if (ctx.LOGIC_OR()!=null){
			Logic_OR or = new Logic_OR();
			for (int i =0;i<ctx.formula().size();i++) or.addFormula((BASE_FORMULA) visitFormula(ctx.formula(i)));
			formula = or;

		}else if (ctx.LOGIC_IMPLY()!=null){
			Logic_IMPLY imply = new Logic_IMPLY();
			imply.addFormula((BASE_FORMULA) visitFormula(ctx.formula(0)));
			imply.addFormula((BASE_FORMULA) visitFormula(ctx.formula(1)));
			formula = imply;

		}else if (ctx.LOGIC_WHEN()!=null){
			Logic_WHEN when = new Logic_WHEN();
			when.addFormula((BASE_FORMULA) visitFormula(ctx.formula(0)));
			when.addFormula((BASE_FORMULA) visitFormula(ctx.formula(1)));
			formula = when;

		}else if (ctx.LOGIC_FORALL()!=null){
			Logic_FORALL forall = new Logic_FORALL();
			for (TerminalNode t:ctx.SIMPLENAME()) forall.addSimplename(t.getText());
			forall.addFormula((BASE_FORMULA) visitFormula(ctx.formula(0)));
			formula = forall;

		} else if (ctx.predicate() != null){
			Logic_PREDICATE predicate = new Logic_PREDICATE();
			predicate.setName(ctx.predicate().SIMPLENAME().toString());
			for (VariablesContext vc:ctx.predicate().variables()) {
				String temp = vc.getText().substring(1,vc.getText().length());
				SimpleVariable simpleVariable = new SimpleVariable(temp.replace("//s+",""),null);
				predicate.addVariable(simpleVariable);
			}
			formula = predicate;

		}else if (ctx.LB()!=null) {
			Logic_BRACKETS brackets = new Logic_BRACKETS();
			brackets.addFormula((BASE_FORMULA) visitFormula(ctx.formula(0)));
			formula = brackets;

		}else System.err.println("ERROR");

		return (SimpleFormula) formula;
	}

	@Override
	public SimpleFormula visitPredicate(PredicateContext ctx) {
		String result = "";
		result+="( "+ctx.SIMPLENAME().getText();
		for(int i=0;i<ctx.variables().size();i++){
			result+=" "+ctx.variables(i).getText().replace("//s+","");
		}
		result+=" )";
		return (SimpleFormula) result;
	}
}

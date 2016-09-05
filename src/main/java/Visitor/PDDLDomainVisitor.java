package Visitor;

import ANTLRgenerated.PDDLDomainGrammarBaseVisitor;
import ANTLRgenerated.PDDLDomainGrammarParser.*;
import FormulaComponents.BaseComponents.*;
import FormulaComponents.SimpleAction;
import FormulaComponents.SimplePredicate;
import FormulaComponents.SimpleVariable;
import PDDLFormulaContainer.*;
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
		formula.setDefine((DefineFormula) visitFormuladefine(ctx.formuladefine()));
		if (ctx.formularequirements()!=null)
			formula.setRequirements((RequirementsFormula) visitFormularequirements(ctx.formularequirements()));
		if (ctx.formulatypes()!=null)
			formula.setTypes((TypesFormula) visitFormulatypes(ctx.formulatypes()));
		if (ctx.formulaconstants()!=null)
			formula.setConstants((ConstantsFormula) visitFormulaconstants(ctx.formulaconstants()));
		if (ctx.formulapredicates()!=null)
			formula.setPredicates((PredicatesFormula) visitFormulapredicates(ctx.formulapredicates()));
		formula.setStructure((StructureFormula) visitFormulastructure(ctx.formulastructure()));
		return (SimpleFormula) formula;
	}

	@Override
	public SimpleFormula visitFormuladefine(FormuladefineContext ctx) {
		return (SimpleFormula) new DefineFormula(ctx.SIMPLENAME().toString());
	}

	@Override
	public SimpleFormula visitFormularequirements(FormularequirementsContext ctx) {
		RequirementsFormula r = new RequirementsFormula();
		for (TerminalNode n:ctx.REQUIREMENTTYPES()) {
			r.addRequirement(n.getText());
			System.out.println(n.getText());
		}
		return (SimpleFormula) r;
	}

	@Override
	public SimpleFormula visitFormulatypes(FormulatypesContext ctx) {
		TypesFormula t = new TypesFormula();
		for (TerminalNode n:ctx.SIMPLENAME()){
			t.addType(n.getText());
			System.out.println(n.getText());
		}
		return (SimpleFormula) t;
	}

	@Override
	public SimpleFormula visitFormulaconstants(FormulaconstantsContext ctx) {
		ConstantsFormula c = new ConstantsFormula();
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
		PredicatesFormula p = new PredicatesFormula();
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
		StructureFormula structure = new StructureFormula();
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

		SimpleAction temp = (SimpleAction) visitParametersdefinition(ctx.parametersdefinition());
		if (temp!=null) action.addAllParameter(temp.getParameters());

		Formula formula = (Formula) visitPreconditiondefinition(ctx.preconditiondefinition());
		if (formula != null)action.setPreconditions(formula);

		formula = (Formula) visitEffectsdefinition(ctx.effectsdefinition());
		if (formula != null) action.setEffects(formula);

		return (SimpleFormula) action;
	}

	@Override
	public SimpleFormula visitParametersdefinition(ParametersdefinitionContext ctx) {
		SimpleAction action = new SimpleAction();
		for (TerminalNode t:ctx.VARIABLE()) {
			action.addParameter(t.getText().substring(1,t.getText().length()));
		}
		return (SimpleFormula) action;
	}

	@Override
	public SimpleFormula visitPreconditiondefinition(PreconditiondefinitionContext ctx) {
		Formula formula = new Formula();
		formula.setFormula((PDDLFormula) visitFormula(ctx.formula()));
		return (SimpleFormula) formula;
	}

	@Override
	public SimpleFormula visitEffectsdefinition(EffectsdefinitionContext ctx) {
		Formula formula = new Formula();
		formula.setFormula((PDDLFormula) visitFormula(ctx.formula()));
		return (SimpleFormula) formula;
	}

	@Override
	public SimpleFormula visitFormula(FormulaContext ctx) {
		PDDLFormula formula = null;

		if (ctx.LOGIC_NOT()!=null){
			PDDLComponentNOT not = new PDDLComponentNOT();
			not.addFormula((PDDLFormula) visitFormula(ctx.formula(0)));
			formula = not;

		}else if (ctx.LOGIC_AND()!=null){
			PDDLComponentAND and = new PDDLComponentAND();
			for (int i =0;i<ctx.formula().size();i++) and.addFormula((PDDLFormula) visitFormula(ctx.formula(i)));
			formula = and;

		}else if (ctx.LOGIC_EQUALS()!=null) {
			PDDLComponentEQUALS equals = new PDDLComponentEQUALS();
			equals.addFormula((PDDLFormula) visitFormula(ctx.formula(0)));
			equals.addFormula((PDDLFormula) visitFormula(ctx.formula(1)));
			formula = equals;

		}else if (ctx.LOGIC_OR()!=null){
			PDDLComponentOR or = new PDDLComponentOR();
			for (int i =0;i<ctx.formula().size();i++) or.addFormula((PDDLFormula) visitFormula(ctx.formula(i)));
			formula = or;

		}else if (ctx.LOGIC_IMPLY()!=null){
			PDDLComponentIMPLY imply = new PDDLComponentIMPLY();
			imply.addFormula((PDDLFormula) visitFormula(ctx.formula(0)));
			imply.addFormula((PDDLFormula) visitFormula(ctx.formula(1)));
			formula = imply;

		}else if (ctx.LOGIC_WHEN()!=null){
			PDDLComponentWHEN when = new PDDLComponentWHEN();
			when.addFormula((PDDLFormula) visitFormula(ctx.formula(0)));
			when.addFormula((PDDLFormula) visitFormula(ctx.formula(1)));
			formula = when;

		}else if (ctx.LOGIC_FORALL()!=null){
			PDDLComponentFORALL forall = new PDDLComponentFORALL();
			for (TerminalNode t:ctx.SIMPLENAME()) forall.addSimplename(t.getText());
			forall.addFormula((PDDLFormula) visitFormula(ctx.formula(0)));
			formula = forall;

		} else if (ctx.predicate() != null){
			PDDLComponentPREDICATE predicate = new PDDLComponentPREDICATE();
			predicate.setName(ctx.predicate().SIMPLENAME().toString());
			for (VariablesContext vc:ctx.predicate().variables())
				predicate.addVariable(vc.getText().substring(1,vc.getText().length()));
			formula = predicate;

		}else if (ctx.LB()!=null) {
			PDDLComponentBRACKETS brackets = new PDDLComponentBRACKETS();
			brackets.addFormula((PDDLFormula) visitFormula(ctx.formula(0)));
			formula = brackets;

		}else if (ctx.SIMPLENAME()!=null){
			PDDLComponentNAME name = new PDDLComponentNAME();
			name.setName(ctx.SIMPLENAME(0).toString());
			formula = name;

		}else System.err.println("ERROR");

		return (SimpleFormula) formula;
	}

	@Override
	public SimpleFormula visitPredicate(PredicateContext ctx) {
		String result = "";
		result+="( "+ctx.SIMPLENAME().getText();
		for(int i=0;i<ctx.variables().size();i++){
			result+=" "+ctx.variables(i).getText();
		}
		result+=" )";
		return (SimpleFormula) result;
	}
}

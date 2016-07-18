grammar PDDLGrammar;
import FORMULAGrammar,REQUIREMENTSGrammar;

pddlfile
	: defineformula tagrequirements? ENDOFFILEFORMULA
	;
tagrequirements
	: formularequirements? tagtypes
	;
tagtypes
	: formulatypes? tagconstants
	;
tagconstants
	: formulaconstants? tagpredicates
	;
tagpredicates
	: formulapredicates? tagconstraints
	;
tagconstraints
	: formulaconstraints? tagstructure
	;
tagstructure
	: formulastructure+
	;


defineformula
	: '(define (domain 'SIMPLENAME')'
	;
formularequirements
	: '(:requirements'(':'REQUIREMENTTYPES)+')'
	;
formulatypes
	: '(:types'(SIMPLENAME)+')'
	;
formulaconstants
	:'(:costants'(SIMPLENAME ('-'SIMPLENAME)?)+')'
	;
formulapredicates
	: '(:predicates'(SIMPLENAME (VARIABLES)*)+')'
	;
formulaconstraints
	: '(:constraints'CONSTRAINTS')'
	;
formulastructure
	: ACTIONDEFINITION
//	| DERIVEDDEFINITION         TODO:implement derived-predicates
//	| DURATIVEACTIONDEFINITION  TODO:implement durative-actions
	;

ACTIONDEFINITION
	: '(:action'SIMPLENAME ':parameters('SIMPLENAME*')'ACTIONBODYDEFINITION
	;
ACTIONBODYDEFINITION
	: (':precondition'(PRECOGFORMULA)*)?' '(':effects'(EFFECTFORMULA)*)?
	;
PRECOGFORMULA
	: 'and'PRECOGFORMULA*
	| 'forall'PRECOGFORMULA*
	| 'preference' SIMPLENAME FORMULA
	| FORMULA
	;
EFFECTFORMULA
	: '(and'EFFECTS*')'
	| EFFECTS
	;
EFFECTS
	: 'forall''('SIMPLENAME*')' EFFECTFORMULA
	| 'when'FORMULA FINALEFFECTS
	| FINALEFFECTS
	;
FINALEFFECTS
	: 'not' SIMPLENAME
	| SIMPLENAME
	;




ENDOFFILEFORMULA
	: ')' EOF
	;

WS : (' ' | '\t' | '\r' | '\n')+ -> skip;


//See http://www.plg.inf.uc3m.es/ipc2011-deterministic/attachments/Resources/kovacs-pddl-3.1-2011.pdf
//for references
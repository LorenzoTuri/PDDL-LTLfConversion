grammar PDDLDomainGrammar;
import REQUIREMENTSGrammar;
@header {
   package ANTLRGenerated;
}

start:pddlfile EOF;

pddlfile
	: LB formuladefine formularequirements? formulatypes? formulaconstants? formulapredicates? /*formulaconstraints?*/ formulastructure RB
	;

formuladefine
	: DEFINETAG LB DOMAINTAG SIMPLENAME RB
	;
formularequirements
	: LB REQUIREMENTTAG (REQUIREMENTTYPES)+ RB
	;
formulatypes
	: LB TYPETAG (SIMPLENAME)+ RB
	;
formulaconstants
	: LB CONSTANTTAG (variables)+ RB
	;
formulapredicates
	: LB PREDICATETAG supportTagPredicates+ RB
	;
supportTagPredicates :
	(LB SIMPLENAME (variables)* RB)
	;
/*formulaconstraints
	: LB CONSTRAINTTAG constraints RB
	;*/
formulastructure
	: actiondefinition*
//	| DERIVEDDEFINITION         TODO:implement derived-predicates
//	| DURATIVEACTIONDEFINITION  TODO:implement durative-actions
	;

actiondefinition
	: LB ACTIONTAG SIMPLENAME parametersdefinition? preconditiondefinition? effectsdefinition? RB
	;
parametersdefinition
	: PARAMETERSTAG LB (VARIABLE)+ RB
	;

preconditiondefinition
	: PRECONDITIONTAG LB formula RB
	;

effectsdefinition
	: EFFECTTAG LB formula RB
	;

predicate
	: SIMPLENAME (variables)*
	;
variables
	: VARIABLE VARIABLETYPE?
	;

/*constraints
	: (LB (condition|formula) RB)+
	;
condition //TODO: rimettere a posto le condizioni, ora sono sbagliate
	: CONDITION_ALWAYS
	| CONDITION_SOMETIME
	| CONDITION_WITHIN NUMBER
	| CONDITION_AT_MOST_ONCE
	| CONDITION_SOMETIME_AFTER constraints
	| CONDITION_SOMETIME_BEFORE constraints
	| CONDITION_ALWAYS_WITHIN NUMBER constraints
	| CONDITION_HOLD_DURING NUMBER NUMBER
	| CONDITION_HOLD_AFTER NUMBER
	;*/
formula
	: LB predicate RB
	| LB formula RB
	| LOGIC_NOT formula
	| LOGIC_EQUALS formula formula
	| LOGIC_AND formula+
	| LOGIC_OR formula+
	| LOGIC_IMPLY formula formula
//	| LOGIC_EXISTS formula+
	| LOGIC_FORALL LB SIMPLENAME+ RB formula
	| LOGIC_WHEN formula formula
	;

LB : '(';
RB : ')';
DEFINETAG : 'define';
DOMAINTAG: 'domain';
REQUIREMENTTAG: ':requirements';
TYPETAG: ':types';
CONSTANTTAG: ':costants';
PREDICATETAG: ':predicates';
CONSTRAINTTAG: ':constraints';
ACTIONTAG: ':action';
PARAMETERSTAG: ':parameters';
PRECONDITIONTAG: ':precondition';
EFFECTTAG: ':effect';

LOGIC_NOT: 'not' | 'NOT';
LOGIC_EQUALS: '=';
LOGIC_AND: 'and' | 'AND';
LOGIC_OR: 'or' | 'OR';
LOGIC_IMPLY: 'imply' | 'IMPLY';
//LOGIC_EXISTS: 'exists' | 'EXISTS';
LOGIC_FORALL: 'forall' | 'FORALL';
LOGIC_WHEN: 'when' | 'WHEN';

CONDITION_ALWAYS: 'always';
CONDITION_SOMETIME: 'sometime';
CONDITION_WITHIN: 'within';
CONDITION_AT_MOST_ONCE: 'at-most-once';
CONDITION_SOMETIME_AFTER: 'sometime-after';
CONDITION_SOMETIME_BEFORE: 'sometime-before';
CONDITION_ALWAYS_WITHIN: 'always-within';
CONDITION_HOLD_DURING: 'hold-during';
CONDITION_HOLD_AFTER: 'hold-after';

VARIABLE: '?'SIMPLENAME ;
VARIABLETYPE: ' - 'SIMPLENAME ;
SIMPLENAME: ('a'..'z'|'A'..'Z'|'-'|'_')+('a'..'z'|'A'..'Z'|'-'|'_'|'0'..'9')* ;
NUMBER: ('0'..'9')+ ;

COMMENT:
	(('//'('a'..'z'|'A'..'Z'|'('|')'|'-'|'_'|'0'..'9'|'.'|','|';'|':'|' '|'\t')*'\n')
	|('/*'('a'..'z'|'A'..'Z'|'('|')'|'-'|'_'|'0'..'9'|'.'|','|';'|':'|' '|'\t'|'\n')*'*/')) -> skip;
WS : (' ' | '\t' | '\n')+ -> skip;


//See http://www.plg.inf.uc3m.es/ipc2011-deterministic/attachments/Resources/kovacs-pddl-3.1-2011.pdf
//for references
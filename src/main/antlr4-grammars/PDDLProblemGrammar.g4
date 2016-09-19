grammar PDDLProblemGrammar;
import REQUIREMENTSGrammar;
@header {
   package ANTLRGenerated;
}

start: pddlproblemfile EOF;

pddlproblemfile:
	LB DEFINETAG body RB
	;

body:
	formulaproblem
	formuladomain
	formularequirements?
	formulaobjects?
	formulainit
	formulagoal
	/*formulaconstraints?
	formulametricspec?*/
	formulalengthspec?
	;

formulaproblem:
	LB PROBLEMTAG SIMPLENAME RB;
formuladomain:
	LB DOMAINTAG SIMPLENAME RB;
formularequirements:
	LB REQUIREMENTTAG (REQUIREMENTTYPES)+ RB;
formulaobjects:
	LB OBJECTSTAG (variable)+ RB;
formulainit:
	LB INITTAG initelement* RB;
formulagoal:
	LB GOALTAG goalelement RB;
formulalengthspec:
	LB LENGTHSPECTAG lengthspecserial? lengthspecparallel? RB {System.out.println("THE USE OF LENGTHSPEC IS DEPRECATED SINCE PDDL 2.1...\nit is yet implemented but no guarantee it is gonna work properly");};

initelement: predicate
	/*| 'at' NUMBER SIMPLENAME
	| LB '=' functionterm NUMBER RB
	| LB '=' functionterm SIMPLENAME RB*/;

goalelement: formula;

formula: predicate
	| LB formula RB
	| NOT formula
	| LB AND formula* RB
	| LB OR formula* RB
	| LB IMPLY formula formula RB
	/* EXISTENTIAL PRECONDITION ELEMENT*/
	| LB FORALL LB SIMPLENAME+ RB formula RB
	;

predicate: LB name ('?'?SIMPLENAME)+ RB;
name: SIMPLENAME;

lengthspecserial: LB SERIALTAG NUMBER RB;
lengthspecparallel: LB PARALLELTAG NUMBER RB;

variable:
	SIMPLENAME ('-'SIMPLENAME)?
	;

LB : '(';
RB : ')';
DEFINETAG: 'domain';
PROBLEMTAG: 'problem';
DOMAINTAG: ':domain';
REQUIREMENTTAG: ':requirements';
OBJECTSTAG: ':objects';
INITTAG: ':init';
GOALTAG: ':goal';
LENGTHSPECTAG: ':length';
SERIALTAG: ':serial';
PARALLELTAG: ':parallel';

AND: 'and';
OR: 'or';
NOT: 'not';
FORALL: 'forall';
IMPLY: 'imply';

NUMBER: ('0'..'9')+ ;
SIMPLENAME: ('a'..'z'|'A'..'Z')+ ('a'..'z'|'A'..'Z'|'-'|'_'|'0'..'9')* ;

COMMENT:
    (('//'('a'..'z'|'A'..'Z'|'('|')'|'-'|'_'|'0'..'9'|'.'|','|';'|':'|' '|'\t')*'\n')
    |('/*'('a'..'z'|'A'..'Z'|'('|')'|'-'|'_'|'0'..'9'|'.'|','|';'|':'|' '|'\t'|'\n')*'*/')) -> skip;
WS : (' ' | '\t' | '\n')+ -> skip;

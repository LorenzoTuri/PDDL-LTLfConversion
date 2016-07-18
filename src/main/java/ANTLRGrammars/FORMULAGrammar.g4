grammar FORMULAGrammar;


SIMPLENAME
	: ('a'..'z'|'A'..'Z'|'-'|'_')*
	;
NUMBER
	: ('0'..'9')+('.'('0'..'9')+)?
	;
VARIABLES
	: '?'SIMPLENAME ('-'SIMPLENAME)?
	;

CONSTRAINTS
	: '('CONDITION CONSTRAINTS')'
	| FORMULA
	;
CONDITION
	: 'always'
	| 'sometime'
	| 'within' NUMBER
	| 'at-most-once'
	| 'sometime-after'CONSTRAINTS
	| 'sometimebefore'CONSTRAINTS
	| 'always-within'NUMBER CONSTRAINTS
	| 'hold-during' NUMBER NUMBER
	| 'hold-after' NUMBER
	|
	;
FORMULA
	: SIMPLENAME
	| '('FORMULA')'
	| '(=' SIMPLENAME SIMPLENAME')'
	| 'not'FORMULA
	| 'and' FORMULA*
	| 'or' FORMULA*
	| 'imply' FORMULA FORMULA
	| 'exists' FORMULA*
	| 'forall' FORMULA*
	;
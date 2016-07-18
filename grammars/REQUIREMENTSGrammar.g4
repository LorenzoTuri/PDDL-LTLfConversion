grammar REQUIREMENTSGrammar;

requirements
	: REQUIREMENTTYPES
	;

REQUIREMENTTYPES:
	  'strips '                     // basic strips add and deletes
	| 'typing '                     // allow type name in declaration of variables
	| 'equality '                   // support = as boilt in predicate
	| 'conditional-effects'         // allow when in action effects
	| 'negative-precondition '      // allow not in goal description
	| 'disjunctive-precondition '   // allow or in goal description
	| 'existential-precondition'    // allow exists in goal description
	| 'universal-precondition'      // allow for-all in goal description
	| 'quantified-preconditions'    // existential+universal precondition
	| 'adl'                         // strips
									// +typing
									// +negative/disjunctive-precondition
									// +equality
									// +quantified-precondition
									// +conditional-effects
//NOT IMPLEMENTED YET
//	| 'durative-actions'            // allow durative actions
//	| 'duration-inequalities'       // allows inequalities in durative actions
//	| 'derived-predicates'          // allows predicates whose truth value is defined by a formula
//	| 'timed-initial-literals'      // allows the initial state to specify literals that will become true at a specified time.
									// implies durative-actions
//	| 'preferences'                 // allow use of preference in action preconditions and goals
//	| 'constraints'                 // allow use of constraints in domain and problem fields
//  NOT COMPATIBLE WITH LTLf FORMULAS
//	| 'continuous-effects'          //
//	| 'fluents'                     //
//	| 'numeric-fluents'             //
//	| 'action-costs'                //
	;
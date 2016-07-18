package PDDLFormula;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by loren on 18/07/2016.
 */
public class PDDLFormula {
	String domain = "";
	Set<Requirements> pddlRequirementsSet;
	Set<Types> pddlTypesSet;
	Set<Constants> pddlConstantsSet;
	Set<Predicates> pddlPredicatesSet;
	Set<Functions> pddlFunctionsSet;
	Set<Constraints> pddlConstraintsSet;
	Set<Structure> pddlStructureSet;

	PDDLFormula(){
		pddlRequirementsSet = new HashSet<Requirements>();
		pddlTypesSet = new HashSet<Types>();
		pddlConstantsSet = new HashSet<Constants>();
		pddlPredicatesSet = new HashSet<Predicates>();
		pddlFunctionsSet = new HashSet<Functions>();
		pddlConstraintsSet = new HashSet<Constraints>();
		pddlStructureSet = new HashSet<Structure>();
	}

	public void setDomain(String s){ domain = s;}
}

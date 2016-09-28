package Main;

import ANTLRGenerated.PDDLDomainGrammarLexer;
import ANTLRGenerated.PDDLDomainGrammarParser;
import PDDLFormulaContainer.PDDLDomain;
import Visitor.PDDLDomainVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Use this class do read the domain file of a PDDL specification
 */
public class DomainVisit {
	private String file;
	public DomainVisit(String file){
		this.file=file;
	}

	/**
	 * returns a PDDLDomain (a storage type for datas) that should be used in combo with a PDDLProblem to generate a
	 * PDDLWorldDescription
	 * @return
	 */
	public PDDLDomain visit(){
		PDDLDomain result;

		ANTLRInputStream in = new ANTLRInputStream(file);
		PDDLDomainGrammarLexer lexer = new PDDLDomainGrammarLexer(in);
		CommonTokenStream token = new CommonTokenStream(lexer);
		PDDLDomainGrammarParser parser = new PDDLDomainGrammarParser(token);

		ParseTree parseTree = parser.pddlfile();

		PDDLDomainVisitor visitor = new PDDLDomainVisitor();
		return (PDDLDomain) visitor.visit(parseTree);
	}
}

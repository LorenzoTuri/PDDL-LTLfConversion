package Main;

import ANTLRgenerated.PDDLDomainGrammarLexer;
import ANTLRgenerated.PDDLDomainGrammarParser;
import PDDLFormulaContainer.PDDLDomain;
import Visitor.PDDLDomainVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Created by loren on 06/09/2016.
 */
public class DomainVisit {
	private String file;
	DomainVisit(String file){
		this.file=file;
	}
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

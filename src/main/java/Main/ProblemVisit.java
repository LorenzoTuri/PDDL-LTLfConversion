package Main;

import ANTLRGenerated.PDDLProblemGrammarLexer;
import ANTLRGenerated.PDDLProblemGrammarParser;
import PDDLFormulaContainer.PDDLProblem;
import Visitor.PDDLProblemVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Use this class to read a Problem file of a PDDL specification
 */
public class ProblemVisit {
	private String file;
	public ProblemVisit(String file){
		this.file=file;
	}

	/**
	 * returns a PDDLProblem (a storage type for datas) that should be used in combo with a PDDLDomain to generate a
	 * PDDLWorldDescription
	 * @return
	 */
	public PDDLProblem visit(){
		ANTLRInputStream in = new ANTLRInputStream(file);
		PDDLProblemGrammarLexer lexer = new PDDLProblemGrammarLexer(in);
		CommonTokenStream token = new CommonTokenStream(lexer);
		PDDLProblemGrammarParser parser = new PDDLProblemGrammarParser(token);
		ParseTree parseTree = parser.start();
		PDDLProblemVisitor visitor = new PDDLProblemVisitor();
		return (PDDLProblem) visitor.visit(parseTree);
	}
}

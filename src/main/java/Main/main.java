package Main;

import ANTLRgenerated.PDDLDomainGrammarLexer;
import ANTLRgenerated.PDDLDomainGrammarParser;
import Visitor.PDDLDomainVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

/**
 * Created by loren on 18/07/2016.
 */
public class main {
	main(String filepath)throws IOException {

		String PDDLFile = (new loadResources(filepath)).getResource();

		ANTLRInputStream in = new ANTLRInputStream(PDDLFile);
		PDDLDomainGrammarLexer lexer = new PDDLDomainGrammarLexer(in);
		CommonTokenStream token = new CommonTokenStream(lexer);
		PDDLDomainGrammarParser parser = new PDDLDomainGrammarParser(token);

		ParseTree parseTree = parser.pddlfile();

		PDDLDomainVisitor visitor = new PDDLDomainVisitor();
		System.out.println(visitor.visit(parseTree));
	}

	public static void main(String[] args){
		try{new main("PDDLExampleDomainFormula.txt");}
		catch (Exception e){e.printStackTrace();}
	}
}

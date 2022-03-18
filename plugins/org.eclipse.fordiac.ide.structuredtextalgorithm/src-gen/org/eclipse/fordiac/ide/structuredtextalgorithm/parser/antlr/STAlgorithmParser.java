/*
 * generated by Xtext 2.25.0
 */
package org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr.internal.InternalSTAlgorithmParser;
import org.eclipse.fordiac.ide.structuredtextalgorithm.services.STAlgorithmGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class STAlgorithmParser extends AbstractAntlrParser {

	@Inject
	private STAlgorithmGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalSTAlgorithmParser createParser(XtextTokenStream stream) {
		return new InternalSTAlgorithmParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "STAlgorithmSource";
	}

	public STAlgorithmGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(STAlgorithmGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}

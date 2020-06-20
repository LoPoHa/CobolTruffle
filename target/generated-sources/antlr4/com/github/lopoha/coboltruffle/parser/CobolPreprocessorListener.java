// Generated from com\github\lopoha\coboltruffle\parser\CobolPreprocessor.g4 by ANTLR 4.8
package com.github.lopoha.coboltruffle.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CobolPreprocessorParser}.
 */
public interface CobolPreprocessorListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CobolPreprocessorParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(CobolPreprocessorParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolPreprocessorParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(CobolPreprocessorParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolPreprocessorParser#copy}.
	 * @param ctx the parse tree
	 */
	void enterCopy(CobolPreprocessorParser.CopyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolPreprocessorParser#copy}.
	 * @param ctx the parse tree
	 */
	void exitCopy(CobolPreprocessorParser.CopyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolPreprocessorParser#ignored}.
	 * @param ctx the parse tree
	 */
	void enterIgnored(CobolPreprocessorParser.IgnoredContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolPreprocessorParser#ignored}.
	 * @param ctx the parse tree
	 */
	void exitIgnored(CobolPreprocessorParser.IgnoredContext ctx);
}
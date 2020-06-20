// Generated from com\github\lopoha\coboltruffle\parser\Cobol.g4 by ANTLR 4.8
package com.github.lopoha.coboltruffle.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CobolParser}.
 */
public interface CobolListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CobolParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(CobolParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(CobolParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#identificationDivision}.
	 * @param ctx the parse tree
	 */
	void enterIdentificationDivision(CobolParser.IdentificationDivisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#identificationDivision}.
	 * @param ctx the parse tree
	 */
	void exitIdentificationDivision(CobolParser.IdentificationDivisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#programID}.
	 * @param ctx the parse tree
	 */
	void enterProgramID(CobolParser.ProgramIDContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#programID}.
	 * @param ctx the parse tree
	 */
	void exitProgramID(CobolParser.ProgramIDContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#environmentDivision}.
	 * @param ctx the parse tree
	 */
	void enterEnvironmentDivision(CobolParser.EnvironmentDivisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#environmentDivision}.
	 * @param ctx the parse tree
	 */
	void exitEnvironmentDivision(CobolParser.EnvironmentDivisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#configurationSection}.
	 * @param ctx the parse tree
	 */
	void enterConfigurationSection(CobolParser.ConfigurationSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#configurationSection}.
	 * @param ctx the parse tree
	 */
	void exitConfigurationSection(CobolParser.ConfigurationSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#specialNames}.
	 * @param ctx the parse tree
	 */
	void enterSpecialNames(CobolParser.SpecialNamesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#specialNames}.
	 * @param ctx the parse tree
	 */
	void exitSpecialNames(CobolParser.SpecialNamesContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#inputOutputSection}.
	 * @param ctx the parse tree
	 */
	void enterInputOutputSection(CobolParser.InputOutputSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#inputOutputSection}.
	 * @param ctx the parse tree
	 */
	void exitInputOutputSection(CobolParser.InputOutputSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#dataDivision}.
	 * @param ctx the parse tree
	 */
	void enterDataDivision(CobolParser.DataDivisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#dataDivision}.
	 * @param ctx the parse tree
	 */
	void exitDataDivision(CobolParser.DataDivisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#fileSection}.
	 * @param ctx the parse tree
	 */
	void enterFileSection(CobolParser.FileSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#fileSection}.
	 * @param ctx the parse tree
	 */
	void exitFileSection(CobolParser.FileSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#workingStorageSection}.
	 * @param ctx the parse tree
	 */
	void enterWorkingStorageSection(CobolParser.WorkingStorageSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#workingStorageSection}.
	 * @param ctx the parse tree
	 */
	void exitWorkingStorageSection(CobolParser.WorkingStorageSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#linkageSection}.
	 * @param ctx the parse tree
	 */
	void enterLinkageSection(CobolParser.LinkageSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#linkageSection}.
	 * @param ctx the parse tree
	 */
	void exitLinkageSection(CobolParser.LinkageSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#procedureDivision}.
	 * @param ctx the parse tree
	 */
	void enterProcedureDivision(CobolParser.ProcedureDivisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#procedureDivision}.
	 * @param ctx the parse tree
	 */
	void exitProcedureDivision(CobolParser.ProcedureDivisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#procedureUsing}.
	 * @param ctx the parse tree
	 */
	void enterProcedureUsing(CobolParser.ProcedureUsingContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#procedureUsing}.
	 * @param ctx the parse tree
	 */
	void exitProcedureUsing(CobolParser.ProcedureUsingContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void enterVariableDefinition(CobolParser.VariableDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void exitVariableDefinition(CobolParser.VariableDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#variableRedefines}.
	 * @param ctx the parse tree
	 */
	void enterVariableRedefines(CobolParser.VariableRedefinesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#variableRedefines}.
	 * @param ctx the parse tree
	 */
	void exitVariableRedefines(CobolParser.VariableRedefinesContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#variableDataType}.
	 * @param ctx the parse tree
	 */
	void enterVariableDataType(CobolParser.VariableDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#variableDataType}.
	 * @param ctx the parse tree
	 */
	void exitVariableDataType(CobolParser.VariableDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#variableDataTypeString}.
	 * @param ctx the parse tree
	 */
	void enterVariableDataTypeString(CobolParser.VariableDataTypeStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#variableDataTypeString}.
	 * @param ctx the parse tree
	 */
	void exitVariableDataTypeString(CobolParser.VariableDataTypeStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#variableValueString}.
	 * @param ctx the parse tree
	 */
	void enterVariableValueString(CobolParser.VariableValueStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#variableValueString}.
	 * @param ctx the parse tree
	 */
	void exitVariableValueString(CobolParser.VariableValueStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#variableDataTypeNumber}.
	 * @param ctx the parse tree
	 */
	void enterVariableDataTypeNumber(CobolParser.VariableDataTypeNumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#variableDataTypeNumber}.
	 * @param ctx the parse tree
	 */
	void exitVariableDataTypeNumber(CobolParser.VariableDataTypeNumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#variableValueNumber}.
	 * @param ctx the parse tree
	 */
	void enterVariableValueNumber(CobolParser.VariableValueNumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#variableValueNumber}.
	 * @param ctx the parse tree
	 */
	void exitVariableValueNumber(CobolParser.VariableValueNumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#variableSize}.
	 * @param ctx the parse tree
	 */
	void enterVariableSize(CobolParser.VariableSizeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#variableSize}.
	 * @param ctx the parse tree
	 */
	void exitVariableSize(CobolParser.VariableSizeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#functionSection}.
	 * @param ctx the parse tree
	 */
	void enterFunctionSection(CobolParser.FunctionSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#functionSection}.
	 * @param ctx the parse tree
	 */
	void exitFunctionSection(CobolParser.FunctionSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#functionSectionStart}.
	 * @param ctx the parse tree
	 */
	void enterFunctionSectionStart(CobolParser.FunctionSectionStartContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#functionSectionStart}.
	 * @param ctx the parse tree
	 */
	void exitFunctionSectionStart(CobolParser.FunctionSectionStartContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#jumpPoint}.
	 * @param ctx the parse tree
	 */
	void enterJumpPoint(CobolParser.JumpPointContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#jumpPoint}.
	 * @param ctx the parse tree
	 */
	void exitJumpPoint(CobolParser.JumpPointContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#functionSectionEnd}.
	 * @param ctx the parse tree
	 */
	void enterFunctionSectionEnd(CobolParser.FunctionSectionEndContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#functionSectionEnd}.
	 * @param ctx the parse tree
	 */
	void exitFunctionSectionEnd(CobolParser.FunctionSectionEndContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#programExit}.
	 * @param ctx the parse tree
	 */
	void enterProgramExit(CobolParser.ProgramExitContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#programExit}.
	 * @param ctx the parse tree
	 */
	void exitProgramExit(CobolParser.ProgramExitContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CobolParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CobolParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#moveStatement}.
	 * @param ctx the parse tree
	 */
	void enterMoveStatement(CobolParser.MoveStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#moveStatement}.
	 * @param ctx the parse tree
	 */
	void exitMoveStatement(CobolParser.MoveStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#initializeStatement}.
	 * @param ctx the parse tree
	 */
	void enterInitializeStatement(CobolParser.InitializeStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#initializeStatement}.
	 * @param ctx the parse tree
	 */
	void exitInitializeStatement(CobolParser.InitializeStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(CobolParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(CobolParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#ifCondition}.
	 * @param ctx the parse tree
	 */
	void enterIfCondition(CobolParser.IfConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#ifCondition}.
	 * @param ctx the parse tree
	 */
	void exitIfCondition(CobolParser.IfConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#ifNumeric}.
	 * @param ctx the parse tree
	 */
	void enterIfNumeric(CobolParser.IfNumericContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#ifNumeric}.
	 * @param ctx the parse tree
	 */
	void exitIfNumeric(CobolParser.IfNumericContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#ifCompare}.
	 * @param ctx the parse tree
	 */
	void enterIfCompare(CobolParser.IfCompareContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#ifCompare}.
	 * @param ctx the parse tree
	 */
	void exitIfCompare(CobolParser.IfCompareContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#ifSingleValue}.
	 * @param ctx the parse tree
	 */
	void enterIfSingleValue(CobolParser.IfSingleValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#ifSingleValue}.
	 * @param ctx the parse tree
	 */
	void exitIfSingleValue(CobolParser.IfSingleValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(CobolParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(CobolParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#functionCallStatement}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallStatement(CobolParser.FunctionCallStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#functionCallStatement}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallStatement(CobolParser.FunctionCallStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CobolParser#programEnd}.
	 * @param ctx the parse tree
	 */
	void enterProgramEnd(CobolParser.ProgramEndContext ctx);
	/**
	 * Exit a parse tree produced by {@link CobolParser#programEnd}.
	 * @param ctx the parse tree
	 */
	void exitProgramEnd(CobolParser.ProgramEndContext ctx);
}
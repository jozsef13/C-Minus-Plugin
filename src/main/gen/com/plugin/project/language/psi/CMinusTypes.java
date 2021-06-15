// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.plugin.project.language.psi.impl.*;

public interface CMinusTypes {

  IElementType ADDITIVE_EXPRESSION = new CMinusElementType("ADDITIVE_EXPRESSION");
  IElementType ADDITIVE_EXPRESSION_1 = new CMinusElementType("ADDITIVE_EXPRESSION_1");
  IElementType ADD_OPERATION = new CMinusElementType("ADD_OPERATION");
  IElementType ARGS = new CMinusElementType("ARGS");
  IElementType ARG_LIST = new CMinusElementType("ARG_LIST");
  IElementType ARG_LIST_1 = new CMinusElementType("ARG_LIST_1");
  IElementType CALL = new CMinusElementType("CALL");
  IElementType COMPOUND_STMT = new CMinusElementType("COMPOUND_STMT");
  IElementType CONST_DECLARATION = new CMinusElementType("CONST_DECLARATION");
  IElementType DECLARATION = new CMinusElementType("DECLARATION");
  IElementType DECLARATION_LIST = new CMinusElementType("DECLARATION_LIST");
  IElementType DECLARATION_LIST_1 = new CMinusElementType("DECLARATION_LIST_1");
  IElementType EXPRESSION = new CMinusElementType("EXPRESSION");
  IElementType EXPRESSION_STMT = new CMinusElementType("EXPRESSION_STMT");
  IElementType FACTOR = new CMinusElementType("FACTOR");
  IElementType FUN_DECLARATION = new CMinusElementType("FUN_DECLARATION");
  IElementType ITERATION_STMT = new CMinusElementType("ITERATION_STMT");
  IElementType LOCAL_DECLARATIONS = new CMinusElementType("LOCAL_DECLARATIONS");
  IElementType MUL_OPERATION = new CMinusElementType("MUL_OPERATION");
  IElementType PARAM = new CMinusElementType("PARAM");
  IElementType PARAMS = new CMinusElementType("PARAMS");
  IElementType PARAM_LIST = new CMinusElementType("PARAM_LIST");
  IElementType PARAM_LIST_1 = new CMinusElementType("PARAM_LIST_1");
  IElementType PROGRAM = new CMinusElementType("PROGRAM");
  IElementType READ_STATEMENT = new CMinusElementType("READ_STATEMENT");
  IElementType REL_OPERATIONS = new CMinusElementType("REL_OPERATIONS");
  IElementType RETURN_STMT = new CMinusElementType("RETURN_STMT");
  IElementType SELECTION_STMT = new CMinusElementType("SELECTION_STMT");
  IElementType SIMPLE_EXPRESSION = new CMinusElementType("SIMPLE_EXPRESSION");
  IElementType STATEMENT = new CMinusElementType("STATEMENT");
  IElementType STATEMENT_LIST = new CMinusElementType("STATEMENT_LIST");
  IElementType STATEMENT_LIST_1 = new CMinusElementType("STATEMENT_LIST_1");
  IElementType TERM = new CMinusElementType("TERM");
  IElementType TERM_1 = new CMinusElementType("TERM_1");
  IElementType TYPE_SPECIFIER = new CMinusElementType("TYPE_SPECIFIER");
  IElementType VAR = new CMinusElementType("VAR");
  IElementType VAR_DECLARATION = new CMinusElementType("VAR_DECLARATION");
  IElementType WRITE_STATEMENT = new CMinusElementType("WRITE_STATEMENT");

  IElementType ADD = new CMinusTokenType("+");
  IElementType ADDITIVE_EXPRESSION1_1_0 = new CMinusTokenType("additive_expression1_1_0");
  IElementType AND = new CMinusTokenType("&&");
  IElementType ARGS_1_0 = new CMinusTokenType("args_1_0");
  IElementType ARG_LIST1_1_0 = new CMinusTokenType("arg_list1_1_0");
  IElementType ASSIGN = new CMinusTokenType("=");
  IElementType BOOL = new CMinusTokenType("bool");
  IElementType CIN = new CMinusTokenType("cin");
  IElementType COMMA = new CMinusTokenType(",");
  IElementType COMMENT = new CMinusTokenType("COMMENT");
  IElementType CONST = new CMinusTokenType("const");
  IElementType COUT = new CMinusTokenType("cout");
  IElementType CRLF = new CMinusTokenType("CRLF");
  IElementType DECLARATION_LIST1_1_0 = new CMinusTokenType("declaration_list1_1_0");
  IElementType DIVIDE = new CMinusTokenType("/");
  IElementType DOUBLE = new CMinusTokenType("double");
  IElementType ELSE = new CMinusTokenType("else");
  IElementType END_OF_INSTRUCTION = new CMinusTokenType(";");
  IElementType EQUAL = new CMinusTokenType("==");
  IElementType FALSE = new CMinusTokenType("false");
  IElementType FLOAT = new CMinusTokenType("float");
  IElementType GREATER = new CMinusTokenType(">");
  IElementType GREATER_OR_EQUAL = new CMinusTokenType(">=");
  IElementType ID = new CMinusTokenType("ID");
  IElementType IF = new CMinusTokenType("if");
  IElementType INT = new CMinusTokenType("int");
  IElementType LEFT_BRACE = new CMinusTokenType("{");
  IElementType LEFT_BRACKET = new CMinusTokenType("[");
  IElementType LEFT_PARANTHESIS = new CMinusTokenType("(");
  IElementType LOCAL_DECLARATIONS_1_0 = new CMinusTokenType("local_declarations_1_0");
  IElementType MULTIPLY = new CMinusTokenType("*");
  IElementType NOT = new CMinusTokenType("!");
  IElementType NOT_EQUAL = new CMinusTokenType("!=");
  IElementType NUM = new CMinusTokenType("NUM");
  IElementType OR = new CMinusTokenType("||");
  IElementType PARAM_LIST1_1_0 = new CMinusTokenType("param_list1_1_0");
  IElementType READ = new CMinusTokenType("<<");
  IElementType RETURN = new CMinusTokenType("return");
  IElementType RIGHT_BRACE = new CMinusTokenType("}");
  IElementType RIGHT_BRACKET = new CMinusTokenType("]");
  IElementType RIGHT_PARANTHESIS = new CMinusTokenType(")");
  IElementType SMALLER = new CMinusTokenType("<");
  IElementType SMALLER_OR_EQUAL = new CMinusTokenType("<=");
  IElementType STATEMENT_LIST1_1_0 = new CMinusTokenType("statement_list1_1_0");
  IElementType STRING = new CMinusTokenType("string");
  IElementType STRING_LITERAL = new CMinusTokenType("STRING_LITERAL");
  IElementType SUBSTRACT = new CMinusTokenType("-");
  IElementType TERM1_1_0 = new CMinusTokenType("term1_1_0");
  IElementType TRUE = new CMinusTokenType("true");
  IElementType VOID = new CMinusTokenType("void");
  IElementType WHILE = new CMinusTokenType("while");
  IElementType WRITE = new CMinusTokenType(">>");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ADDITIVE_EXPRESSION) {
        return new CMinusAdditiveExpressionImpl(node);
      }
      else if (type == ADDITIVE_EXPRESSION_1) {
        return new CMinusAdditiveExpression1Impl(node);
      }
      else if (type == ADD_OPERATION) {
        return new CMinusAddOperationImpl(node);
      }
      else if (type == ARGS) {
        return new CMinusArgsImpl(node);
      }
      else if (type == ARG_LIST) {
        return new CMinusArgListImpl(node);
      }
      else if (type == ARG_LIST_1) {
        return new CMinusArgList1Impl(node);
      }
      else if (type == CALL) {
        return new CMinusCallImpl(node);
      }
      else if (type == COMPOUND_STMT) {
        return new CMinusCompoundStmtImpl(node);
      }
      else if (type == CONST_DECLARATION) {
        return new CMinusConstDeclarationImpl(node);
      }
      else if (type == DECLARATION) {
        return new CMinusDeclarationImpl(node);
      }
      else if (type == DECLARATION_LIST) {
        return new CMinusDeclarationListImpl(node);
      }
      else if (type == DECLARATION_LIST_1) {
        return new CMinusDeclarationList1Impl(node);
      }
      else if (type == EXPRESSION) {
        return new CMinusExpressionImpl(node);
      }
      else if (type == EXPRESSION_STMT) {
        return new CMinusExpressionStmtImpl(node);
      }
      else if (type == FACTOR) {
        return new CMinusFactorImpl(node);
      }
      else if (type == FUN_DECLARATION) {
        return new CMinusFunDeclarationImpl(node);
      }
      else if (type == ITERATION_STMT) {
        return new CMinusIterationStmtImpl(node);
      }
      else if (type == LOCAL_DECLARATIONS) {
        return new CMinusLocalDeclarationsImpl(node);
      }
      else if (type == MUL_OPERATION) {
        return new CMinusMulOperationImpl(node);
      }
      else if (type == PARAM) {
        return new CMinusParamImpl(node);
      }
      else if (type == PARAMS) {
        return new CMinusParamsImpl(node);
      }
      else if (type == PARAM_LIST) {
        return new CMinusParamListImpl(node);
      }
      else if (type == PARAM_LIST_1) {
        return new CMinusParamList1Impl(node);
      }
      else if (type == PROGRAM) {
        return new CMinusProgramImpl(node);
      }
      else if (type == READ_STATEMENT) {
        return new CMinusReadStatementImpl(node);
      }
      else if (type == REL_OPERATIONS) {
        return new CMinusRelOperationsImpl(node);
      }
      else if (type == RETURN_STMT) {
        return new CMinusReturnStmtImpl(node);
      }
      else if (type == SELECTION_STMT) {
        return new CMinusSelectionStmtImpl(node);
      }
      else if (type == SIMPLE_EXPRESSION) {
        return new CMinusSimpleExpressionImpl(node);
      }
      else if (type == STATEMENT) {
        return new CMinusStatementImpl(node);
      }
      else if (type == STATEMENT_LIST) {
        return new CMinusStatementListImpl(node);
      }
      else if (type == STATEMENT_LIST_1) {
        return new CMinusStatementList1Impl(node);
      }
      else if (type == TERM) {
        return new CMinusTermImpl(node);
      }
      else if (type == TERM_1) {
        return new CMinusTerm1Impl(node);
      }
      else if (type == TYPE_SPECIFIER) {
        return new CMinusTypeSpecifierImpl(node);
      }
      else if (type == VAR) {
        return new CMinusVarImpl(node);
      }
      else if (type == VAR_DECLARATION) {
        return new CMinusVarDeclarationImpl(node);
      }
      else if (type == WRITE_STATEMENT) {
        return new CMinusWriteStatementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

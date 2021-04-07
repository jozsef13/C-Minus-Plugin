// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.plugin.project.language.psi.CMinusTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class CMinusParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return cminusFile(b, l + 1);
  }

  /* ********************************************************** */
  // ADD | SUBSTRACT
  public static boolean add_operation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_operation")) return false;
    if (!nextTokenIs(b, "<add operation>", ADD, SUBSTRACT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADD_OPERATION, "<add operation>");
    r = consumeToken(b, ADD);
    if (!r) r = consumeToken(b, SUBSTRACT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // term add_operation additive_expression | term
  public static boolean additive_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "additive_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADDITIVE_EXPRESSION, "<additive expression>");
    r = additive_expression_0(b, l + 1);
    if (!r) r = term(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // term add_operation additive_expression
  private static boolean additive_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "additive_expression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = term(b, l + 1);
    r = r && add_operation(b, l + 1);
    r = r && additive_expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression COMMA arg_list | expression
  public static boolean arg_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARG_LIST, "<arg list>");
    r = arg_list_0(b, l + 1);
    if (!r) r = expression(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // expression COMMA arg_list
  private static boolean arg_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arg_list_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && arg_list(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // arg_list | /*epsilon*/
  public static boolean args(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "args")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARGS, "<args>");
    r = arg_list(b, l + 1);
    if (!r) r = consumeToken(b, ARGS_1_0);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ID LEFT_PARANTHESIS args RIGHT_PARANTHESIS
  public static boolean call(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "call")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, LEFT_PARANTHESIS);
    r = r && args(b, l + 1);
    r = r && consumeToken(b, RIGHT_PARANTHESIS);
    exit_section_(b, m, CALL, r);
    return r;
  }

  /* ********************************************************** */
  // program*
  static boolean cminusFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cminusFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!program(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cminusFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // LEFT_BRACE local_declarations statement_list RIGHT_BRACE
  public static boolean compound_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compound_stmt")) return false;
    if (!nextTokenIs(b, LEFT_BRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_BRACE);
    r = r && local_declarations(b, l + 1);
    r = r && statement_list(b, l + 1);
    r = r && consumeToken(b, RIGHT_BRACE);
    exit_section_(b, m, COMPOUND_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // var_declaration | fun_declaration
  public static boolean declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration")) return false;
    if (!nextTokenIs(b, "<declaration>", INT, VOID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DECLARATION, "<declaration>");
    r = var_declaration(b, l + 1);
    if (!r) r = fun_declaration(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // declaration declaration_list | declaration
  public static boolean declaration_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_list")) return false;
    if (!nextTokenIs(b, "<declaration list>", INT, VOID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DECLARATION_LIST, "<declaration list>");
    r = declaration_list_0(b, l + 1);
    if (!r) r = declaration(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // declaration declaration_list
  private static boolean declaration_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_list_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = declaration(b, l + 1);
    r = r && declaration_list(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // var ASSIGN expression | simple_expression
  public static boolean expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION, "<expression>");
    r = expression_0(b, l + 1);
    if (!r) r = simple_expression(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // var ASSIGN expression
  private static boolean expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = var(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression END_OF_INSTRUCTION | END_OF_INSTRUCTION
  public static boolean expression_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_stmt")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION_STMT, "<expression stmt>");
    r = expression_stmt_0(b, l + 1);
    if (!r) r = consumeToken(b, END_OF_INSTRUCTION);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // expression END_OF_INSTRUCTION
  private static boolean expression_stmt_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_stmt_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    r = r && consumeToken(b, END_OF_INSTRUCTION);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LEFT_PARANTHESIS expression RIGHT_PARANTHESIS | var | call | NUM
  public static boolean factor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "factor")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FACTOR, "<factor>");
    r = factor_0(b, l + 1);
    if (!r) r = var(b, l + 1);
    if (!r) r = call(b, l + 1);
    if (!r) r = consumeToken(b, NUM);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // LEFT_PARANTHESIS expression RIGHT_PARANTHESIS
  private static boolean factor_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "factor_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_PARANTHESIS);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RIGHT_PARANTHESIS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // type_specifier ID LEFT_PARANTHESIS params RIGHT_PARANTHESIS compound_stmt
  public static boolean fun_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fun_declaration")) return false;
    if (!nextTokenIs(b, "<fun declaration>", INT, VOID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FUN_DECLARATION, "<fun declaration>");
    r = type_specifier(b, l + 1);
    r = r && consumeTokens(b, 0, ID, LEFT_PARANTHESIS);
    r = r && params(b, l + 1);
    r = r && consumeToken(b, RIGHT_PARANTHESIS);
    r = r && compound_stmt(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // WHILE LEFT_PARANTHESIS expression RIGHT_PARANTHESIS statement
  public static boolean iteration_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "iteration_stmt")) return false;
    if (!nextTokenIs(b, WHILE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, WHILE, LEFT_PARANTHESIS);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RIGHT_PARANTHESIS);
    r = r && statement(b, l + 1);
    exit_section_(b, m, ITERATION_STMT, r);
    return r;
  }

  /* ********************************************************** */
  // var_declaration local_declarations | /*epsilon*/
  public static boolean local_declarations(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_declarations")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LOCAL_DECLARATIONS, "<local declarations>");
    r = local_declarations_0(b, l + 1);
    if (!r) r = consumeToken(b, LOCAL_DECLARATIONS_1_0);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // var_declaration local_declarations
  private static boolean local_declarations_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "local_declarations_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = var_declaration(b, l + 1);
    r = r && local_declarations(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MULTIPLY | DIVIDE
  public static boolean mul_operation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_operation")) return false;
    if (!nextTokenIs(b, "<mul operation>", DIVIDE, MULTIPLY)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MUL_OPERATION, "<mul operation>");
    r = consumeToken(b, MULTIPLY);
    if (!r) r = consumeToken(b, DIVIDE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // type_specifier ID | type_specifier ID LEFT_BRACKET RIGHT_BRACKET
  public static boolean param(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "param")) return false;
    if (!nextTokenIs(b, "<param>", INT, VOID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAM, "<param>");
    r = param_0(b, l + 1);
    if (!r) r = param_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // type_specifier ID
  private static boolean param_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "param_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_specifier(b, l + 1);
    r = r && consumeToken(b, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  // type_specifier ID LEFT_BRACKET RIGHT_BRACKET
  private static boolean param_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "param_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_specifier(b, l + 1);
    r = r && consumeTokens(b, 0, ID, LEFT_BRACKET, RIGHT_BRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // param COMMA param_list | param
  public static boolean param_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "param_list")) return false;
    if (!nextTokenIs(b, "<param list>", INT, VOID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAM_LIST, "<param list>");
    r = param_list_0(b, l + 1);
    if (!r) r = param(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // param COMMA param_list
  private static boolean param_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "param_list_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = param(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && param_list(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // param_list | void
  public static boolean params(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "params")) return false;
    if (!nextTokenIs(b, "<params>", INT, VOID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAMS, "<params>");
    r = param_list(b, l + 1);
    if (!r) r = consumeToken(b, VOID);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // declaration_list|COMMENT|CRLF
  static boolean program(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "program")) return false;
    boolean r;
    r = declaration_list(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CRLF);
    return r;
  }

  /* ********************************************************** */
  // SMALLER_OR_EQUAL | SMALLER | GREATER | GREATER_OR_EQUAL | EQUAL | NOT_EQUAL
  public static boolean rel_operations(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rel_operations")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, REL_OPERATIONS, "<rel operations>");
    r = consumeToken(b, SMALLER_OR_EQUAL);
    if (!r) r = consumeToken(b, SMALLER);
    if (!r) r = consumeToken(b, GREATER);
    if (!r) r = consumeToken(b, GREATER_OR_EQUAL);
    if (!r) r = consumeToken(b, EQUAL);
    if (!r) r = consumeToken(b, NOT_EQUAL);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // RETURN END_OF_INSTRUCTION | RETURN expression END_OF_INSTRUCTION
  public static boolean return_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_stmt")) return false;
    if (!nextTokenIs(b, RETURN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseTokens(b, 0, RETURN, END_OF_INSTRUCTION);
    if (!r) r = return_stmt_1(b, l + 1);
    exit_section_(b, m, RETURN_STMT, r);
    return r;
  }

  // RETURN expression END_OF_INSTRUCTION
  private static boolean return_stmt_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_stmt_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RETURN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, END_OF_INSTRUCTION);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IF LEFT_PARANTHESIS expression RIGHT_PARANTHESIS statement | IF LEFT_PARANTHESIS expression RIGHT_PARANTHESIS statement ELSE statement
  public static boolean selection_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "selection_stmt")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = selection_stmt_0(b, l + 1);
    if (!r) r = selection_stmt_1(b, l + 1);
    exit_section_(b, m, SELECTION_STMT, r);
    return r;
  }

  // IF LEFT_PARANTHESIS expression RIGHT_PARANTHESIS statement
  private static boolean selection_stmt_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "selection_stmt_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IF, LEFT_PARANTHESIS);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RIGHT_PARANTHESIS);
    r = r && statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // IF LEFT_PARANTHESIS expression RIGHT_PARANTHESIS statement ELSE statement
  private static boolean selection_stmt_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "selection_stmt_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IF, LEFT_PARANTHESIS);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RIGHT_PARANTHESIS);
    r = r && statement(b, l + 1);
    r = r && consumeToken(b, ELSE);
    r = r && statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // additive_expression rel_operations additive_expression | additive_expression
  public static boolean simple_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SIMPLE_EXPRESSION, "<simple expression>");
    r = simple_expression_0(b, l + 1);
    if (!r) r = additive_expression(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // additive_expression rel_operations additive_expression
  private static boolean simple_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_expression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = additive_expression(b, l + 1);
    r = r && rel_operations(b, l + 1);
    r = r && additive_expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression_stmt | compound_stmt | selection_stmt | iteration_stmt | return_stmt
  public static boolean statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATEMENT, "<statement>");
    r = expression_stmt(b, l + 1);
    if (!r) r = compound_stmt(b, l + 1);
    if (!r) r = selection_stmt(b, l + 1);
    if (!r) r = iteration_stmt(b, l + 1);
    if (!r) r = return_stmt(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // statement statement_list | /*epsilon*/
  public static boolean statement_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATEMENT_LIST, "<statement list>");
    r = statement_list_0(b, l + 1);
    if (!r) r = consumeToken(b, STATEMENT_LIST_1_0);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // statement statement_list
  private static boolean statement_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_list_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = statement(b, l + 1);
    r = r && statement_list(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // factor mul_operation term | factor
  public static boolean term(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TERM, "<term>");
    r = term_0(b, l + 1);
    if (!r) r = factor(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // factor mul_operation term
  private static boolean term_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = factor(b, l + 1);
    r = r && mul_operation(b, l + 1);
    r = r && term(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // INT | VOID
  public static boolean type_specifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_specifier")) return false;
    if (!nextTokenIs(b, "<type specifier>", INT, VOID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE_SPECIFIER, "<type specifier>");
    r = consumeToken(b, INT);
    if (!r) r = consumeToken(b, VOID);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ID | ID LEFT_BRACKET expression RIGHT_BRACKET
  public static boolean var(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "var")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    if (!r) r = var_1(b, l + 1);
    exit_section_(b, m, VAR, r);
    return r;
  }

  // ID LEFT_BRACKET expression RIGHT_BRACKET
  private static boolean var_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "var_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, LEFT_BRACKET);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RIGHT_BRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // type_specifier ID END_OF_INSTRUCTION | type_specifier ID [NUM]
  public static boolean var_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "var_declaration")) return false;
    if (!nextTokenIs(b, "<var declaration>", INT, VOID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VAR_DECLARATION, "<var declaration>");
    r = var_declaration_0(b, l + 1);
    if (!r) r = var_declaration_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // type_specifier ID END_OF_INSTRUCTION
  private static boolean var_declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "var_declaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_specifier(b, l + 1);
    r = r && consumeTokens(b, 0, ID, END_OF_INSTRUCTION);
    exit_section_(b, m, null, r);
    return r;
  }

  // type_specifier ID [NUM]
  private static boolean var_declaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "var_declaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_specifier(b, l + 1);
    r = r && consumeToken(b, ID);
    r = r && var_declaration_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [NUM]
  private static boolean var_declaration_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "var_declaration_1_2")) return false;
    consumeToken(b, NUM);
    return true;
  }

}

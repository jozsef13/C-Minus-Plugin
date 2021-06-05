package com.plugin.project.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.plugin.project.language.psi.CMinusTypes;
import com.intellij.psi.TokenType;
import static com.plugin.project.language.psi.CMinusTypes.*;

%%

%class CMinusLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF=\R
WHITE_SPACE = \s+
COMMENT=("/"\*([^*]|[\r\n]|(\*+([^*/]|[\r\n])))*\*+"/")|("//".*)
D=[0-9]
L=[a-zA-Z]
STRING_LITERAL=\"([^\\\"]|\\.)*\"
NUM=\d+
ID=\w+

%state WAITING_VALUE

%%

<YYINITIAL> {COMMENT}                                       { yybegin(YYINITIAL); return CMinusTypes.COMMENT; }

<WAITING_VALUE> {CRLF}({CRLF}|{WHITE_SPACE})+               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }

({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

{STRING_LITERAL}                                            {return STRING_LITERAL; }

"else"                                                      { return ELSE;}
"if"                                                        { return IF; }
"int"                                                       { return INT; }
"return"                                                    { return RETURN; }
"void"                                                      { return VOID; }
"while"                                                     { return WHILE; }
"true"                                                      { return TRUE; }
"false"                                                     { return FALSE; }
"bool"                                                      { return BOOL; }
"char"                                                      { return CHAR; }
"double"                                                    { return DOUBLE; }
"float"                                                     { return FLOAT; }
"const"                                                     { return CONST; }
"cin"                                                       { return CIN; }
"cout"                                                      { return COUT; }
"<<"                                                        { return READ; }
">>"                                                        { return WRITE; }

"||"                                                        { return OR; }
"&&"                                                        { return AND; }
"<"                                                         { return SMALLER; }
"<="                                                        { return SMALLER_OR_EQUAL; }
">"                                                         { return GREATER; }
">="                                                        { return GREATER_OR_EQUAL; }
"=="                                                        { return EQUAL; }
"!="                                                        { return NOT_EQUAL; }
"+"	                                                        { return ADD; }
"-"	                                                        { return SUBSTRACT; }
"*"                                                         { return MULTIPLY; }
"/"                                                         { return DIVIDE; }
"="	                                                        { return ASSIGN; }
"!"                                                         { return NOT; }
";"	                                                        { return END_OF_INSTRUCTION; }
","                                                         { return COMMA; }
{NUM}                                                       { return NUM; }
{ID}	                                                    { return ID; }
"("                                                         { return LEFT_PARANTHESIS; }
")"                                                         { return RIGHT_PARANTHESIS; }
"["                                                         { return LEFT_BRACKET; }
"]"                                                         { return RIGHT_BRACKET; }
"{"                                                         { return LEFT_BRACE; }
"}"                                                         { return RIGHT_BRACE; }
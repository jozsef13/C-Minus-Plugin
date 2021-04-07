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
ID={L}({L}|{D})*
NUM={D}+

%state WAITING_VALUE

%%

<YYINITIAL> {COMMENT}                                       { yybegin(YYINITIAL); return CMinusTypes.COMMENT; }

<WAITING_VALUE> {CRLF}({CRLF}|{WHITE_SPACE})+               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }

({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

"else"                                                      { return ELSE;}
"if"                                                        { return IF; }
"int"                                                       { return INT; }
"return"                                                    { return RETURN; }
"void"                                                      { return VOID; }
"while"                                                     { return WHILE; }

{ID}	                                                    { return ID; }
{NUM}                                                       { return NUM; }

"+"	                                                        { return ADD; }
"-"	                                                        { return SUBSTRACT; }
"*"                                                         { return MULTIPLY; }
"/"                                                         { return DIVIDE; }
"<"                                                         { return SMALLER; }
"<="                                                        { return SMALLER_OR_EQUAL; }
">"                                                         { return GREATER; }
">="                                                        { return GREATER_OR_EQUAL; }
"=="                                                        { return EQUAL; }
"!="                                                        { return NOT_EQUAL; }
"="	                                                        { return ASSIGN; }
";"	                                                        { return END_OF_INSTRUCTION; }
","                                                         { return COMMA; }
"("                                                         { return LEFT_PARANTHESIS; }
")"                                                         { return RIGHT_PARANTHESIS; }
"["                                                         { return LEFT_BRACKET; }
"]"                                                         { return RIGHT_BRACKET; }
"{"                                                         { return LEFT_BRACE; }
"}"                                                         { return RIGHT_BRACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }
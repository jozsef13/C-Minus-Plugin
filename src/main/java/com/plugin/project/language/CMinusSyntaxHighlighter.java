package com.plugin.project.language;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.plugin.project.language.psi.CMinusTypes;
import org.jetbrains.annotations.NotNull;

public class CMinusSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey SEPARATOR = TextAttributesKey.createTextAttributesKey("CMINUS_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey KEY = TextAttributesKey.createTextAttributesKey("CMINUS_KEY", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey VALUE = TextAttributesKey.createTextAttributesKey("CMINUS_VALUE", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey COMMENT = TextAttributesKey.createTextAttributesKey("CMINUS_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("CMINUS_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
    public static final TextAttributesKey BRACES = TextAttributesKey.createTextAttributesKey("CMINUS_BRACES", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey BRACKETS = TextAttributesKey.createTextAttributesKey("CMINUS_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey COMMA = TextAttributesKey.createTextAttributesKey("CMINUS_COMMA", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey IDENTIFIER = TextAttributesKey.createTextAttributesKey("CMINUS_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey("CMINUS_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey PARENTHESES = TextAttributesKey.createTextAttributesKey("CMINUS_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey SEMICOLON = TextAttributesKey.createTextAttributesKey("CMINUS_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);

    private static final TextAttributesKey[] SEPARATOR_KEYS = new TextAttributesKey[]{SEPARATOR};
    private static final TextAttributesKey[] KEY_KEYS = new TextAttributesKey[]{KEY};
    private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] BAD_CHARACTER_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] BRACES_KEYS = new TextAttributesKey[]{BRACES};
    private static final TextAttributesKey[] BRACKETS_KEYS = new TextAttributesKey[]{BRACKETS};
    private static final TextAttributesKey[] COMMA_KEYS = new TextAttributesKey[]{COMMA};
    private static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{IDENTIFIER};
    private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
    private static final TextAttributesKey[] PARENTHESES_KEYS = new TextAttributesKey[]{PARENTHESES};
    private static final TextAttributesKey[] SEMICOLON_KEYS = new TextAttributesKey[]{SEMICOLON};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @Override
    public @NotNull Lexer getHighlightingLexer() {
        return new CMinusLexerAdapter();
    }

    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHARACTER_KEYS;
        } else if (tokenType.equals(CMinusTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(CMinusTypes.STRING_LITERAL)) {
            return VALUE_KEYS;
        } else if (tokenType.equals(CMinusTypes.LEFT_BRACE) || tokenType.equals(CMinusTypes.RIGHT_BRACE)) {
            return BRACES_KEYS;
        } else if (tokenType.equals(CMinusTypes.LEFT_BRACKET) || tokenType.equals(CMinusTypes.RIGHT_BRACKET)) {
            return BRACKETS_KEYS;
        } else if (tokenType.equals(CMinusTypes.LEFT_PARANTHESIS) || tokenType.equals(CMinusTypes.RIGHT_PARANTHESIS)) {
            return PARENTHESES_KEYS;
        } else if (tokenType.equals(CMinusTypes.COMMA)) {
            return COMMA_KEYS;
        } else if (tokenType.equals(CMinusTypes.END_OF_INSTRUCTION)) {
            return SEMICOLON_KEYS;
        } else if (tokenType.equals(CMinusTypes.ID)) {
            return IDENTIFIER_KEYS;
        } else if (tokenType.equals(CMinusTypes.NUM) || tokenType.equals(CMinusTypes.TRUE) || tokenType.equals(CMinusTypes.FALSE)) {
            return NUMBER_KEYS;
        } else if (tokenType.equals(CMinusTypes.ASSIGN) || tokenType.equals(CMinusTypes.MULTIPLY) ||
                tokenType.equals(CMinusTypes.DIVIDE) || tokenType.equals(CMinusTypes.ADD) ||
                tokenType.equals(CMinusTypes.SUBSTRACT) || tokenType.equals(CMinusTypes.EQUAL) ||
                tokenType.equals(CMinusTypes.NOT_EQUAL) || tokenType.equals(CMinusTypes.GREATER) ||
                tokenType.equals(CMinusTypes.GREATER_OR_EQUAL) || tokenType.equals(CMinusTypes.SMALLER) ||
                tokenType.equals(CMinusTypes.SMALLER_OR_EQUAL) || tokenType.equals(CMinusTypes.AND) ||
                tokenType.equals(CMinusTypes.OR) || tokenType.equals(CMinusTypes.NOT) || tokenType.equals(CMinusTypes.READ) ||
                tokenType.equals(CMinusTypes.WRITE)) {
            return SEPARATOR_KEYS;
        } else if (tokenType.equals(CMinusTypes.INT) || tokenType.equals(CMinusTypes.VOID) ||
                tokenType.equals(CMinusTypes.FLOAT) || tokenType.equals(CMinusTypes.DOUBLE) ||
                tokenType.equals(CMinusTypes.CHAR) || tokenType.equals(CMinusTypes.BOOL) ||
                tokenType.equals(CMinusTypes.RETURN) || tokenType.equals(CMinusTypes.IF) ||
                tokenType.equals(CMinusTypes.WHILE) || tokenType.equals(CMinusTypes.CIN) || tokenType.equals(CMinusTypes.COUT) ||
                tokenType.equals(CMinusTypes.ELSE) || tokenType.equals(CMinusTypes.CONST)) {
            return KEY_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}

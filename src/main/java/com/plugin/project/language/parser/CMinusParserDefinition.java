package com.plugin.project.language.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.plugin.project.language.CMinusLanguage;
import com.plugin.project.language.CMinusLexerAdapter;
import com.plugin.project.language.psi.CMinusFile;
import com.plugin.project.language.psi.CMinusTypes;
import org.jetbrains.annotations.NotNull;

public class CMinusParserDefinition implements ParserDefinition {
    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(CMinusTypes.COMMENT);

    public static final IFileElementType FILE = new IFileElementType(CMinusLanguage.INSTANCE);

    @Override
    public @NotNull Lexer createLexer(Project project) {
        return new CMinusLexerAdapter();
    }

    @Override
    public PsiParser createParser(final Project project) {
        return new CMinusParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public @NotNull TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return TokenSet.create(CMinusTypes.STRING_LITERAL);
    }

    @Override
    public @NotNull PsiElement createElement(ASTNode node) {
        return CMinusTypes.Factory.createElement(node);
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new CMinusFile(viewProvider);
    }

    @Override
    public @NotNull TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @Override
    public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }
}

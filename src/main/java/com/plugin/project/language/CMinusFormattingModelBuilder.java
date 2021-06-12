package com.plugin.project.language;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.TokenSet;
import com.plugin.project.language.psi.CMinusTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CMinusFormattingModelBuilder implements FormattingModelBuilder {

    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings){
        return new SpacingBuilder(settings, CMinusLanguage.INSTANCE)
                .around(CMinusTypes.AND).spaces(1)
                .around(CMinusTypes.OR).spaces(1)
                .after(CMinusTypes.TYPE_SPECIFIER).spaces(1)
                .around(CMinusTypes.REL_OPERATIONS).spaces(1)
                .around(CMinusTypes.ADD_OPERATION).spaces(1)
                .around(CMinusTypes.MUL_OPERATION).spaces(1)
                .after(CMinusTypes.FUN_DECLARATION).spaces(1)
                .after(CMinusTypes.COMMA).spaces(1)
                .after(CMinusTypes.IF).spaces(1)
                .after(CMinusTypes.WHILE).spaces(1)
                .around(CMinusTypes.READ).spaces(1)
                .around(CMinusTypes.WRITE).spaces(1)
                .after(CMinusTypes.CONST).spaces(1)
                .around(CMinusTypes.ELSE).spaces(1)
                .after(CMinusTypes.RETURN).spaces(1);
    }

    @Override
    public @NotNull FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        return new FormattingModel() {
            private FormattingModel myModel;
            {
                myModel = FormattingModelProvider.createFormattingModelForPsiFile(element.getContainingFile(), new CMinusBlock(element, createSpaceBuilder(settings)), settings);
            }
            @Override
            public @NotNull Block getRootBlock() {
                return myModel.getRootBlock();
            }

            @Override
            public @NotNull FormattingDocumentModel getDocumentModel() {
                return myModel.getDocumentModel();
            }

            @Override
            public TextRange replaceWhiteSpace(TextRange textRange, String whiteSpace) {
                return myModel.replaceWhiteSpace(textRange, whiteSpace);
            }

            @Override
            public TextRange shiftIndentInsideRange(ASTNode node, TextRange range, int indent) {
                return myModel.shiftIndentInsideRange(node, range, indent);
            }

            @Override
            public void commitChanges() {
                myModel.commitChanges();
            }
        };
    }

    @Override
    public @Nullable TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}

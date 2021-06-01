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
        SpacingBuilder spacingBuilderToReturn = new SpacingBuilder(settings, CMinusLanguage.INSTANCE);
        spacingBuilderToReturn.append(new SpacingBuilder(settings, CMinusLanguage.INSTANCE).around(CMinusTypes.REL_OPERATIONS).spaceIf(settings.getCommonSettings(CMinusLanguage.INSTANCE.getID()).SPACE_AROUND_RELATIONAL_OPERATORS).before(CMinusTypes.EXPRESSION).none());
        spacingBuilderToReturn.append(new SpacingBuilder(settings, CMinusLanguage.INSTANCE).around(CMinusTypes.ADD_OPERATION).spaceIf(settings.getCommonSettings(CMinusLanguage.INSTANCE.getID()).SPACE_AROUND_ADDITIVE_OPERATORS).before(CMinusTypes.EXPRESSION).none());
        spacingBuilderToReturn.append(new SpacingBuilder(settings, CMinusLanguage.INSTANCE).around(CMinusTypes.MUL_OPERATION).spaceIf(settings.getCommonSettings(CMinusLanguage.INSTANCE.getID()).SPACE_AROUND_MULTIPLICATIVE_OPERATORS).before(CMinusTypes.EXPRESSION).none());
        spacingBuilderToReturn.append(new SpacingBuilder(settings, CMinusLanguage.INSTANCE).around(CMinusTypes.ASSIGN).spaceIf(settings.getCommonSettings(CMinusLanguage.INSTANCE.getID()).SPACE_AROUND_ASSIGNMENT_OPERATORS).before(CMinusTypes.EXPRESSION).none());
//        spacingBuilderToReturn.append(new SpacingBuilder(settings, CMinusLanguage.INSTANCE).around(CMinusTypes.TYPE_SPECIFIER).spaceIf(settings.getCommonSettings(CMinusLanguage.INSTANCE.getID()).SPACE_AFTER_TYPE_CAST).before(CMinusTypes.ID).none());
//        spacingBuilderToReturn.append(new SpacingBuilder(settings, CMinusLanguage.INSTANCE).around(CMinusTypes.COMMA).spaceIf(settings.getCommonSettings(CMinusLanguage.INSTANCE.getID()).SPACE_AFTER_COMMA).before(CMinusTypes.PARAM).none());
//        spacingBuilderToReturn.append(new SpacingBuilder(settings, CMinusLanguage.INSTANCE).around(CMinusTypes.COMMA).spaceIf(settings.getCommonSettings(CMinusLanguage.INSTANCE.getID()).SPACE_AFTER_COMMA).before(CMinusTypes.ARGS).none());

        return new SpacingBuilder(settings, CMinusLanguage.INSTANCE).around(CMinusTypes.ASSIGN).spaceIf(settings.getCommonSettings(CMinusLanguage.INSTANCE.getID()).SPACE_AROUND_ASSIGNMENT_OPERATORS).before(CMinusTypes.EXPRESSION).none();
    }

    @Override
    public @NotNull FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        return FormattingModelProvider.createFormattingModelForPsiFile(element.getContainingFile(), new CMinusBlock(element.getNode(), Wrap.createWrap(WrapType.NONE, false), Alignment.createAlignment(), createSpaceBuilder(settings)), settings);
    }

    @Override
    public @Nullable TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}

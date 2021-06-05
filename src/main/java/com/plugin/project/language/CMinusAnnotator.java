package com.plugin.project.language;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.plugin.project.language.psi.CMinusConstDeclaration;
import com.plugin.project.language.psi.CMinusFunDeclaration;
import com.plugin.project.language.psi.CMinusVarDeclaration;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CMinusAnnotator implements Annotator {

    public static final String CMINUS_PREFIX_STR = "cminus";
    public static final String CMINUS_SEPARATOR_STR = ":";

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof PsiLiteralExpression)) {
            return;
        }

        PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
        String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
        if ((value == null) || !value.startsWith(CMINUS_PREFIX_STR + CMINUS_SEPARATOR_STR)) {
            return;
        }

        TextRange prefixRange = TextRange.from(element.getTextRange().getStartOffset(), CMINUS_PREFIX_STR.length() + 1);
        TextRange separatorRange = TextRange.from(prefixRange.getEndOffset(), CMINUS_SEPARATOR_STR.length());
        TextRange keyRange = new TextRange(separatorRange.getEndOffset(), element.getTextRange().getEndOffset() - 1);

        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(prefixRange).textAttributes(DefaultLanguageHighlighterColors.KEYWORD).create();
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(separatorRange).textAttributes(CMinusSyntaxHighlighter.SEPARATOR).create();

        String key = value.substring(CMINUS_PREFIX_STR.length() + CMINUS_SEPARATOR_STR.length());
        boolean isFunction = false;
        if(key.endsWith("()")){
            isFunction = true;
            String[] tempKey = key.split("\\(\\)");
            List<CMinusFunDeclaration> functionReferences = CMinusUtil.findFunctionReferences(element.getProject(), tempKey[0]);
            if(functionReferences.isEmpty()){
                holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved function reference")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .withFix(new CMinusCreateFunctionQuickFix(key))
                        .create();
            } else {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                        .range(keyRange).textAttributes(CMinusSyntaxHighlighter.VALUE).create();
            }
        } else {
            List<CMinusFunDeclaration> functionReferences = CMinusUtil.findFunctionReferences(element.getProject(), key);

            if(!functionReferences.isEmpty()){
                isFunction = true;
                holder.newAnnotation(HighlightSeverity.ERROR, "Incorrect function reference")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .withFix(new CMinusIncorrectFunctionQuickFix(literalExpression))
                        .create();
            }
        }


        List<CMinusVarDeclaration> variableReferences = CMinusUtil.findVariableReferences(element.getProject(), key);
        List<CMinusConstDeclaration> constReferences = CMinusUtil.findConstantReferences(element.getProject(), key);
        if(variableReferences.isEmpty() && constReferences.isEmpty() && !isFunction){
            holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved variable/constant reference")
                    .range(keyRange)
                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                    .withFix(new CMinusCreateVariableQuickFix(key))
                    .withFix(new CMinusCreateConstantQuickFix(key))
                    .create();
        } else if(!variableReferences.isEmpty() && !constReferences.isEmpty() && !isFunction){
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(keyRange).textAttributes(CMinusSyntaxHighlighter.VALUE).create();
        }
    }
}

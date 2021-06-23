package com.plugin.project.language.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.plugin.project.language.highlighter.CMinusAnnotator;
import org.jetbrains.annotations.NotNull;

public class CMinusReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiLiteralExpression.class),
                new PsiReferenceProvider() {
                    @Override
                    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
                        String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;

                        if(value != null && value.startsWith(CMinusAnnotator.CMINUS_PREFIX_STR + CMinusAnnotator.CMINUS_SEPARATOR_STR)){
                            TextRange reference = new TextRange(CMinusAnnotator.CMINUS_PREFIX_STR.length() + CMinusAnnotator.CMINUS_SEPARATOR_STR.length() + 1, value.length() + 1);
                            return new PsiReference[]{new CMinusJavaReference(element, reference)};
                        }
                        return PsiReference.EMPTY_ARRAY;
                    }
                });
    }
}

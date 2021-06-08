package com.plugin.project.language;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.plugin.project.language.psi.*;
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

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CMinusCall.class), new PsiReferenceProvider() {
            @Override
            public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                CMinusCall call = (CMinusCall) element;
                String value = call.getCallId();
                if(value != null){
                    TextRange reference = new TextRange(0, value.length()+1);
                    return new PsiReference[]{new CMinusLocalReference(element, reference)};
                }
                return PsiReference.EMPTY_ARRAY;
            }
        });

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CMinusVar.class), new PsiReferenceProvider() {
            @Override
            public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                CMinusVar var = (CMinusVar) element;
                String value = var.getVarId();
                if(value != null){
                    TextRange reference = new TextRange(0, value.length()+1);
                    return new PsiReference[]{new CMinusLocalReference(element, reference)};
                }
                return PsiReference.EMPTY_ARRAY;
            }
        });

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CMinusParam.class), new PsiReferenceProvider() {
            @Override
            public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                CMinusParam var = (CMinusParam) element;
                String value = var.getParamId();
                if(value != null){
                    TextRange reference = new TextRange(0, value.length()+1);
                    return new PsiReference[]{new CMinusLocalReference(element, reference)};
                }
                return PsiReference.EMPTY_ARRAY;
            }
        });
        System.out.println(PlatformPatterns.psiElement().withElementType(CMinusTypes.ID));
        System.out.println(" ---- Here we are!!!!!! -----------");
        registrar.registerReferenceProvider(PlatformPatterns.psiElement().withElementType(CMinusTypes.ID), new PsiReferenceProvider() {
            @Override
            public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                PsiElement psiElement = element;
                String value = element.getText();
                if(value != null){
                    TextRange reference = new TextRange(0, value.length()+1);
                    return new PsiReference[]{new CMinusLocalReference(element, reference)};
                }
                return PsiReference.EMPTY_ARRAY;
            }
        });
    }
}

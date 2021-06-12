package com.plugin.project.language;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.plugin.project.language.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class CMinusAnnotator implements Annotator {

    public static final String CMINUS_PREFIX_STR = "cminus";
    public static final String CMINUS_SEPARATOR_STR = ":";

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof PsiLiteralExpression) {
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
            annotateForPsiLitExpAccordingToKey(key, keyRange, holder, element);
        } else {
            String key = "";
            TextRange keyRange = null;
            if (element instanceof CMinusCall) {
                key = ((CMinusCall) element).getCallId();
                keyRange = new TextRange(element.getTextRange().getStartOffset(), element.getTextRange().getEndOffset());
            } else if (element instanceof CMinusVar) {
                key = ((CMinusVar) element).getVarId();
                keyRange = new TextRange(element.getTextRange().getStartOffset(), element.getTextRange().getEndOffset());
            } else if(element instanceof CMinusVarDeclaration) {
                key = ((CMinusVarDeclaration) element).getVarDeclId();
                keyRange = new TextRange(((CMinusVarDeclaration) element).getId().getTextRange().getStartOffset(), ((CMinusVarDeclaration) element).getId().getTextRange().getEndOffset());
            } else if(element instanceof CMinusFunDeclaration) {
                key = ((CMinusFunDeclaration) element).getFunDeclId();
                keyRange = new TextRange(((CMinusFunDeclaration) element).getId().getTextRange().getStartOffset(), ((CMinusFunDeclaration) element).getId().getTextRange().getEndOffset());
            } else if(element instanceof CMinusConstDeclaration) {
                key = ((CMinusConstDeclaration) element).getConstDeclId();
                keyRange = new TextRange(((CMinusConstDeclaration) element).getId().getTextRange().getStartOffset(), ((CMinusConstDeclaration) element).getId().getTextRange().getEndOffset());
            }

            if (!key.isEmpty() && keyRange != null) {
                if(element instanceof CMinusCall || element instanceof CMinusVar){
                    annotateForFactorAccordingToKey(key, keyRange, holder, element);
                } else if(element instanceof CMinusVarDeclaration || element instanceof CMinusConstDeclaration || element instanceof CMinusFunDeclaration){
                    verifyIfDeclarationExists(key, keyRange, holder, element);
                }

            }
        }
    }

    private void verifyIfDeclarationExists(String key, TextRange keyRange, AnnotationHolder holder, PsiElement element) {
        if(element instanceof CMinusVarDeclaration) {
            List<CMinusVarDeclaration> varDeclarations = CMinusUtil.findLocalVariableReferences(element.getContainingFile(), key);
            if(varDeclarations.size() > 1) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Variable already declared")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .create();
            }

            List<CMinusVar> varUsages = CMinusUtil.findLocalVarUsages(element.getContainingFile(), key);
            if(varUsages.isEmpty()){
                holder.newAnnotation(HighlightSeverity.WARNING, "Variable is never used")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.WEAK_WARNING)
                        .create();
            }
        } else if(element instanceof CMinusFunDeclaration) {
            List<CMinusFunDeclaration> functionReferences = CMinusUtil.findLocalFunctionReferences(element.getContainingFile(), key);
            if (functionReferences.size() > 1){
                holder.newAnnotation(HighlightSeverity.ERROR, "Function already declared")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .create();
            }

            if(!key.equals("main")){
                List<CMinusCall> functionCalls = CMinusUtil.findLocalFunctionCalls(element.getContainingFile(), key);
                if(functionCalls.isEmpty()){
                    holder.newAnnotation(HighlightSeverity.WARNING, "Function is never called")
                            .range(keyRange)
                            .highlightType(ProblemHighlightType.WEAK_WARNING)
                            .create();
                }
            }
        } else if(element instanceof CMinusConstDeclaration) {
            List<CMinusConstDeclaration> constReferences = CMinusUtil.findLocalConstantReferences(element.getContainingFile(), key);
            if (constReferences.size() > 1){
                holder.newAnnotation(HighlightSeverity.ERROR, "Constant already declared")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .create();
            }

            List<CMinusVar> constantUsages = CMinusUtil.findLocalVarUsages(element.getContainingFile(), key);
            if(constantUsages.isEmpty()){
                holder.newAnnotation(HighlightSeverity.WARNING, "Constant is never used")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.WEAK_WARNING)
                        .create();
            }
        }
    }

    private void annotateForFactorAccordingToKey(String key, TextRange keyRange, AnnotationHolder holder, PsiElement element) {
        if (element instanceof CMinusCall) {
            String[] tempKey = key.split("\\(");
            List<CMinusFunDeclaration> functionReferences = CMinusUtil.findLocalFunctionReferences(element.getContainingFile(), tempKey[0]);

            if (functionReferences.isEmpty()) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved function reference")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .withFix(new CMinusCreateFunctionQuickFix(key, true))
                        .create();
            } else {
                int args = 0;
                int params = 0;
                boolean flag = false;

                CMinusArgs argsElement = ((CMinusCall) element).getArgs();
                if (argsElement != null) {
                    args++;
                    PsiElement[] argsChildren = Objects.requireNonNull(argsElement.getArgList()).getChildren();
                    for (PsiElement argChild : argsChildren) {
                        if (argChild instanceof CMinusArgList1) {
                            args++;
                        }
                    }
                }

                for (CMinusFunDeclaration funDeclaration : functionReferences) {
                    params = 0;
                    if (flag) {
                        break;
                    }

                    CMinusParams paramsElement = funDeclaration.getParams();
                    if (paramsElement != null) {
                        params++;
                        PsiElement[] paramChildren = Objects.requireNonNull(paramsElement.getParamList()).getChildren();
                        for (PsiElement paramChild : paramChildren) {
                            if (paramChild instanceof CMinusParamList1) {
                                params++;
                            }
                        }
                    }
                    if (args == params) {
                        flag = true;
                    }
                }
                if (flag) {
                    holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                            .range(keyRange).textAttributes(CMinusSyntaxHighlighter.IDENTIFIER).create();
                } else {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Not enough arguments")
                            .range(keyRange)
                            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                            .withFix(new CMinusIncorrectFunctionLocalQuickFix(element, params))
                            .create();
                }
            }
        } else {
            boolean isFunction = false;
            List<CMinusFunDeclaration> functionReferences = CMinusUtil.findLocalFunctionReferences(element.getContainingFile(), key);
            if (!functionReferences.isEmpty()) {
                isFunction = true;
                int params = 0;
                for (CMinusFunDeclaration funDeclaration : functionReferences) {
                    params = 0;

                    CMinusParams paramsElement = funDeclaration.getParams();
                    if (paramsElement != null) {
                        params++;
                        PsiElement[] paramChildren = Objects.requireNonNull(paramsElement.getParamList()).getChildren();
                        for (PsiElement paramChild : paramChildren) {
                            if (paramChild instanceof CMinusParamList1) {
                                params++;
                            }
                        }
                    }
                }
                holder.newAnnotation(HighlightSeverity.ERROR, "Incorrect function reference")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .withFix(new CMinusIncorrectFunctionLocalQuickFix(element, params))
                        .create();
            }

            if (!isFunction) {
                List<CMinusVarDeclaration> variableReferences = CMinusUtil.findLocalVariableReferences(element.getContainingFile(), key);
                List<CMinusConstDeclaration> constReferences = CMinusUtil.findLocalConstantReferences(element.getContainingFile(), key);
                List<CMinusParam> paramReference = CMinusUtil.findLocalParamReferences(element.getContainingFile(), key);
                if (variableReferences.isEmpty() && constReferences.isEmpty() && paramReference.isEmpty()) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved variable/constant reference")
                            .range(keyRange)
                            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                            .withFix(new CMinusCreateVariableQuickFix(key, true))
                            .withFix(new CMinusCreateConstantQuickFix(key, true))
                            .create();
                } else {
                    holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                            .range(keyRange).textAttributes(CMinusSyntaxHighlighter.IDENTIFIER).create();
                }
            }
        }
    }

    private void annotateForPsiLitExpAccordingToKey(String key, TextRange keyRange, AnnotationHolder holder, PsiElement element) {
        boolean isFunction = false;
        if (key.endsWith("()")) {
            isFunction = true;
            String[] tempKey = key.split("\\(\\)");
            List<CMinusFunDeclaration> functionReferences = CMinusUtil.findFunctionReferences(element.getProject(), tempKey[0]);
            if (functionReferences.isEmpty()) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved function reference")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .withFix(new CMinusCreateFunctionQuickFix(key, false))
                        .create();
            } else {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                        .range(keyRange).textAttributes(CMinusSyntaxHighlighter.VALUE).create();
            }
        } else {
            List<CMinusFunDeclaration> functionReferences = CMinusUtil.findFunctionReferences(element.getProject(), key);

            if (!functionReferences.isEmpty()) {
                isFunction = true;
                holder.newAnnotation(HighlightSeverity.ERROR, "Incorrect function reference")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .withFix(new CMinusIncorrectFunctionJavaQuickFix((PsiLiteralExpression) element))
                        .create();
            }
        }


        List<CMinusVarDeclaration> variableReferences = CMinusUtil.findVariableReferences(element.getProject(), key);
        List<CMinusConstDeclaration> constReferences = CMinusUtil.findConstantReferences(element.getProject(), key);
        if (variableReferences.isEmpty() && constReferences.isEmpty() && !isFunction) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved variable/constant reference")
                    .range(keyRange)
                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                    .withFix(new CMinusCreateVariableQuickFix(key, false))
                    .withFix(new CMinusCreateConstantQuickFix(key, false))
                    .create();
        } else if (!variableReferences.isEmpty() && !constReferences.isEmpty() && !isFunction) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(keyRange).textAttributes(CMinusSyntaxHighlighter.VALUE).create();
        }
    }
}

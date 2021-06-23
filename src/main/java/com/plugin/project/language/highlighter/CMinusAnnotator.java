package com.plugin.project.language.highlighter;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.plugin.project.language.highlighter.CMinusSyntaxHighlighter;
import com.plugin.project.language.psi.*;
import com.plugin.project.language.quickFix.*;
import com.plugin.project.language.util.CMinusUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            annotateJavaKeyReferences(key, keyRange, holder, element);
        } else {
            String key = "";
            TextRange keyRange = null;
            if (element instanceof CMinusCall) {
                key = ((CMinusCall) element).getCallId();
                keyRange = new TextRange(element.getTextRange().getStartOffset(), element.getTextRange().getEndOffset());
            } else if (element instanceof CMinusVar) {
                key = ((CMinusVar) element).getVarId();
                keyRange = new TextRange(element.getTextRange().getStartOffset(), element.getTextRange().getEndOffset());
            } else if (element instanceof CMinusVarDeclaration) {
                key = ((CMinusVarDeclaration) element).getVarDeclId();
                keyRange = new TextRange(((CMinusVarDeclaration) element).getId().getTextRange().getStartOffset(), ((CMinusVarDeclaration) element).getId().getTextRange().getEndOffset());
            } else if (element instanceof CMinusFunDeclaration) {
                key = ((CMinusFunDeclaration) element).getFunDeclId();
                keyRange = new TextRange(((CMinusFunDeclaration) element).getId().getTextRange().getStartOffset(), ((CMinusFunDeclaration) element).getId().getTextRange().getEndOffset());
            } else if (element instanceof CMinusConstDeclaration) {
                key = ((CMinusConstDeclaration) element).getConstDeclId();
                keyRange = new TextRange(((CMinusConstDeclaration) element).getId().getTextRange().getStartOffset(), ((CMinusConstDeclaration) element).getId().getTextRange().getEndOffset());
            } else if(element instanceof CMinusParam){
                key = ((CMinusParam) element).getParamId();
                keyRange = new TextRange(((CMinusParam) element).getId().getTextRange().getStartOffset(), ((CMinusParam) element).getId().getTextRange().getEndOffset());
            }

            if (!key.isEmpty() && keyRange != null) {
                if (element instanceof CMinusCall || element instanceof CMinusVar) {
                    annotateLocalKeyReferences(key, keyRange, holder, element);
                } else if (element instanceof CMinusVarDeclaration || element instanceof CMinusConstDeclaration || element instanceof CMinusFunDeclaration
                        || element instanceof CMinusParam) {
                    boolean declarationExists = verifyIfDeclarationExists(key, keyRange, holder, element);
                    if (element instanceof CMinusFunDeclaration && !declarationExists) {
                        checkReturnType(key, keyRange, holder, (CMinusFunDeclaration) element);
                    }
                }

            }
        }
    }

    private void checkReturnType(String key, TextRange keyRange, AnnotationHolder holder, CMinusFunDeclaration element) {
        CMinusTypeSpecifier elementTypeSpecifier = element.getTypeSpecifier();
        if (!elementTypeSpecifier.getFirstChild().getNode().getElementType().equals(CMinusTypes.VOID)) {
            CMinusReturnStmt returnStatement = checkIfReturnStatementExists(element, key, keyRange, holder, elementTypeSpecifier);
            if (returnStatement != null) {
                CMinusSimpleExpression simpleExpression = PsiTreeUtil.findChildOfType(returnStatement, CMinusSimpleExpression.class);
                if (simpleExpression != null) {
                    Collection<CMinusFactor> factors = PsiTreeUtil.findChildrenOfType(simpleExpression, CMinusFactor.class);
                    checkTypeMismatch(element.getFunDeclId(), elementTypeSpecifier, factors, keyRange, holder, element);
                }
            }
        }
    }

    private CMinusReturnStmt checkIfReturnStatementExists(CMinusFunDeclaration element, String key, TextRange keyRange, AnnotationHolder holder, CMinusTypeSpecifier elementTypeSpecifier) {
        CMinusReturnStmt returnStmt = PsiTreeUtil.findChildOfType(element, CMinusReturnStmt.class);
        if (returnStmt == null) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Return statement is missing!")
                    .range(keyRange)
                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                    .withFix(new CMinusAddReturnStatementQuickFix(elementTypeSpecifier, element))
                    .create();
        }

        return returnStmt;
    }

    private boolean verifyIfDeclarationExists(String key, TextRange keyRange, AnnotationHolder holder, PsiElement element) {
        boolean declarationExists = false;
        if (element instanceof CMinusVarDeclaration) {
            List<CMinusVarDeclaration> varDeclarations = CMinusUtil.findLocalVariableReferences(element.getContainingFile(), key);
            if (varDeclarations.size() > 1) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Variable already declared")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .create();
                declarationExists = true;
            }

            List<CMinusVar> varUsages = CMinusUtil.findLocalVarUsages(element.getContainingFile(), key);
            if (varUsages.isEmpty()) {
                holder.newAnnotation(HighlightSeverity.WARNING, "Variable is never used")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.WEAK_WARNING)
                        .create();
            }

            if (((CMinusVarDeclaration) element).getTypeSpecifier().getFirstChild().getNode().getElementType().equals(CMinusTypes.VOID)) {
                holder.newAnnotation(HighlightSeverity.ERROR, "You cannot declare a variable of type void")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.WEAK_WARNING)
                        .create();
            }

            if (element.getText().contains("[") && element.getText().contains("]")) {
                if (!((CMinusVarDeclaration) element).getTypeSpecifier().getFirstChild().getNode().getElementType().equals(CMinusTypes.INT)) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "You cannot declare an array variable of any type different than int")
                            .range(keyRange)
                            .highlightType(ProblemHighlightType.WEAK_WARNING)
                            .create();
                }
            }
        } else if (element instanceof CMinusFunDeclaration) {
            List<CMinusFunDeclaration> functionReferences = CMinusUtil.findLocalFunctionReferences(element.getContainingFile(), key);
            if (functionReferences.size() > 1) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Function already declared")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .create();
                declarationExists = true;
            }

            if (!key.equals("main")) {
                List<CMinusCall> functionCalls = CMinusUtil.findLocalFunctionCalls(element.getContainingFile(), key);
                if (functionCalls.isEmpty()) {
                    holder.newAnnotation(HighlightSeverity.WARNING, "Function is never called")
                            .range(keyRange)
                            .highlightType(ProblemHighlightType.WEAK_WARNING)
                            .create();
                }
            }
        } else if (element instanceof CMinusConstDeclaration) {
            List<CMinusConstDeclaration> constReferences = CMinusUtil.findLocalConstantReferences(element.getContainingFile(), key);
            if (constReferences.size() > 1) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Constant already declared")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .create();
                declarationExists = true;
            }

            List<CMinusVar> constantUsages = CMinusUtil.findLocalVarUsages(element.getContainingFile(), key);
            if (constantUsages.isEmpty()) {
                holder.newAnnotation(HighlightSeverity.WARNING, "Constant is never used")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.WEAK_WARNING)
                        .create();
            }
        } else if(element instanceof CMinusParam){
            if (((CMinusParam) element).getTypeSpecifier().getFirstChild().getNode().getElementType().equals(CMinusTypes.VOID)) {
                holder.newAnnotation(HighlightSeverity.ERROR, "You cannot declare a parameter of type void")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.WEAK_WARNING)
                        .create();
            }

            if (element.getText().contains("[") && element.getText().contains("]")) {
                if (!((CMinusParam) element).getTypeSpecifier().getFirstChild().getNode().getElementType().equals(CMinusTypes.INT)) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "You cannot declare an array parameter of any type different than int")
                            .range(keyRange)
                            .highlightType(ProblemHighlightType.WEAK_WARNING)
                            .create();
                }
            }
        }

        return declarationExists;
    }

    private void annotateLocalKeyReferences(String key, TextRange keyRange, AnnotationHolder holder, PsiElement element) {
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
                boolean argumentsFlag = false;

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
                    if (argumentsFlag) {
                        break;
                    }

                    CMinusParams paramsElement = funDeclaration.getParams();
                    if (null != paramsElement) {
                        params++;
                        if (paramsElement.getParamList() != null) {
                            PsiElement[] paramChildren = Objects.requireNonNull(paramsElement.getParamList()).getChildren();
                            for (PsiElement paramChild : paramChildren) {
                                if (paramChild instanceof CMinusParamList1) {
                                    params++;
                                }
                            }
                        }
                    }
                    if (args == params) {
                        argumentsFlag = true;
                    }
                }

                if (argumentsFlag) {
                    boolean argumentsSameTypeFlag = true;
                    CMinusArgs argumentElements = ((CMinusCall) element).getArgs();
                    if (argumentElements != null) {
                        CMinusFunDeclaration funReference = functionReferences.get(0);
                        CMinusParams parametersElements = funReference.getParams();
                        if (parametersElements != null) {
                            CMinusParamList parametersList = parametersElements.getParamList();
                            CMinusArgList argumentsList = argumentElements.getArgList();
                            Collection<CMinusParam> parameters = PsiTreeUtil.findChildrenOfType(parametersList, CMinusParam.class);
                            Collection<CMinusExpression> expressions = PsiTreeUtil.findChildrenOfType(argumentsList, CMinusExpression.class);
                            if (expressions.size() == parameters.size()) {
                                Iterator<CMinusExpression> expressionIterator = expressions.iterator();
                                for (CMinusParam parameter : parameters) {
                                    CMinusExpression expression = expressionIterator.next();
                                    CMinusTypeSpecifier paramTypeSpecifier = parameter.getTypeSpecifier();
                                    Collection<CMinusFactor> factors = PsiTreeUtil.findChildrenOfType(expression, CMinusFactor.class);
                                    argumentsSameTypeFlag = checkTypeMismatch(parameter.getParamId(), paramTypeSpecifier, factors, keyRange, holder, parameter);
                                }
                            }
                        }
                    }
                    if (argumentsSameTypeFlag) {
                        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                                .range(keyRange).textAttributes(CMinusSyntaxHighlighter.IDENTIFIER).create();
                    }
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
                List<CMinusVarDeclaration> tempVarReferences = CMinusUtil.findLocalVariableReferences(element.getContainingFile(), key);
                List<CMinusVarDeclaration> variableReferences = tempVarReferences.stream().filter(varReference -> {
                    CMinusFunDeclaration myElementParent = PsiTreeUtil.getParentOfType(element, CMinusFunDeclaration.class);
                    CMinusFunDeclaration referenceParent = PsiTreeUtil.getParentOfType(varReference, CMinusFunDeclaration.class);
                    return varReference.getParent() instanceof CMinusDeclaration || (myElementParent != null && myElementParent.equals(referenceParent));
                }).collect(Collectors.toList());

                List<CMinusConstDeclaration> constReferences = CMinusUtil.findLocalConstantReferences(element.getContainingFile(), key);

                List<CMinusParam> tempParamReferences = CMinusUtil.findLocalParamReferences(element.getContainingFile(), key);
                List<CMinusParam> paramReference = tempParamReferences.stream().filter(param -> {
                    CMinusFunDeclaration myElementParent = PsiTreeUtil.getParentOfType(element, CMinusFunDeclaration.class);
                    CMinusFunDeclaration referenceParent = PsiTreeUtil.getParentOfType(param, CMinusFunDeclaration.class);
                    return myElementParent.equals(referenceParent);
                }).collect(Collectors.toList());

                if (variableReferences.isEmpty() && constReferences.isEmpty() && paramReference.isEmpty()) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved variable/constant reference")
                            .range(keyRange)
                            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                            .withFix(new CMinusCreateVariableQuickFix(key, true))
                            .withFix(new CMinusCreateConstantQuickFix(key, true))
                            .create();
                } else {
                    boolean isSameType = true;
                    if (element instanceof CMinusVar) {
                        if (element.getParent() instanceof CMinusExpression) {
                            if (element.getParent().getText().contains("=")) {
                                CMinusTypeSpecifier elementTypeSpecifier = !variableReferences.isEmpty() ? variableReferences.get(0).getTypeSpecifier() : !paramReference.isEmpty() ? paramReference.get(0).getTypeSpecifier() : null;
                                if (elementTypeSpecifier != null) {
                                    Collection<CMinusFactor> factors = PsiTreeUtil.findChildrenOfType(element.getParent(), CMinusFactor.class);
                                    isSameType = checkTypeMismatch(((CMinusVar) element).getVarId(), elementTypeSpecifier, factors, keyRange, holder, element);
                                }
                            }
                        }
                    }
                    if (isSameType) {
                        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                                .range(keyRange).textAttributes(CMinusSyntaxHighlighter.IDENTIFIER).create();
                    }
                }
            }
        }
    }

    private boolean checkTypeMismatch(String elementId, CMinusTypeSpecifier elementTypeSpecifier, Collection<CMinusFactor> factors, TextRange keyRange, AnnotationHolder holder, PsiElement element) {
        boolean isSameType = true;
        boolean isArgumentAndAssign = false;
        for (CMinusFactor factor : factors) {
            if (factor.getVar() == null || (factor.getVar() != null && !factor.getVar().getVarId().equals(elementId))) {
                if (elementTypeSpecifier.getFirstChild().getNode().getElementType().equals(CMinusTypes.INT)) {
                    if (factor.getNum() == null) {
                        if (factor.getStringLiteral() != null) {
                            holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" +
                                    elementTypeSpecifier.getFirstChild().getText() + "', you can't assign 'string' value")
                                    .range(keyRange)
                                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                    .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, "string"))
                                    .create();
                            isSameType = false;
                        } else if (factor.getFirstChild().getNode().getElementType().equals(CMinusTypes.TRUE) ||
                                factor.getFirstChild().getNode().getElementType().equals(CMinusTypes.FALSE)) {
                            holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "', you can't assign 'bool' value")
                                    .range(keyRange)
                                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                    .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, "bool"))
                                    .create();
                            isSameType = false;
                        } else {
                            if (factor.getCall() != null) {
                                isSameType = checkCallTypeMismatch(elementId, keyRange, holder, element, elementTypeSpecifier, factor);
                            } else if (factor.getVar() != null) {
                                if (!(element instanceof CMinusVar && PsiTreeUtil.getParentOfType(factor.getVar(), CMinusArgList.class) != null)) {
                                    CMinusTypeSpecifier variableTypeSpecifier = getVariableReferenceTypeSpecifier(element, factor);
                                    if (variableTypeSpecifier != null) {
                                        isSameType = checkVarTypeMismatch(elementId, keyRange, holder, elementTypeSpecifier, factor, variableTypeSpecifier);
                                    } else {
                                        List<CMinusConstDeclaration> innerConstReferences = CMinusUtil.findLocalConstantReferences(element.getContainingFile(), factor.getVar().getVarId());
                                        if (!innerConstReferences.isEmpty()) {
                                            CMinusConstDeclaration innerConstReference = innerConstReferences.get(0);
                                            if (innerConstReference.getNum() == null) {
                                                if (innerConstReference.getStringLiteral() != null) {
                                                    holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "', " +
                                                            "you can't assign 'string' value")
                                                            .range(keyRange)
                                                            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                                            .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, "string"))
                                                            .create();
                                                    isSameType = false;
                                                } else if (innerConstReference.getConstantValue().equals("true") || innerConstReference.getConstantValue().equals("false")) {
                                                    holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "', you can't assign 'bool' value")
                                                            .range(keyRange)
                                                            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                                            .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, "bool"))
                                                            .create();
                                                    isSameType = false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (elementTypeSpecifier.getFirstChild().getNode().getElementType().equals(CMinusTypes.STRING)) {
                    if (factor.getStringLiteral() == null) {
                        if (factor.getNum() != null) {
                            holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "', you can't assign 'int' value")
                                    .range(keyRange)
                                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                    .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, "int"))
                                    .create();
                            isSameType = false;
                        } else if (factor.getFirstChild().getNode().getElementType().equals(CMinusTypes.TRUE) ||
                                factor.getFirstChild().getNode().getElementType().equals(CMinusTypes.FALSE)) {
                            holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "', you can't assign 'bool' value")
                                    .range(keyRange)
                                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                    .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, "bool"))
                                    .create();
                            isSameType = false;
                        } else {
                            if (factor.getCall() != null) {
                                isSameType = checkCallTypeMismatch(elementId, keyRange, holder, element, elementTypeSpecifier, factor);
                            } else if (factor.getVar() != null) {
                                if (element instanceof CMinusVar && PsiTreeUtil.getParentOfType(factor.getVar(), CMinusArgList.class) == null) {
                                    CMinusTypeSpecifier variableTypeSpecifier = getVariableReferenceTypeSpecifier(element, factor);
                                    if (variableTypeSpecifier != null) {
                                        isSameType = checkVarTypeMismatch(elementId, keyRange, holder, elementTypeSpecifier, factor, variableTypeSpecifier);
                                    } else {
                                        List<CMinusConstDeclaration> innerConstReferences = CMinusUtil.findLocalConstantReferences(element.getContainingFile(), factor.getVar().getVarId());
                                        if (!innerConstReferences.isEmpty()) {
                                            CMinusConstDeclaration innerConstReference = innerConstReferences.get(0);
                                            if (innerConstReference.getStringLiteral() == null) {
                                                if (innerConstReference.getNum() != null) {
                                                    holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "', you can't assign 'int' value")
                                                            .range(keyRange)
                                                            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                                            .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, "string"))
                                                            .create();
                                                    isSameType = false;
                                                } else if (innerConstReference.getConstantValue().equals("true") || innerConstReference.getConstantValue().equals("false")) {
                                                    holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "', you can't assign 'bool' value")
                                                            .range(keyRange)
                                                            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                                            .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, "bool"))
                                                            .create();
                                                    isSameType = false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (elementTypeSpecifier.getFirstChild().getNode().getElementType().equals(CMinusTypes.BOOL)) {
                    if (!(factor.getFirstChild().getNode().getElementType().equals(CMinusTypes.TRUE) ||
                            !factor.getFirstChild().getNode().getElementType().equals(CMinusTypes.FALSE))) {
                        if (factor.getStringLiteral() != null) {
                            holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "', you can't assign 'string' value")
                                    .range(keyRange)
                                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                    .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, "string"))
                                    .create();
                            isSameType = false;
                        } else if (factor.getNum() != null) {
                            holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "', you can't assign 'int' value")
                                    .range(keyRange)
                                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                    .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, "int"))
                                    .create();
                            isSameType = false;
                        } else {
                            if (factor.getCall() != null) {
                                isSameType = checkCallTypeMismatch(elementId, keyRange, holder, element, elementTypeSpecifier, factor);
                            } else if (factor.getVar() != null) {
                                if (element instanceof CMinusVar && PsiTreeUtil.getParentOfType(factor.getVar(), CMinusArgList.class) == null) {
                                    CMinusTypeSpecifier variableTypeSpecifier = getVariableReferenceTypeSpecifier(element, factor);
                                    if (variableTypeSpecifier != null) {
                                        isSameType = checkVarTypeMismatch(elementId, keyRange, holder, elementTypeSpecifier, factor, variableTypeSpecifier);
                                    } else {
                                        List<CMinusConstDeclaration> innerConstReferences = CMinusUtil.findLocalConstantReferences(element.getContainingFile(), factor.getVar().getVarId());
                                        if (!innerConstReferences.isEmpty()) {
                                            CMinusConstDeclaration innerConstReference = innerConstReferences.get(0);
                                            if (!(innerConstReference.getConstantValue().equals("true") || innerConstReference.getConstantValue().equals("false"))) {
                                                if (innerConstReference.getStringLiteral() != null) {
                                                    holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "', you can't assign 'string' value")
                                                            .range(keyRange)
                                                            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                                            .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, "string"))
                                                            .create();
                                                    isSameType = false;
                                                } else if (innerConstReference.getNum() != null) {
                                                    holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "', you can't assign 'int' value")
                                                            .range(keyRange)
                                                            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                                            .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, "bool"))
                                                            .create();
                                                    isSameType = false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return isSameType;
    }

    private boolean checkVarTypeMismatch(String elementId, TextRange keyRange, AnnotationHolder holder, CMinusTypeSpecifier elementTypeSpecifier, CMinusFactor factor, CMinusTypeSpecifier variableTypeSpecifier) {
        boolean isSameType = true;
        if (!variableTypeSpecifier.getFirstChild().getText().equals(elementTypeSpecifier.getFirstChild().getText())) {
            isSameType = false;
            holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "' and '" + factor.getVar().getVarId() + "' is of type '" + variableTypeSpecifier.getFirstChild().getText() + "'!")
                    .range(keyRange)
                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                    .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, variableTypeSpecifier.getFirstChild().getText()))
                    .withFix(new CMinusChangeTypeQuickFix(variableTypeSpecifier, elementTypeSpecifier.getFirstChild().getText()))
                    .create();
        }
        return isSameType;
    }

    @Nullable
    private CMinusTypeSpecifier getVariableReferenceTypeSpecifier(PsiElement element, CMinusFactor factor) {
        List<CMinusVarDeclaration> innerVarReferences = CMinusUtil.findLocalVariableReferences(element.getContainingFile(), factor.getVar().getVarId());
        List<CMinusParam> innerParamReferences = CMinusUtil.findLocalParamReferences(element.getContainingFile(), factor.getVar().getVarId());
        return !innerVarReferences.isEmpty() ? innerVarReferences.get(0).getTypeSpecifier() : !innerParamReferences.isEmpty() ? innerParamReferences.get(0).getTypeSpecifier() : null;
    }

    private boolean checkCallTypeMismatch(String elementId, TextRange keyRange, AnnotationHolder holder, PsiElement element, CMinusTypeSpecifier elementTypeSpecifier, CMinusFactor factor) {
        boolean isSameType = true;
        List<CMinusFunDeclaration> functionCallReferences = CMinusUtil.findLocalFunctionReferences(element.getContainingFile(), factor.getCall().getCallId());
        CMinusTypeSpecifier functionTypeSpecifier = !functionCallReferences.isEmpty() ? functionCallReferences.get(0).getTypeSpecifier() : null;
        if (functionTypeSpecifier != null) {
            if (!functionTypeSpecifier.getFirstChild().getText().equals(elementTypeSpecifier.getFirstChild().getText())) {
                isSameType = false;
                holder.newAnnotation(HighlightSeverity.ERROR, "Type mismatch! '" + elementId + "' is of type '" + elementTypeSpecifier.getFirstChild().getText() + "' and '" + factor.getCall().getCallId() + "' is of type '" + functionTypeSpecifier.getFirstChild().getText() + "'!")
                        .range(keyRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .withFix(new CMinusChangeTypeQuickFix(elementTypeSpecifier, functionTypeSpecifier.getFirstChild().getText()))
                        .withFix(new CMinusChangeTypeQuickFix(functionTypeSpecifier, elementTypeSpecifier.getFirstChild().getText()))
                        .create();
            }
        }
        return isSameType;
    }

    private void annotateJavaKeyReferences(String key, TextRange keyRange, AnnotationHolder holder, PsiElement element) {
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

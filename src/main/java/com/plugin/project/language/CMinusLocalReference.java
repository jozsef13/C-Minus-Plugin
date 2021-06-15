package com.plugin.project.language;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.plugin.project.language.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CMinusLocalReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private String id;

    public CMinusLocalReference(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
        id = element.getText();
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        final List<PsiElement> references = CMinusUtil.findLocalReferences(myElement.getContainingFile(), id);
        List<ResolveResult> results = new ArrayList<>();
        for (PsiElement reference : references) {
            if(reference instanceof CMinusFunDeclaration){
                int params = 0;
                CMinusParams cMinusParams = ((CMinusFunDeclaration) reference).getParams();
                if (cMinusParams != null) {
                    params++;
                    PsiElement[] paramChildren = Objects.requireNonNull(cMinusParams.getParamList()).getChildren();
                    for (PsiElement paramChild : paramChildren) {
                        if (paramChild instanceof CMinusParamList1) {
                            params++;
                        }
                    }
                }

                CMinusFunDeclaration tempReference = (CMinusFunDeclaration) reference;
                StringBuilder sb = new StringBuilder(((CMinusFunDeclaration) reference).getFunDeclId() + "(");
                int i = 0;
                while(i < params){
                    sb.append("arg").append(i);
                    i++;
                    if(i!=params){
                        sb.append(", ");
                    }
                }
                tempReference.setName(sb.toString());
                results.add(new PsiElementResolveResult(tempReference));
            } else {
                results.add(new PsiElementResolveResult(reference));
            }
        }

        return results.toArray(new ResolveResult[results.size()]);
    }

    @Override
    public @Nullable PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<PsiElement> references = CMinusUtil.findLocalReferences(myElement.getContainingFile());
        List<LookupElement> variants = new ArrayList<>();
        for (final PsiElement reference : references) {
            if (reference instanceof CMinusFunDeclaration) {
                if (((CMinusFunDeclaration) reference).getFunDeclId() != null && ((CMinusFunDeclaration) reference).getFunDeclId().length() > 0) {
                    variants.add(LookupElementBuilder.create(((CMinusFunDeclaration) reference).getFunDeclId()).withIcon(CMinusIcons.USAGE).withTypeText(reference.getContainingFile().getName() + " - function"));
                }
            } else if (reference instanceof CMinusVarDeclaration) {
                if (((CMinusVarDeclaration) reference).getVarDeclId() != null && ((CMinusVarDeclaration) reference).getVarDeclId().length() > 0) {
                    if(myElement instanceof CMinusVar){
                        if(reference.getParent() instanceof CMinusDeclaration){
                            variants.add(LookupElementBuilder.create(((CMinusVarDeclaration) reference).getVarDeclId()).withIcon(CMinusIcons.USAGE).withTypeText(reference.getContainingFile().getName() + " - variable"));
                        } else {
                            CMinusFunDeclaration myElementParent = PsiTreeUtil.getParentOfType(myElement, CMinusFunDeclaration.class);
                            CMinusFunDeclaration referenceParent = PsiTreeUtil.getParentOfType(reference, CMinusFunDeclaration.class);
                            if (myElementParent.equals(referenceParent)) {
                                variants.add(LookupElementBuilder.create(((CMinusVarDeclaration) reference).getVarDeclId()).withIcon(CMinusIcons.USAGE).withTypeText(reference.getContainingFile().getName() + " - variable"));
                            }
                        }
                    }
                }
            } else if (reference instanceof CMinusConstDeclaration) {
                if (((CMinusConstDeclaration) reference).getConstDeclId() != null && ((CMinusConstDeclaration) reference).getConstDeclId().length() > 0) {
                    variants.add(LookupElementBuilder.create(((CMinusConstDeclaration) reference).getConstDeclId()).withIcon(CMinusIcons.USAGE).withTypeText(reference.getContainingFile().getName() + " - constant"));
                }
            } else if (reference instanceof CMinusParam) {
                if (((CMinusParam) reference).getParamId() != null && ((CMinusParam) reference).getParamId().length() > 0) {
                    if (myElement instanceof CMinusVar) {
                        CMinusFunDeclaration myElementParent = PsiTreeUtil.getParentOfType(myElement, CMinusFunDeclaration.class);
                        CMinusFunDeclaration referenceParent = PsiTreeUtil.getParentOfType(reference, CMinusFunDeclaration.class);
                        if (myElementParent.equals(referenceParent)) {
                            variants.add(LookupElementBuilder.create(((CMinusParam) reference).getParamId()).withIcon(CMinusIcons.USAGE).withTypeText(reference.getContainingFile().getName() + " - parameter"));
                        }
                    }
                }
            }
        }
        return variants.toArray();
    }

    @Override
    public @NotNull TextRange getRangeInElement() {
        return TextRange.from(0, getElement().getTextLength());
    }

    @Override
    public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
        if (myElement instanceof CMinusVar) {
            return ((CMinusVar) myElement).setName(newElementName);
        } else if (myElement instanceof CMinusCall) {
            return ((CMinusCall) myElement).setName(newElementName);
        } else if (myElement instanceof CMinusVarDeclaration) {
            return ((CMinusVarDeclaration) myElement).setName(newElementName);
        } else if (myElement instanceof CMinusFunDeclaration) {
            return ((CMinusFunDeclaration) myElement).setName(newElementName);
        } else if (myElement instanceof CMinusConstDeclaration) {
            return ((CMinusConstDeclaration) myElement).setName(newElementName);
        } else if (myElement instanceof CMinusParam) {
            return ((CMinusParam) myElement).setName(newElementName);
        }

        return null;
    }
}

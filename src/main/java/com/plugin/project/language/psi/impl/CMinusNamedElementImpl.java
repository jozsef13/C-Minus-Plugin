package com.plugin.project.language.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceService;
import com.plugin.project.language.psi.CMinusNamedElement;
import org.jetbrains.annotations.NotNull;

public abstract class CMinusNamedElementImpl extends ASTWrapperPsiElement implements CMinusNamedElement {

    public CMinusNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public PsiReference getReference() {
        PsiReference[] references = getReferences();
        return references.length == 0 ? null : references[0];
    }

    @Override
    public PsiReference @NotNull [] getReferences() {
        return PsiReferenceService.getService().getContributedReferences(this);
    }
}

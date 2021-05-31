package com.plugin.project.language.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.plugin.project.language.psi.CMinusNamedElement;
import org.jetbrains.annotations.NotNull;

public abstract class CMinusNamedElementImpl extends ASTWrapperPsiElement implements CMinusNamedElement {

    public CMinusNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}

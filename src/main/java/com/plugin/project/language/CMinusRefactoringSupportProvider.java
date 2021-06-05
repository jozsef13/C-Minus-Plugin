package com.plugin.project.language;

import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import com.plugin.project.language.psi.CMinusConstDeclaration;
import com.plugin.project.language.psi.CMinusFunDeclaration;
import com.plugin.project.language.psi.CMinusVarDeclaration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CMinusRefactoringSupportProvider extends RefactoringSupportProvider {

    @Override
    public boolean isMemberInplaceRenameAvailable(@NotNull PsiElement element, @Nullable PsiElement context) {
        return (element instanceof CMinusFunDeclaration) || (element instanceof CMinusVarDeclaration) || (element instanceof CMinusConstDeclaration);
    }
}

package com.plugin.project.language;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import com.plugin.project.language.psi.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CMinusFindUsagesProvider implements FindUsagesProvider {
    @Override
    public @Nullable WordsScanner getWordsScanner() {
        return new DefaultWordsScanner(new CMinusLexerAdapter(), TokenSet.create(CMinusTypes.ID), TokenSet.create(CMinusTypes.COMMENT), TokenSet.EMPTY);
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof PsiNamedElement;
    }

    @Override
    public @Nullable String getHelpId(@NotNull PsiElement psiElement) {
        return null;
    }

    @Override
    public @Nls @NotNull String getType(@NotNull PsiElement element) {
        if (element instanceof CMinusFunDeclaration) {
            return "cminus function";
        } else if (element instanceof CMinusVarDeclaration) {
            return "cminus variable";
        } else if (element instanceof CMinusConstDeclaration) {
            return "cminus constant";
        } else if(element instanceof CMinusVar){
            return "cminus variable use";
        } else {
            return "";
        }
    }

    @Override
    public @Nls @NotNull String getDescriptiveName(@NotNull PsiElement element) {
        return getName(element);
    }

    @Override
    public @Nls @NotNull String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        return getName(element);
    }

    @Nls
    @NotNull
    private String getName(@NotNull PsiElement element) {
        if (element instanceof CMinusFunDeclaration) {
            return ((CMinusFunDeclaration) element).getFunDeclId();
        } else if (element instanceof CMinusVarDeclaration) {
            return ((CMinusVarDeclaration) element).getVarDeclId();
        } else if (element instanceof CMinusConstDeclaration) {
            return ((CMinusConstDeclaration) element).getConstDeclId();
        } else if(element instanceof CMinusVar){
            return ((CMinusVar) element).getVarId();
        } else {
            return "";
        }
    }
}

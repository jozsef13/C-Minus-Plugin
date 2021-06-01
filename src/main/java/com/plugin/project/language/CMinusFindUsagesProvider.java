package com.plugin.project.language;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import com.plugin.project.language.psi.CMinusFunDeclaration;
import com.plugin.project.language.psi.CMinusTypes;
import com.plugin.project.language.psi.CMinusVarDeclaration;
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
        if(element instanceof CMinusFunDeclaration){
            return "cminus function";
        } else if(element instanceof CMinusVarDeclaration){
            return "cminus variable";
        } else {
            return "";
        }
    }

    @Override
    public @Nls @NotNull String getDescriptiveName(@NotNull PsiElement element) {
        if(element instanceof CMinusFunDeclaration){
            return ((CMinusFunDeclaration) element).getFunDeclId();
        } else if(element instanceof CMinusVarDeclaration){
            return ((CMinusVarDeclaration) element).getVarDeclId();
        } else {
            return "";
        }
    }

    @Override
    public @Nls @NotNull String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        if(element instanceof CMinusFunDeclaration){
            return ((CMinusFunDeclaration) element).getFunDeclId();
        } else if(element instanceof CMinusVarDeclaration){
            return ((CMinusVarDeclaration) element).getVarDeclId();
        } else {
            return "";
        }
    }
}

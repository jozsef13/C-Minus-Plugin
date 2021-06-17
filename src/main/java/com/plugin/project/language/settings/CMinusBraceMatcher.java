package com.plugin.project.language.settings;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.plugin.project.language.psi.CMinusTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CMinusBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(CMinusTypes.LEFT_PARANTHESIS, CMinusTypes.RIGHT_PARANTHESIS, false),
            new BracePair(CMinusTypes.LEFT_BRACKET, CMinusTypes.RIGHT_BRACKET, false),
            new BracePair(CMinusTypes.LEFT_BRACE, CMinusTypes.RIGHT_BRACE, true)
    };

    @Override
    public BracePair @NotNull [] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        final PsiElement psiElement = file.findElementAt(openingBraceOffset);
        return psiElement.getTextOffset();
    }
    
}

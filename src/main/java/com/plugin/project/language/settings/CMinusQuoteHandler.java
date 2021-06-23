package com.plugin.project.language.settings;

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.intellij.psi.tree.TokenSet;
import com.plugin.project.language.psi.CMinusTokenType;
import com.plugin.project.language.psi.CMinusTypes;

public class CMinusQuoteHandler extends SimpleTokenSetQuoteHandler {
    public CMinusQuoteHandler() {
        super(CMinusTypes.STRING_LITERAL);
    }
}

package com.plugin.project.language;

import com.intellij.lexer.FlexAdapter;
import com.plugin.project.language.CMinusLexer;

public class CMinusLexerAdapter extends FlexAdapter {
    public CMinusLexerAdapter() {
        super(new CMinusLexer(null));
    }
}

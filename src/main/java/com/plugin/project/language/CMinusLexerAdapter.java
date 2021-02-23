package com.plugin.project.language;

import com.intellij.lexer.FlexAdapter;

public class CMinusLexerAdapter extends FlexAdapter {
    public CMinusLexerAdapter() {
        super(new CMinusLexer(null));
    }
}

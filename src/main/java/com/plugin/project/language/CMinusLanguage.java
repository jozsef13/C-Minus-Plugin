package com.plugin.project.language;

import com.intellij.lang.Language;

public class CMinusLanguage  extends Language {

    public static final CMinusLanguage INSTANCE = new CMinusLanguage();

    protected CMinusLanguage() {
        super("CMinus");
    }
}

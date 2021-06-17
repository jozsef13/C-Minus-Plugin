package com.plugin.project.language.settings;

import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import com.plugin.project.language.CMinusLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CMinusLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {
    @Override
    public @Nullable String getCodeSample(@NotNull SettingsType settingsType) {
        return "int gcd ( int u, int v )\n" +
                "{\n" +
                "if ( v == 0 )\n" +
                "return u ;\n" +
                "else\n" +
                "return gcd (v, u-u/v*v) ;\n" +
                "/* u-u/v*v == u mod v */\n" +
                "}\n" +
                "void main(void)\n" +
                "{\n" +
                "int x;\n" +
                "int y;\n" +
                "x = input();\n" +
                "y = input();\n" +
                "output(gcd(x,y));\n" +
                "}";
    }

    @Override
    public @NotNull Language getLanguage() {
        return CMinusLanguage.INSTANCE;
    }

    @Override
    public void customizeSettings(@NotNull CodeStyleSettingsCustomizable consumer, @NotNull SettingsType settingsType) {
        if(settingsType == SettingsType.SPACING_SETTINGS){
            consumer.showStandardOptions("SPACE_AROUND_ASSIGNMENT_OPERATORS");
            consumer.renameStandardOption("SPACE_AROUND_ASSIGNMENT_OPERATORS", "Assign");
        } else if(settingsType == SettingsType.BLANK_LINES_SETTINGS){
            consumer.showStandardOptions("KEEP_BLANK_LINES_IN_CODE");
        }
    }
}

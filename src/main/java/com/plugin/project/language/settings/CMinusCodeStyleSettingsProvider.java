package com.plugin.project.language.settings;

import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.psi.codeStyle.CodeStyleConfigurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;
import com.plugin.project.language.CMinusLanguage;
import com.plugin.project.language.settings.CMinusCodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CMinusCodeStyleSettingsProvider extends CodeStyleSettingsProvider {

    @Override
    public @Nullable CustomCodeStyleSettings createCustomSettings(CodeStyleSettings settings) {
        return new CMinusCodeStyleSettings(settings);
    }

    @Override
    public @Nullable String getConfigurableDisplayName() {
        return "CMinus";
    }

    @Override
    public @NotNull CodeStyleConfigurable createConfigurable(@NotNull CodeStyleSettings settings, @NotNull CodeStyleSettings modelSettings) {
        return new CodeStyleAbstractConfigurable(settings, modelSettings, this.getConfigurableDisplayName()) {
            @Override
            protected CodeStyleAbstractPanel createPanel(CodeStyleSettings settings) {
                return new CMinusCodeStyleMainPanel(getCurrentSettings(), settings);
            }
        };
    }

    private static class CMinusCodeStyleMainPanel extends TabbedLanguageCodeStylePanel {

        protected CMinusCodeStyleMainPanel(CodeStyleSettings currentSettings, CodeStyleSettings settings) {
            super(CMinusLanguage.INSTANCE, currentSettings, settings);
        }
    }
}

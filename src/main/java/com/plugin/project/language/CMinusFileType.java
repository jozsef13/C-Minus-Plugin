package com.plugin.project.language;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CMinusFileType extends LanguageFileType {

    public static final CMinusFileType INSTANCE = new CMinusFileType();

    public CMinusFileType() {
        super(CMinusLanguage.INSTANCE);
    }

    @Override
    public @NonNls
    @NotNull String getName() {
        return "CMinus File";
    }

    @Override
    public @NotNull String getDescription() {
        return "CMinus language file";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return "cmin";
    }

    @Override
    public @Nullable Icon getIcon() {
        return CMinusIcons.FILE;
    }
}

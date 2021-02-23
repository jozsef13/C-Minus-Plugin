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
    @NotNull
    public String getName() {
        return "CMinus File";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "CMinus language file";
    }

    @Override
    @NotNull
    public String getDefaultExtension() {
        return "cmin";
    }

    @Override
    @Nullable
    public Icon getIcon() {
        return CMinusIcons.FILE;
    }
}

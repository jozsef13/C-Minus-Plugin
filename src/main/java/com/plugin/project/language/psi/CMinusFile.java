package com.plugin.project.language.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.plugin.project.language.CMinusFileType;
import com.plugin.project.language.CMinusLanguage;
import org.jetbrains.annotations.NotNull;

public class CMinusFile extends PsiFileBase {

    public CMinusFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, CMinusLanguage.INSTANCE);
    }

    @Override
    @NotNull
    public FileType getFileType() {
        return CMinusFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "CMinus File";
    }
}

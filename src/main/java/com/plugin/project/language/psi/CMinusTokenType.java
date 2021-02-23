package com.plugin.project.language.psi;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;
import com.plugin.project.language.CMinusLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CMinusTokenType extends IElementType {
    public CMinusTokenType(@NonNls @NotNull String debugName) {
        super(debugName, CMinusLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "CMinusTokenType." + super.toString();
    }
}

package com.plugin.project.language;

import com.intellij.codeInsight.template.TemplateActionContext;
import com.intellij.codeInsight.template.TemplateContextType;
import org.jetbrains.annotations.NotNull;

public class CMinusContext extends TemplateContextType {
    protected CMinusContext() {
        super("CMINUS", "CMinus");
    }

    @Override
    public boolean isInContext(@NotNull TemplateActionContext templateActionContext) {
        return templateActionContext.getFile().getName().endsWith(".cmin");
    }
}

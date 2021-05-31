package com.plugin.project.language.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;
import com.plugin.project.language.CMinusFileType;

public class CMinusElementFactory {
    public static CMinusFunDeclaration createFunction(Project project, String name) {
        StringBuilder strb = new StringBuilder();
        strb.append("int ").append(name).append("(){}");
        final CMinusFile file = createFile(project, strb.toString());
        return PsiTreeUtil.findChildOfType(file, CMinusFunDeclaration.class);
    }

    public static CMinusVarDeclaration createVariable(Project project, String name) {
        StringBuilder strb = new StringBuilder();
        strb.append("int ").append(name).append(";");
        final CMinusFile file = createFile(project, strb.toString());
        return (CMinusVarDeclaration) PsiTreeUtil.findChildOfType(file, CMinusVarDeclaration.class);
    }

    private static CMinusFile createFile(Project project, String text) {
        String name = "dummy.cmin";
        return (CMinusFile) PsiFileFactory.getInstance(project).createFileFromText(name, CMinusFileType.INSTANCE, text);
    }
}
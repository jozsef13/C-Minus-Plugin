package com.plugin.project.language.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;
import com.plugin.project.language.CMinusFileType;

public class CMinusElementFactory {
    public static CMinusFunDeclaration createFunction(Project project, String name) {
        final CMinusFile file = createFile(project, "int " + name.split("\\(\\)")[0] + "(){}");
        return PsiTreeUtil.findChildOfType(file, CMinusFunDeclaration.class);
    }

    public static CMinusVarDeclaration createVariable(Project project, String name) {
        final CMinusFile file = createFile(project, "int " + name + ";");
        return PsiTreeUtil.findChildOfType(file, CMinusVarDeclaration.class);
    }

    private static CMinusFile createFile(Project project, String text) {
        String name = "dummy.cmin";
        return (CMinusFile) PsiFileFactory.getInstance(project).createFileFromText(name, CMinusFileType.INSTANCE, text);
    }

    public static CMinusFunDeclaration createFunction(Project project, String name, String typeSpecifier){
        final CMinusFile file = createFile(project, typeSpecifier + name.split("\\(\\)")[0] + "(){}");
        return PsiTreeUtil.findChildOfType(file, CMinusFunDeclaration.class);
    }

    public static CMinusVarDeclaration createVariable(Project project, String name, String typeSpecifier){
        final CMinusFile file = createFile(project, typeSpecifier + name + ";\n");
        return PsiTreeUtil.findChildOfType(file, CMinusVarDeclaration.class);
    }

    public static PsiElement createCRLF(Project project){
        final CMinusFile file = createFile(project, "\n");
        return file.getFirstChild();
    }

    public static CMinusConstDeclaration createConstant(Project project, String name) {
        final CMinusFile file = createFile(project, "const " + name + " = 0;\n");
        return PsiTreeUtil.findChildOfType(file, CMinusConstDeclaration.class);
    }

    public static PsiElement createParameter(Project project, String newName) {
        final CMinusFile file = createFile(project, "void fun(int " + newName + ") {}");
        return PsiTreeUtil.findChildOfType(file, CMinusParam.class);
    }

    public static CMinusReturnStmt createReturnStmt(Project project, String returnValue) {
        final CMinusFile file = createFile(project, "void fun() { \treturn " + returnValue + ";}");
        return PsiTreeUtil.findChildOfType(file, CMinusReturnStmt.class);
    }
}

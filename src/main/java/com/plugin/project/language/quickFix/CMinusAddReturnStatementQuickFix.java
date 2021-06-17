package com.plugin.project.language.quickFix;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.IncorrectOperationException;
import com.plugin.project.language.psi.*;
import org.jetbrains.annotations.NotNull;

public class CMinusAddReturnStatementQuickFix extends BaseIntentionAction {

    private CMinusTypeSpecifier elementTypeSpecifier;
    private CMinusFunDeclaration funDeclaration;

    public CMinusAddReturnStatementQuickFix(CMinusTypeSpecifier elementTypeSpecifier, CMinusFunDeclaration funDeclaration) {
        this.elementTypeSpecifier = elementTypeSpecifier;
        this.funDeclaration = funDeclaration;
    }

    @Override
    public @IntentionName @NotNull String getText() {
        return "Add return statement";
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return getText();
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return true;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        ApplicationManager.getApplication().invokeLater(() ->{
            VirtualFile virtualFile = file.getVirtualFile();
            addReturnStatement(project, virtualFile);
        });
    }

    private void addReturnStatement(Project project, VirtualFile virtualFile) {
        WriteCommandAction.writeCommandAction(project).run(() -> {
//            CMinusFile cminFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            CMinusCompoundStmt compoundStmt = funDeclaration.getCompoundStmt();
            if(compoundStmt != null){
                ASTNode lastChildNode = compoundStmt.getNode().getLastChildNode();

                String returnValue = "";

                if(elementTypeSpecifier.getFirstChild().getNode().getElementType().equals(CMinusTypes.INT)){
                    returnValue = "0";
                } else if(elementTypeSpecifier.getFirstChild().getNode().getElementType().equals(CMinusTypes.STRING_LITERAL)){
                    returnValue = String.valueOf('\"' + '\"');
                } else if (elementTypeSpecifier.getFirstChild().getNode().getElementType().equals(CMinusTypes.BOOL)){
                    returnValue = "false";
                }

                CMinusReturnStmt returnStmt = CMinusElementFactory.createReturnStmt(project, returnValue);
                compoundStmt.getNode().addChild(returnStmt.getNode(), lastChildNode);
                compoundStmt.getNode().addChild(CMinusElementFactory.createCRLF(project).getNode(), lastChildNode);
                ((Navigatable)returnStmt.getLastChild().getNavigationElement()).navigate(true);
                FileEditorManager.getInstance(project).getSelectedTextEditor().getCaretModel().moveCaretRelatively(2, 0, false, false, false);
            }
        });
    }
}

package com.plugin.project.language.quickFix;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.PsiDocumentManagerImpl;
import com.intellij.util.IncorrectOperationException;
import com.plugin.project.language.psi.CMinusFunDeclaration;
import com.plugin.project.language.psi.CMinusParam;
import com.plugin.project.language.psi.CMinusTypeSpecifier;
import com.plugin.project.language.psi.CMinusVarDeclaration;
import org.jetbrains.annotations.NotNull;

public class CMinusChangeTypeQuickFix extends BaseIntentionAction {
    private CMinusTypeSpecifier typeSpecifier;
    private String newTypeSpecifier;

    public CMinusChangeTypeQuickFix(CMinusTypeSpecifier typeSpecifier, String newTypeSpecifier) {
        this.typeSpecifier = typeSpecifier;
        this.newTypeSpecifier = newTypeSpecifier;
    }

    @Override
    public @IntentionName @NotNull String getText() {
        StringBuilder sb = new StringBuilder("Change type of ");
        if(typeSpecifier.getParent() instanceof CMinusFunDeclaration){
            sb.append("function '" + ((CMinusFunDeclaration) typeSpecifier.getParent()).getFunDeclId() + "'");
        } else if(typeSpecifier.getParent() instanceof CMinusVarDeclaration){
            sb.append("variable '" + ((CMinusVarDeclaration) typeSpecifier.getParent()).getVarDeclId() + "'");
        } else if(typeSpecifier.getParent() instanceof CMinusParam){
            sb.append("parameter '" + ((CMinusParam) typeSpecifier.getParent()).getParamId() + "'");
        }
        sb.append("('").append(typeSpecifier.getText()).append("') to '").append(newTypeSpecifier).append("'");
        return sb.toString();
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
        ApplicationManager.getApplication().invokeLater(() -> {
            if(typeSpecifier != null && newTypeSpecifier != null && !newTypeSpecifier.isEmpty()){
                changeType(project);
            }
        });
    }

    private void changeType(Project project) {
        WriteCommandAction.writeCommandAction(project).run(() -> {
            PsiFile psiFile = typeSpecifier.getContainingFile();
            Document document = PsiDocumentManagerImpl.getInstance(project).getDocument(psiFile);
            if(document != null){
                document.replaceString(typeSpecifier.getTextRange().getStartOffset(), typeSpecifier.getTextRange().getEndOffset(), newTypeSpecifier);
            }
        });
    }
}

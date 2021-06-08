package com.plugin.project.language;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.PsiDocumentManagerImpl;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public class CMinusIncorrectFunctionLocalQuickFix extends BaseIntentionAction {

    PsiElement element;
    int params;

    public CMinusIncorrectFunctionLocalQuickFix(PsiElement element, int params) {
        this.element = element;
        this.params = params;
    }

    @Override
    public @IntentionName @NotNull String getText() {
        return "Define function reference correctly";
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
            if(element != null){
                defineFunctionReference(project);
            }
        });
    }

    private void defineFunctionReference(Project project) {
        WriteCommandAction.writeCommandAction(project).run(() -> {
            PsiFile psiFile = element.getContainingFile();
            Document document = PsiDocumentManagerImpl.getInstance(project).getDocument(psiFile);
            if (document != null) {
                if(params == 0){
                    document.replaceString(element.getTextRange().getStartOffset(), element.getTextRange().getEndOffset(),
                            element.getText().substring(0, element.getText().length()) + "()");
                } else {
                    int i = 0;
                    StringBuilder sb = new StringBuilder("(");
                    while(i < params){
                        sb.append("arg").append(i);
                        i++;
                        if(i!=params){
                            sb.append(", ");
                        }
                    }
                    sb.append(")");
                    if(element.getText().contains("\\(") && element.getText().contains("\\)")){
                        document.replaceString(element.getTextRange().getStartOffset(), element.getTextRange().getEndOffset(),
                                element.getText().substring(0, element.getText().indexOf("(")) + sb.toString());
                    } else {
                        document.replaceString(element.getTextRange().getStartOffset(), element.getTextRange().getEndOffset(),
                                element.getText().substring(0, element.getText().length()) + sb.toString());
                    }

                }

            }
        });
    }
}

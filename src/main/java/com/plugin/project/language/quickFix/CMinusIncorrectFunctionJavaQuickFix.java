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
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.impl.PsiDocumentManagerImpl;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public class CMinusIncorrectFunctionJavaQuickFix extends BaseIntentionAction {

    PsiLiteralExpression literalExpression;

    public CMinusIncorrectFunctionJavaQuickFix(PsiLiteralExpression literalExpression) {
        this.literalExpression = literalExpression;
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
            if(literalExpression != null){
                defineFunctionReference(project);
            }
        });
    }

    private void defineFunctionReference(@NotNull Project project) {
        WriteCommandAction.writeCommandAction(project).run(() -> {
            PsiFile psiFile = literalExpression.getContainingFile();
            Document document = PsiDocumentManagerImpl.getInstance(project).getDocument(psiFile);
            if (document != null) {
                document.replaceString(literalExpression.getTextRange().getStartOffset(), literalExpression.getTextRange().getEndOffset()-1,
                        literalExpression.getText().substring(0, literalExpression.getText().length()-1) + "()");
            }
        });
    }
}

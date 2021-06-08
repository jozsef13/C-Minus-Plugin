package com.plugin.project.language;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.IncorrectOperationException;
import com.plugin.project.language.psi.CMinusElementFactory;
import com.plugin.project.language.psi.CMinusFile;
import com.plugin.project.language.psi.CMinusFunDeclaration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class CMinusCreateFunctionQuickFix extends BaseIntentionAction {

    private final String id;
    private boolean isLocal;

    public CMinusCreateFunctionQuickFix(String id, boolean isLocal) {
        this.id = id;
        this.isLocal = isLocal;
    }

    @Override
    public @IntentionName @NotNull String getText() {
        return "Create function '" + id + "'";
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return "Create function";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return true;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        ApplicationManager.getApplication().invokeLater(() -> {
            Collection<VirtualFile> virtualFiles = new ArrayList<>();
            if(isLocal){
                virtualFiles.add(file.getVirtualFile());
            } else {
                virtualFiles = FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
            }

            if(virtualFiles.size() == 1){
                createFunction(project, virtualFiles.iterator().next());
            } else {
                final FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(CMinusFileType.INSTANCE);
                descriptor.setRoots(ProjectUtil.guessProjectDir(project));
                final VirtualFile file1 = FileChooser.chooseFile(descriptor, project, null);
                if(file1 != null){
                    createFunction(project, file1);
                }
            }
        });
    }

    private void createFunction(final Project project, final VirtualFile file) {
        WriteCommandAction.writeCommandAction(project).run(() -> {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(file);
            ASTNode lastChildNode = cMinusFile.getNode().getLastChildNode().getLastChildNode();
            if(lastChildNode != null){
                cMinusFile.getNode().addChild(CMinusElementFactory.createCRLF(project).getNode());
            }

            CMinusFunDeclaration funDeclaration = CMinusElementFactory.createFunction(project, id.replaceAll(" ", "\\\\ "), "void ");
            cMinusFile.getNode().addChild(funDeclaration.getNode());
            ((Navigatable)funDeclaration.getLastChild().getNavigationElement()).navigate(true);
            FileEditorManager.getInstance(project).getSelectedTextEditor().getCaretModel().moveCaretRelatively(2, 0, false, false, false);
        });
    }
}

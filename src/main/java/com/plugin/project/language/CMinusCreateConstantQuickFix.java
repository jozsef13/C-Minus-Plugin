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
import com.plugin.project.language.psi.CMinusConstDeclaration;
import com.plugin.project.language.psi.CMinusElementFactory;
import com.plugin.project.language.psi.CMinusFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class CMinusCreateConstantQuickFix extends BaseIntentionAction {

    private final String id;
    private boolean isLocal = false;

    public CMinusCreateConstantQuickFix(String key, boolean isLocal) {
        this.id = key;
        this.isLocal = isLocal;
    }

    @Override
    public @IntentionName @NotNull String getText() {
        return "Create constant '" + id + "'";
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return "Create constant";
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
                createConstant(project, virtualFiles.iterator().next());
            } else {
                final FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(CMinusFileType.INSTANCE);
                descriptor.setRoots(ProjectUtil.guessProjectDir(project));
                final VirtualFile file1 = FileChooser.chooseFile(descriptor, project, null);
                if(file1 != null){
                    createConstant(project, file1);
                }
            }
        });
    }

    private void createConstant(Project project, VirtualFile file) {
        WriteCommandAction.writeCommandAction(project).run(() -> {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(file);
            ASTNode firstChildNode = cMinusFile.getNode().getFirstChildNode();
            if(firstChildNode != null){
                cMinusFile.getNode().addChild(CMinusElementFactory.createCRLF(project).getNode(), firstChildNode);
            }

            CMinusConstDeclaration constDeclaration = CMinusElementFactory.createConstant(project, id.replaceAll(" ", "\\\\ "));
            cMinusFile.getNode().addChild(constDeclaration.getNode(), firstChildNode);
            ((Navigatable)constDeclaration.getLastChild().getNavigationElement()).navigate(true);
            FileEditorManager.getInstance(project).getSelectedTextEditor().getCaretModel().moveCaretRelatively(2, 0, false, false, false);
        });
    }
}

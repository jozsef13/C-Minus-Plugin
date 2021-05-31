package com.plugin.project.language;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.plugin.project.language.psi.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CMinusUtil {

    public static List<PsiElement> findReferences(Project project, String id){
        List<PsiElement> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                Collection<PsiElement> references = PsiTreeUtil.findChildrenOfType(cMinusFile, PsiElement.class);
                for (PsiElement reference : references) {
                    String referenceId = "";
                    if(reference instanceof CMinusCall){
                        referenceId = ((CMinusCall)reference).getCallId();
                    } else if(reference instanceof CMinusFunDeclaration){
                        referenceId = ((CMinusFunDeclaration)reference).getFunDeclId();
                    } else if(reference instanceof CMinusParam){
                        referenceId = ((CMinusParam)reference).getParamId();
                    } else if(reference instanceof CMinusVar){
                        referenceId = ((CMinusVar)reference).getVarId();
                    } else if(reference instanceof CMinusVarDeclaration){
                        referenceId = ((CMinusVarDeclaration)reference).getVarDeclId();
                    }

                    if(!referenceId.isEmpty()){
                        if (id.equals(referenceId)) {
                            result.add(reference);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<CMinusFunDeclaration> findFunctionReferences(Project project, String id){
        List<CMinusFunDeclaration> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                Collection<CMinusFunDeclaration> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusFunDeclaration.class);
                for (CMinusFunDeclaration reference : references) {
                    if (id.equals(reference.getFunDeclId())) {
                        result.add(reference);
                    }
                }
            }
        }
        return result;
    }

    public static List<CMinusVarDeclaration> findVariableReferences(Project project, String id){
        List<CMinusVarDeclaration> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                Collection<CMinusVarDeclaration> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusVarDeclaration.class);
                for (CMinusVarDeclaration reference : references) {
                    if (id.equals(reference.getVarDeclId())) {
                        result.add(reference);
                    }
                }
            }
        }
        return result;
    }

    public static List<CMinusFunDeclaration> findFunctionReferences(Project project) {
        List<CMinusFunDeclaration> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                Collection<CMinusFunDeclaration> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusFunDeclaration.class);
                if (references != null) {
                    Collections.addAll(result, references.toArray(new CMinusFunDeclaration[references.size()]));
                }
            }
        }
        return result;
    }

    public static List<CMinusVarDeclaration> findVariableReferences(Project project) {
        List<CMinusVarDeclaration> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                Collection<CMinusVarDeclaration> references = PsiTreeUtil.findChildrenOfType(cMinusFile, CMinusVarDeclaration.class);
                if (references != null) {
                    Collections.addAll(result, references.toArray(new CMinusVarDeclaration[references.size()]));
                }
            }
        }
        return result;
    }

    public static List<PsiElement> findReferences(Project project) {
        List<PsiElement> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                Collection<PsiElement> references = PsiTreeUtil.findChildrenOfType(cMinusFile, PsiElement.class);
                if(references != null){
                    Collections.addAll(result, references.toArray(new PsiElement[references.size()]));
                }
            }
        }
        return result;
    }
}

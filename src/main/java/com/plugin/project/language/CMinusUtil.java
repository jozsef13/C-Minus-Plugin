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

    public static List<PsiElement> findProperties(Project project, String id){
        List<PsiElement> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                PsiElement[] properties = PsiTreeUtil.getChildrenOfType(cMinusFile, PsiElement.class);
                if (properties != null) {
                    for (PsiElement property : properties) {
                        String propertyId = "";
                        if(property instanceof CMinusCall){
                            propertyId = ((CMinusCall)property).getCallId();
                        } else if(property instanceof CMinusFunDeclaration){
                            propertyId = ((CMinusFunDeclaration)property).getFunDeclId();
                        } else if(property instanceof CMinusParam){
                            propertyId = ((CMinusParam)property).getParamId();
                        } else if(property instanceof CMinusVar){
                            propertyId = ((CMinusVar)property).getVarId();
                        } else if(property instanceof CMinusVarDeclaration){
                            propertyId = ((CMinusVarDeclaration)property).getVarDeclId();
                        }

                        if(!propertyId.isEmpty()){
                            if (id.equals(propertyId)) {
                                result.add(property);
                            }
                        }

                    }
                }
            }
        }
        return result;
    }

    public static List<PsiElement> findProperties(Project project) {
        List<PsiElement> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CMinusFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CMinusFile cMinusFile = (CMinusFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cMinusFile != null) {
                PsiElement[] properties = PsiTreeUtil.getChildrenOfType(cMinusFile, PsiElement.class);
                if (properties != null) {
                    Collections.addAll(result, properties);
                }
            }
        }
        return result;
    }
}

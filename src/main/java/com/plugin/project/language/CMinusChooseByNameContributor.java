package com.plugin.project.language;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.plugin.project.language.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CMinusChooseByNameContributor implements ChooseByNameContributor {
    @Override
    public String @NotNull [] getNames(Project project, boolean includeNonProjectItems) {
        List<PsiElement> references = CMinusUtil.findReferences(project);
        List<String> names = new ArrayList<>(references.size());
        for (PsiElement reference : references){
            String tempName = "";
            if(reference instanceof CMinusFunDeclaration){
                if(((CMinusFunDeclaration) reference).getFunDeclId() != null && ((CMinusFunDeclaration) reference).getFunDeclId().length() > 0){
                    tempName = ((CMinusFunDeclaration) reference).getFunDeclId();
                }
            } else if(reference instanceof CMinusVarDeclaration){
                if(((CMinusVarDeclaration) reference).getVarDeclId() != null && ((CMinusVarDeclaration) reference).getVarDeclId().length() > 0){
                    tempName = ((CMinusVarDeclaration) reference).getVarDeclId();
                }
            } else if (reference instanceof CMinusConstDeclaration){
                if(((CMinusConstDeclaration) reference).getConstDeclId() != null && ((CMinusConstDeclaration) reference).getConstDeclId().length() > 0){
                    tempName = ((CMinusConstDeclaration) reference).getConstDeclId();
                }
            } else if(reference instanceof CMinusCall){
                if(((CMinusCall) reference).getCallId() != null && ((CMinusCall) reference).getCallId().length() > 0){
                    tempName = ((CMinusCall) reference).getCallId();
                }
            } else if(reference instanceof CMinusVar){
                if(((CMinusVar) reference).getVarId() != null && ((CMinusVar) reference).getVarId().length() > 0){
                    tempName = ((CMinusVar) reference).getVarId();
                }
            } else if(reference instanceof CMinusParam){
                if(((CMinusParam) reference).getParamId() != null && ((CMinusParam) reference).getParamId().length() > 0){
                    tempName = ((CMinusParam) reference).getParamId();
                }
            }

            if(!tempName.isEmpty()){
                names.add(tempName);
            }
        }

        return names.toArray(new String[names.size()]);
    }

    @Override
    public NavigationItem @NotNull [] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        List<PsiElement> references = CMinusUtil.findReferences(project, name);
        return references.toArray(new NavigationItem[references.size()]);
    }
}

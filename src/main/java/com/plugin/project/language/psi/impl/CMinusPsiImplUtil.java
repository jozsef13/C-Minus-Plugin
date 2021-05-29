package com.plugin.project.language.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.plugin.project.language.psi.*;

public class CMinusPsiImplUtil {
    private static String getIdentifier(PsiElement element){
        ASTNode idNode = element.getNode().findChildByType(CMinusTypes.ID);
        if(idNode != null){
            return idNode.getText().replaceAll("\\\\ ", " ");
        } else {
            return null;
        }
    }

    public static String getCallId(CMinusCall callElement){
        return getIdentifier(callElement);
    }

    public static String getFunDeclId(CMinusFunDeclaration funDeclElement){
        return getIdentifier(funDeclElement);
    }

    public static String getParamId(CMinusParam paramElement){
        return getIdentifier(paramElement);
    }

    public static String getVarId(CMinusVar varElement){
        return getIdentifier(varElement);
    }

    public static String getVarDeclId(CMinusVarDeclaration varDeclElement){
        return getIdentifier(varDeclElement);
    }
}

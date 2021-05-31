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

    public static PsiElement setName(PsiElement element, String newName){
        ASTNode keyNode = element.getNode().findChildByType(CMinusTypes.ID);
        if(keyNode != null){
            PsiElement elementReference = null;
            if(element instanceof CMinusVarDeclaration){
                elementReference = CMinusElementFactory.createVariable(element.getProject(), newName);
            } else if(element instanceof CMinusFunDeclaration){
                elementReference = CMinusElementFactory.createFunction(element.getProject(), newName);
            }
            System.out.println("Here");
            if(elementReference != null){
                ASTNode newKeyNode = elementReference.getNode().findChildByType(CMinusTypes.ID);
                if(newKeyNode != null){
                    element.getNode().replaceChild(keyNode, newKeyNode);
                }
            }
        }

        return element;
    }

    public static PsiElement getNameIdentifier(PsiElement element){
        if(element instanceof CMinusVarDeclaration || element instanceof CMinusFunDeclaration){
            ASTNode node = element.getNode().findChildByType(CMinusTypes.ID);
            if(node != null){
                return node.getPsi();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getName(PsiElement element){
        if(element instanceof CMinusVarDeclaration){
            return getVarDeclId((CMinusVarDeclaration) element);
        } else if(element instanceof CMinusFunDeclaration){
            return getFunDeclId((CMinusFunDeclaration) element);
        } else {
            return getIdentifier(element);
        }
    }
}

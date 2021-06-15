package com.plugin.project.language.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.plugin.project.language.CMinusIcons;
import com.plugin.project.language.CMinusLocalReference;
import com.plugin.project.language.psi.*;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CMinusPsiImplUtil {
    private static String getIdentifier(PsiElement element) {
        ASTNode idNode = element.getNode().findChildByType(CMinusTypes.ID);
        if (idNode != null) {
            return idNode.getText().replaceAll("\\\\ ", " ");
        } else {
            return null;
        }
    }

    public static String getCallId(CMinusCall callElement) {
        return getIdentifier(callElement);
    }

    public static String getFunDeclId(CMinusFunDeclaration funDeclElement) {
        return getIdentifier(funDeclElement);
    }

    public static String getParamId(CMinusParam paramElement) {
        return getIdentifier(paramElement);
    }

    public static String getVarId(CMinusVar varElement) {
        return getIdentifier(varElement);
    }

    public static String getVarDeclId(CMinusVarDeclaration varDeclElement) {
        return getIdentifier(varDeclElement);
    }

    public static PsiElement setName(PsiElement element, String newName) {
        ASTNode keyNode = element.getNode().findChildByType(CMinusTypes.ID);
        if (keyNode != null) {
            PsiElement elementReference = null;
            if (element instanceof CMinusVarDeclaration) {
                elementReference = CMinusElementFactory.createVariable(element.getProject(), newName);
            } else if (element instanceof CMinusFunDeclaration) {
                elementReference = CMinusElementFactory.createFunction(element.getProject(), newName);
            } else if (element instanceof CMinusConstDeclaration) {
                elementReference = CMinusElementFactory.createConstant(element.getProject(), newName);
            } else if(element instanceof CMinusVar){
                if(StringUtils.isAllUpperCase(newName)){
                    elementReference = CMinusElementFactory.createConstant(element.getProject(), newName);
                } else {
                    elementReference = CMinusElementFactory.createVariable(element.getProject(), newName);
                }
            } else if(element instanceof CMinusCall){
                elementReference = CMinusElementFactory.createFunction(element.getProject(), newName);
            } else if(element instanceof CMinusParam){
                elementReference = CMinusElementFactory.createParameter(element.getProject(), newName);
            }

            if (elementReference != null) {
                ASTNode newKeyNode = elementReference.getNode().findChildByType(CMinusTypes.ID);
                if (newKeyNode != null) {
                    element.getNode().replaceChild(keyNode, newKeyNode);
                }
            }
        }

        return element;
    }

    public static PsiElement getNameIdentifier(PsiElement element) {
        ASTNode node = element.getNode().findChildByType(CMinusTypes.ID);
        if (node != null) {
            return node.getPsi();
        } else {
            return null;
        }
    }

    public static String getName(PsiElement element) {
        if (element instanceof CMinusVarDeclaration) {
            return getVarDeclId((CMinusVarDeclaration) element);
        } else if (element instanceof CMinusFunDeclaration) {
            return getFunDeclId((CMinusFunDeclaration) element);
        } else if (element instanceof CMinusConstDeclaration) {
            return getConstDeclId((CMinusConstDeclaration) element);
        } else {
            return getIdentifier(element);
        }
    }

    public static String getConstDeclId(CMinusConstDeclaration constantElement) {
        return getIdentifier(constantElement);
    }

    public static ItemPresentation getPresentation(final PsiElement element) {
        return new ItemPresentation() {
            @Override
            public @Nullable String getPresentableText() {
                return getIdentifier(element) + " : " + getTypeSpecifier(element);
            }

            @Override
            public @Nullable String getLocationString() {
                return element.getContainingFile().getName();
            }

            @Override
            public @Nullable Icon getIcon(boolean unused) {
                return CMinusIcons.FILE;
            }
        };
    }

    private static String getTypeSpecifier(PsiElement element) {
        ASTNode idNode = element.getNode().findChildByType(CMinusTypes.TYPE_SPECIFIER);
        if (idNode != null) {
            return idNode.getText().replaceAll("\\\\ ", " ");
        } else if(element instanceof CMinusConstDeclaration){
            return "const";
        } else {
            if(element instanceof CMinusVar){
                return "variable";
            } else if(element instanceof CMinusCall){
                return "function call";
            } else {
                return null;
            }
        }
    }

    public static String getConstantValue(final CMinusConstDeclaration element) {
        if (element.getNode().findChildByType(CMinusTypes.ASSIGN) != null) {
            ASTNode numNode = element.getNode().findChildByType(CMinusTypes.NUM);
            ASTNode stringLiteralNode = element.getNode().findChildByType(CMinusTypes.STRING_LITERAL);

            if (numNode != null) {
                return numNode.getText();
            } else if (stringLiteralNode != null) {
                return stringLiteralNode.getText();
            }
        }

        return null;
    }

    public static PsiReference getReference(PsiElement element){
        if(element instanceof CMinusCall){
            return new CMinusLocalReference(element, new TextRange(0, ((CMinusCall) element).getCallId().length()));
        } else if(element instanceof CMinusVar){
            return new CMinusLocalReference(element, new TextRange(0, ((CMinusVar) element).getVarId().length()));
        } else if(element instanceof CMinusVarDeclaration){
            return new CMinusLocalReference(element, new TextRange(0, ((CMinusVarDeclaration) element).getVarDeclId().length()));
        } else if(element instanceof CMinusFunDeclaration){
            return new CMinusLocalReference(element, new TextRange(0, ((CMinusFunDeclaration) element).getFunDeclId().length()));
        } else if(element instanceof CMinusConstDeclaration){
            return new CMinusLocalReference(element, new TextRange(0, ((CMinusConstDeclaration) element).getConstDeclId().length()));
        } else if(element instanceof CMinusParam){
            return new CMinusLocalReference(element, new TextRange(0, ((CMinusParam) element).getParamId().length()));
        }

        return null;
    }
}

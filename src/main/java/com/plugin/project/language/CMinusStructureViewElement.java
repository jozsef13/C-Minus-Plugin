package com.plugin.project.language;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.plugin.project.language.psi.CMinusConstDeclaration;
import com.plugin.project.language.psi.CMinusFile;
import com.plugin.project.language.psi.CMinusFunDeclaration;
import com.plugin.project.language.psi.CMinusVarDeclaration;
import com.plugin.project.language.psi.impl.CMinusConstDeclarationImpl;
import com.plugin.project.language.psi.impl.CMinusFunDeclarationImpl;
import com.plugin.project.language.psi.impl.CMinusVarDeclarationImpl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CMinusStructureViewElement implements StructureViewTreeElement, SortableTreeElement {

    private final NavigatablePsiElement myElement;

    public CMinusStructureViewElement(@NotNull NavigatablePsiElement element) {
        this.myElement = element;
    }

    @Override
    public Object getValue() {
        return myElement;
    }

    @Override
    public @NotNull String getAlphaSortKey() {
        String name = myElement.getName();
        return name != null ? name : "";
    }

    @Override
    public @NotNull ItemPresentation getPresentation() {
        ItemPresentation presentation = myElement.getPresentation();
        return presentation != null ? presentation : new PresentationData();
    }

    @Override
    public TreeElement @NotNull [] getChildren() {
        if(myElement instanceof CMinusFile){
            List<CMinusFunDeclaration> functionReferences = new ArrayList<>(PsiTreeUtil.findChildrenOfType(myElement, CMinusFunDeclaration.class));
            List<CMinusVarDeclaration> variableReferences = new ArrayList<>(PsiTreeUtil.findChildrenOfType(myElement, CMinusVarDeclaration.class));
            List<CMinusConstDeclaration> constantReferences = new ArrayList<>(PsiTreeUtil.findChildrenOfType(myElement, CMinusConstDeclaration.class));
            List<TreeElement> treeElements = new ArrayList<>(functionReferences.size() + variableReferences.size() + constantReferences.size());

            for (CMinusFunDeclaration functionReference : functionReferences){
                treeElements.add(new CMinusStructureViewElement((CMinusFunDeclarationImpl) functionReference));
            }

            for (CMinusVarDeclaration variableReference: variableReferences){
                treeElements.add(new CMinusStructureViewElement((CMinusVarDeclarationImpl) variableReference));
            }

            for (CMinusConstDeclaration constantReference : constantReferences){
                treeElements.add(new CMinusStructureViewElement((CMinusConstDeclarationImpl) constantReference));
            }

            return treeElements.toArray(new TreeElement[treeElements.size()]);
        }
        return EMPTY_ARRAY;
    }

    @Override
    public void navigate(boolean requestFocus) {
        myElement.navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return myElement.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return myElement.canNavigateToSource();
    }
}

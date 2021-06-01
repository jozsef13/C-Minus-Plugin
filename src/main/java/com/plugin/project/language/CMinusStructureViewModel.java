package com.plugin.project.language;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.psi.PsiFile;
import com.plugin.project.language.psi.CMinusFile;
import org.jetbrains.annotations.NotNull;

public class CMinusStructureViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {

    public CMinusStructureViewModel(@NotNull PsiFile psiFile) {
        super(psiFile, new CMinusStructureViewElement(psiFile));
    }

    @Override
    public Sorter @NotNull [] getSorters() {
        return new Sorter[]{Sorter.ALPHA_SORTER};
    }

    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        return element instanceof CMinusFile;
    }
}

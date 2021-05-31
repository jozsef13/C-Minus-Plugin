package com.plugin.project.language;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.plugin.project.language.psi.CMinusFunDeclaration;
import com.plugin.project.language.psi.CMinusVarDeclaration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CMinusReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String id;

    public CMinusReference(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
        id = element.getText().substring(rangeInElement.getStartOffset(), rangeInElement.getEndOffset());
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        final List<PsiElement> references = CMinusUtil.findReferences(project, id);
        List<ResolveResult> results = new ArrayList<>();
        for(PsiElement reference : references){
            results.add(new PsiElementResolveResult(reference));
        }

        return results.toArray(new ResolveResult[results.size()]);
    }

    @Override
    public @Nullable PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        Project project = myElement.getProject();
        List<PsiElement> references = CMinusUtil.findReferences(project);
        List<LookupElement> variants = new ArrayList<>();
        for(final PsiElement reference : references){
            if(reference instanceof CMinusFunDeclaration){
                if(((CMinusFunDeclaration) reference).getFunDeclId() != null && ((CMinusFunDeclaration) reference).getFunDeclId().length() > 0){
                    variants.add(LookupElementBuilder.create(((CMinusFunDeclaration) reference).getFunDeclId()).withIcon(CMinusIcons.FILE).withTypeText(reference.getContainingFile().getName() + " - function"));
                }
            } else if(reference instanceof CMinusVarDeclaration){
                if(((CMinusVarDeclaration) reference).getVarDeclId() != null && ((CMinusVarDeclaration) reference).getVarDeclId().length() > 0){
                    variants.add(LookupElementBuilder.create(((CMinusVarDeclaration) reference).getVarDeclId()).withIcon(CMinusIcons.FILE).withTypeText(reference.getContainingFile().getName() + " - variable"));
                }
            }
        }
        return variants.toArray();
    }
}

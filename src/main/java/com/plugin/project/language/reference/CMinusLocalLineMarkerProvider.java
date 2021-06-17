package com.plugin.project.language.reference;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.plugin.project.language.CMinusIcons;
import com.plugin.project.language.util.CMinusUtil;
import com.plugin.project.language.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class CMinusLocalLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        if (!(element.getParent() instanceof CMinusCall) && !(element.getParent() instanceof CMinusVar)) {
            return;
        }

        if (element instanceof CMinusArgs || element.getText().equals("(") || element.getText().equals(")") || element instanceof PsiWhiteSpace) {
            return;
        }

        String possibleReferences = "";

        if (element.getParent() instanceof CMinusCall) {
            possibleReferences = ((CMinusCall) element.getParent()).getCallId();
        } else if (element.getParent() instanceof CMinusVar) {
            possibleReferences = ((CMinusVar) element.getParent()).getVarId();
        }

        if (!possibleReferences.isEmpty()) {
            CMinusFile file = (CMinusFile) element.getContainingFile();
            final List<CMinusFunDeclaration> functionReferences = CMinusUtil.findLocalFunctionReferences(file, possibleReferences);
            if (functionReferences.size() > 0) {
                NavigationGutterIconBuilder<PsiElement> builder =
                        NavigationGutterIconBuilder.create(CMinusIcons.USAGE)
                                .setTargets(functionReferences)
                                .setTooltipText("Navigate to CMinus language function reference");

                result.add(builder.createLineMarkerInfo(element));

            }

            final List<CMinusVarDeclaration> variableReferences = CMinusUtil.findLocalVariableReferences(file, possibleReferences);
            if (variableReferences.size() > 0) {
                NavigationGutterIconBuilder<PsiElement> builder =
                        NavigationGutterIconBuilder.create(CMinusIcons.USAGE)
                                .setTargets(variableReferences)
                                .setTooltipText("Navigate to CMinus language variable reference");

                result.add(builder.createLineMarkerInfo(element));
            }

            final List<CMinusConstDeclaration> constReferences = CMinusUtil.findLocalConstantReferences(file, possibleReferences);
            if (constReferences.size() > 0) {
                NavigationGutterIconBuilder<PsiElement> builder =
                        NavigationGutterIconBuilder.create(CMinusIcons.USAGE)
                                .setTargets(constReferences)
                                .setTooltipText("Navigate to CMinus language constant reference");

                result.add(builder.createLineMarkerInfo(element));
            }

            final List<CMinusParam> paramReference = CMinusUtil.findLocalParamReferences(file, possibleReferences);
            if (paramReference.size() > 0) {
                NavigationGutterIconBuilder<PsiElement> builder =
                        NavigationGutterIconBuilder.create(CMinusIcons.USAGE)
                                .setTargets(paramReference)
                                .setTooltipText("Navigate to CMinus language parameter reference");

                result.add(builder.createLineMarkerInfo(element));
            }
        }
    }
}

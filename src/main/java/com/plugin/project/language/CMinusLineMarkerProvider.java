package com.plugin.project.language;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.impl.source.tree.java.PsiJavaTokenImpl;
import com.plugin.project.language.psi.CMinusFunDeclaration;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class CMinusLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        if (!(element instanceof PsiJavaTokenImpl) || !(element.getParent() instanceof PsiLiteralExpression)) {
            return;
        }

        PsiLiteralExpression literalExpression = (PsiLiteralExpression) element.getParent();
        String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
        if ((value == null) ||
                !value.startsWith(CMinusAnnotator.CMINUS_PREFIX_STR + CMinusAnnotator.CMINUS_SEPARATOR_STR)) {
            return;
        }

        Project project = element.getProject();
        String possibleFunctionReferences = value.substring(
                CMinusAnnotator.CMINUS_PREFIX_STR.length() + CMinusAnnotator.CMINUS_SEPARATOR_STR.length()
        );
        final List<CMinusFunDeclaration> functionReferences = CMinusUtil.findFunctionReferences(project, possibleFunctionReferences);
        if (functionReferences.size() > 0) {
            // Add the property to a collection of line marker info
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(CMinusIcons.FILE)
                            .setTargets(functionReferences)
                            .setTooltipText("Navigate to CMinus language function reference");
            result.add(builder.createLineMarkerInfo(element));
        }
    }
}

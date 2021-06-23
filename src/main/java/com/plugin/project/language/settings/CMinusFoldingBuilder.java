package com.plugin.project.language.settings;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.plugin.project.language.highlighter.CMinusAnnotator;
import com.plugin.project.language.util.CMinusUtil;
import com.plugin.project.language.psi.CMinusConstDeclaration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CMinusFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        FoldingGroup group = FoldingGroup.newGroup(CMinusAnnotator.CMINUS_PREFIX_STR);
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        Collection<PsiLiteralExpression> literalExpressions = PsiTreeUtil.findChildrenOfType(root, PsiLiteralExpression.class);

        for (final PsiLiteralExpression literalExpression : literalExpressions){
            String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
            if(value != null && value.startsWith(CMinusAnnotator.CMINUS_PREFIX_STR + CMinusAnnotator.CMINUS_SEPARATOR_STR)){
                Project project = literalExpression.getProject();
                String key = value.substring(CMinusAnnotator.CMINUS_PREFIX_STR.length() + CMinusAnnotator.CMINUS_SEPARATOR_STR.length());
                final List<CMinusConstDeclaration> constants = CMinusUtil.findConstantReferences(project, key);
                if(constants.size() == 1){
                    descriptors.add(new FoldingDescriptor(literalExpression.getNode(), new TextRange(literalExpression.getTextRange().getStartOffset()+1, literalExpression.getTextRange().getEndOffset()-1), group));
                }
            }
        }
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    @Override
    public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
        String retText = "...";
        if (node.getPsi() instanceof PsiLiteralExpression){
            PsiLiteralExpression nodeElement = (PsiLiteralExpression) node.getPsi();
            String key = ((String)nodeElement.getValue()).substring(CMinusAnnotator.CMINUS_PREFIX_STR.length() + CMinusAnnotator.CMINUS_SEPARATOR_STR.length());
            final List<CMinusConstDeclaration> constants = CMinusUtil.findConstantReferences(nodeElement.getProject(), key);
            String place = constants.get(0).getConstantValue();

            return place == null ? retText : place.replaceAll("\n", "\\n").replaceAll("\"", "\\\\\"");
        }
        return retText;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return true;
    }
}

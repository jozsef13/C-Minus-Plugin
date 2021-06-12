package com.plugin.project.language;

import com.intellij.application.options.CodeStyle;
import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.IElementType;
import com.plugin.project.language.psi.CMinusTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CMinusBlock implements Block {

    private final ASTNode myNode;
    private List<Block> myBlocks;
    private final SpacingBuilder spacingBuilder;

    public CMinusBlock(PsiElement psiElement, SpacingBuilder builder) {
        spacingBuilder = builder;
        myNode = psiElement.getNode();
        psiElement.getFirstChild();
    }


    protected List<Block> buildChildren() {
        List<Block> result = new ArrayList<>();

        myNode.getPsi().acceptChildren(new PsiElementVisitor() {
            @Override
            public void visitElement(@NotNull PsiElement element) {
                final ASTNode node = element.getNode();

                if(node != null){
                    final IElementType nodeType = node.getElementType();
                    if(nodeType == CMinusTypes.LEFT_BRACE || nodeType == CMinusTypes.RIGHT_BRACE || nodeType == CMinusTypes.STATEMENT){
                        final CMinusBlock block = new CMinusBlock(element, spacingBuilder);
                        result.add(block);
                    }
                }

                super.visitElement(element);
            }
        });

        return result;
    }

    @Override
    public Indent getIndent() {
        final IElementType type = myNode.getElementType();
        return Indent.getNormalIndent(true);
    }

    @Override
    public @Nullable Spacing getSpacing(@Nullable Block block1, @NotNull Block block2) {
        if (block1 != null) {
            final IElementType type = ((CMinusBlock) block1).myNode.getElementType();
            final IElementType type1 = ((CMinusBlock) block2).myNode.getElementType();

            if(type == CMinusTypes.LEFT_BRACE || type1 == CMinusTypes.RIGHT_BRACE){
                CodeStyleSettings mySettings = CodeStyle.getSettings(myNode.getPsi().getProject());
                return Spacing.createSpacing(0, 0, 1, true, 2);
            }
        }
        return spacingBuilder.getSpacing(this, block1, block2);
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }

    @Override
    public @NotNull ChildAttributes getChildAttributes(int newChildIndex) {
        if(myNode.getElementType() == CMinusTypes.STATEMENT || myNode.getElementType() == CMinusTypes.LEFT_BRACE || myNode.getElementType() == CMinusTypes.RIGHT_BRACE){
            return new ChildAttributes(Indent.getNormalIndent(), null);
        }
        return new ChildAttributes(Indent.getNoneIndent(), null);
    }

    public boolean isIncomplete(){
        return false;
    }

    @Override
    public @NotNull TextRange getTextRange() {
        return myNode.getTextRange();
    }

    @Override
    public @NotNull List<Block> getSubBlocks() {
        if(myBlocks == null){
            myBlocks = buildChildren();
        }

        return myBlocks;
    }

    @Override
    public @Nullable Wrap getWrap() {
        return null;
    }

    @Nullable
    public Alignment getAlignment() {
        return null;
    }
}

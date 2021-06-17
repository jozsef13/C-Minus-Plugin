package com.plugin.project.language.formatting;

import com.intellij.application.options.CodeStyle;
import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import com.plugin.project.language.psi.CMinusTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CMinusBlock extends AbstractBlock {

    private final ASTNode myNode;
    private List<Block> myBlocks;
    private final SpacingBuilder spacingBuilder;

    public CMinusBlock(PsiElement psiElement, SpacingBuilder builder) {
        super(psiElement.getNode(), Wrap.createWrap(WrapType.NONE, false), Alignment.createAlignment());
        spacingBuilder = builder;
        myNode = psiElement.getNode();
        psiElement.getFirstChild();
    }


    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<>();
        ASTNode child = myNode.getFirstChildNode();
        while(child != null){
            if(child.getElementType() != TokenType.WHITE_SPACE){
                Block block = new CMinusBlock(child.getPsi(), spacingBuilder);
                blocks.add(block);
            }

            child = child.getTreeNext();
        }

        return blocks;
    }

    @Override
    public Indent getIndent() {
        if(myNode != null){
            final IElementType type = myNode.getElementType();
            boolean isParentLocalDecl = false;
            if(myNode.getPsi() != null && myNode.getPsi().getParent() != null && myNode.getPsi().getParent().getNode() != null
                    && myNode.getPsi().getParent().getNode().getElementType() != null){
                final IElementType parentType = myNode.getPsi().getParent().getNode().getElementType();
                isParentLocalDecl = parentType == CMinusTypes.LOCAL_DECLARATIONS;
            }

            if(type == CMinusTypes.STATEMENT || (type == CMinusTypes.VAR_DECLARATION && isParentLocalDecl)){
                return Indent.getNormalIndent(true);
            }
        }


        return Indent.getNoneIndent();
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
        if(myNode.getElementType() == CMinusTypes.STATEMENT || myNode.getElementType() == CMinusTypes.VAR_DECLARATION){
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
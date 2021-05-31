// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.plugin.project.language.psi.CMinusTypes.*;
import com.plugin.project.language.psi.*;

public class CMinusVarDeclarationImpl extends CMinusNamedElementImpl implements CMinusVarDeclaration {

  public CMinusVarDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CMinusVisitor visitor) {
    visitor.visitVarDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CMinusVisitor) accept((CMinusVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CMinusTypeSpecifier getTypeSpecifier() {
    return findNotNullChildByClass(CMinusTypeSpecifier.class);
  }

  @Override
  @NotNull
  public PsiElement getId() {
    return findNotNullChildByType(ID);
  }

  @Override
  @Nullable
  public PsiElement getNum() {
    return findChildByType(NUM);
  }

  @Override
  public String getVarDeclId() {
    return CMinusPsiImplUtil.getVarDeclId(this);
  }

  @Override
  public String getName() {
    return CMinusPsiImplUtil.getName(this);
  }

  @Override
  public PsiElement setName(String newName) {
    return CMinusPsiImplUtil.setName(this, newName);
  }

  @Override
  public PsiElement getNameIdentifier() {
    return CMinusPsiImplUtil.getNameIdentifier(this);
  }

}

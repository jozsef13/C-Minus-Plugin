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
import com.intellij.navigation.ItemPresentation;

public class CMinusConstDeclarationImpl extends CMinusNamedElementImpl implements CMinusConstDeclaration {

  public CMinusConstDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CMinusVisitor visitor) {
    visitor.visitConstDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CMinusVisitor) accept((CMinusVisitor)visitor);
    else super.accept(visitor);
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
  @Nullable
  public PsiElement getStringLiteral() {
    return findChildByType(STRING_LITERAL);
  }

  @Override
  public String getConstDeclId() {
    return CMinusPsiImplUtil.getConstDeclId(this);
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

  @Override
  public ItemPresentation getPresentation() {
    return CMinusPsiImplUtil.getPresentation(this);
  }

  @Override
  public String getConstantValue() {
    return CMinusPsiImplUtil.getConstantValue(this);
  }

}

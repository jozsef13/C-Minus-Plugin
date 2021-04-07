// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.plugin.project.language.psi.CMinusTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.plugin.project.language.psi.*;

public class CMinusFactorImpl extends ASTWrapperPsiElement implements CMinusFactor {

  public CMinusFactorImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CMinusVisitor visitor) {
    visitor.visitFactor(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CMinusVisitor) accept((CMinusVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CMinusCall getCall() {
    return findChildByClass(CMinusCall.class);
  }

  @Override
  @Nullable
  public CMinusExpression getExpression() {
    return findChildByClass(CMinusExpression.class);
  }

  @Override
  @Nullable
  public CMinusVar getVar() {
    return findChildByClass(CMinusVar.class);
  }

  @Override
  @Nullable
  public PsiElement getNum() {
    return findChildByType(NUM);
  }

}

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

public class CMinusAdditiveExpressionImpl extends ASTWrapperPsiElement implements CMinusAdditiveExpression {

  public CMinusAdditiveExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CMinusVisitor visitor) {
    visitor.visitAdditiveExpression(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CMinusVisitor) accept((CMinusVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CMinusAddOperation getAddOperation() {
    return findChildByClass(CMinusAddOperation.class);
  }

  @Override
  @Nullable
  public CMinusAdditiveExpression getAdditiveExpression() {
    return findChildByClass(CMinusAdditiveExpression.class);
  }

  @Override
  @NotNull
  public CMinusTerm getTerm() {
    return findNotNullChildByClass(CMinusTerm.class);
  }

}

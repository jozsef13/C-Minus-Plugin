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

public class CMinusTermImpl extends ASTWrapperPsiElement implements CMinusTerm {

  public CMinusTermImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CMinusVisitor visitor) {
    visitor.visitTerm(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CMinusVisitor) accept((CMinusVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CMinusFactor getFactor() {
    return findNotNullChildByClass(CMinusFactor.class);
  }

  @Override
  @Nullable
  public CMinusMulOperation getMulOperation() {
    return findChildByClass(CMinusMulOperation.class);
  }

  @Override
  @Nullable
  public CMinusTerm getTerm() {
    return findChildByClass(CMinusTerm.class);
  }

}

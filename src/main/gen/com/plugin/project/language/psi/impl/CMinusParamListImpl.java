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

public class CMinusParamListImpl extends ASTWrapperPsiElement implements CMinusParamList {

  public CMinusParamListImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CMinusVisitor visitor) {
    visitor.visitParamList(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CMinusVisitor) accept((CMinusVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CMinusParam getParam() {
    return findNotNullChildByClass(CMinusParam.class);
  }

  @Override
  @NotNull
  public CMinusParamList1 getParamList1() {
    return findNotNullChildByClass(CMinusParamList1.class);
  }

}

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

public class CMinusStatementImpl extends ASTWrapperPsiElement implements CMinusStatement {

  public CMinusStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CMinusVisitor visitor) {
    visitor.visitStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CMinusVisitor) accept((CMinusVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CMinusCompoundStmt getCompoundStmt() {
    return findChildByClass(CMinusCompoundStmt.class);
  }

  @Override
  @Nullable
  public CMinusExpressionStmt getExpressionStmt() {
    return findChildByClass(CMinusExpressionStmt.class);
  }

  @Override
  @Nullable
  public CMinusIterationStmt getIterationStmt() {
    return findChildByClass(CMinusIterationStmt.class);
  }

  @Override
  @Nullable
  public CMinusReadStatement getReadStatement() {
    return findChildByClass(CMinusReadStatement.class);
  }

  @Override
  @Nullable
  public CMinusReturnStmt getReturnStmt() {
    return findChildByClass(CMinusReturnStmt.class);
  }

  @Override
  @Nullable
  public CMinusSelectionStmt getSelectionStmt() {
    return findChildByClass(CMinusSelectionStmt.class);
  }

  @Override
  @Nullable
  public CMinusWriteStatement getWriteStatement() {
    return findChildByClass(CMinusWriteStatement.class);
  }

  @Override
  @Nullable
  public PsiElement getComment() {
    return findChildByType(COMMENT);
  }

}

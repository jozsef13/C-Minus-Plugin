// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CMinusStatement extends PsiElement {

  @Nullable
  CMinusCompoundStmt getCompoundStmt();

  @Nullable
  CMinusExpressionStmt getExpressionStmt();

  @Nullable
  CMinusIterationStmt getIterationStmt();

  @Nullable
  CMinusReadStatement getReadStatement();

  @Nullable
  CMinusReturnStmt getReturnStmt();

  @Nullable
  CMinusSelectionStmt getSelectionStmt();

  @Nullable
  CMinusWriteStatement getWriteStatement();

  @Nullable
  PsiElement getComment();

}

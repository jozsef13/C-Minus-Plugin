// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CMinusFactor extends PsiElement {

  @Nullable
  CMinusCall getCall();

  @Nullable
  CMinusExpression getExpression();

  @Nullable
  CMinusVar getVar();

  @Nullable
  PsiElement getNum();

  @Nullable
  PsiElement getStringLiteral();

}

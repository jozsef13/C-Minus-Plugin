// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CMinusFunDeclaration extends PsiElement {

  @NotNull
  CMinusCompoundStmt getCompoundStmt();

  @Nullable
  CMinusParams getParams();

  @NotNull
  CMinusTypeSpecifier getTypeSpecifier();

  @NotNull
  PsiElement getId();

  String getFunDeclId();

}

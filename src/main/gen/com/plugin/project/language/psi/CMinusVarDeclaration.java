// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CMinusVarDeclaration extends CMinusNamedElement {

  @NotNull
  CMinusTypeSpecifier getTypeSpecifier();

  @NotNull
  PsiElement getId();

  @Nullable
  PsiElement getNum();

  String getVarDeclId();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

  //WARNING: getPresentation(...) is skipped
  //matching getPresentation(CMinusVarDeclaration, ...)
  //methods are not found in CMinusPsiImplUtil

}

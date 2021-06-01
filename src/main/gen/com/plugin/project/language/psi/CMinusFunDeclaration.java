// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.navigation.ItemPresentation;

public interface CMinusFunDeclaration extends CMinusNamedElement {

  @NotNull
  CMinusCompoundStmt getCompoundStmt();

  @Nullable
  CMinusParams getParams();

  @NotNull
  CMinusTypeSpecifier getTypeSpecifier();

  @NotNull
  PsiElement getId();

  String getFunDeclId();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

}

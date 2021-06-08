// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.navigation.ItemPresentation;

public interface CMinusVar extends CMinusNamedElement {

  @Nullable
  CMinusExpression getExpression();

  @NotNull
  PsiElement getId();

  String getVarId();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

}

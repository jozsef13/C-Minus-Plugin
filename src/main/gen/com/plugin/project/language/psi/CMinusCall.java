// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

public interface CMinusCall extends PsiElement {

  @Nullable
  CMinusArgs getArgs();

  @NotNull
  PsiElement getId();

  String getCallId();

  ItemPresentation getPresentation();

  PsiReference getReference();

  PsiElement setName(String newName);

}

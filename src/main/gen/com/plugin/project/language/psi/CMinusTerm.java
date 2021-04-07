// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CMinusTerm extends PsiElement {

  @NotNull
  CMinusFactor getFactor();

  @Nullable
  CMinusMulOperation getMulOperation();

  @Nullable
  CMinusTerm getTerm();

}

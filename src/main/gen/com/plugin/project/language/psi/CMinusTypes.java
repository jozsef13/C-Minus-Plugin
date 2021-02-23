// This is a generated file. Not intended for manual editing.
package com.plugin.project.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.plugin.project.language.psi.impl.*;

public interface CMinusTypes {

  IElementType PROPERTY = new CMinusElementType("PROPERTY");

  IElementType COMMENT = new CMinusTokenType("COMMENT");
  IElementType CRLF = new CMinusTokenType("CRLF");
  IElementType KEY = new CMinusTokenType("KEY");
  IElementType SEPARATOR = new CMinusTokenType("SEPARATOR");
  IElementType VALUE = new CMinusTokenType("VALUE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == PROPERTY) {
        return new CMinusPropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

package com.plugin.project.language;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class CMinusColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Separator", CMinusSyntaxHighlighter.SEPARATOR),
            new AttributesDescriptor("Key", CMinusSyntaxHighlighter.KEY),
            new AttributesDescriptor("Value", CMinusSyntaxHighlighter.VALUE),
            new AttributesDescriptor("Comment", CMinusSyntaxHighlighter.COMMENT),
            new AttributesDescriptor("Bad Character", CMinusSyntaxHighlighter.BAD_CHARACTER),
            new AttributesDescriptor("Brace", CMinusSyntaxHighlighter.BRACES),
            new AttributesDescriptor("Bracket", CMinusSyntaxHighlighter.BRACKETS),
            new AttributesDescriptor("Comma", CMinusSyntaxHighlighter.COMMA),
            new AttributesDescriptor("Identifier", CMinusSyntaxHighlighter.IDENTIFIER),
            new AttributesDescriptor("Number", CMinusSyntaxHighlighter.NUMBER),
            new AttributesDescriptor("Parentheses", CMinusSyntaxHighlighter.PARENTHESES),
            new AttributesDescriptor("Semicolon", CMinusSyntaxHighlighter.SEMICOLON)
    };

    @Override
    public @Nullable Icon getIcon() {
        return CMinusIcons.FILE;
    }

    @Override
    public @NotNull SyntaxHighlighter getHighlighter() {
        return new CMinusSyntaxHighlighter();
    }

    @Override
    public @NotNull String getDemoText() {
        return "int gcd ( int u, int v )\n" +
                "{\n" +
                "if ( v == 0 )\n" +
                "return u ;\n" +
                "else\n" +
                "return gcd (v, u-u/v*v) ;\n" +
                "/* u-u/v*v == u mod v */\n" +
                "}\n" +
                "void main(void)\n" +
                "{\n" +
                "int x;\n" +
                "int y;\n" +
                "x = input();\n" +
                "y = input();\n" +
                "output(gcd(x,y));\n" +
                "}";
    }

    @Override
    public @Nullable Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @Override
    public AttributesDescriptor @NotNull [] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @Override
    public ColorDescriptor @NotNull [] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @Override
    public @NotNull @NlsContexts.ConfigurableName String getDisplayName() {
        return "CMinus";
    }
}

package com.plugin.project.language;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import com.plugin.project.language.psi.CMinusTypes;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CMinusCompletionContributor extends CompletionContributor {

    public CMinusCompletionContributor() {

//        extend(CompletionType.BASIC, PlatformPatterns.psiElement().withElementType(CMinusTypes.CIN),
//                new CompletionProvider<CompletionParameters>() {
//                    @Override
//                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
//                        result.addElement(LookupElementBuilder.create(CMinusTypes.READ.toString()));
//                    }
//                });
//
//        extend(CompletionType.BASIC, PlatformPatterns.psiElement().withElementType(CMinusTypes.COUT),
//                new CompletionProvider<CompletionParameters>() {
//                    @Override
//                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
//                        result.addElement(LookupElementBuilder.create(CMinusTypes.WRITE.toString()));
//                    }
//                });
//
//        extend(CompletionType.BASIC, PlatformPatterns.psiElement().withElementType(CMinusTypes.IF),
//                new CompletionProvider<CompletionParameters>() {
//                    @Override
//                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
//                        result.addElement(LookupElementBuilder.create(CMinusTypes.LEFT_PARANTHESIS.toString()));
//                        result.addElement(LookupElementBuilder.create(CMinusTypes.RIGHT_PARANTHESIS.toString()));
//                        result.addElement(LookupElementBuilder.create(CMinusTypes.LEFT_BRACE.toString()));
//                        result.addElement(LookupElementBuilder.create(CMinusTypes.RIGHT_BRACE.toString()));
//                    }
//                });
//
//        extend(CompletionType.BASIC, PlatformPatterns.psiElement().withElementType(CMinusTypes.WHILE),
//                new CompletionProvider<CompletionParameters>() {
//                    @Override
//                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
//                        result.addElement(LookupElementBuilder.create(CMinusTypes.LEFT_PARANTHESIS.toString()));
//                        result.addElement(LookupElementBuilder.create(CMinusTypes.RIGHT_PARANTHESIS.toString()));
//                        result.addElement(LookupElementBuilder.create(CMinusTypes.LEFT_BRACE.toString()));
//                        result.addElement(LookupElementBuilder.create(CMinusTypes.RIGHT_BRACE.toString()));
//                    }
//                });


    }

}

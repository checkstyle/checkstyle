package com.puppycrawl.tools.checkstyle.grammar;

import java.util.ArrayDeque;
import java.util.Deque;

import org.antlr.v4.runtime.Lexer;

public class CompositeLexerContextCache {

    private final Deque<Integer> curlyBracketCounterStack;
    private int stringTemplateDepth;
    private final Lexer lexer;

    public CompositeLexerContextCache(
        Lexer lexer
    ) {
        curlyBracketCounterStack = new ArrayDeque<>();
        stringTemplateDepth = 0;
        this.lexer = lexer;
    }

    public void updateLeftCurlyBraceContext() {
        if (isInStringTemplateContext()) {
            incrementBracketCounter();
        }
    }

    public void updateRightCurlyBraceContext(int mode) {
        if (isInStringTemplateContext()) {
            if (curlyBracketCounterStack.isEmpty()) {
                pushToModeStack(mode);
            }
            else {
                final int bracketCounter = curlyBracketCounterStack.pop();
                if (bracketCounter == 0) {
                    pushToModeStack(mode);
                }
                else {
                    curlyBracketCounterStack.push(bracketCounter - 1);
                }
            }
        }
    }

    public void enterStringTemplateContext() {
        stringTemplateDepth++;
        curlyBracketCounterStack.push(0);
    }
    public void exitStringTemplateContext() {
        stringTemplateDepth--;
    }

    private void pushToModeStack(int mode) {
        lexer.more();
        lexer.pushMode(mode);
    }

    private void incrementBracketCounter() {
        final int bracketCounter = curlyBracketCounterStack.pop();
        curlyBracketCounterStack.push(bracketCounter + 1);
    }

    private boolean isInStringTemplateContext() {
        return stringTemplateDepth > 0;
    }




}
